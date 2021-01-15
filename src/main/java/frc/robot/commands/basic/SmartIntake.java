// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.MagIntake;

public class SmartIntake extends CommandBase {

    private MagIntake magIntake;
    private double highMagTimer = 0.0, lowMagTimer = 0.0;
    private static final double HIGH_MAG_POWER = 0.425, LOW_MAG_POWER = 0.0;
    private static final double LOOP_TIME = 0.02;
    private IntakeDirection direction;

    public enum IntakeDirection {
        in,
        out
    }

    public SmartIntake(MagIntake magIntake, IntakeDirection direction) {
        this.magIntake = magIntake;
        this.direction = direction;
        addRequirements(magIntake);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        if (direction == IntakeDirection.in) {
            magIntake.setIntake(0.8, 0.6);

            // Magazine motor time countdown
            if (highMagTimer > 0) {
                magIntake.setHighMagPercent(HIGH_MAG_POWER);
                highMagTimer -= LOOP_TIME;
            } else {
                magIntake.setHighIntakePercent(0);
                highMagTimer = 0;
            }
            if (lowMagTimer > 0) {
                magIntake.setLowIntakePercent(LOW_MAG_POWER);
                lowMagTimer -= LOOP_TIME;
            } else {
                magIntake.setLowIntakePercent(0);
                lowMagTimer = 0;
            }

            // Checking beam breaks to initialize countdowns
            if (magIntake.getBeamBreak1()) {
                lowMagTimer = 0.15;
            }
            if (magIntake.getBeamBreak3()) {
                highMagTimer = 0;
            } else if (magIntake.getBeamBreak2()) {
                lowMagTimer = 0.15;
                highMagTimer = 0.15;
            }
        } else if (direction == IntakeDirection.out) {
            magIntake.setIntake(-0.8, 0.6);
            magIntake.setMagPercent(-0.4, -0.2);
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