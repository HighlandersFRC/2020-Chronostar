/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ButtonMap;
import frc.robot.OI;
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
      RobotMap.intake2Motor.set(ControlMode.PercentOutput, -0.3);
    }
    else{
      RobotMap.intakePiston.set(RobotMap.unleashIntake);
      RobotMap.intakeMotor.set(ControlMode.PercentOutput,0.55);
      RobotMap.intake2Motor.set(ControlMode.PercentOutput, 0.4);
    }
  }
  public void teleopPeriodic(){
    if(RobotMap.magazine.stuck == false){
      if(ButtonMap.RunIntake()){
        RobotMap.intakeMotor.set(ControlMode.PercentOutput, SmartDashboard.getNumber("falcon", 0));
        RobotMap.intake2Motor.set(ControlMode.PercentOutput, SmartDashboard.getNumber("775", 0));
      }
      else if(ButtonMap.reverseMag()){
        RobotMap.intakeMotor.set(ControlMode.PercentOutput,-0.3);
        RobotMap.intake2Motor.set(ControlMode.PercentOutput, -0.5);
      }
      else{
        RobotMap.intakeMotor.set(ControlMode.PercentOutput,0);
        RobotMap.intake2Motor.set(ControlMode.PercentOutput, 0);
      }
    }
    else{
      RobotMap.intakeMotor.set(ControlMode.PercentOutput,SmartDashboard.getNumber("falcon", 0));
      RobotMap.intake2Motor.set(ControlMode.PercentOutput, SmartDashboard.getNumber("775", 0));
    }
    if (OI.operatorController.getBumper(Hand.kLeft)) {
      RobotMap.intakePiston.set(RobotMap.restrainIntake);
    } else RobotMap.intakePiston.set(RobotMap.unleashIntake);
  }
}
