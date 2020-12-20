// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Magazine;

public class SetHighMag extends CommandBase {

    private double power;

    public SetHighMag(double power) {
        this.power = power;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        Magazine.highMag.set(power);
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
        return true;
    }
}
