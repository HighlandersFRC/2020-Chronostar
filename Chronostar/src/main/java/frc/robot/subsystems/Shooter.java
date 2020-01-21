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

public class Shooter extends SubsystemBase {
  /**
   * Creates a new Shooter.
   */
  private double kF;
  private double kP;
  private double kI;
  private double kD;
  private int flyWheelAcceleration;
  private double bigNumber = 1E99;
  private double desiredRPM;
  public Shooter() {

  }
  public void initShooterPID(){
    /*RobotMap.shooterMaster.config_kF(0, kF);
    RobotMap.shooterMaster.config_kP(0, kP);
    RobotMap.shooterMaster.config_kI(0, kI);
    RobotMap.shooterMaster.config_kD(0, kD);
    RobotMap.shooterMaster.configMotionAcceleration(flyWheelAcceleration);
    RobotMap.shooterMaster.configMotionCruiseVelocity(0);
    RobotMap.shooterMaster.set(ControlMode.MotionMagic, bigNumber);*/
  }
  public void setFlyWheelSpeed(double Velocity){
    //RobotMap.shooterMaster.configMotionCruiseVelocity(convertRPMToEncoderTicsPer100ms(Velocity));
  }
  public int convertRPMToEncoderTicsPer100ms(double rpm){
    return (int)rpm;
  }

  @Override
  public void periodic() {

  }
}
