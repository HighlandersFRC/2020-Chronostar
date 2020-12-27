// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.defaultcommands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.subsystems.Intake;

public class IntakeDefaultCommand extends CommandBase {

    public IntakeDefaultCommand(Intake m_intake) {
        addRequirements(m_intake);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        if (OI.getOperatorRightTrigger() >= 0.5) {
            RobotMap.intake.setIntake(0.8, -0.6);
        } else if (OI.getOperatorLeftTrigger() >= 0.5) {
            RobotMap.intake.setIntake(-0.8, 0.6);
        } else {
            RobotMap.intake.setIntake(0, 0);
        }
        SmartDashboard.putNumber("Current", RobotMap.intakeMotor.getOutputCurrent());
        SmartDashboard.putNumber("IntakeTemp", RobotMap.intakeMotor.getTemperature());
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
