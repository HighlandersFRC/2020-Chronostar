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
        RobotMap.leftFlywheel.set(ControlMode.Velocity, Shooter.rpmToUnitsPer100Ms(velocity));
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {}

    public boolean isFinished() {
        return isAtTargetRPM();
    }

    public static boolean isAtTargetRPM() {
        return Math.abs(velocity - RobotMap.shooter.getShooterRPM()) >= 100;
    }
}
