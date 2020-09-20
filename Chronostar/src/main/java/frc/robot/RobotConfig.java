package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

/** Add your docs here. */
public class RobotConfig {

    public void startBaseConfig() {
        setCurrentLimitsEnabled();
        RobotMap.shooter.init();
        RobotMap.drive.init();
        RobotMap.intake.init();
        RobotMap.magazine.init();
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

    public static void setDriveBrake() {
        for (TalonFX t : RobotMap.allMotors) {
            t.setNeutralMode(NeutralMode.Brake);
        }
    }

    public static void setDriveCoast() {
        for (TalonFX t : RobotMap.allMotors) {
            t.setNeutralMode(NeutralMode.Coast);
        }
    }
}
