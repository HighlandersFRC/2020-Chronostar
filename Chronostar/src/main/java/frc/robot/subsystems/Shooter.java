package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.RobotStats;

public class Shooter extends SubsystemBase {
  /**
   * Creates a new Shooter.
   */
  public Shooter() {
    RobotMap.leftFlywheel.selectProfileSlot(0, 0);
    RobotMap.leftFlywheel.config_kF(0, 0.1136666666666667f);
    RobotMap.leftFlywheel.config_kP(0, 0);
    RobotMap.leftFlywheel.config_kI(0, 0);
    RobotMap.leftFlywheel.config_kD(0, 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    RobotMap.leftFlywheel.set(ControlMode.Velocity, 5000 / 600 * RobotStats.ticksPerShooterRotation);
  }
}
