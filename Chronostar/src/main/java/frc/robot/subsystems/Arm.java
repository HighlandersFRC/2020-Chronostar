/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ButtonMap;
import frc.robot.RobotMap;
import frc.robot.tools.controlLoops.PID;

public class Arm extends SubsystemBase {
  /**
   * Creates a new Arm.
   */
  private PID armPID;
  private double kp = 0.01;
  private double ki;
  private double kd;
  private double maxValue = 13.2;
  private double minValue = -2.9;
  private CANDigitalInput forwardLimit;

  public Arm() {

  }
  public void initArmPID(){
    armPID = new PID(kp, ki, kd);
    armPID.setMaxOutput(0.1);
    armPID.setMinOutput(-0.1);
    RobotMap.armMotor.getEncoder().setPosition(0);
    armPID.setSetPoint(maxValue);
    forwardLimit = RobotMap.armMotor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyClosed);
  }
  public void setArmPosition(double p){
    armPID.setSetPoint(p);

  }

  @Override
  public void periodic() {
    if(forwardLimit.get()){
      RobotMap.armMotor.getEncoder().setPosition(maxValue);
    }
    armPID.updatePID(RobotMap.armMotor.getEncoder().getPosition());

    //RobotMap.armMotor.set(armPID.getResult());
    SmartDashboard.putNumber("result", armPID.getResult());
  }
  public void teleopPeriodic(){
    if(ButtonMap.armUp()){
      setArmPosition(0);
    }
    else if(ButtonMap.armDown()){
      setArmPosition(maxValue);
    }

  }
}
