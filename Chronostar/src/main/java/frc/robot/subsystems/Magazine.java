/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ButtonMap;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.RobotState;
public class Magazine extends SubsystemBase {
  /**
   * Creates a new Magazine.
   */
  public Magazine() {

  }

  @Override
  public void periodic() {
    if(RobotState.isOperatorControl()&&!RobotState.isDisabled()){
      if(ButtonMap.moveMagazineForward()){
        RobotMap.feederMaster.set(ControlMode.PercentOutput, 1.0);
        RobotMap.indexerMotor.set(0.6);
      }
      else if(ButtonMap.moveMagazineReverse()){
        RobotMap.feederMaster.set(ControlMode.PercentOutput, -1.0);
      }
      else{
        RobotMap.indexerMotor.set(0);
        RobotMap.feederMaster.set(ControlMode.PercentOutput, 0);
      }
      if(ButtonMap.moveIndexer()){
        RobotMap.indexerMotor.set(0.6);
      }
      else{
        RobotMap.indexerMotor.set(0);
      }
    }
  }
}
