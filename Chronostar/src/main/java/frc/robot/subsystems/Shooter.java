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
  private double kP = 0.45;
  private double kI;
  private int offCount;
  private double kD = 10;
  private double shooterPower;
  private double offTime;

  public Shooter() {

  }
  public void initShooterPID(){
    shooterPower = 0;
    RobotMap.shooterMaster.config_kF(0, kF);
    RobotMap.shooterMaster.config_kP(0, kP);
    RobotMap.shooterMaster.config_kI(0, kI);
    RobotMap.shooterMaster.config_kD(0, kD);
  }
  public void setFlyWheelSpeed(double velocity){
    RobotMap.shooterMaster.set(ControlMode.Velocity, convertRPMToEncoderTicsPer100ms(velocity));
  }
  public int convertRPMToEncoderTicsPer100ms(double rpm){
    return (int)((rpm/600)*RobotStats.flyWheelTicsPerWheelRotation);
  }
  public double getShooterVelocity(){
    return (RobotMap.shooterMaster.getSelectedSensorVelocity()/RobotStats.flyWheelTicsPerWheelRotation)*600;
  }

  @Override
  public void periodic() {
    if(RobotMap.shooterMaster.getMotorOutputPercent()!=0 && RobotMap.shooterMaster.getSelectedSensorVelocity() ==0){
      System.out.println("WARNING, ENCODER FALIURE");
      RobotMap.shooterMaster.set(ControlMode.PercentOutput, 0);
    }
    else{
      if(RobotState.isOperatorControl()&&!RobotState.isDisabled()){
        if(ButtonMap.shooterUp()){
          shooterPower = shooterPower + 100;
        }
        if(ButtonMap.shooterDown()){
          shooterPower = shooterPower -100;
        }
        if(shooterPower>RobotStats.maxShooterRPM){
          shooterPower = RobotStats.maxShooterRPM;
        }
        else if(shooterPower <0){
          shooterPower = 0;
        }
        System.out.println(shooterPower);
        SmartDashboard.putNumber("Speed", this.getShooterVelocity());
        SmartDashboard.putBoolean("Close", Math.abs(this.getShooterVelocity()-shooterPower)<100);
        if(Math.abs(this.getShooterVelocity()-shooterPower)>100){
          offCount++;
          SmartDashboard.putNumber("count", offCount);
        }
        else{
          offCount = 0;
        }
        setFlyWheelSpeed(shooterPower);
      }
    }
  }
}
