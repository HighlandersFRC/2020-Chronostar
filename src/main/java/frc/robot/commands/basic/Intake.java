// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.OI;
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
        if (magIntake.getBeamBreak1()
                & magIntake.getBeamBreak2()
                & magIntake.getBeamBreak3()
                & OI.getOperatorLeftTrigger() < 0.5) {
            magIntake.setMagazine(0, 0);
        } else if (!magIntake.getBeamBreak3() & OI.getOperatorLeftTrigger() < 0.5) {
            magIntake.setMagazine(0, 0);
        } else if (!magIntake.getBeamBreak1() & magIntake.getBeamBreak2()) {
            magIntake.setMagazine(0.5, 0);
        } else if (!magIntake.getBeamBreak2() & magIntake.getBeamBreak1()) {
            magIntake.setMagazine(0.3, 0.4);
        } else if (!magIntake.getBeamBreak2() & !magIntake.getBeamBreak1()) {
            magIntake.setMagazine(0.25, 0.3);
        } else if (!magIntake.getBeamBreak3() & !magIntake.getBeamBreak2()) {
            magIntake.setMagazine(0.2, 0);
        } else if (!magIntake.getBeamBreak2() & magIntake.getBeamBreak3()) {
            magIntake.setMagazine(0.3, 0.4);
        } else {
            magIntake.setMagazine(0.3, 0.2);
        }
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return true;
    }
}
