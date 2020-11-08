// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.tools.pathtools;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.RobotMap;
import frc.robot.sensors.DriveEncoder;
import frc.robot.sensors.Navx;

public class Odometry extends CommandBase {
    private double theta = 0;
    private double thetaNext = 0;
    private Navx navx;
    private double leftSideNext;
    private double leftSide;
    private double leftDelta;
    private DriveEncoder leftDriveEncoder;
    private double rightSideNext;
    private double rightSide;
    private double rightDelta;
    private DriveEncoder rightDriveEncoder;
    private double centerDelta;
    private double x;
    private double y;
    private double yNext;
    private double xNext;
    private boolean shouldRun;
    private boolean isReversed;
    private boolean finish;
    private double thetaOffset;

    public Odometry(boolean reversed) {

        isReversed = reversed;
        leftDriveEncoder =
                new DriveEncoder(
                        RobotMap.leftDriveLead,
                        RobotMap.leftDriveLead.getSelectedSensorPosition(0));
        rightDriveEncoder =
                new DriveEncoder(
                        RobotMap.rightDriveLead,
                        RobotMap.rightDriveLead.getSelectedSensorPosition(0));
        navx = new Navx(RobotMap.ahrs);
    }

    public Odometry(boolean reversed, double startingX, double startingY) {

        isReversed = reversed;
        leftDriveEncoder =
                new DriveEncoder(
                        RobotMap.leftDriveLead,
                        RobotMap.leftDriveLead.getSelectedSensorPosition(0));
        rightDriveEncoder =
                new DriveEncoder(
                        RobotMap.rightDriveLead,
                        RobotMap.rightDriveLead.getSelectedSensorPosition(0));
        navx = new Navx(RobotMap.ahrs);
        x = startingX;
        y = startingY;
    }

    public void reverseOdometry(boolean revsered) {
        isReversed = revsered;
    }

    @Override
    public void initialize() {
        shouldRun = true;
        navx.softResetAngle();
        navx.softResetYaw();
        leftDriveEncoder.softReset();
        rightDriveEncoder.softReset();
        finish = false;
    }

    public void endOdmetry() {
        finish = true;
    }

    public void zero() {
        x = 0;
        y = 0;
        theta = 0;
        navx.softResetYaw();
        navx.softResetAngle();
        leftDriveEncoder.softReset();
        rightDriveEncoder.softReset();
        leftSide = 0;
        leftSideNext = 0;
        rightSide = 0;
        rightSideNext = 0;
        centerDelta = 0;
        rightDelta = 0;
        leftDelta = 0;
        theta = 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double gettheta() {
        return theta;
    }

    public void setX(double xValue) {
        x = xValue;
    }

    public void setY(double yValue) {
        y = yValue;
    }

    public void setTheta(double thetaValue) {
        thetaOffset = thetaValue;
    }

    public void setReversed(boolean reversed) {
        isReversed = reversed;
    }

    @Override
    public void execute() {
        if (shouldRun) {
            if (isReversed) {
                leftSideNext = leftDriveEncoder.getDistance();
                rightSideNext = rightDriveEncoder.getDistance();
                thetaNext = navx.currentAngle() + thetaOffset;
                leftDelta = (leftSideNext - leftSide);
                rightDelta = (rightSideNext - rightSide);
                centerDelta = (leftDelta + rightDelta) / 2;
                yNext = y - centerDelta * Math.sin(Math.toRadians(thetaNext));
                xNext = x - centerDelta * Math.cos(Math.toRadians(thetaNext));
                x = xNext;
                y = yNext;
                theta = thetaNext;
                leftSide = leftSideNext;
                rightSide = rightSideNext;
            } else {
                leftSideNext = leftDriveEncoder.getDistance();
                rightSideNext = rightDriveEncoder.getDistance();
                thetaNext = navx.currentAngle() + thetaOffset;
                leftDelta = (leftSideNext - leftSide);
                rightDelta = (rightSideNext - rightSide);
                centerDelta = (leftDelta + rightDelta) / 2;
                xNext = x + centerDelta * Math.cos(Math.toRadians(thetaNext));
                yNext = y + centerDelta * Math.sin(Math.toRadians(thetaNext));
                x = xNext;
                y = yNext;
                theta = thetaNext;
                leftSide = leftSideNext;
                rightSide = rightSideNext;
            }
        }
    }

    @Override
    public boolean isFinished() {
        if (finish) {
            return true;
        }
        return false;
    }

    public void end() {
        shouldRun = false;
    }
}
