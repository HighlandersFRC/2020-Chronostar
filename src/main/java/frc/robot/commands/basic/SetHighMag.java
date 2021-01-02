// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import frc.robot.subsystems.MagIntake;

public class SetHighMag extends InstantCommand {

    private MagIntake magIntake;
    private boolean direction;
    private final double power = 0.425;

    public SetHighMag(MagIntake magIntake, boolean direction) {
        this.magIntake = magIntake;
        this.direction = direction;
    }

    @Override
    public void initialize() {
        magIntake.setHighMagPercent(direction ? -power : power);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {}
}
