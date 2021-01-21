package frc.robot.commands.defaults;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.OI;
import frc.robot.subsystems.Drive;

public class DriveDefault extends CommandBase {
    private Drive drive;

    public DriveDefault(Drive drive) {
        this.drive = drive;
        addRequirements(this.drive);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        drive.arcadeDrive(OI.getDriverLeftY() / 2, OI.getDriverRightX() / 3);
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
