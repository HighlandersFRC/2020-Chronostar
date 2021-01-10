// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.defaults;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.OI;
import frc.robot.subsystems.MagIntake;

public class MagIntakeDefault extends CommandBase {

    private MagIntake magIntake;

    public MagIntakeDefault(MagIntake magIntake) {
        this.magIntake = magIntake;
        addRequirements(this.magIntake);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        // Intake stuff
        magIntake.setIntake(0, 0);
        magIntake.intakePistonDown();
        if (OI.operatorRT.get()) {
            magIntake.setIntake(0.8, -0.6);
        } else if (OI.operatorLT.get()) {
            magIntake.setIntake(-0.8, 0.6);
        } else {
            magIntake.setIntake(0, 0);
        }

        // Magazine stuff
        SmartDashboard.putBoolean("beam break 1", magIntake.getBeamBreak1());
        SmartDashboard.putBoolean("beam break 2", magIntake.getBeamBreak2());
        SmartDashboard.putBoolean("beam break 3", magIntake.getBeamBreak3());
        if (OI.operatorLT.get()) {
            // magIntake.setMagPercent(-0.4, -0.2);
        } else if (magIntake.getBeamBreak1()
                & magIntake.getBeamBreak2()
                & magIntake.getBeamBreak3()
                & OI.operatorRT.get()) {
            // magIntake.setMagPercent(0, 0);
        } else if (!magIntake.getBeamBreak3() & OI.operatorRT.get()) {
            // magIntake.setMagPercent(0, 0);
        } else if (!magIntake.getBeamBreak1() & magIntake.getBeamBreak2()) {
            // magIntake.setMagPercent(0.5, 0);
        } else if (!magIntake.getBeamBreak2() & magIntake.getBeamBreak1()) {
            // magIntake.setMagPercent(-0.3, 0.4);
        } else if (!magIntake.getBeamBreak2() & !magIntake.getBeamBreak1()) {
            // magIntake.setMagPercent(0.3, 0.25);
        } else if (!magIntake.getBeamBreak3() & !magIntake.getBeamBreak2()) {
            // magIntake.setMagPercent(0.2, 0);
        } else if (!magIntake.getBeamBreak2() & magIntake.getBeamBreak3()) {
            // magIntake.setMagPercent(0.3, -0.4);
        } else {
            // magIntake.setMagPercent(-0.3, 0.2);
        }
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
