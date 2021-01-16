// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.OI;
import frc.robot.subsystems.MagIntake;

public class SmartIntake extends CommandBase {
    /** Creates a new Intake. */
    private MagIntake magIntake;

    public SmartIntake(MagIntake magIntake) {
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
            magIntake.setIntakePercent(0.8, 0);
        }
        if (magIntake.getBeamBreak(1)
                & magIntake.getBeamBreak(2)
                & magIntake.getBeamBreak(3)
                & !OI.operatorController.getBumper(Hand.kLeft)) {
            magIntake.setMagPercent(0, 0);
        } else if (!magIntake.getBeamBreak(3) & !OI.operatorController.getBumper(Hand.kLeft)) {
            magIntake.setMagPercent(0, 0);
            magIntake.setIntakePercent(0, 0.3);
        } else if (!magIntake.getBeamBreak(1) & magIntake.getBeamBreak(2)) {
            magIntake.setMagPercent(0.5, 0.2);
            magIntake.setIntakePercent(0, 0.6);
        } else if (!magIntake.getBeamBreak(2) & magIntake.getBeamBreak(1)) {
            magIntake.setMagPercent(-0.2, 0.4);
        } else if (!magIntake.getBeamBreak(2) & !magIntake.getBeamBreak(1)) {
            magIntake.setMagPercent(0.25, 0.35);
        } else if (!magIntake.getBeamBreak(3) & !magIntake.getBeamBreak(2)) {
            magIntake.setMagPercent(0.2, 0);
        } else if (!magIntake.getBeamBreak(2) & magIntake.getBeamBreak(3)) {
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
        return false;
    }
}
