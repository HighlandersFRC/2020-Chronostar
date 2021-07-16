// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Drive;

public class DriveBackwards extends CommandBase {
    private Drive drive;

    /** Creates a new DriveBackwards. */
    public DriveBackwards(Drive drive) {
        this.drive = drive;

        addRequirements(drive);
        // Use addRequirements() here to declare subsystem dependencies.
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        drive.init();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        drive.setSpeed(2);
        drive.setMotionMagicLeft(2);
        drive.setMotionMagicRight(2);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        drive.setRightPercent(0);
        drive.setLeftPercent(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
