package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;

import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.*;
import frc.robot.tools.controlloops.PID;

public class Drive extends SubsystemBase {

    double deadzone = 0.01;
    private double alignmentkP = 0.1;
    private double alignmentkI;
    private double alignmentkD;
    private PID alignmentPID;

    public Drive() {}

    public void init() {
        alignmentPID = new PID(alignmentkP, alignmentkI, alignmentkD);
        RobotMap.leftDriveLead.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
        RobotMap.rightDriveLead.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
        RobotMap.leftDriveFollower1.set(ControlMode.Follower, RobotStats.leftDriveLeadID);
        RobotMap.rightDriveFollower1.set(ControlMode.Follower, RobotStats.rightDriveLeadID);
        RobotMap.rightDriveLead.setInverted(true);
        RobotMap.rightDriveFollower1.setInverted(InvertType.FollowMaster);
        RobotMap.leftDriveFollower1.setInverted(InvertType.FollowMaster);
        RobotMap.leftDriveLead.setSensorPhase(false);
        RobotMap.rightDriveLead.setSensorPhase(false);
        RobotMap.leftDriveLead.setSelectedSensorPosition(0);
        RobotMap.rightDriveLead.setSelectedSensorPosition(0);
    }

    private void setLeftPercent(double percent) {
        RobotMap.leftDriveLead.set(ControlMode.PercentOutput, percent);
    }

    private void setRightPercent(double percent) {
        RobotMap.rightDriveLead.set(ControlMode.PercentOutput, percent);
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

    public void trackVisionTape() {}

    public void teleopPeriodic() {
        arcadeDrive(ButtonMap.getThrottle(), ButtonMap.getTurn());
        if (ButtonMap.getDriverRightBumper()) {
            RobotMap.visionRelay.set(Value.kForward);
        } else {
            RobotMap.visionRelay.set(Value.kReverse);
        }
    }
}
