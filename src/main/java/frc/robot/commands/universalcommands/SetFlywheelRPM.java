// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.RobotMap;
import frc.robot.subsystems.Shooter;

public class SetFlywheelRPM extends CommandBase {

    private static double velocity;

    public SetFlywheelRPM(double rpm) {
        velocity = rpm;
    }

    @Override
    public void initialize() {
        RobotMap.leftFlywheel.set(ControlMode.Velocity, Shooter.rpmToUnitsPer100Ms(rpm));
    }

    @Override
    public void execute() {
        if (isAtTargetRPM()) {
            new RunMags(0.5, -1).schedule();
            RobotMap.intake2Motor.set(ControlMode.PercentOutput, -0.8);
        } else {
            new RunMags(0, 0).schedule();
            RobotMap.intake2Motor.set(ControlMode.PercentOutput, 0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        RobotMap.leftFlywheel.set(ControlMode.Velocity, 0);
        new RunMags(0, 0).schedule();
        RobotMap.intake2Motor.set(ControlMode.PercentOutput, 0);
    }

    public boolean isFinished() {
        return !ButtonMap.shoot();
    }

    public static boolean isAtTargetRPM() {
        return Math.abs(rpm - RobotMap.shooter.getShooterRPM()) <= 200;
    }
}
