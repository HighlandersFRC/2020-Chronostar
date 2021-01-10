// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.MagIntake;

public class Intake extends CommandBase {
    private MagIntake magIntake;

    public Intake(MagIntake magIntake) {
        this.magIntake = magIntake;
        addRequirements(magIntake);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        magIntake.setIntake(0.8, 0.6);
        
        if (magIntake.getBeamBreak1() || magIntake.getBeamBreak2() || magIntake.getBeamBreak3()) {
            if (magIntake.getBeamBreak3()) {
                new StopHighMag(magIntake).schedule();
            } else if (magIntake.getBeamBreak2()) {
                new BumpHighMag(magIntake).schedule();
                new BumpLowMag(magIntake).schedule();
            }
            if (magIntake.getBeamBreak1()) {
                if (catchCounter <= 50) {
                    new BumpLowMag(magIntake).schedule();
                } else {
                    if (tryCounter <= 25) {
                        tryCounter++;
                    } else {
                        new BumpLowMag(magIntake).schedule();
                        catchCounter = 0;
                        tryCounter = 0;
                    }
                }
                catchCounter++;
            } else {
                catchCounter = 0;
            }
        } else {
            magIntake.setMagazine(0, 0);
        }
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return true;
    }
}
