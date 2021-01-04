// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.OI;
import frc.robot.subsystems.MagIntake;

public class Intake extends CommandBase {
    /** Creates a new Intake. */
    private MagIntake magIntake;

    public Intake(MagIntake magIntake) {
        this.magIntake = magIntake;
        addRequirements(magIntake);
        // Use addRequirements() here to declare subsystem dependencies.
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (OI.operatorRB.get()) {
            magIntake.setIntake(0.8, -0.6);
        }
        if (magIntake.getBeamBreak1()
                & magIntake.getBeamBreak2()
                & magIntake.getBeamBreak3()
                & OI.getOperatorLeftTrigger() < 0.5) {
            magIntake.setMagPercent(0, 0);
        } else if (!magIntake.getBeamBreak3() & OI.getOperatorLeftTrigger() < 0.5) {
            magIntake.setMagPercent(0, 0);
        } else if (!magIntake.getBeamBreak1() & magIntake.getBeamBreak2()) {
            magIntake.setMagPercent(0.5, 0);
        } else if (!magIntake.getBeamBreak2() & magIntake.getBeamBreak1()) {
            magIntake.setMagPercent(-0.3, 0.4);
        } else if (!magIntake.getBeamBreak2() & !magIntake.getBeamBreak1()) {
            magIntake.setMagPercent(0.3, 0.25);
        } else if (!magIntake.getBeamBreak3() & !magIntake.getBeamBreak2()) {
            magIntake.setMagPercent(0.2, 0);
        } else if (!magIntake.getBeamBreak2() & magIntake.getBeamBreak3()) {
            magIntake.setMagPercent(0.3, -0.4);
        } else {
            magIntake.setMagPercent(-0.3, 0.2);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (!OI.operatorRB.get()) {
            return true;
        }
        return false;
    }
}
