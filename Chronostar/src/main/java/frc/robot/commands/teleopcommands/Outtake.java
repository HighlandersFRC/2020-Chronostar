// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.teleopcommands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.*;

public class Outtake extends CommandBase {
    /** Creates a new Outtake. */
    public Outtake() {
        // Use addRequirements() here to declare subsystem dependencies.
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        RobotMap.lowMag.set(ControlMode.PercentOutput, -1);
        RobotMap.highMag.set(0.75);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {}

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        RobotMap.lowMag.set(ControlMode.PercentOutput, 0);
        RobotMap.highMag.set(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return ButtonMap.getOperatorLeftTrigger() <= 0.5;
    }
}
