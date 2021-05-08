package frc.robot.commands.basic;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Hood;
import frc.robot.tools.controlloops.PID;

public class SetHoodPosition extends CommandBase {

    private int hoodPosCount = 0;
    private Hood hood;
    private double target;
    private PID pid;
    private final double kP = 0.17;
    private final double kI = 0.005;
    private final double kD = 0.0;

    public SetHoodPosition(Hood hood, double target) {
        this.hood = hood;
        addRequirements(hood);
        this.target = target;
    }

    @Override
    public void initialize() {
        SmartDashboard.putBoolean("finished hoodPID", false);
        pid = new PID(kP, kI, kD);
        pid.setSetPoint(target);
        pid.setMaxOutput(0.5);
        pid.setMinOutput(-0.5);
        hoodPosCount = 0;
    }

    @Override
    public void execute() {
        pid.updatePID(hood.getHoodPosition());
        SmartDashboard.putNumber("Hood PID Output", pid.getResult());
        hood.setHoodPercent(pid.getResult());
    }

    @Override
    public void end(boolean interrupted) {
        hood.setHoodPercent(0);
        SmartDashboard.putBoolean("finished hoodPID", true);
    }

    @Override
    public boolean isFinished() {
        if (Math.abs(target - hood.getHoodPosition()) < 0.05) {
            return true;
        }
        return false;
    }
}
