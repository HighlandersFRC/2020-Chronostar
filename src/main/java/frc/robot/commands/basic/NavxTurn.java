package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Peripherals;
import frc.robot.tools.controlloops.PID;

public class NavxTurn extends CommandBase {

    private PID pid;
    private final double p = 0.015;
    private final double i = 0;
    private final double d = 0.2;
    private Peripherals peripherals;
    private Drive drive;
    private double target;

    public NavxTurn(Drive drive, Peripherals peripherals, double target) {
        this.drive = drive;
        this.peripherals = peripherals;
        this.target = target;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        pid = new PID(p, i, d);
        pid.setSetPoint(target);
        pid.setMinOutput(-0.5);
        pid.setMaxOutput(0.5);
    }

    @Override
    public void execute() {
        pid.updatePID(peripherals.getNavxAngle());
        drive.setLeftPercent(pid.getResult());
        drive.setRightPercent(-pid.getResult());
    }

    @Override
    public void end(boolean interrupted) {
        drive.setLeftPercent(0);
        drive.setRightPercent(0);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(target - peripherals.getNavxAngle()) < 5;
    }
}
