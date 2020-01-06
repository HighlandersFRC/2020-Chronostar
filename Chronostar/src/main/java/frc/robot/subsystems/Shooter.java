/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {

	private double f = 0.0;
	private double p = 0.0;
	private double i = 0.000000;
	private double d = 0;
  private int profile = 0;
  private double desiredAcceleration;
  private double desiredCruiseVelocity;
  public Shooter() {

  }
  public void initVelocityControl(){
  
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
