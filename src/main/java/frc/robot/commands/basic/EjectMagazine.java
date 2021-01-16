// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import frc.robot.subsystems.MagIntake;

public class EjectMagazine extends InstantCommand {

    private MagIntake magIntake;
    private final double LOW_MAG_PERCENT = 0.25;
    private final double HIGH_MAG_PERCENT = 0.8;
    private final double HIGH_INTAKE_PERCENT = 0.8;

    public EjectMagazine(MagIntake magIntake) {
        this.magIntake = magIntake;
        addRequirements(this.magIntake);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        magIntake.setMagPercent(LOW_MAG_PERCENT, HIGH_MAG_PERCENT);
        magIntake.setIntakePercent(0, HIGH_INTAKE_PERCENT);
    }

    @Override
    public void end(boolean interrupted) {}
}
