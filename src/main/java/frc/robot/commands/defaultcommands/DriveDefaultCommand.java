/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.defaultcommands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.OI;
import frc.robot.subsystems.Drive;

public class DriveDefaultCommand extends CommandBase {
    private Drive drive;
    private double turn;
    private double rightPower;
    private double leftPower;
    private double finalLeft = 0;
    private double finalRight = 0;

    public DriveDefaultCommand(Drive drive) {
        this.drive = drive;
        addRequirements(this.drive);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (Math.abs(OI.getDriverRightX()) > 0.1) {
            turn = OI.getDriverRightX();
        } else {
            turn = 0;
        }

        leftPower = OI.getDriverLeftY();
        rightPower = OI.getDriverLeftY() + turn;

        if (Math.abs(leftPower) > 1) {
            finalRight = rightPower / Math.abs(leftPower);
            finalLeft = leftPower / Math.abs(leftPower);
        } else if (Math.abs(rightPower) > 1) {
            finalRight = rightPower / Math.abs(rightPower);
            finalLeft = leftPower / Math.abs(rightPower);
        } else {
            finalRight = rightPower;
            finalLeft = leftPower;
        }

        drive.setRightPercent(finalRight);
        Drive.leftDriveLead.set(ControlMode.PercentOutput, finalLeft);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
