package frc.robot.commands.basic;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Drive;
import frc.robot.tools.controlloops.PID;

public class DriveAgain extends CommandBase {
    private Drive drive;
    private double target;
    private PID pid;
    private double kP = 0.05;
    private double kI = 0.005;
    private double kD = 0.0;
    /** Creates a new DriveForwards. */
    public DriveAgain(Drive drive) {
        this.drive = drive;
        addRequirements(drive);

        // Use addRequirements() here to declare subsystem dependencies.
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        SmartDashboard.putBoolean("drive forwards", true);
        pid = new PID(kP, kI, kD);
        pid.setSetPoint(10);
        pid.setMinOutput(-0.7);
        pid.setMaxOutput(0.7);
        drive.resetEncoder();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        pid.updatePID(drive.getDriveFeet());
        SmartDashboard.putNumber("drive forward output", pid.getResult());
        SmartDashboard.putNumber("getdrivefeet", drive.getDriveFeet());
        drive.setLeftPercent(pid.getResult());
        drive.setRightPercent(pid.getResult());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        SmartDashboard.putBoolean("drive forwards", true);
        drive.setRightPercent(0);
        drive.setLeftPercent(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (Math.abs(drive.getDriveFeet() - 10) < 0.5) {
            return true;
        }
        return false;
    }
}
