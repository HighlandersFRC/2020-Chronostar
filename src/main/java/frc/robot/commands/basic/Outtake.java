// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.OI;
import frc.robot.subsystems.MagIntake;

public class Outtake extends CommandBase {
    /** Creates a new Intake. */
    private MagIntake magIntake;

    public Outtake(MagIntake magIntake) {
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
        magIntake.setIntake(-0.8, 0.6);
        magIntake.setMagPercent(-0.4, -0.2);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (OI.getOperatorRightTrigger() < 0.5) {
            return true;
        }
        return false;
    }
}
