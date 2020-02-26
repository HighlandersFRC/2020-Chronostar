/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import frc.robot.ButtonMap;
import frc.robot.OI;
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
  private double kf = .02;
  private double kp = 0.00000000002;
  private double ki = 0.00000000001;
  private double kd = 0.00;
  private float maxPoint = 14.5f;
  private float minPoint = -2;
  private float maxControlPoint = 13.5f;
  private float maxvel = 10;
  private float minvel = 10;
  private float accel = 3;
  private double allowederror = 0.1;

  private CANPIDController ArmPidController = new CANPIDController(RobotMap.armMotor);
  private CANEncoder armEncoder;
  private double ArmSetPoint;
  private CANDigitalInput forwardLimit;
  private CANDigitalInput reverseLimit;
  ;



  public void initarm(){

    RobotMap.armMotor.setInverted(true);
    forwardLimit = RobotMap.armMotor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen);
    reverseLimit = RobotMap.armMotor.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyOpen);
    //RobotMap.armMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
    RobotMap.armMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);

    RobotMap.armMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, maxPoint);
    RobotMap.armMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, minPoint);
    
   //SmartDashboard.putBoolean("Forward Limit Enabled", m_forwardLimit.isLimitSwitchEnabled());
    //SmartDashboard.putBoolean("Reverse Limit Enabled", m_reverseLimit.isLimitSwitchEnabled());
    ArmPidController = RobotMap.armMotor.getPIDController();
    armEncoder = RobotMap.armMotor.getEncoder();
    ArmPidController.setFF(kf);
    ArmPidController.setP(kp);
    ArmPidController.setI(ki);
    ArmPidController.setD(kd);
    ArmPidController.setOutputRange(-1,1);
    ArmPidController.setSmartMotionMaxVelocity(5, 0);
    ArmPidController.setSmartMotionMinOutputVelocity(-5, 0);
    ArmPidController.setSmartMotionMaxAccel(3, 0);
    ArmPidController.setSmartMotionAllowedClosedLoopError(0.2, 1);
    //SmartDashboard.putNumber("Set arm Position", 0);
    ArmSetPoint = maxPoint;
    
  }
  public Arm() {

  }
  public void resetEncodermin(){
    RobotMap.armMotor.getEncoder().setPosition(minPoint);
  }
  public void resetEncodermax(){
    RobotMap.armMotor.getEncoder().setPosition(maxPoint);
  }
  public void SetArmPosition(double SetArmPos){
    SetArmPos = ArmSetPoint;
    ArmPidController.setReference(SetArmPos, ControlType.kSmartMotion);
  }

  @Override
  public void periodic() {
   if(forwardLimit.get() == true){
      resetEncodermax();
   }
    if (ArmSetPoint <= minPoint){
      ArmSetPoint = minPoint;
    }
    if (ArmSetPoint >= maxPoint){
      ArmSetPoint = maxPoint;
    }
    if(ButtonMap.deployClimber() == true){
      ArmSetPoint = 3;
    }
    else if(ButtonMap.enableArmControl() == true){
      ArmSetPoint = (ArmSetPoint+ButtonMap.armOutput()*0.08);
      ArmPidController.setReference(ArmSetPoint, ControlType.kSmartMotion);
    }
    else if (armEncoder.getPosition() >= maxControlPoint && ArmSetPoint >= maxControlPoint){
      RobotMap.armMotor.set(0.2);
    }
    else{
      ArmPidController.setReference(ArmSetPoint, ControlType.kSmartMotion);
    }
    /*SmartDashboard.putNumber("arm target", ArmSetPoint);
    SmartDashboard.putNumber("arm pos", armEncoder.getPosition());
    SmartDashboard.putNumber("Arm Output", RobotMap.armMotor.getAppliedOutput());
    SmartDashboard.putNumber("KF value", ArmPidController.getFF());
    SmartDashboard.putNumber("KP value", ArmPidController.getP());
    SmartDashboard.putBoolean("arm forward limit", forwardLimit.get());
    SmartDashboard.putBoolean("arm revers limit", reverseLimit.get());*/

    
    // This method will be called once per scheduler run
  }
}
