// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import frc.robot.RobotMap;

public class SetLowIntakePercent extends InstantCommand {

    private double percent;

    public SetLowIntakePercent(double percent) {
        this.percent = percent;
    }

    @Override
    public void initialize() {
        RobotMap.intake.setLowIntakePercent(percent);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {}
}
