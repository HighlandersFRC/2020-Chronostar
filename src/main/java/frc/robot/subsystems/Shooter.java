// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.ButtonMap;
import frc.robot.RobotMap;
import frc.robot.RobotStats;
import frc.robot.commands.universalcommands.DumbFireSequence;
import frc.robot.commands.universalcommands.FireSequence;
import frc.robot.commands.universalcommands.RunMags;
import frc.robot.commands.universalcommands.SetFlywheelVelocity;

public class Shooter extends SubsystemBase {
    public FireSequence standardFireSequence;
    public FireSequence closeUpFireSequence;

    public Shooter() {}

    public void init() {
        RobotMap.rightFlywheel.setNeutralMode(NeutralMode.Coast);
        RobotMap.leftFlywheel.setNeutralMode(NeutralMode.Coast);
        RobotMap.rightFlywheel.set(ControlMode.Follower, RobotStats.leftFlywheelID);
        RobotMap.leftFlywheel.setInverted(true);
        RobotMap.rightFlywheel.setInverted(InvertType.OpposeMaster);
        RobotMap.leftFlywheel.configClosedLoopPeakOutput(0, RobotStats.maxPercentage);
        RobotMap.leftFlywheel.configPeakOutputForward(0.7);
        RobotMap.leftFlywheel.configPeakOutputReverse(0);
        RobotMap.leftFlywheel.configVoltageCompSaturation(11.7);
        RobotMap.leftFlywheel.enableVoltageCompensation(true);
        RobotMap.leftFlywheel.setSensorPhase(true);
        RobotMap.leftFlywheel.selectProfileSlot(0, 0);
        RobotMap.leftFlywheel.config_kF(0, 0.075);
        RobotMap.leftFlywheel.config_kP(0, 3);
        RobotMap.leftFlywheel.config_kI(0, 0.025);
        RobotMap.leftFlywheel.config_kD(0, 0);
        RobotMap.leftFlywheel.config_IntegralZone(0, RobotStats.shooterIntegralRange);
        standardFireSequence = new FireSequence(5000);
        closeUpFireSequence = new FireSequence(4500);
    }

    @Override
    public void periodic() {}

    public void teleopPeriodic() {
        if (ButtonMap.shoot()) {
            new DumbFireSequence().schedule();
        }
    }

    public static double unitsPer100MsToRpm(double units) {
        return (units * 600) / RobotStats.ticksPerShooterRotation;
    }

    public double getShooterRPM() {
        return unitsPer100MsToRpm(RobotMap.leftFlywheel.getSelectedSensorVelocity());
    }

    public static int rpmToUnitsPer100Ms(double rpm) {
        return (int) Math.round((rpm / 600) * RobotStats.ticksPerShooterRotation);
    }

    public Command fire(double rpm) {
        return new RunCommand(() -> {})
                .withInterrupt(() -> SetFlywheelVelocity.isAtTargetRPM())
                .andThen(new InstantCommand(() -> new RunMags(0.5, -1).schedule()));
    }
}
