package frc.robot.commands.basic;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Constants;
import frc.robot.subsystems.Drive;
import frc.robot.tools.math.Point;
import frc.robot.tools.pathing.Odometry;

public class PurePursuit extends CommandBase {

    private class PathRunnable implements Runnable {
        public void run() {
            findClosestPoint();
            setWheelVelocities(
                    trajectory.getStates().get(closestSegment).velocityMetersPerSecond,
                    trajectory.getStates().get(closestSegment).curvatureRadPerMeter);
        }
    }

    private int closestSegment;
    private int startingNumber;
    private double dx, dy;
    private double distToPoint;
    private double minDistToPoint;
    private Point closestPoint;
    private Drive drive;
    private Odometry odometry;
    private Trajectory trajectory;
    private Notifier notifier = new Notifier(new PathRunnable());

    public PurePursuit(
            Drive drive, Odometry odometry, double lookaheadDistance, Trajectory trajectory) {
        this.drive = drive;
        this.odometry = odometry;
        this.trajectory = trajectory;
        addRequirements(this.drive);
    }

    @Override
    public void initialize() {
        odometry.setX(trajectory.getStates().get(0).poseMeters.getX());
        odometry.setY(trajectory.getStates().get(0).poseMeters.getY());
        closestPoint = new Point(0, 0);
        closestSegment = 0;
        minDistToPoint = 10000;
        startingNumber = 1;
        distToPoint = 0;
        dx = 0;
        dy = 0;
        notifier.startPeriodic(0.02);
    }

    private void findClosestPoint() {
        for (int i = startingNumber; i < trajectory.getStates().size() - 1; i++) {
            dx = trajectory.getStates().get(i).poseMeters.getTranslation().getX() - odometry.getX();
            dy = trajectory.getStates().get(i).poseMeters.getTranslation().getY() - odometry.getY();
            distToPoint = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
            if (distToPoint < minDistToPoint) {
                minDistToPoint = distToPoint;
                closestSegment = i;
                closestPoint.setLocation(
                        trajectory.getStates().get(i).poseMeters.getTranslation().getX(),
                        trajectory.getStates().get(i).poseMeters.getTranslation().getY());
            }
        }
        startingNumber = closestSegment;
        minDistToPoint = 1000;
    }

    private void setWheelVelocities(double targetVelocity, double curvature) {
        double leftVelocity, rightVelocity;
        double v;
        if (closestSegment < 3) {
            v = targetVelocity + 0.6;
        } else {
            v = targetVelocity;
        }
        leftVelocity = (double) v * (2 + curvature * Constants.DRIVE_WHEEL_BASE) / 2;
        rightVelocity = (double) v * (2 - curvature * Constants.DRIVE_WHEEL_BASE) / 2;
        drive.setLeftSpeed(leftVelocity);
        drive.setRightSpeed(rightVelocity);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {
        notifier.stop();
        drive.setLeftPercent(0);
        drive.setRightPercent(0);
    }

    @Override
    public boolean isFinished() {
        return trajectory.getStates().size() - closestSegment < 5;
    }
}
