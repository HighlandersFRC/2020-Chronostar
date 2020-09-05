package frc.robot.commands.universalcommands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotMap;

public class SetLowMag extends CommandBase {

  private double power;

  public SetLowMag(double power) {
    this.power = power;
  }

  @Override
  public void initialize() {
    RobotMap.lowMag.set(ControlMode.PercentOutput, power);
  }

  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}