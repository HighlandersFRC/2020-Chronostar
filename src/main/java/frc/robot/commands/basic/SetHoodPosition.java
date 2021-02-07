package frc.robot.commands.basic;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Hood;

public class SetHoodPosition extends CommandBase {

    private int hoodPosCount = 0;
    private Hood hood;
    private double target;

    public SetHoodPosition(Hood hood, double target) {
        this.hood = hood;
        addRequirements(hood);
        this.target = target;
    }

    @Override
    public void initialize() {
        hoodPosCount = 0;
    }

    @Override
    public void execute() {
        hoodPosCount++;
        SmartDashboard.putNumber("HoodPosition", hood.getHoodPosition());
        hood.setHoodTarget(target);
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return true;
    }
}
