package frc.robot.commands.basic;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Constants;
import frc.robot.subsystems.Drive;
import frc.robot.tools.math.Point;
import frc.robot.tools.math.Vector;
import frc.robot.tools.pathing.Odometry;

public class PurePursuit extends CommandBase {

    private class PathRunnable implements Runnable {
        public void run() {
            findClosestPoint();
            findLookaheadPoint();
            updateDistToEnd();
            move();
        }
    }

    private int closestSegment;
    private Point lookaheadPoint;
    private Point lastLookaheadPoint;
    private int startingNumber;
    private double dx, dy;
    private double distToPoint;
    private double minDistToPoint;
    private Point closestPoint;
    private double lookaheadDistance;
    private Point startingPointOfLineSegment;
    private boolean firstLookaheadFound;
    private int startingNumberLA;
    private Vector lineSegmentVector;
    private Point endPointOfLineSegment;
    private Point robotPosition;
    private Vector robotPositionVector;
    private double lookaheadIndexT1;
    private double lookaheadIndexT2;
    private double partialPointIndex;
    private double lastPointIndex;
    private Vector distToEndVector;
    private Drive drive;
    private Odometry odometry;
    private Trajectory trajectory;
    private Notifier notifier = new Notifier(new PathRunnable());

    public PurePursuit(
            Drive drive, Odometry odometry, double lookaheadDistance, Trajectory trajectory) {
        this.lookaheadDistance = lookaheadDistance;
        this.drive = drive;
        this.odometry = odometry;
        this.trajectory = trajectory;
        addRequirements(this.drive);
    }

    @Override
    public void initialize() {
        odometry.setX(trajectory.getStates().get(0).poseMeters.getX());
        odometry.setY(trajectory.getStates().get(0).poseMeters.getY());
        distToEndVector = new Vector(12, 12);
        lookaheadPoint = new Point(0, 0);
        closestPoint = new Point(0, 0);
        robotPosition = new Point(0, 0);
        startingPointOfLineSegment = new Point(0, 0);
        endPointOfLineSegment = new Point(0, 0);
        lineSegmentVector = new Vector(0, 0);
        robotPositionVector = new Vector(0, 0);
        lastLookaheadPoint = new Point(0, 0);
        closestSegment = 0;
        minDistToPoint = 10000;
        startingNumber = 1;
        startingNumberLA = 0;
        lastPointIndex = 0;
        partialPointIndex = 0;
        lookaheadIndexT1 = 0;
        lookaheadIndexT2 = 0;
        minDistToPoint = 0;
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

    private void findLookaheadPoint() {
        firstLookaheadFound = false;
        for (int i = startingNumberLA; i < trajectory.getStates().size() - 1; i++) {
            startingPointOfLineSegment.setLocation(
                    trajectory.getStates().get(i).poseMeters.getTranslation().getX(),
                    trajectory.getStates().get(i).poseMeters.getTranslation().getY());
            endPointOfLineSegment.setLocation(
                    trajectory.getStates().get(i + 1).poseMeters.getTranslation().getX(),
                    trajectory.getStates().get(i + 1).poseMeters.getTranslation().getY());
            robotPosition.setLocation(odometry.getX(), odometry.getY());
            lineSegmentVector.setI(
                    endPointOfLineSegment.getX() - startingPointOfLineSegment.getX());
            lineSegmentVector.setJ(
                    endPointOfLineSegment.getY() - startingPointOfLineSegment.getY());
            robotPositionVector.setI(startingPointOfLineSegment.getX() - robotPosition.getX());
            robotPositionVector.setJ(startingPointOfLineSegment.getY() - robotPosition.getY());
            double a = lineSegmentVector.dot(lineSegmentVector);
            double b = 2 * robotPositionVector.dot(lineSegmentVector);
            double c =
                    robotPositionVector.dot(robotPositionVector) - Math.pow(lookaheadDistance, 2);
            double discriminant = Math.pow(b, 2) - (4 * a * c);
            if (discriminant < 0) {
                lookaheadPoint.setLocation(lastLookaheadPoint.getX(), lastLookaheadPoint.getY());
            } else {
                discriminant = Math.sqrt(discriminant);
                lookaheadIndexT1 = (-b - discriminant) / (2 * a);
                lookaheadIndexT2 = (-b + discriminant) / (2 * a);
                if (lookaheadIndexT1 >= 0 && lookaheadIndexT1 <= 1) {
                    partialPointIndex = i + lookaheadIndexT2;
                    if (partialPointIndex > lastPointIndex) {
                        lookaheadPoint.setLocation(
                                startingPointOfLineSegment.getX()
                                        + lookaheadIndexT1 * lineSegmentVector.getI(),
                                startingPointOfLineSegment.getY()
                                        + lookaheadIndexT1 * lineSegmentVector.getJ());
                        firstLookaheadFound = true;
                    }
                } else if (lookaheadIndexT2 >= 0 && lookaheadIndexT2 <= 1) {
                    partialPointIndex = i + lookaheadIndexT2;
                    if (partialPointIndex > lastPointIndex) {
                        lookaheadPoint.setLocation(
                                startingPointOfLineSegment.getX()
                                        + lookaheadIndexT2 * lineSegmentVector.getI(),
                                startingPointOfLineSegment.getY()
                                        + lookaheadIndexT2 * lineSegmentVector.getJ());
                        firstLookaheadFound = true;
                    }
                }
            }
            if (firstLookaheadFound) {
                break;
            } else if (!firstLookaheadFound && i == trajectory.getStates().size() - 1) {
                lookaheadPoint.setLocation(lastLookaheadPoint.getX(), lastLookaheadPoint.getY());
            }
        }
        lastLookaheadPoint.setLocation(lookaheadPoint.getX(), lookaheadPoint.getY());
        if (partialPointIndex > lastPointIndex) {
            lastPointIndex = partialPointIndex;
        }
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

    private void updateDistToEnd() {
        distToEndVector.setI(
                trajectory
                                .getStates()
                                .get(trajectory.getStates().size() - 1)
                                .poseMeters
                                .getTranslation()
                                .getX()
                        - odometry.getX());
        distToEndVector.setJ(
                trajectory
                                .getStates()
                                .get(trajectory.getStates().size() - 1)
                                .poseMeters
                                .getTranslation()
                                .getY()
                        - odometry.getY());

        startingNumberLA = (int) partialPointIndex;
        lastLookaheadPoint = lookaheadPoint;
    }

    private void move() {
        setWheelVelocities(
                trajectory.getStates().get(closestSegment).velocityMetersPerSecond,
                trajectory.getStates().get(closestSegment).curvatureRadPerMeter);
        SmartDashboard.putNumber("dist to end", distToEndVector.magnitude());
        SmartDashboard.putNumber("i dist", distToEndVector.getI());
        SmartDashboard.putNumber("j dist", distToEndVector.getJ());
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
        return distToEndVector.magnitude() < 0.5;
    }
}
