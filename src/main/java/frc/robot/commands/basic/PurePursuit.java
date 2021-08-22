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
    private Trajectory trajectory;
    private double lookaheadDistance;
    private int startingNumberLA;
    private Point robotPos;
    private double k;
    private Odometry odometry;
    private boolean inverted;
    private Drive drive;
    private double maxVelocity;

    public PurePursuit(
            Drive drive,
            Odometry odometry,
            Trajectory trajectory,
            double lookAhead,
            double kValue,
            boolean direction) {
        this.drive = drive;
        this.trajectory = trajectory;
        this.lookaheadDistance = lookAhead;
        this.k = kValue;
        this.odometry = odometry;
        this.inverted = direction;
        addRequirements(this.drive);
    }

    private void setWheelVelocities(double targetVelocity, double curvature) {
        double leftVelocity;
        double rightVelocity;
        SmartDashboard.putNumber("Curvature", curvature);
        SmartDashboard.putNumber("Velocity", targetVelocity);
        //leftVelocity = targetVelocity * (2 + (curvature * Constants.DRIVE_WHEEL_BASE)) / 2;
        //rightVelocity = targetVelocity * (2 - (curvature * Constants.DRIVE_WHEEL_BASE)) / 2;
        leftVelocity = targetVelocity + curvature*Constants.DRIVE_WHEEL_BASE;
        rightVelocity = targetVelocity - curvature*Constants.DRIVE_WHEEL_BASE;
        SmartDashboard.putNumber("Left V", leftVelocity);
        SmartDashboard.putNumber("Right V", rightVelocity);
        drive.setLeftSpeed(-leftVelocity);
        drive.setRightSpeed(-rightVelocity);
    }

    private double findRobotCurvature(Point robotPosition, Vector lookAheadPoint) {
        double dx = lookAheadPoint.getI() -  robotPosition.getX();
        double dy = lookAheadPoint.getJ() - robotPosition.getY();
        double radius = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        double theta = Math.atan2(dy, dx);
        SmartDashboard.putNumber("theta", theta);
        Vector b = new Vector(robotPosition.getX() + Math.cos(robotPosition.getTheta()), robotPosition.getY() + Math.sin(robotPosition.getTheta()));
        
        double xVal = radius * Math.sin(theta);
        // double a = -Math.tan(robotPosition.getTheta());
        // double b = 1;
        // double c = (Math.tan(robotPosition.getTheta()) * robotPosition.getX()) - robotPosition.getY();
        // double xVal = Math.abs((a * lookAheadPoint.getI()) + (lookAheadPoint.getJ() + c) / Math.sqrt((Math.pow(a, 2) + Math.pow(b, 2))));
        double side = Math.signum(Math.sin(robotPosition.getTheta()) * (lookAheadPoint.getI() - robotPosition.getX()) - (Math.cos(robotPosition.getTheta()) * (lookAheadPoint.getJ()) - robotPosition.getY()));
        SmartDashboard.putNumber("Side", side);
        double curvature = ((2 * xVal) / Math.pow(lookaheadDistance, 2));
        curvature = curvature * side;
        SmartDashboard.putNumber("Curvature", curvature);
        return curvature;
    }

public Vector findLookAheadPoint(double startX, double startY, double endX, double endY, Point robotPosition, double lookaheadDistance) {
    double dx = endX - startX;
    double dy = endY - startY;

    SmartDashboard.putNumber("dx", dx);
    SmartDashboard.putNumber("dy", dy);

    Vector d = new Vector(dx, dy);
    Vector f = new Vector(robotPosition.getX() - startX, robotPosition.getY()  - startY);

    double a = d.dot(d);
    double b = 2 * (f.dot(d));
    double c = f.dot(f) - Math.pow(lookaheadDistance, 2);

    double discriminant = Math.pow(b, 2) - 4*a*c;

    Vector lookAheadPt = new Vector(0, 0);
    SmartDashboard.putNumber("Discriminant", discriminant);
    
    if(discriminant < 0) {
    }
    else {
        discriminant = Math.sqrt(discriminant);
        double t1 = (-b - discriminant)/(2*a);
        double t2 = (-b + discriminant)/(2*a);

        SmartDashboard.putNumber("t1", t1);
        SmartDashboard.putNumber("t2", t2);
        SmartDashboard.putNumber("b", b);
        SmartDashboard.putNumber("a", a);
        if(t1 >= 0 && t1 <= 1) {
            lookAheadPt = d.multiply(d, t1);
            lookAheadPt.setI(lookAheadPt.getI() + startX);
            lookAheadPt.setJ(lookAheadPt.getJ() + startY);
            return lookAheadPt;                
        }else 
        if(t2 >= 0 && t2 <= 1) {
            lookAheadPt = d.multiply(d, t2);
            lookAheadPt.setI(lookAheadPt.getI() + startX);
            lookAheadPt.setJ(lookAheadPt.getJ() + startY);
            return lookAheadPt;
        }  
        
    }
    return null;
    }

    private void algorithm() {
        Vector lookAheadPoint = null;
        robotPos.setLocation(odometry.getX(), odometry.getY());
        SmartDashboard.putNumber("Robot X", robotPos.getX());
        SmartDashboard.putNumber("Robot Y", robotPos.getY());
        SmartDashboard.putNumber("Starting Lookahead", startingNumberLA);
        for (int i = startingNumberLA; i < trajectory.getStates().size() - 1; i++) {
            double endPointX = trajectory.getStates().get(i + 1).poseMeters.getTranslation().getX();
            double endPointY = trajectory.getStates().get(i + 1).poseMeters.getTranslation().getY();
            double startPointX = trajectory.getStates().get(i).poseMeters.getTranslation().getX();
            double startPointY = trajectory.getStates().get(i).poseMeters.getTranslation().getY();
            lookAheadPoint = findLookAheadPoint(startPointX, startPointY, endPointX, endPointY, robotPos, lookaheadDistance);
            if(lookAheadPoint != null) {
                // startingNumberLA = i;
                break;
            }
        }
        if(lookAheadPoint != null) {
            System.out.println("Got a good point");
            SmartDashboard.putNumber("Lookahead X", lookAheadPoint.getI());
            SmartDashboard.putNumber("Lookahead Y", lookAheadPoint.getJ());
            SmartDashboard.putNumber("Dist to Lookahead", Math.pow(lookAheadPoint.getI() - robotPos.getX(), 2) + Math.pow(lookAheadPoint.getJ() - robotPos.getY(), 2));
            double desiredRobotCurvature = findRobotCurvature(robotPos, lookAheadPoint);
            double desiredVelocity = k/desiredRobotCurvature;
            if(desiredVelocity > 0 && desiredVelocity > maxVelocity) {
                desiredVelocity = maxVelocity;
            }
            else if(desiredVelocity < 0 && Math.abs(desiredVelocity) > maxVelocity) {
                desiredVelocity = -maxVelocity;
            }
            setWheelVelocities(-desiredVelocity, desiredRobotCurvature);
        }
        int length = trajectory.getStates().size() - 1;
        double endX = trajectory.getStates().get(length).poseMeters.getTranslation().getX();
        double endY = trajectory.getStates().get(length).poseMeters.getTranslation().getY();

        double robotX = robotPos.getX();
        double robotY = robotPos.getY();
        SmartDashboard.putNumber("Dist to end", Math.sqrt((Math.pow(endX - robotX, 2) + (Math.pow(endY - robotY, 2)))));
    }

    @Override
    public void initialize() {
        odometry.setX(trajectory.getStates().get(0).poseMeters.getTranslation().getX());
        odometry.setY(trajectory.getStates().get(0).poseMeters.getTranslation().getY());
        odometry.setInverted(inverted);
        robotPos = new Point(0, 0);
        startingNumberLA = 0;
        maxVelocity = 1;
    }

    @Override
    public void execute() {
        algorithm();
    }

    @Override
    public boolean isFinished() {
        int length = trajectory.getStates().size() - 1;
        double endX = trajectory.getStates().get(length).poseMeters.getTranslation().getX();
        double endY = trajectory.getStates().get(length).poseMeters.getTranslation().getY();
        double robotX = robotPos.getX();
        double robotY = robotPos.getY();
        double distToEnd = Math.sqrt((Math.pow(endX - robotX, 2) + (Math.pow(endY - robotY, 2))));
        return distToEnd < 0.1;
    }

    @Override
    public void end(boolean interrupted) {
        drive.setLeftPercent(0);
        drive.setRightPercent(0);
    }
}