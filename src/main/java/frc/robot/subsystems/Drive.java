// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.*;
import frc.robot.commands.defaultcommands.DriveDefaultCommand;
import frc.robot.tools.controlloops.PID;

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

    public Drive() {}

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
        setDefaultCommand(new DriveDefaultCommand(true));
    }

    public void setLeftPercent(double percent) {
        RobotMap.leftDriveLead.set(ControlMode.PercentOutput, percent);
    }

    public void setRightPercent(double percent) {
        RobotMap.rightDriveLead.set(ControlMode.PercentOutput, percent);
    }

    public void setLeftSpeed(double fps) {
        RobotMap.leftDriveLead.set(ControlMode.Velocity, Constants.driveFPSToUnitsPer100MS(fps));
    }

    public void setRightSpeed(double fps) {
        RobotMap.rightDriveLead.set(ControlMode.Velocity, Constants.driveFPSToUnitsPer100MS(fps));
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

    public void orientToTarget() {
        try {
            if (!RobotState.isOperatorControl()) {
                if (Math.abs(OI.getDriverLeftY()) > deadzone) {
                    visionTapePercent = Math.tanh(OI.getDriverLeftY()) * 4 / Math.PI;
                } else {
                    visionTapePercent = 0;
                }
            } else {
                visionTapePercent = 0;
            }
            RobotConfig.setDriveBrake();
            RobotMap.visionCam.updateVision();
            if (Timer.getFPGATimestamp() - RobotMap.visionCam.getLastParseTime() > 0.25) {
                aPID.updatePID(0);
                return;
            } else {
                aPID.updatePID(RobotMap.visionCam.getAngle());
            }

            setLeftPercent(visionTapePercent * 6 + aPID.getResult());
            setRightPercent(visionTapePercent * 6 + aPID.getResult());

        } catch (Exception e) {

        }
    }

    public void teleopPeriodic() {}
}
