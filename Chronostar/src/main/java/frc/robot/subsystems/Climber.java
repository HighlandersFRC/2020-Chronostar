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

public class Climber extends SubsystemBase {
  /**
   * Creates a new Climber.
   */
  public Climber() {

  }

  @Override
  public void periodic() {
    if(ButtonMap.winchUp() == true){
      RobotMap.winchMotor.set(-.50);
    }
    else{ 
      RobotMap.winchMotor.set(0.0);
    }
    
    // This method will be called once per scheduler run
  }
}
