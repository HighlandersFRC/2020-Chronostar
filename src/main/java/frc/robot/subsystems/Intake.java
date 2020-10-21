// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.ButtonMap;
import frc.robot.OI;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase {
    public Intake() {}

    public void init() {
        RobotMap.intakeMotor.setInverted(true);
    }

    @Override
    public void periodic() {
        if (ButtonMap.getOperatorRightTrigger() >= 0.5) {
            RobotMap.intakeMotor.set(ControlMode.PercentOutput, 0.8);
            RobotMap.intake2Motor.set(ControlMode.PercentOutput, 0.6);
        } else if (ButtonMap.getOperatorLeftTrigger() >= 0.5) {
            RobotMap.intakeMotor.set(ControlMode.PercentOutput, -0.8);
            RobotMap.intake2Motor.set(ControlMode.PercentOutput, -0.6);
        } else {
            RobotMap.intakeMotor.set(ControlMode.PercentOutput, 0);
            RobotMap.intake2Motor.set(ControlMode.PercentOutput, 0);
        }
        if (OI.operatorController.getBackButton()) {
            RobotMap.intakePiston.set(Value.kReverse);
        } else {
            RobotMap.intakePiston.set(Value.kForward);
        }
    }
}
