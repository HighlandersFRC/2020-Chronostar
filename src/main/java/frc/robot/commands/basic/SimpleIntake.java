// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.MagIntake;

public class SimpleIntake extends CommandBase {

    public enum IntakeDirection {
        IN,
        OUT
    }

    private MagIntake magIntake;
    private static final double HIGH_MAG_POWER = 0.425, LOW_MAG_POWER = 0.4;
    private static final double LOW_INTAKE_POWER = 0.8, HIGH_INTAKE_POWER = 0.6;
    private IntakeDirection direction;

    public SimpleIntake(MagIntake magIntake, IntakeDirection direction) {
        this.magIntake = magIntake;
        this.direction = direction;
    }

    @Override
    public void initialize() {
        if (direction == IntakeDirection.IN) {
            magIntake.setIntakePercent(LOW_INTAKE_POWER, HIGH_INTAKE_POWER);
            magIntake.setMagPercent(LOW_MAG_POWER, HIGH_MAG_POWER);
        } else if (direction == IntakeDirection.OUT) {
            magIntake.setIntakePercent(-LOW_INTAKE_POWER, -HIGH_INTAKE_POWER);
            magIntake.setMagPercent(-LOW_MAG_POWER, -HIGH_MAG_POWER);
        }
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
