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

public class Intake extends SubsystemBase {
  public Intake() {
  }
  @Override
  public void periodic() {
  }
  public void autonomousPeriodic(){
    if(RobotMap.magazine.stuck){
      RobotMap.intakePiston.set(RobotMap.unleashIntake);
      RobotMap.intakeMotor.set(ControlMode.PercentOutput,-0.45);
    }
    else{
      RobotMap.intakePiston.set(RobotMap.unleashIntake);
      RobotMap.intakeMotor.set(ControlMode.PercentOutput,0.55);
    }
  }
  public void teleopPeriodic(){
    if(RobotMap.magazine.stuck == false){
      if(ButtonMap.RunIntake()){
        RobotMap.intakePiston.set(RobotMap.unleashIntake);
        RobotMap.intakeMotor.set(ControlMode.PercentOutput,0.7);
      }
      else if(ButtonMap.reverseMag()){
        RobotMap.intakePiston.set(RobotMap.unleashIntake);
        RobotMap.intakeMotor.set(ControlMode.PercentOutput,-0.7);
      }
      else{
        RobotMap.intakePiston.set(RobotMap.restrainIntake);
        RobotMap.intakeMotor.set(ControlMode.PercentOutput,0);
      }
    }
    else{
      RobotMap.intakePiston.set(RobotMap.unleashIntake);
      RobotMap.intakeMotor.set(ControlMode.PercentOutput,-0.2);
    }
    
  }
}
