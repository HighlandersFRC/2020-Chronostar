/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.ButtonMap;
import frc.robot.RobotMap;
import frc.robot.commands.controls.MagazineAutomation;
import frc.robot.commands.controls.MagazineControl;
import frc.robot.commands.controls.ShootingSequence;

public class Magazine extends SubsystemBase {
  /**
   * Creates a new Magazine.
   */
  private boolean lastState;
  private double magCount;
  public Magazine() {

  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("beamBroken", !RobotMap.beamBreakOne.get());
    if (RobotMap.beamBreakOne.get() != lastState){
      if(RobotMap.beamBreakOne.get() == false){
        new MagazineAutomation().schedule();
        magCount++;
      }
    }
    lastState = RobotMap.beamBreakOne.get();
    SmartDashboard.putNumber("balls in mag", magCount);
    

    // This method will be called once per scheduler run
  }
  public void runMagazineSystem(){
    RobotMap.magazineBelt.set(ControlMode.PercentOutput, 1);
    RobotMap.magazineWheel.set(ControlMode.PercentOutput, 1);
    RobotMap.indexer.set(.6);
  }
  public void stopMagazineSystem(){
    RobotMap.magazineBelt.set(ControlMode.PercentOutput, 0);
    RobotMap.magazineWheel.set(ControlMode.PercentOutput, 0);
    RobotMap.indexer.set(0);
  }

  public void setWheelSpeed(double wheelSpeed){
    RobotMap.magazineWheel.set(ControlMode.PercentOutput, wheelSpeed);
  }
  public void setBeltSpeed(double beltSpeed){
    RobotMap.magazineBelt.set(ControlMode.PercentOutput, beltSpeed);
  }
  public void setIndexerSpeed(double indexerSpeed){
    RobotMap.indexer.set(indexerSpeed);
  }

  public void teleopPeriodic(){
    SmartDashboard.putBoolean("beamBroken", !RobotMap.beamBreakOne.get());

    if(ButtonMap.runMag() == true){
      new ShootingSequence().schedule();
    }

    if(ButtonMap.runIndexer() == true){
      RobotMap.indexer.set(.6);
    }

    if(ButtonMap.reverseMag() == true){
      RobotMap.magazineWheel.set(ControlMode.PercentOutput, -.6);
      RobotMap.magazineBelt.set(ControlMode.PercentOutput, -1);
    }
    if(ButtonMap.stopReverseMag()){
      RobotMap.magazineWheel.set(ControlMode.PercentOutput, 0);
      RobotMap.magazineBelt.set(ControlMode.PercentOutput, 0);
    }
  }
}