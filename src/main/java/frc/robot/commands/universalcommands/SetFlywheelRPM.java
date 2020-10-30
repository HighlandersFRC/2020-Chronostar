// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.*;

public class SetFlywheelRPM extends CommandBase {

    private static double velocity;
    private static double position;

    public SetFlywheelRPM(double rpm, double hoodPos) {
        velocity = rpm;
        position = hoodPos;
    }

    @Override
    public void initialize() {
        RobotMap.leftFlywheel.set(ControlMode.Velocity, Constants.rpmToUnitsPer100Ms(velocity));
    }

    @Override
    public void execute() {
        if (isAtTargetRPM()) {
            new SetMags(0.5, -1).schedule();
            RobotMap.intake2Motor.set(ControlMode.PercentOutput, 0.8);
        } else {
            new SetMags(0, 0).schedule();
            RobotMap.intake2Motor.set(ControlMode.PercentOutput, 0);
            RobotMap.hood.setHoodTarget(position);
        }
    }

    @Override
    public void end(boolean interrupted) {
        RobotMap.leftFlywheel.set(ControlMode.Velocity, 0);
        new SetMags(0, 0).schedule();
        RobotMap.intake2Motor.set(ControlMode.PercentOutput, 0);
    }

    public boolean isFinished() {
        return !ButtonMap.shoot();
    }

    public static boolean isAtTargetRPM() {
        return velocity - RobotMap.shooter.getShooterRPM() <= 200
                && velocity - RobotMap.shooter.getShooterRPM() >= -200;
    }
}
