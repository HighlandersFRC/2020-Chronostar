// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Hood;

public class SetHoodPosition extends CommandBase {

    private final Hood hood;
    private final double position;

    public SetHoodPosition(final Hood hood, final double position) {
        this.hood = hood;
        this.position = position;
        addRequirements(this.hood);
    }

    @Override
    public void initialize() {
        hood.setHoodTarget(position);
    }

    @Override
    public void execute() {}

    @Override
    public void end(final boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return true;
    }
}
