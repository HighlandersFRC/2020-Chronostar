package frc.robot.commands.universalcommands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.*;

public class Fire extends CommandBase {
  double targetTime;
  public Fire() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    targetTime = Timer.getFPGATimestamp() + 1;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotMap.lowMag.set(ControlMode.PercentOutput, 0.6);
    RobotMap.highMag.set(-0.9);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Timer.getFPGATimestamp() > targetTime;
  }
}
