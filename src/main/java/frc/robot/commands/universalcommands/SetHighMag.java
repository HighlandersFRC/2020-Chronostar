// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import frc.robot.RobotMap;

public class SetHighMag extends InstantCommand {

    private double power;

    public SetHighMag(double power) {
        this.power = power;
    }

    @Override
    public void initialize() {
        RobotMap.highMag.set(power);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {}
}
