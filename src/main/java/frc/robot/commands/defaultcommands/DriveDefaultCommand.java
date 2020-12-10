// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.defaultcommands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.OI;
import frc.robot.RobotMap;

public class DriveDefaultCommand extends CommandBase {

    private boolean arcade;

    public DriveDefaultCommand(boolean isArcade) {
        arcade = isArcade;
        addRequirements(RobotMap.drive);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        if (arcade) {
            RobotMap.drive.arcadeDrive(OI.getDriverLeftY(), OI.getDriverRightX());
        } else {
            RobotMap.drive.tankDrive(OI.getDriverLeftY(), OI.getDriverRightY());
        }
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
