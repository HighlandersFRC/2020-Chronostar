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
import frc.robot.RobotMap;
import frc.robot.subsystems.Drive;

public class DriveDefaultCommand extends CommandBase {
    /** Creates a new DriveDefaultCommand. */
    public DriveDefaultCommand() {
        addRequirements(RobotMap.drive);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double turn;
        double rightPower;
        double leftPower;
        double finalLeft = 0;
        double finalRight = 0;

        // drive code

        if (Math.abs(OI.driverController.getRawAxis(4)) > 0.1) {
            turn = (OI.driverController.getRawAxis(4));
        } else {
            turn = 0;
        }

        leftPower = OI.driverController.getRawAxis(1) - turn;
        rightPower = OI.driverController.getRawAxis(1) + turn;

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

        Drive.rightDriveLead.set(ControlMode.PercentOutput, finalRight);
        Drive.leftDriveLead.set(ControlMode.PercentOutput, finalLeft);

        Drive.rightDriveLead.set(ControlMode.PercentOutput, turn);
        Drive.leftDriveLead.set(ControlMode.PercentOutput, -turn);
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
