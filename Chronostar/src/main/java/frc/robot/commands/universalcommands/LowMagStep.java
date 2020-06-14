package frc.robot.commands.universalcommands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotMap;

public class LowMagStep extends CommandBase {

  private double desiredTime;
  private double startTime;

  public LowMagStep(double time) {
    desiredTime = time;
  }

  @Override
  public void initialize() {
    startTime = Timer.getFPGATimestamp();
    RobotMap.lowMag.set(ControlMode.PercentOutput, 0.6);
  }

  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotMap.lowMag.set(ControlMode.PercentOutput, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Timer.getFPGATimestamp() >= startTime + desiredTime;
  }
}
