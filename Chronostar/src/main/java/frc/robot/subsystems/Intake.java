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
  public void teleopPeriodic(){
    if(ButtonMap.RunIntake()){
      RobotMap.intakeMotor.set(0.5);
    }
    else if(ButtonMap.reverseMag()){
      RobotMap.intakeMotor.set(-0.6);

    }
    else{
      RobotMap.intakeMotor.set(0);
    }

  }
}
