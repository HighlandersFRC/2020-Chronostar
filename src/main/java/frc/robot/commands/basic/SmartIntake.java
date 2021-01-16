// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.OI;
import frc.robot.subsystems.MagIntake;

public class SmartIntake extends CommandBase {
    private MagIntake magIntake;

    private static final double INTAKING_POWER = 0.8;
    private static final double MIDDLE_BREAK_3_POWER = 0.3;
    private static final double LOW_MAG_BREAK_1_POWER = 0.5;
    private static final double HIGH_MAG_BREAK_1_POWER = 0.2;
    private static final double MIDDLE_WHEEL_BREAK_1_POWER = 0.6;
    private static final double LOW_MAG_BREAK_2_NO_1_POWER = -0.2;
    private static final double HIGH_MAG_BREAK_2_NO_1_POWER = 0.4;
    private static final double LOW_MAG_BREAK_2_AND_1_POWER = 0.25;
    private static final double HIGH_MAG_BREAK_2_AND_1_POWER = 0.35;
    private static final double LOW_MAG_BREAK_2_AND_3_POWER = 0.2;
    private static final double LOW_MAG_BREAK_2_NO_3_POWER = 0.3;
    private static final double HIGH_MAG_BREAK_2_NO_3_POWER = -0.4;
    private static final double LOW_MAG_ELSE_POWER = -0.3;
    private static final double HIGH_MAG_ELSE_POWER = 0.2;

    public SmartIntake(MagIntake magIntake) {
        this.magIntake = magIntake;
        addRequirements(magIntake);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        if (OI.operatorRB.get()) {
            magIntake.setIntakePercent(INTAKING_POWER, 0);
        }
        if (magIntake.getBeamBreak(1)
                & magIntake.getBeamBreak(2)
                & magIntake.getBeamBreak(3)
                & !OI.operatorController.getBumper(Hand.kLeft)) {
            magIntake.setMagPercent(0, 0);
        } else if (!magIntake.getBeamBreak(3) & !OI.operatorController.getBumper(Hand.kLeft)) {
            magIntake.setMagPercent(0, 0);
            magIntake.setIntakePercent(0, MIDDLE_BREAK_3_POWER);
        } else if (!magIntake.getBeamBreak(1) & magIntake.getBeamBreak(2)) {
            magIntake.setMagPercent(LOW_MAG_BREAK_1_POWER, HIGH_MAG_BREAK_1_POWER);
            magIntake.setIntakePercent(0, MIDDLE_WHEEL_BREAK_1_POWER);
        } else if (!magIntake.getBeamBreak(2) & magIntake.getBeamBreak(1)) {
            magIntake.setMagPercent(LOW_MAG_BREAK_2_NO_1_POWER, HIGH_MAG_BREAK_2_NO_1_POWER);
        } else if (!magIntake.getBeamBreak(2) & !magIntake.getBeamBreak(1)) {
            magIntake.setMagPercent(LOW_MAG_BREAK_2_AND_1_POWER, HIGH_MAG_BREAK_2_AND_1_POWER);
        } else if (!magIntake.getBeamBreak(3) & !magIntake.getBeamBreak(2)) {
            magIntake.setMagPercent(LOW_MAG_BREAK_2_AND_3_POWER, 0);
        } else if (!magIntake.getBeamBreak(2) & magIntake.getBeamBreak(3)) {
            magIntake.setMagPercent(LOW_MAG_BREAK_2_NO_3_POWER, HIGH_MAG_BREAK_2_NO_3_POWER);
        } else {
            magIntake.setMagPercent(LOW_MAG_ELSE_POWER, HIGH_MAG_ELSE_POWER);
        }
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
