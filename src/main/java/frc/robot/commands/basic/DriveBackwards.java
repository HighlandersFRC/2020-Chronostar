// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Drive;
import frc.robot.tools.controlloops.PID;

public class DriveBackwards extends CommandBase {
    private Drive drive;
    private PID pid;
    private double kP = 0.05;
    private double kI = 0.005;
    private double kD = 0.0;

    /** Creates a new DriveBackwards. */
    public DriveBackwards(Drive drive) {
        this.drive = drive;

        addRequirements(this.drive);
        // Use addRequirements() here to declare subsystem dependencies.
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        SmartDashboard.putBoolean("Finished Drive Backwards", false);
        pid = new PID(kP, kI, kD);
        pid.setSetPoint(17);
        pid.setMinOutput(-0.4);
        pid.setMaxOutput(0.4);
    }
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        pid.updatePID(drive.getDriveFeet());
        drive.setLeftPercent(-pid.getResult());
        drive.setRightPercent(-pid.getResult());
        SmartDashboard.putNumber("drive pid output", pid.getResult());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        drive.setRightPercent(0);
        drive.setLeftPercent(0);
        SmartDashboard.putBoolean("Finished Drive Backwards", true);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (Math.abs(drive.getDriveFeet() + 15) < 0.5) {
            return true;
        }

        return false;
    }
}
