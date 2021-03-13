// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import frc.robot.subsystems.MagIntake;

public class CancelMagazine extends InstantCommand {

    private MagIntake magIntake;

    public CancelMagazine(MagIntake magIntake) {
        this.magIntake = magIntake;
        addRequirements(this.magIntake);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        magIntake.setMagPercent(0, 0);
        magIntake.setIntakePercent(0, 0);
    }

    @Override
    public void end(boolean interrupted) {}
}
