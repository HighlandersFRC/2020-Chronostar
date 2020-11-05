// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.*;
import frc.robot.tools.controlloops.PID;
import frc.robot.tools.pathtools.Odometry;

public class Drive extends SubsystemBase {

    double deadzone = 0.01;
    private double vkF = 0.0455925;
    private double vkP = 0.21;
    private double vkI = 0.000002;
    private double vkD = 0;
    private double akP = 0.1;
    private double akI = 0;
    private double akD = 0;
    private PID aPID;
    private double visionTapePercent;
    private Odometry autoOdometry;

    public Drive() {}

    public void startAutoOdometry(double x, double y, double theta) {}
    ;

    public double getDriveTrainX() {
        return autoOdometry.getX();
    }

    public double getDriveTrainY() {
        return autoOdometry.getY();
    }

    public double getDriveTrainHeading() {
        return autoOdometry.gettheta();
    }

    public void setDriveTrainX(double x) {
        autoOdometry.setX(x);
    }

    public void setDriveTrainY(double y) {
        autoOdometry.setY(y);
    }

    public void setDriveTrainHeading(double theta) {
        autoOdometry.setTheta(theta);
    }

    public void setOdometryReversed(boolean reversed) {
        autoOdometry.setReversed(reversed);
    }

    public void init() {
        aPID = new PID(akP, akI, akD);
        RobotMap.leftDriveLead.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
        RobotMap.rightDriveLead.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
        RobotMap.leftDriveFollower.set(ControlMode.Follower, Constants.LEFT_DRIVE_LEAD_ID);
        RobotMap.rightDriveFollower.set(ControlMode.Follower, Constants.RIGHT_DRIVE_LEAD_ID);
        RobotMap.rightDriveLead.setInverted(true);
        RobotMap.rightDriveFollower.setInverted(InvertType.FollowMaster);
        RobotMap.leftDriveFollower.setInverted(InvertType.FollowMaster);
        RobotMap.leftDriveLead.setSensorPhase(false);
        RobotMap.rightDriveLead.setSensorPhase(false);
        RobotMap.leftDriveLead.setSelectedSensorPosition(0);
        RobotMap.rightDriveLead.setSelectedSensorPosition(0);

        RobotMap.leftDriveLead.selectProfileSlot(0, 0);
        RobotMap.rightDriveLead.selectProfileSlot(0, 0);
        RobotMap.leftDriveLead.config_kF(0, vkF);
        RobotMap.rightDriveLead.config_kF(0, vkF);
        RobotMap.leftDriveLead.config_kP(0, vkP);
        RobotMap.rightDriveLead.config_kP(0, vkP);
        RobotMap.leftDriveLead.config_kI(0, vkI);
        RobotMap.rightDriveLead.config_kI(0, vkI);
        RobotMap.leftDriveLead.config_kD(0, vkD);
        RobotMap.rightDriveLead.config_kD(0, vkD);
    }

    private void setLeftPercent(double percent) {
        RobotMap.leftDriveLead.set(ControlMode.PercentOutput, percent);
    }

    private void setRightPercent(double percent) {
        RobotMap.rightDriveLead.set(ControlMode.PercentOutput, percent);
    }

    private void setLeftSpeed(double fps) {
        RobotMap.leftDriveLead.set(ControlMode.Velocity, Constants.fpsToEncVelocity(fps));
    }

    private void setRightSpeed(double fps) {
        RobotMap.rightDriveLead.set(ControlMode.Velocity, Constants.fpsToEncVelocity(fps));
    }

    public void arcadeDrive(double throttle, double turn) {
        double left, right;
        double differential;
        if (Math.abs(throttle) > deadzone) {
            throttle = Math.tanh(throttle) * 4 / Math.PI;
        } else throttle = 0;
        if (Math.abs(turn) < deadzone) {
            turn = 0;
        }
        differential = turn;
        left = throttle + differential;
        right = throttle - differential;
        if (Math.abs(left) > 1) {
            right = Math.abs(right / left) * Math.signum(right);
            left = Math.signum(left);
        } else if (Math.abs(right) > 1) {
            left = Math.abs(left / right) * Math.signum(left);
            right = Math.signum(right);
        }
        setLeftPercent(left);
        setRightPercent(right);
    }

    public void tankDrive(double left, double right) {
        setLeftPercent(left);
        setRightPercent(right);
    }

    @Override
    public void periodic() {}

    public void trackVisionTape() {
        try {
            if (!RobotState.isOperatorControl()) {
                if (Math.abs(ButtonMap.getThrottle()) > deadzone) {
                    visionTapePercent = Math.tanh(ButtonMap.getThrottle()) * 4 / Math.PI;
                } else {
                    visionTapePercent = 0;
                }
            } else {
                visionTapePercent = 0;
            }
            RobotConfig.setDriveBrake();
            // Robot.visionCam.updateVision();
            if (Timer.getFPGATimestamp() - Robot.visionCam.lastParseTime > 0.25) {
                aPID.updatePID(0);
                return;
            } else {
                aPID.updatePID(Robot.visionCam.getAngle());
            }

            setLeftSpeed(visionTapePercent * 6 + aPID.getResult());
            setRightSpeed(visionTapePercent * 6 + aPID.getResult());

        } catch (Exception e) {

        }
    }

    public void teleopPeriodic() {
        arcadeDrive(ButtonMap.getThrottle(), ButtonMap.getTurn());
        if (ButtonMap.getDriverRightBumper()) {
            RobotMap.visionRelay.set(Value.kForward);
        } else {
            RobotMap.visionRelay.set(Value.kReverse);
        }
    }
}
