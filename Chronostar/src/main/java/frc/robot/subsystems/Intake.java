/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ButtonMap;
import frc.robot.RobotMap;
import frc.robot.commands.controls.MagazineAutomation;

public class Intake extends SubsystemBase {
  /**
   * Creates a new Intake.
   */
  public Intake() {
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void autonomousPeriodic(){
    if(RobotMap.magazine.stuck){
      RobotMap.intakeMotor.set(ControlMode.PercentOutput,-0.4);
    }
    else{
      RobotMap.intakeMotor.set(ControlMode.PercentOutput,0.4);
    }
  }
  public void teleopPeriodic(){
    if(RobotMap.magazine.stuck == false){
      if(ButtonMap.RunIntake()){
        RobotMap.intakeMotor.set(ControlMode.PercentOutput,0.4);
      }
      else if(ButtonMap.reverseMag()){
        RobotMap.intakeMotor.set(ControlMode.PercentOutput,-0.4);
      }
      else{
        RobotMap.intakeMotor.set(ControlMode.PercentOutput,0);
      }
    }
    else{
      RobotMap.intakeMotor.set(ControlMode.PercentOutput,-0.5);
    }
    
  }
}
