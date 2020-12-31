// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.defaultcommands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.commands.universalcommands.SetPiston;
import frc.robot.subsystems.Intake;

public class IntakeDefaultCommand extends CommandBase {

    private Intake intake;

    public IntakeDefaultCommand(Intake intake) {
        this.intake = intake;
        addRequirements(this.intake);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        if (OI.getOperatorRightTrigger() >= 0.5) {
            intake.setIntake(0.8, 0.6);
        } else if (OI.getOperatorLeftTrigger() >= 0.5) {
            intake.setIntake(-0.8, -0.6);
        } else {
            intake.setIntake(0, 0);
        }
        new SetPiston(RobotMap.intakePiston, Value.kReverse).schedule();
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
