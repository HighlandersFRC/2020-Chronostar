package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ButtonMap;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase {
  public Intake() {
  }

  @Override
  public void periodic() {
    if (ButtonMap.getOperatorRightTrigger() >= 0.5) {
      RobotMap.lowIntake.set(ControlMode.PercentOutput, 0.325);
      RobotMap.highIntake.set(ControlMode.PercentOutput, 0.75);
    } else if (ButtonMap.getOperatorLeftTrigger() >= 0.5) {
      RobotMap.lowIntake.set(ControlMode.PercentOutput, -0.325);
      RobotMap.highIntake.set(ControlMode.PercentOutput, -0.75);
    } else {
      RobotMap.lowIntake.set(ControlMode.PercentOutput, 0);
      RobotMap.highIntake.set(ControlMode.PercentOutput, 0);
    }
  }
}
