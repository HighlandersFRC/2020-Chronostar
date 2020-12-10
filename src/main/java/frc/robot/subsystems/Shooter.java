// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.commands.universalcommands.SetFlywheelRPM;

public class Shooter extends SubsystemBase {
    public Shooter() {}

    public void init() {
        RobotMap.rightFlywheel.setNeutralMode(NeutralMode.Coast);
        RobotMap.leftFlywheel.setNeutralMode(NeutralMode.Coast);
        RobotMap.rightFlywheel.set(ControlMode.Follower, Constants.LEFT_FLYWHEEL_ID);
        RobotMap.leftFlywheel.setInverted(true);
        RobotMap.rightFlywheel.setInverted(InvertType.OpposeMaster);
        RobotMap.leftFlywheel.configClosedLoopPeakOutput(0, Constants.MAX_SHOOTER_PERCENTAGE);
        RobotMap.leftFlywheel.configPeakOutputForward(0.6);
        RobotMap.leftFlywheel.configPeakOutputReverse(0);
        RobotMap.leftFlywheel.configVoltageCompSaturation(11.7);
        RobotMap.leftFlywheel.enableVoltageCompensation(true);
        RobotMap.leftFlywheel.setSensorPhase(true);
        RobotMap.leftFlywheel.selectProfileSlot(0, 0);
        RobotMap.leftFlywheel.config_kF(0, 0.075);
        RobotMap.leftFlywheel.config_kP(0, 3);
        RobotMap.leftFlywheel.config_kI(0, 0.025);
        RobotMap.leftFlywheel.config_kD(0, 0);
        RobotMap.leftFlywheel.config_IntegralZone(0, Constants.SHOOTER_INTEGRAL_RANGE);
        setDefaultCommand(new SetFlywheelRPM(0));
    }

    @Override
    public void periodic() {}

    public void setVelocity(double desiredVelocity) {
        if (desiredVelocity > Constants.MAX_SHOOTER_RPM) {
            desiredVelocity = Constants.MAX_SHOOTER_RPM;
        } else if (desiredVelocity < 0) {
            desiredVelocity = 0;
        }
        RobotMap.leftFlywheel.set(
                ControlMode.Velocity, Constants.shooterRPMToUnitsPer100MS(desiredVelocity));
    }

    public void teleopPeriodic() {}

    public static double unitsPer100MsToRpm(double units) {
        return (units * 600) / Constants.TICKS_PER_SHOOTER_ROTATION;
    }

    public double getShooterRPM() {
        return unitsPer100MsToRpm(RobotMap.leftFlywheel.getSelectedSensorVelocity());
    }
}
