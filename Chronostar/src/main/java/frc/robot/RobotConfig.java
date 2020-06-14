package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

/**
 * Add your docs here.
 */
public class RobotConfig {

    public void startBaseConfig() {
        RobotMap.rightFlyWheel.configFactoryDefault();
        RobotMap.leftFlyWheel.configFactoryDefault();
        RobotMap.rightFlyWheel.setNeutralMode(NeutralMode.Coast);
        RobotMap.leftFlyWheel.setNeutralMode(NeutralMode.Coast);

        RobotMap.leftDriveLead.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
        RobotMap.rightDriveLead.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
        RobotMap.leftDriveFollower1.set(ControlMode.Follower, Constants.leftDriveLeadID);
        RobotMap.rightDriveFollower1.set(ControlMode.Follower, Constants.rightDriveLeadID);
        RobotMap.rightDriveLead.setInverted(true);
        RobotMap.rightDriveFollower1.setInverted(InvertType.FollowMaster);
        RobotMap.leftDriveFollower1.setInverted(InvertType.FollowMaster);
        RobotMap.leftDriveLead.setSensorPhase(false);
        RobotMap.rightDriveLead.setSensorPhase(false);
        RobotMap.leftDriveLead.setSelectedSensorPosition(0);
        RobotMap.rightDriveLead.setSelectedSensorPosition(0);
        setCurrentLimitsEnabled();
    }

    public void startAutoConfig() {
        setVoltageCompensation(Constants.driveMaxVoltage);
        setDriveBrake();
    }

    public void startTeleopConfig() {
        setDriveCoast();
    }

    private void setVoltageCompensation(double volts) {
        for (TalonFX t : RobotMap.allMotors) {
            t.configVoltageCompSaturation(volts);
        }
    }

    private void setCurrentLimitsEnabled() {
        for (TalonFX t : RobotMap.allMotors) {
            t.configSupplyCurrentLimit(Constants.currentLimitEnabled);
        }
    }
    
    private void setCurrentLimitsDisabled() {
        for (TalonFX t : RobotMap.allMotors) {
            t.configSupplyCurrentLimit(Constants.currentLimitDisabled);
        }
    }

    private void setDriveBrake() {
        for (TalonFX t : RobotMap.allMotors) {
            t.setNeutralMode(NeutralMode.Brake);
        }
    }

    private void setDriveCoast() {
        for (TalonFX t : RobotMap.allMotors) {
            t.setNeutralMode(NeutralMode.Coast);
        }
    }
}
