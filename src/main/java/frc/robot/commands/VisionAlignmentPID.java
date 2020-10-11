/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

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

  private PID pid;
  private final double kP = 0;
  private final double kI = 0;
  private final double kD = 0;

  public double jevoisAngle;

  private double startingTime;

  public VisionAlignmentPID() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pid = new PID(kP, kI, kD);
    pid.setSetPoint(0);
    pid.setMinOutput(-0.75);
    pid.setMaxOutput(0.75);
    RobotMap.visionRelay.set(Value.kForward);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    try{
      startingTime = Timer.getFPGATimestamp();
      Robot.visionCam.updateVision();
      jevoisAngle = Robot.visionCam.getAngle();
      SmartDashboard.putNumber("Jevois Angle", jevoisAngle);
      SmartDashboard.putNumber("Get result", pid.getResult());
      if(Timer.getFPGATimestamp()-Robot.visionCam.lastParseTime<0.25){
        pid.updatePID(jevoisAngle + 9.5);
        //System.out.println("hi");
        RobotMap.leftDriveLead.set(ControlMode.PercentOutput, pid.getResult());
        RobotMap.rightDriveLead.set(ControlMode.PercentOutput, -pid.getResult());
      }
      else{
        //System.out.println("hewwo");
        pid.updatePID(0);
        RobotMap.leftDriveLead.set(ControlMode.PercentOutput, 0);
        RobotMap.rightDriveLead.set(ControlMode.PercentOutput, 0);
      }
    }
    catch(Exception e) {
  
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
    if(!OI.driverController.getXButton()){
      return true;
    }
    //System.out.println("Still running command");
    return false;
  }
}
