package frc.robot.commands.basic;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Constants;
import frc.robot.subsystems.Drive;
import frc.robot.tools.math.Point;
import frc.robot.tools.math.Vector;
import frc.robot.tools.pathing.Odometry;

public class PurePursuit extends CommandBase {

    private int closestSegment;
    private Point lookaheadPoint;
    private Point lastLookaheadPoint;
    private int startingNumber;
    private double dx, dy;
    private double distToPoint;
    private double minDistToPoint;
    private Point closestPoint;
    private double lookaheadDistance;
    private double desiredCurvature;
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
    private double curveAdjustedVelocity;
    private double k;
    private Drive drive;
    private Odometry odometry;
    private Trajectory trajectory;
    private final double Y_OFFSET = 26.9375;

    public PurePursuit(
            Drive drive,
            Odometry odometry,
            double lookaheadDistance,
            double k,
            Trajectory trajectory) {
        this.lookaheadDistance = lookaheadDistance;
        this.k = k;
        this.drive = drive;
        this.odometry = odometry;
        this.trajectory = trajectory;
        addRequirements(this.drive);
    }

    @Override
    public void initialize() {
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
        desiredCurvature = 0;
        minDistToPoint = 0;
        distToPoint = 0;
        dx = 0;
        dy = 0;
        curveAdjustedVelocity = 0;
    }

    private void findRobotCurvature() {
        double a = -Math.tan(Math.toRadians(odometry.getTheta()));
        double b = 1;
        double c =
                Math.tan(Math.toRadians(odometry.getTheta())) * odometry.getX() - odometry.getY();
        double x =
                Math.abs(a * lookaheadPoint.getX() + b * lookaheadPoint.getY() + c)
                        / Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
        double side =
                Math.signum(
                        Math.sin(Math.toRadians(odometry.getTheta()))
                                        * (lookaheadPoint.getX() - odometry.getX())
                                - Math.cos(Math.toRadians(odometry.getTheta()))
                                        * (lookaheadPoint.getY() - odometry.getY()));
        desiredCurvature = (2 * x / Math.pow(lookaheadDistance, 2)) * side;
    }

    private void setWheelVelocities(double targetVelocity, double curvature) {
        double leftVelocity, rightVelocity;
        double v;
        double c = -curvature;
        if (closestSegment < 3) {
            v = targetVelocity + 0.6;
        } else {
            v = targetVelocity;
        }
        leftVelocity = (double) v * (2 + c * Constants.DRIVE_WHEEL_BASE) / 2;
        rightVelocity = (double) v * (2 - c * Constants.DRIVE_WHEEL_BASE) / 2;
        drive.setLeftSpeed(leftVelocity);
        drive.setRightSpeed(rightVelocity);
    }

    @Override
    public void execute() {
        for (int i = startingNumber; i < trajectory.getStates().size() - 1; i++) {
            dx = trajectory.getStates().get(i).poseMeters.getTranslation().getX() - odometry.getX();
            dy =
                    trajectory.getStates().get(i).poseMeters.getTranslation().getY()
                            - odometry.getY()
                            - Y_OFFSET;
            distToPoint = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
            if (distToPoint < minDistToPoint) {
                minDistToPoint = distToPoint;
                closestSegment = i;
                closestPoint.setLocation(
                        trajectory.getStates().get(i).poseMeters.getTranslation().getX(),
                        trajectory.getStates().get(i).poseMeters.getTranslation().getY()
                                - Y_OFFSET);
            }
        }
        startingNumber = closestSegment;
        minDistToPoint = 1000;
        firstLookaheadFound = false;
        for (int i = startingNumberLA; i < trajectory.getStates().size() - 1; i++) {
            startingPointOfLineSegment.setLocation(
                    trajectory.getStates().get(i).poseMeters.getTranslation().getX(),
                    trajectory.getStates().get(i).poseMeters.getTranslation().getY() - Y_OFFSET);
            endPointOfLineSegment.setLocation(
                    trajectory.getStates().get(i + 1).poseMeters.getTranslation().getX(),
                    trajectory.getStates().get(i + 1).poseMeters.getTranslation().getY()
                            - Y_OFFSET);
            robotPosition.setLocation(odometry.getX(), odometry.getY());
            lineSegmentVector.setI(
                    endPointOfLineSegment.getX() - startingPointOfLineSegment.getX());
            lineSegmentVector.setJ(
                    endPointOfLineSegment.getY() - startingPointOfLineSegment.getY());
            double a = lineSegmentVector.dot(lineSegmentVector);
            double b = 2 * robotPositionVector.dot(lineSegmentVector);
            double c =
                    robotPositionVector.dot(robotPositionVector) - Math.pow(lookaheadDistance, 2);
            double discriminant = Math.pow(b, 2) - 4 * a * c;
            if (discriminant < 0) {
                lookaheadPoint.setLocation(lastLookaheadPoint.getX(), lastLookaheadPoint.getY());
            } else {
                discriminant = Math.sqrt(discriminant);
                lookaheadIndexT1 = (-b - discriminant) / (2 * a);
                lookaheadIndexT2 = (-b + discriminant) / (2 * a);
                if (lookaheadIndexT1 >= 0 && lookaheadIndexT2 <= 1) {
                    partialPointIndex = i + lookaheadIndexT2;
                    if (partialPointIndex > lastPointIndex) {
                        lookaheadPoint.setLocation(
                                startingPointOfLineSegment.getX()
                                        + lookaheadIndexT1 * lineSegmentVector.getI(),
                                startingPointOfLineSegment.getY()
                                        + lookaheadIndexT1 * lineSegmentVector.getJ());
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
                        - Y_OFFSET
                        - odometry.getY());

        startingNumberLA = (int) partialPointIndex;
        lastLookaheadPoint = lookaheadPoint;
        findRobotCurvature();
        if (drive.safelyDivide(k, trajectory.getStates().get(closestSegment).curvatureRadPerMeter)
                        != Double.NaN
                && trajectory.getStates().get(closestSegment).velocityMetersPerSecond
                        != Double.NaN) {
            curveAdjustedVelocity =
                    Math.abs(
                            Math.min(
                                    drive.safelyDivide(
                                            k,
                                            trajectory
                                                    .getStates()
                                                    .get(closestSegment)
                                                    .curvatureRadPerMeter),
                                    trajectory
                                            .getStates()
                                            .get(closestSegment)
                                            .velocityMetersPerSecond));
        } else {
            curveAdjustedVelocity = 0;
        }
        setWheelVelocities(curveAdjustedVelocity, desiredCurvature);
        SmartDashboard.putNumber("dist to end", distToEndVector.magnitude());
        SmartDashboard.putNumber("dist to end i", distToEndVector.getI());
        SmartDashboard.putNumber("dist to end j", distToEndVector.getJ());
    }

    @Override
    public void end(boolean interrupted) {
        drive.setLeftPercent(0);
        drive.setRightPercent(0);
        SmartDashboard.putNumber("finished", Math.random());
    }

    @Override
    public boolean isFinished() {
        return distToEndVector.magnitude() < 0.5;
    }
}
