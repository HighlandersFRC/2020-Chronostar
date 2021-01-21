package frc.robot.commands.basic;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Peripherals;
import frc.robot.tools.math.Point;
import frc.robot.tools.math.Vector;
import frc.robot.tools.pathing.Odometry;

import java.util.ArrayList;

public class PurePursuit extends CommandBase {

    private class PathRunnable implements Runnable {
        public void run() {
            algorithm();
        }
    }

    private ArrayList<Trajectory> trajectories;
    private int closestSegment;
    private Point lookaheadPoint;
    private Point lastLookaheadPoint;
    private Notifier pathNotifier;
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
    private Peripherals peripherals;
    private Odometry odometry;
    private int selectedPath;
    private Trajectory selectedTrajectory;

    public PurePursuit(
            Drive drive,
            Peripherals peripherals,
            Odometry odometry,
            double lookaheadDistance,
            double k,
            int selectedPath,
            Trajectory... trajectories) {
        this.lookaheadDistance = lookaheadDistance;
        this.k = k;
        for (Trajectory t : trajectories) {
            this.trajectories.add(t);
        }
        this.selectedPath = selectedPath;
        this.drive = drive;
        this.peripherals = peripherals;
        this.odometry = odometry;
        selectedTrajectory = trajectories[selectedPath];
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
        pathNotifier = new Notifier(new PathRunnable());
        pathNotifier.startPeriodic(0.02);
    }

    private void findRobotCurvature() {}

    private void setWheelVelocities(double targetVelocity, double curvature) {}

    private void algorithm() {
        odometry.setLeft(drive.getLeftPosition());
        odometry.setRight(drive.getRightPosition());
        for (int i = startingNumber; i < selectedTrajectory.getStates().size() - 1; i++) {
            dx =
                    selectedTrajectory.getStates().get(i).poseMeters.getTranslation().getX()
                            - odometry.getX();
            dy =
                    selectedTrajectory.getStates().get(i).poseMeters.getTranslation().getY()
                            - odometry.getY();
            distToPoint = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
            if (distToPoint < minDistToPoint) {
                minDistToPoint = distToPoint;
                closestSegment = i;
                closestPoint.setLocation(
                        selectedTrajectory.getStates().get(i).poseMeters.getTranslation().getX(),
                        selectedTrajectory.getStates().get(i).poseMeters.getTranslation().getX());
            }
        }
        startingNumber = closestSegment;
        minDistToPoint = 1000;
        firstLookaheadFound = false;
        for (int i = startingNumberLA; i < selectedTrajectory.getStates().size() - 1; i++) {
            startingPointOfLineSegment.setLocation(
                    selectedTrajectory.getStates().get(i).poseMeters.getTranslation().getX(),
                    selectedTrajectory.getStates().get(i).poseMeters.getTranslation().getY());
            endPointOfLineSegment.setLocation(
                    selectedTrajectory.getStates().get(i + 1).poseMeters.getTranslation().getX(),
                    selectedTrajectory.getStates().get(i + 1).poseMeters.getTranslation().getY());
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
            } else if (!firstLookaheadFound && i == selectedTrajectory.getStates().size() - 1) {
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
                selectedTrajectory
                                .getStates()
                                .get(selectedTrajectory.getStates().size() - 1)
                                .poseMeters
                                .getTranslation()
                                .getX()
                        - odometry.getX());
        distToEndVector.setJ(
                selectedTrajectory
                                .getStates()
                                .get(selectedTrajectory.getStates().size() - 1)
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
                                        / selectedTrajectory
                                                .getStates()
                                                .get(closestSegment)
                                                .curvatureRadPerMeter),
                        selectedTrajectory.getStates().get(closestSegment).velocityMetersPerSecond);
        setWheelVelocities(curveAdjustedVelocity, desiredCurvature);
        endThetaError = Constants.boundHalfDegrees(Math.toDegrees(selectedTrajectory.getStates().get(selectedTrajectory.getStates().size() - 1).poseMeters.getRotation().getDegrees()) - odometry.getTheta();
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
