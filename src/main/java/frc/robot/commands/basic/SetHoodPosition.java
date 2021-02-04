package frc.robot.commands.basic;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Hood;

public class SetHoodPosition extends CommandBase {

    private Hood hood;
    private double target;

    public SetHoodPosition(Hood hood, double target) {
        this.hood = hood;
        this.target = target;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        hood.setHoodTarget(target);
    }

    @Override
    public void end(boolean interrupted) {
        SmartDashboard.putBoolean("VisionAlignment PID", true);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
