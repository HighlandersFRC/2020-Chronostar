// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.defaults;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Hood;

public class HoodDefault extends CommandBase {

    private Hood hood;

    public HoodDefault(Hood hood) {
        this.hood = hood;
        addRequirements(this.hood);
    }

    @Override
    public void initialize() {
        hood.setHoodTarget(0);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}