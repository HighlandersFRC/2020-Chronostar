// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.LightRing;

public class VisionAlignment extends CommandBase {

    private LightRing lightRing;
    private Drive drive;

    public VisionAlignment(LightRing lightRing, Drive drive) {
        this.drive = drive;
        this.lightRing = lightRing;
        addRequirements(this.drive, this.lightRing);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        lightRing.turnOn();
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
