package frc.robot.commands.basic;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Peripherals;
import frc.robot.tools.controlloops.PID;

public class NavxTurn extends CommandBase {

    private PID pid;
    private final double p = 0.01;
    private final double i = 0.0005;
    private final double d = 0.01;
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
        SmartDashboard.putBoolean("finished navxturn", false);
        // peripherals.zeroNavx();
        pid = new PID(p, i, d);
        pid.setSetPoint(target);
        pid.setMinOutput(-0.3);
        pid.setMaxOutput(0.3);
        peripherals.zeroNavx();
    }

    @Override
    public void execute() {
        pid.updatePID(peripherals.getNavxAngle());
        SmartDashboard.putNumber("turn pid output", pid.getResult());
        drive.setLeftPercent(pid.getResult());
        drive.setRightPercent(-pid.getResult());
    }

    @Override
    public void end(boolean interrupted) {
        SmartDashboard.putBoolean("finished navxturn", true);
        drive.setLeftPercent(0);
        drive.setRightPercent(0);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(target - peripherals.getNavxAngle()) < 5;
    }
}
