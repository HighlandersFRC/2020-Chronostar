// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.*;

public class Fire extends CommandBase {

    private double targetVelocity;
    private double startTime;

    public Fire(double velocity) {
        targetVelocity = velocity;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        RobotMap.shooter.setVelocity(targetVelocity);
        startTime = Timer.getFPGATimestamp();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (RobotMap.shooter.getShooterRPM() >= targetVelocity + 10
                || RobotMap.shooter.getShooterRPM() <= targetVelocity - 10) {
            RobotMap.lowMag.set(ControlMode.PercentOutput, 0.2);
            RobotMap.highMag.set(-0.9);
        } else {
            RobotMap.lowMag.set(ControlMode.PercentOutput, 0);
            RobotMap.highMag.set(0);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        RobotMap.shooter.setVelocity(0);
        RobotMap.lowMag.set(ControlMode.PercentOutput, 0);
        RobotMap.highMag.set(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return !ButtonMap.shoot();
    }
}
