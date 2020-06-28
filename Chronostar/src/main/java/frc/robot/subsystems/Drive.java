package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.*;

public class Drive extends SubsystemBase {

  double deadzone = 0.01;

  public Drive() {
  }

  private void setLeftPercent(double percent) {
    RobotMap.leftDriveLead.set(ControlMode.PercentOutput, percent);
  }

  private void setRightPercent(double percent) {
    RobotMap.rightDriveLead.set(ControlMode.PercentOutput, percent);
  }

  public void arcadeDrive(double throttle, double turn) {
    double left, right;
    double differential;
    if (Math.abs(throttle) > deadzone){
      throttle = Math.tanh(throttle) * 4/Math.PI;
    } else throttle = 0;
    if (Math.abs(turn) < deadzone) {
      turn = 0;
    }
    differential = turn;
    left = throttle + differential;
    right = throttle - differential;
    if (Math.abs(left) > 1) {
      right = Math.abs(right / left) * Math.signum(right);
      left = Math.signum(left);
    } else if (Math.abs(right) > 1) {
      left = Math.abs(left / right) * Math.signum(left);
      right = Math.signum(right);
    }
    setLeftPercent(left);
    setRightPercent(right);
  }
  
  public void tankDrive(double left, double right) {
    setLeftPercent(left);
    setRightPercent(right);
  }

  @Override
  public void periodic() {
    arcadeDrive(ButtonMap.getThrottle(), ButtonMap.getTurn());
    if (ButtonMap.getOperatorPOV() == 270) {
      setLeftPercent(0.2);
      setRightPercent(0.2);
    } else if (ButtonMap.getOperatorPOV() == 90) {
      setLeftPercent(-0.2);
      setRightPercent(-0.2);
    }
  }
}
