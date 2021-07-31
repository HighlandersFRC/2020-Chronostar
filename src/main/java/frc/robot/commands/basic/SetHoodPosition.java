package frc.robot.commands.basic;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Hood;
import frc.robot.tools.controlloops.PID;

public class SetHoodPosition extends CommandBase {

    private PID pid;
    private double kP = 0.17;
    private double kI = 0.005;
    private double kD = 0.0;
    private Hood hood;
    private double target;
    private int hoodCounter = 0;

    public SetHoodPosition(Hood hood, double target) {
        this.hood = hood;
        addRequirements(hood);
        this.target = target;
    }

    @Override
    public void initialize() {
        hoodCounter = 0;
        SmartDashboard.putBoolean("finished hoodPID", false);
        pid = new PID(kP, kI, kD);
        pid.setSetPoint(target);
        pid.setMaxOutput(0.5);
        pid.setMinOutput(-0.5);
    }

    @Override
    public void execute() {
        hoodCounter++;
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
        return true;
    }
}
