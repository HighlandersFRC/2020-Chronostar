/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import frc.robot.ButtonMap;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Arm extends SubsystemBase {
  /**
   * Creates a new arm.
   */
  private double kf = .04;
  private double kp = 0.000002;
  private double ki = 0.00000001;
  private double kd = 0.000007;
  private float maxpoint = 22;
  private float minpoint = 0;
  private float maxvel = 15;
  private float minvel = -15;
  private float accel = 10;
  private double allowederror = 0.1;

  private CANPIDController pidController = new CANPIDController(RobotMap.armMotor);
  private CANEncoder armEncoder;
  private double setPoint;
  private CANDigitalInput forwardLimit;
  private CANDigitalInput reverseLimit;



  public void initarm(){

    forwardLimit = RobotMap.armMotor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyClosed);
    reverseLimit = RobotMap.armMotor.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyOpen);
    RobotMap.armMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
    RobotMap.armMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);

    RobotMap.armMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, maxpoint);
    RobotMap.armMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, minpoint);
   //SmartDashboard.putBoolean("Forward Limit Enabled", m_forwardLimit.isLimitSwitchEnabled());
    //SmartDashboard.putBoolean("Reverse Limit Enabled", m_reverseLimit.isLimitSwitchEnabled());
    pidController = RobotMap.armMotor.getPIDController();
    armEncoder = RobotMap.armMotor.getEncoder();
    pidController.setFF(kf);
    pidController.setP(kp);
    pidController.setI(ki);
    pidController.setD(kd);
    pidController.setOutputRange(-1, 1);
    pidController.setSmartMotionMaxVelocity(maxvel, 0);
    pidController.setSmartMotionMinOutputVelocity(minvel, 0);
    pidController.setSmartMotionMaxAccel(accel, 0);
    pidController.setSmartMotionAllowedClosedLoopError(allowederror, 0);
    SmartDashboard.putNumber("Set Position", 0);
  }
  public Arm() {

  }
  public void resetEncodermin(){
    RobotMap.armMotor.getEncoder().setPosition(minpoint);
  }
  public void resetEncodermax(){
    RobotMap.armMotor.getEncoder().setPosition(maxpoint);
  }

  @Override
  public void periodic() {
   if(reverseLimit.get() == true) {
     resetEncodermin();
   }
   if(forwardLimit.get() == true){
      resetEncodermax();
   }
    setPoint = SmartDashboard.getNumber("Set Position", 0);
    if (setPoint <= minpoint){
      setPoint = minpoint;
    }
    if (setPoint >= maxpoint){
      setPoint = maxpoint;
    }
    pidController.setReference(setPoint, ControlType.kSmartMotion);
    SmartDashboard.putNumber("arm target", setPoint);
    SmartDashboard.putNumber("arm pos", armEncoder.getPosition());
    SmartDashboard.putNumber("Output", RobotMap.armMotor.getAppliedOutput());

    
    // This method will be called once per scheduler run
  }
}