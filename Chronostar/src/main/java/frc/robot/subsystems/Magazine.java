package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ButtonMap;
import frc.robot.RobotMap;

public class Magazine extends SubsystemBase {
  public Magazine() {

  }

  @Override
  public void periodic() {
    if (ButtonMap.getOperatorAButton()) {
      RobotMap.lowMag.set(ControlMode.PercentOutput, 0.5);
    } else if (ButtonMap.getOperatorBButton()) {
      RobotMap.lowMag.set(ControlMode.PercentOutput, -0.5);
    } else {
      RobotMap.lowMag.set(ControlMode.PercentOutput, 0);
    }
  }
}
