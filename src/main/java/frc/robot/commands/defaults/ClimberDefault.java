// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.defaults;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.OI;
import frc.robot.subsystems.Climber;

public class ClimberDefault extends CommandBase {
    private Climber climber;
    /** Creates a new ClimberDefault. */
    public ClimberDefault() {
        this.climber = climber;
        addRequirements(this.climber);
        // Use addRequirements() here to declare subsystem dependencies.
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        climber.climberPistonDown();
        if (OI.operatorController.getYButton()) {
            System.out.println("aye bruh");
            climber.climberMotorUp();
        } else if (OI.operatorController.getAButton()) {
            climber.climberMotorDown();
        } else {
            climber.climberMotorStop();
        }
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
