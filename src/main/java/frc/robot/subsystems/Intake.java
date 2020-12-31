// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.RobotMap;
import frc.robot.commands.defaultcommands.IntakeDefaultCommand;

public class Intake extends SubsystemBase {
    public Intake() {}

    public void init() {
        RobotMap.intakeMotor.setInverted(true);
        setDefaultCommand(new IntakeDefaultCommand(this));
    }

    @Override
    public void periodic() {}

    public void setIntake(double frontIntakePercent, double backIntakePercent) {
        RobotMap.intakeMotor.set(ControlMode.PercentOutput, frontIntakePercent);
        RobotMap.intake2Motor.set(ControlMode.PercentOutput, backIntakePercent);
    }

    public void setLowIntakePercent(double power) {
        RobotMap.intakeMotor.set(ControlMode.PercentOutput, power);
    }

    public void setHighIntakePercent(double power) {
        RobotMap.intake2Motor.set(ControlMode.PercentOutput, power);
    }
}
