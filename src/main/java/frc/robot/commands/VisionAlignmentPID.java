// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.tools.controlloops.PID;

public class VisionAlignmentPID extends CommandBase {
  /**
   * Creates a new VisionAlignmentPID.
   */

  public VisionAlignmentPID() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  public static PID pid;
  private final double kP = 0.0077;
  private final double kI = 0.0007;
  private final double kD = 0.003;

  @Override
  public void initialize() {
    pid = new PID(kP, kI, kD);
    pid.setSetPoint(0);
    pid.setMinOutput(-0.75);
    pid.setMaxOutput(0.75);
    RobotMap.visionRelay.set(Value.kForward);
  }

 public double jevoisAngle;
 public double startingTime;
  @Override
  public void execute() {
    try{
      startingTime = Timer.getFPGATimestamp();
      Robot.visionCam.updateVision();
      jevoisAngle = Robot.visionCam.getAngle();
      SmartDashboard.putNumber("Jevois Angle", jevoisAngle);
      SmartDashboard.putNumber("Get result", pid.getResult());
      if(Timer.getFPGATimestamp() - Robot.visionCam.lastParseTime < 0.25){
        pid.updatePID(jevoisAngle + 9.5);
        RobotMap.leftDriveLead.set(ControlMode.PercentOutput, pid.getResult());
        RobotMap.rightDriveLead.set(ControlMode.PercentOutput, -pid.getResult());
      }
      else{
        pid.updatePID(0);
        RobotMap.leftDriveLead.set(ControlMode.PercentOutput, 0);
        RobotMap.rightDriveLead.set(ControlMode.PercentOutput, 0);
        RobotMap.visionRelay.set(Value.kForward);
      }

    } catch(Exception e) {
  
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotMap.leftDriveLead.set(ControlMode.PercentOutput, 0);
    RobotMap.rightDriveLead.set(ControlMode.PercentOutput, 0);
    RobotMap.visionRelay.set(Value.kReverse); 
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (!OI.driverController.getXButton()) {
      return true;
    }
    return false;
  }
}
