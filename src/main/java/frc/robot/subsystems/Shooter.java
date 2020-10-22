// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.ButtonMap;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.commands.universalcommands.SetFlywheelRPM;

public class Shooter extends SubsystemBase {
    public SetFlywheelRPM standardRPM;
    public SetFlywheelRPM closeUpRPM;
    public SetFlywheelRPM zero;

    public Shooter() {}

    public void init() {
        RobotMap.rightFlywheel.setNeutralMode(NeutralMode.Coast);
        RobotMap.leftFlywheel.setNeutralMode(NeutralMode.Coast);
        RobotMap.rightFlywheel.set(ControlMode.Follower, Constants.leftFlywheelID);
        RobotMap.leftFlywheel.setInverted(true);
        RobotMap.rightFlywheel.setInverted(InvertType.OpposeMaster);
        RobotMap.leftFlywheel.configClosedLoopPeakOutput(0, Constants.maxPercentage);
        RobotMap.leftFlywheel.configPeakOutputForward(0.7);
        RobotMap.leftFlywheel.configPeakOutputReverse(0);
        RobotMap.leftFlywheel.configVoltageCompSaturation(11.7);
        RobotMap.leftFlywheel.enableVoltageCompensation(true);
        RobotMap.leftFlywheel.setSensorPhase(true);
        RobotMap.leftFlywheel.selectProfileSlot(0, 0);
        RobotMap.leftFlywheel.config_kF(0, 0.075);
        RobotMap.leftFlywheel.config_kP(0, 3);
        RobotMap.leftFlywheel.config_kI(0, 0.025);
        RobotMap.leftFlywheel.config_kD(0, 10);
        RobotMap.leftFlywheel.config_IntegralZone(0, Constants.shooterIntegralRange);
        standardRPM = new SetFlywheelRPM(5000);
        closeUpRPM = new SetFlywheelRPM(4500);
        zero = new SetFlywheelRPM(0);
    }

    @Override
    public void periodic() {}

    public void teleopPeriodic() {
        if (ButtonMap.shoot() && !standardRPM.isScheduled()) {
            standardRPM.schedule();
        } else {
            zero.schedule();
        }
    }

    public static double unitsPer100MsToRpm(double units) {
        return (units * 600) / Constants.ticksPerShooterRotation;
    }

    public double getShooterRPM() {
        return unitsPer100MsToRpm(RobotMap.leftFlywheel.getSelectedSensorVelocity());
    }

    public static int rpmToUnitsPer100Ms(double rpm) {
        return (int) Math.round((rpm / 600) * Constants.ticksPerShooterRotation);
    }
}
