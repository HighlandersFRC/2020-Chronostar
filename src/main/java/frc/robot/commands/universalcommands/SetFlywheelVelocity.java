// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.*;
import frc.robot.subsystems.Shooter;

public class SetFlywheelVelocity extends CommandBase {

    private static double targetVelocity;

    public SetFlywheelVelocity(double velocity) {
        if (velocity > RobotStats.maxRPM) {
            velocity = RobotStats.maxRPM;
        }
        targetVelocity = velocity;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        SmartDashboard.putNumber("target velocity", targetVelocity);
        SmartDashboard.putNumber("current velocity", RobotMap.shooter.getShooterRPM());
        RobotMap.leftFlywheel.set(ControlMode.Velocity, Shooter.rpmToUnitsPer100Ms(5000));
    }

    public static boolean isAtTargetRPM() {
        return Math.abs(RobotMap.shooter.getShooterRPM() - targetVelocity) < 100;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {}

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Math.abs(RobotMap.shooter.getShooterRPM() - targetVelocity) < 100;
    }
}
