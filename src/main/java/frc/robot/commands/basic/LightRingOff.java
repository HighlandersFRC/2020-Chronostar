// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.LightRing;

public class LightRingOff extends CommandBase {
    private LightRing lightRing;

    public LightRingOff(LightRing lightRing) {
        this.lightRing = lightRing;
        addRequirements(lightRing);
    }

    @Override
    public void initialize() {
        lightRing.turnVisionOff();
    }

    @Override
    public void execute() {}

    @Override
    public void end(final boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
