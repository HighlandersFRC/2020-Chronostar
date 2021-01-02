// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import frc.robot.subsystems.MagIntake;

public class SetLowMag extends InstantCommand {

    private MagIntake magIntake;
    private boolean direction;
    private final double power = 0.4;

    public SetLowMag(MagIntake magIntake, boolean direction) {
        this.magIntake = magIntake;
        this.direction = direction;
    }

    @Override
    public void initialize() {
        magIntake.setLowMagPercent(direction ? power : -power);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {}
}
