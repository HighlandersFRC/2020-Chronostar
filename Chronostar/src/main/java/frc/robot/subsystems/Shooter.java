package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.RobotStats;

public class Shooter extends SubsystemBase {
  /**
   * Creates a new Shooter.
   */
  public Shooter() {
    RobotMap.leftFlyWheel.selectProfileSlot(0, 0);
    RobotMap.leftFlyWheel.config_kF(0, 0.0562087912087912);
    RobotMap.leftFlyWheel.config_kP(0, 0);
    RobotMap.leftFlyWheel.config_kI(0, 0);
    RobotMap.leftFlyWheel.config_kD(0, 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    RobotMap.leftFlyWheel.set(ControlMode.Velocity, 4000 * RobotStats.ticksPerShooterRotation / 600);
  }
}
