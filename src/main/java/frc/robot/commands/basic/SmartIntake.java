// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.MagIntake;
import frc.robot.subsystems.MagIntake.BeamBreakID;

public class SmartIntake extends CommandBase {

    public enum IntakeDirection {
        IN,
        OUT
    }

    private MagIntake magIntake;
    private double highMagTimer = 0.0, lowMagTimer = 0.0;
    private static final double HIGH_MAG_POWER = 0.425, LOW_MAG_POWER = 0.4;
    private static final double LOOP_TIME = 0.02;
    private static final double PULSE_DURATION = 0.15;
    private static final double LOW_INTAKE_POWER = 0.8, HIGH_INTAKE_POWER = 0.6;
    private IntakeDirection direction;

    public SmartIntake(MagIntake magIntake, IntakeDirection direction) {
        this.magIntake = magIntake;
        this.direction = direction;
        addRequirements(magIntake);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        if (direction == IntakeDirection.IN) {
            magIntake.setIntakePercent(LOW_INTAKE_POWER, HIGH_INTAKE_POWER);

            // Magazine motor time countdown
            if (highMagTimer > 0) {
                magIntake.setHighMagPercent(HIGH_MAG_POWER);
                highMagTimer -= LOOP_TIME;
            } else {
                magIntake.setHighMagPercent(0);
                highMagTimer = 0;
            }
            if (lowMagTimer > 0) {
                magIntake.setLowMagPercent(LOW_MAG_POWER);
                lowMagTimer -= LOOP_TIME;
            } else {
                magIntake.setLowMagPercent(0);
                lowMagTimer = 0;
            }

            // Checking beam breaks to initialize countdowns
            if (magIntake.getBeamBreak(BeamBreakID.ONE)) {
                lowMagTimer = PULSE_DURATION;
            }
            if (magIntake.getBeamBreak(BeamBreakID.THREE)) {
                highMagTimer = 0;
            } else if (magIntake.getBeamBreak(BeamBreakID.TWO)) {
                lowMagTimer = PULSE_DURATION;
                highMagTimer = PULSE_DURATION;
            }
        } else if (direction == IntakeDirection.OUT) {
            magIntake.setIntakePercent(-LOW_INTAKE_POWER, -HIGH_INTAKE_POWER);
            magIntake.setMagPercent(-HIGH_MAG_POWER, -LOW_MAG_POWER);
        }
    }

    @Override
    public void end(boolean interrupted) {
        highMagTimer = 0;
        lowMagTimer = 0;
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
