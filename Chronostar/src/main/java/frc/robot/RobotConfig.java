package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax.IdleMode;

import frc.robot.subsystems.Shooter;

/**
 * Add your docs here.
 */
public class RobotConfig {

    public void startBaseConfig() {
        RobotMap.rightFlywheel.configFactoryDefault();
        RobotMap.leftFlywheel.configFactoryDefault();
        RobotMap.rightFlywheel.setNeutralMode(NeutralMode.Coast);
        RobotMap.leftFlywheel.setNeutralMode(NeutralMode.Coast);

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
        setCurrentLimitsEnabled();
        RobotMap.lowMag.setNeutralMode(NeutralMode.Brake);
        RobotMap.highMag.setIdleMode(IdleMode.kBrake);
        RobotMap.intakeMotor.setNeutralMode(NeutralMode.Brake);
        RobotMap.intake2Motor.setNeutralMode(NeutralMode.Brake);
        RobotMap.intakeMotor.setInverted(true);
        RobotMap.highMag.setIdleMode(IdleMode.kBrake);
        RobotMap.lowMag.configVoltageCompSaturation(11.7);
        RobotMap.lowMag.enableVoltageCompensation(true);
        RobotMap.rightFlywheel.set(ControlMode.Follower, RobotStats.leftFlywheelID);
        RobotMap.leftFlywheel.setInverted(true);
        RobotMap.rightFlywheel.setInverted(InvertType.OpposeMaster);
        RobotMap.leftFlywheel.configClosedLoopPeakOutput(0, RobotStats.maxPercentage);
        RobotMap.leftFlywheel.configPeakOutputForward(0.415);
        RobotMap.leftFlywheel.configPeakOutputReverse(0);
        RobotMap.leftFlywheel.configVoltageCompSaturation(11.7);
        RobotMap.leftFlywheel.enableVoltageCompensation(true);
        RobotMap.leftFlywheel.setSensorPhase(true);
        RobotMap.shooter.initialize();
    }

    public void startAutoConfig() {
        setVoltageCompensation(RobotStats.driveMaxVoltage);
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
            t.configSupplyCurrentLimit(RobotStats.currentLimitEnabled);
        }
    }
    
    private void setCurrentLimitsDisabled() {
        for (TalonFX t : RobotMap.allMotors) {
            t.configSupplyCurrentLimit(RobotStats.currentLimitDisabled);
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
