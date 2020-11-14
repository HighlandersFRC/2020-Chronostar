// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

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
        RobotMap.hood.init();
    }

    public void startAutoConfig() {
        setVoltageCompensation(Constants.DRIVE_MAX_VOLTAGE);
        setDriveMotorsBrake();
        RobotMap.hood.init();
    }

    public void startTeleopConfig() {
        setDriveMotorsCoast();
        RobotMap.hood.init();
    }

    private void setVoltageCompensation(double volts) {
        for (TalonFX t : RobotMap.driveMotors) {
            t.configVoltageCompSaturation(volts);
        }
    }

    private void setCurrentLimitsEnabled() {
        for (TalonFX t : RobotMap.driveMotors) {
            t.configSupplyCurrentLimit(Constants.currentLimitEnabled);
        }
    }

    public static void setDriveMotorsBrake() {
        for (TalonFX t : RobotMap.driveMotors) {
            t.setNeutralMode(NeutralMode.Brake);
        }
    }

    public static void setDriveMotorsCoast() {
        for (TalonFX t : RobotMap.driveMotors) {
            t.setNeutralMode(NeutralMode.Coast);
        }
    }
}
