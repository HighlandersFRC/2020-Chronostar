// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import frc.robot.subsystems.MagIntake;

public class EjectMagazine extends InstantCommand {

    private MagIntake magIntake;

    public EjectMagazine(MagIntake magIntake) {
        this.magIntake = magIntake;
        addRequirements(this.magIntake);
    }

    @Override
    public void initialize() {
        magIntake.setMagazine(0.5, 1);
        magIntake.setIntake(0, 0.8);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {}
}
