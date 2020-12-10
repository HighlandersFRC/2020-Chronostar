// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import frc.robot.RobotMap;

public class SetHoodPosition extends InstantCommand {

    double position;

    public SetHoodPosition(double pos) {
        addRequirements(RobotMap.hood);
        position = pos;
    }

    @Override
    public void initialize() {
        RobotMap.hood.setHoodPosition(position);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {}
}
