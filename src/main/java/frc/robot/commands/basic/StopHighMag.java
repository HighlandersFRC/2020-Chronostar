// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import frc.robot.subsystems.MagIntake;

public class StopHighMag extends InstantCommand {

    private MagIntake magIntake;

    public StopHighMag(MagIntake magIntake) {
        this.magIntake = magIntake;
    }

    @Override
    public void initialize() {
        magIntake.setHighMagPercent(0);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {}
}
