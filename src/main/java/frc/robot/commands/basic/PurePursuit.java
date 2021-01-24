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
    private double endThetaError;
    private Drive drive;
    private Odometry odometry;
    private Trajectory trajectory;

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
        startingNumber = 0;
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
                Math.abs(a * lookaheadPoint.getXPos() + b * lookaheadPoint.getYPos() + c)
                        / Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
        double side =
                Math.signum(
                        Math.sin(Math.toRadians(odometry.getTheta()))
                                        * (lookaheadPoint.getXPos() - odometry.getX())
                                - Math.cos(Math.toRadians(odometry.getTheta()))
                                        * (lookaheadPoint.getYPos() - odometry.getY()));
        desiredCurvature = (2 * x / Math.pow(lookaheadDistance, 2)) * side;
    }

    private void setWheelVelocities(double targetVelocity, double curvature) {
        double leftVelocity, rightVelocity;
        double v;
        double c = -curvature;
        if (closestSegment < 3) {
            v = 0.6 + targetVelocity;
        } else {
            v = targetVelocity;
        }
        leftVelocity = v * (2 + c * Constants.DRIVE_WHEEL_BASE) / 2;
        rightVelocity = v * (2 - c * Constants.DRIVE_WHEEL_BASE) / 2;
        drive.setLeftSpeed(leftVelocity);
        drive.setRightSpeed(rightVelocity);
    }

    @Override
    public void execute() {
        SmartDashboard.putNumber("distToEndVector magnitude", distToEndVector.magnitude());
        odometry.setLeft(drive.getLeftPosition());
        odometry.setRight(drive.getRightPosition());
        for (int i = startingNumber; i < trajectory.getStates().size() - 1; i++) {
            dx = trajectory.getStates().get(i).poseMeters.getTranslation().getX() - odometry.getX();
            dy = trajectory.getStates().get(i).poseMeters.getTranslation().getY() - odometry.getY();
            distToPoint = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
            if (distToPoint < minDistToPoint) {
                minDistToPoint = distToPoint;
                closestSegment = i;
                closestPoint.setLocation(
                        trajectory.getStates().get(i).poseMeters.getTranslation().getX(),
                        trajectory.getStates().get(i).poseMeters.getTranslation().getX());
            }
        }
        startingNumber = closestSegment;
        minDistToPoint = 1000;
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
                    endPointOfLineSegment.getXPos() - startingPointOfLineSegment.getXPos());
            lineSegmentVector.setJ(
                    endPointOfLineSegment.getYPos() - startingPointOfLineSegment.getYPos());
            double a = lineSegmentVector.dot(lineSegmentVector);
            double b = 2 * robotPositionVector.dot(lineSegmentVector);
            double c =
                    robotPositionVector.dot(robotPositionVector) - Math.pow(lookaheadDistance, 2);
            double discriminant = Math.pow(b, 2) - 4 * a * c;
            if (discriminant < 0) {
                lookaheadPoint.setLocation(
                        lastLookaheadPoint.getXPos(), lastLookaheadPoint.getYPos());
            } else {
                discriminant = Math.sqrt(discriminant);
                lookaheadIndexT1 = (-b - discriminant) / (2 * a);
                lookaheadIndexT2 = (-b + discriminant) / (2 * a);
                if (lookaheadIndexT1 >= 0 && lookaheadIndexT2 <= 1) {
                    partialPointIndex = i + lookaheadIndexT2;
                    if (partialPointIndex > lastPointIndex) {
                        lookaheadPoint.setLocation(
                                startingPointOfLineSegment.getXPos()
                                        + lookaheadIndexT1 * lineSegmentVector.getI(),
                                startingPointOfLineSegment.getYPos()
                                        + lookaheadIndexT1 * lineSegmentVector.getJ());
                        firstLookaheadFound = true;
                    }
                }
            }
            if (firstLookaheadFound) {
                break;
            } else if (!firstLookaheadFound && i == trajectory.getStates().size() - 1) {
                System.out.println("failed");
                lookaheadPoint.setLocation(
                        lastLookaheadPoint.getXPos(), lastLookaheadPoint.getYPos());
            }
        }
        lastLookaheadPoint.setLocation(lookaheadPoint.getXPos(), lookaheadPoint.getYPos());
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
                        - odometry.getY());
        startingNumberLA = (int) partialPointIndex;
        lastLookaheadPoint = lookaheadPoint;
        findRobotCurvature();
        curveAdjustedVelocity =
                Math.min(
                        Math.abs(
                                k
                                        / trajectory
                                                .getStates()
                                                .get(closestSegment)
                                                .curvatureRadPerMeter),
                        trajectory.getStates().get(closestSegment).velocityMetersPerSecond);
        setWheelVelocities(curveAdjustedVelocity, desiredCurvature);
        endThetaError =
                Constants.boundHalfDegrees(
                        Math.toDegrees(
                                        trajectory
                                                .getStates()
                                                .get(trajectory.getStates().size() - 1)
                                                .poseMeters
                                                .getRotation()
                                                .getDegrees())
                                - odometry.getTheta());
    }

    @Override
    public void end(boolean interrupted) {
        drive.setLeftSpeed(0);
        drive.setRightSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return distToEndVector.magnitude() < 0.20;
    }
}
