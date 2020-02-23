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
  private double kf = .4;
  private double kp = 0.00001;
  private double ki = 0.0000000;
  private double kd = 0.00000;
  private float maxpoint = 13.2f;
  private float minpoint = -2.9f;
  private float maxvel = 10;
  private float minvel = 10;
  private float accel = 3;
  private double allowederror = 0.1;

  private CANPIDController ArmPidController = new CANPIDController(RobotMap.armMotor);
  private CANEncoder armEncoder;
  private double setPoint;
  private CANDigitalInput forwardLimit;
  private CANDigitalInput reverseLimit;



  public void initarm(){

    RobotMap.armMotor.restoreFactoryDefaults();
    forwardLimit = RobotMap.armMotor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyClosed);
    reverseLimit = RobotMap.armMotor.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyOpen);
    RobotMap.armMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
    RobotMap.armMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);

    RobotMap.armMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, maxpoint);
    RobotMap.armMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, minpoint);
    
   //SmartDashboard.putBoolean("Forward Limit Enabled", m_forwardLimit.isLimitSwitchEnabled());
    //SmartDashboard.putBoolean("Reverse Limit Enabled", m_reverseLimit.isLimitSwitchEnabled());
    ArmPidController = RobotMap.armMotor.getPIDController();
    armEncoder = RobotMap.armMotor.getEncoder();
    ArmPidController.setFF(kf);
    ArmPidController.setP(kp);
    ArmPidController.setI(ki);
    ArmPidController.setD(kd);
    ArmPidController.setOutputRange(-1, 1);
    ArmPidController.setSmartMotionMaxVelocity(maxvel, 1);
    ArmPidController.setSmartMotionMinOutputVelocity(minvel, 1);
    ArmPidController.setSmartMotionMaxAccel(accel, 1);
    ArmPidController.setSmartMotionAllowedClosedLoopError(allowederror, 1);
    SmartDashboard.putNumber("Set arm Position", 0);
    
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
    setPoint = SmartDashboard.getNumber("Set arm Position", 0);
    if (setPoint <= minpoint){
      setPoint = minpoint;
    }
    if (setPoint >= maxpoint){
      setPoint = maxpoint;
    }
    
    ArmPidController.setReference(setPoint, ControlType.kSmartMotion);
    SmartDashboard.putNumber("arm target", setPoint);
    SmartDashboard.putNumber("arm pos", armEncoder.getPosition());
    SmartDashboard.putNumber("Arm Output", RobotMap.armMotor.getAppliedOutput());
    SmartDashboard.putNumber("KF value", ArmPidController.getFF());
    SmartDashboard.putNumber("KP value", ArmPidController.getP());

    
    // This method will be called once per scheduler run
  }
}