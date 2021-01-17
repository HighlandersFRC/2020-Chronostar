package frc.robot.commands.defaults;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Hood;

public class HoodDefault extends CommandBase {

    private Hood hood;

    public HoodDefault(Hood hood) {
        this.hood = hood;
        addRequirements(hood);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
