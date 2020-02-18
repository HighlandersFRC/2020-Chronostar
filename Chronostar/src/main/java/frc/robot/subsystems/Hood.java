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

public class Hood extends SubsystemBase {
  /**
   * Creates a new Hood.
   */
  private double kf = .05;
  private double kp = 0.000001;
  private double ki = 0.00000001;
  private double kd = 0.00007;
  private float maxpoint = 22;
  private float minpoint = 0;
  private CANPIDController mpidController = new CANPIDController(RobotMap.hoodMotor);
  private CANEncoder hoodEncoder;
  private double setPoint;
  private CANDigitalInput m_forwardLimit;
  private CANDigitalInput m_reverseLimit;



  public void inithood(){

    m_forwardLimit = RobotMap.hoodMotor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen);
    m_reverseLimit = RobotMap.hoodMotor.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyOpen);

    RobotMap.hoodMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
    RobotMap.hoodMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);

    RobotMap.hoodMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, maxpoint);
    RobotMap.hoodMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, minpoint);

    //SmartDashboard.putBoolean("Forward Limit Enabled", m_forwardLimit.isLimitSwitchEnabled());
    //SmartDashboard.putBoolean("Reverse Limit Enabled", m_reverseLimit.isLimitSwitchEnabled());

    mpidController = RobotMap.hoodMotor.getPIDController();

    hoodEncoder = RobotMap.hoodMotor.getEncoder();

    mpidController.setFF(kf);
    mpidController.setP(kp);
    mpidController.setI(ki);
    mpidController.setD(kd);

    mpidController.setOutputRange(-1, 1);
    mpidController.setSmartMotionMaxVelocity(15, 0);
    mpidController.setSmartMotionMinOutputVelocity(-15, 0);
    mpidController.setSmartMotionMaxAccel(10, 0);
    mpidController.setSmartMotionAllowedClosedLoopError(.1, 0);

    SmartDashboard.putNumber("Set Position", 0);
  }
  public Hood() {

  }
  public void resetEncodermin(){
    RobotMap.hoodMotor.getEncoder().setPosition(minpoint);
  }
  public void resetEncodermax(){
    RobotMap.hoodMotor.getEncoder().setPosition(maxpoint);
  }
  public void setHoodPosition(double desiredPosition){
    mpidController.setReference(desiredPosition, ControlType.kSmartMotion);
  }
  public double autoHoodPositionCloseDistance(double dist){
    return 0;

  }
  public double autoHoodPositionFarDistance(double dist){
    return 0;

  }

  @Override
  public void periodic() {
    if(m_reverseLimit.get() == true) {
      resetEncodermin();
    }
    if(m_forwardLimit.get() == true){
       resetEncodermax();
    }
  }
  public void teleopPeriodic(){

     setPoint = SmartDashboard.getNumber("Set Position", 0);
     if (setPoint <= minpoint){
       setPoint = minpoint;
     }
     if (setPoint >= maxpoint){
       setPoint = maxpoint;
     }
     setHoodPosition(setPoint);
     SmartDashboard.putNumber("hood target", setPoint);
     SmartDashboard.putNumber("hood pos", hoodEncoder.getPosition());
     SmartDashboard.putNumber("Output", RobotMap.hoodMotor.getAppliedOutput());
  }
}