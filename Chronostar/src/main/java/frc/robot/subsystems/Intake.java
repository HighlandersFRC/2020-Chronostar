package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ButtonMap;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase {
  public boolean isOuttaking = false;
  public boolean isIntaking = false;
  public Intake() {

  }

  @Override
  public void periodic() {
    isIntaking = false;
    isOuttaking = false;
    if (ButtonMap.getOperatorRightTrigger() >= 0.5) {
      isIntaking = true;
      isOuttaking = false;
      RobotMap.lowIntake.set(ControlMode.PercentOutput, 0.5);
      RobotMap.highIntake.set(ControlMode.PercentOutput, 0.5);
    } else if (ButtonMap.getOperatorLeftTrigger() >= 0.5) {
      isIntaking = false;
      isOuttaking = true;
      RobotMap.lowIntake.set(ControlMode.PercentOutput, -0.5);
      RobotMap.highIntake.set(ControlMode.PercentOutput, -0.5);
    } else {
      isIntaking = false;
      isOuttaking = false;
      RobotMap.lowIntake.set(ControlMode.PercentOutput, 0);
      RobotMap.highIntake.set(ControlMode.PercentOutput, 0);
    }
  }

  public void disable() {
    isIntaking = false;
    isOuttaking = false;
  }  
}
