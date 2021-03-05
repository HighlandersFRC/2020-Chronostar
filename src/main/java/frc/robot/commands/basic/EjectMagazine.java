// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.robot.subsystems.MagIntake;

public class EjectMagazine extends CommandBase {

    private MagIntake magIntake;
    private final double LOW_MAG_PERCENT = 0.4;
    private final double HIGH_MAG_PERCENT = 1;
    private final double HIGH_INTAKE_PERCENT = 0.8;
    WaitCommand waitCommand;
    private int counter = 0;

    public EjectMagazine(MagIntake magIntake) {
        this.magIntake = magIntake;
        addRequirements(this.magIntake);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        counter++;
        magIntake.setMagPercent(LOW_MAG_PERCENT, HIGH_MAG_PERCENT);
        magIntake.setIntakePercent(0, HIGH_INTAKE_PERCENT);
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
