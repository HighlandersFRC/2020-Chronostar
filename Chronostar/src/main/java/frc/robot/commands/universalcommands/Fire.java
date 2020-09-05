package frc.robot.commands.universalcommands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.*;

public class Fire extends CommandBase {

  private double targetVelocity;
  private int counter;
  private double startTime;

  public Fire(double velocity) {
    targetVelocity = velocity;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotMap.shooter.setVelocity(targetVelocity);
    startTime = Timer.getFPGATimestamp();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (RobotMap.shooter.getShooterRPM() + 20 < targetVelocity) {
      counter++;
    }
    if (Timer.getFPGATimestamp() > startTime + 2) {
      RobotMap.lowMag.set(ControlMode.PercentOutput, 0.3);
      RobotMap.highMag.set(-1);
    } else {
      RobotMap.lowMag.set(ControlMode.PercentOutput, 0);
      RobotMap.highMag.set(0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotMap.shooter.setVelocity(0);
    RobotMap.lowMag.set(ControlMode.PercentOutput, 0);
    RobotMap.highMag.set(0);
    SmartDashboard.putNumber("Counter", counter);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return !ButtonMap.shoot();
  }
}