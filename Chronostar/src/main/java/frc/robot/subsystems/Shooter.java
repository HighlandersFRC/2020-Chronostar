/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ButtonMap;
import frc.robot.RobotMap;
import frc.robot.RobotStats;

public class Shooter extends SubsystemBase {
  /**
   * Creates a new Shooter.
   */
  private double kF = 0.05;
  private double kP = 0.25;
  private double kI = 0.0001;
  private int offCount;
  private double kD = 0;
  private int iZone = 439;
  private double shooterPower;
  private double offTime;
  private boolean shooterUpLastState = false;
  private boolean shooterDownLastState = false;
  private int velCounter;

  public Shooter() {

  }
  public void initShooterPID(){
    shooterPower = 0;
    RobotMap.shooterMaster.config_kF(0, kF);
    RobotMap.shooterMaster.config_kP(0, kP);
    RobotMap.shooterMaster.config_kI(0, kI);
    RobotMap.shooterMaster.config_kD(0, kD);
    RobotMap.shooterMaster.config_IntegralZone(0, iZone);
    //SmartDashboard.putNumber("setVelocity", 0);
    velCounter = 0;
  }
  public void setFlyWheelSpeed(double velocity){
    if(velocity>RobotStats.maxShooterRPM){
      velocity = RobotStats.maxShooterRPM;
    }
    else if(velocity <0){
      velocity = 0;
    }
    RobotMap.shooterMaster.set(ControlMode.Velocity, convertRPMToEncoderTicsPer100ms(velocity));
  }
  public int convertRPMToEncoderTicsPer100ms(double rpm){
    return (int)((rpm/600)*RobotStats.flyWheelTicsPerWheelRotation);
  }
  public double getShooterVelocity(){
    return (RobotMap.shooterMaster.getSelectedSensorVelocity()/RobotStats.flyWheelTicsPerWheelRotation)*600;
  }
  public boolean flyWheelSpeedClose(){
    return RobotMap.shooterMaster.getClosedLoopError()<this.convertRPMToEncoderTicsPer100ms(100);
  }
  @Override
  public void periodic() {
    SmartDashboard.putNumber("Speed", this.getShooterVelocity());
    //SmartDashboard.putBoolean("Close", Math.abs(this.getShooterVelocity()-shooterPower)<100);
    if(Math.abs(this.getShooterVelocity()-shooterPower)>100){
      offCount++;
      //SmartDashboard.putNumber("count", offCount);
    }
    else{
      offCount = 0;
    }
  }
  public void teleopPeriodic(){
    if(RobotMap.shooterMaster.getMotorOutputPercent()!=0 && RobotMap.shooterMaster.getSelectedSensorVelocity() ==0){
      System.out.println("WARNING, ENCODER FALIURE");
      //RobotMap.shooterMaster.set(ControlMode.PercentOutput, 0);
    }
    else{
    
    }
  
    
  }
}
