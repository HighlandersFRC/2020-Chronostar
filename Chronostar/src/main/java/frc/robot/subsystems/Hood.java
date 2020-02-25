/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import frc.robot.ButtonMap;
import frc.robot.RobotMap;
import frc.robot.sensors.LidarLite;
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
  private double kf = .02;
  private double kp = 0.0002;
  private double ki = 0.00001;
  private double kd = 0.0002;
  private float maxpoint = 22;
  private float minpoint = 0;
  private CANPIDController mpidController = new CANPIDController(RobotMap.hoodMotor);
  private CANDigitalInput m_forwardLimit;
  private CANDigitalInput m_reverseLimit;
  private double dPosition;
  private CANEncoder hoodEncoder;


  public void inithood(){

    m_forwardLimit = RobotMap.hoodMotor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen);
    m_reverseLimit = RobotMap.hoodMotor.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyOpen);

    RobotMap.hoodMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
    RobotMap.hoodMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);

    RobotMap.hoodMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, maxpoint);
    RobotMap.hoodMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, minpoint);
    RobotMap.hoodMotor.enableVoltageCompensation(11.3);
    mpidController = RobotMap.hoodMotor.getPIDController();

    hoodEncoder = RobotMap.hoodMotor.getEncoder();

    mpidController.setFF(kf);
    mpidController.setP(kp);
    mpidController.setI(ki);
    mpidController.setD(kd);
    mpidController.setIZone(.2);
    mpidController.setOutputRange(-1, 1);
    mpidController.setSmartMotionMaxVelocity(100, 0);
    mpidController.setSmartMotionMinOutputVelocity(-100, 0);
    mpidController.setSmartMotionMaxAccel(80, 0);
    mpidController.setSmartMotionAllowedClosedLoopError(.1, 0);

  }
  public Hood() {

  }
  public void resetEncodermin(){
    RobotMap.hoodMotor.getEncoder().setPosition(minpoint);
  }
  public void resetEncodermax(){
    RobotMap.hoodMotor.getEncoder().setPosition(maxpoint);
  }
  public boolean hoodClose(){
    return Math.abs(RobotMap.hoodMotor.getEncoder().getPosition()-dPosition)<0.2&&Math.abs(RobotMap.hoodMotor.get())<0.01;
  }
  public double getOptimalPosition(){
    double dist = RobotMap.lidar1.getDistance();
    if(dist>=1.9 &&dist <=20){
      return 0.0075*Math.pow(dist,3) -0.3353*Math.pow(dist,2) +4.8974*dist - 8.1235;
    }
    else{
      return -1;
    }
  }
  public void setHoodPosition(double desiredPosition){
    dPosition =desiredPosition;
    if(desiredPosition == 0){
      mpidController.setReference(-0.5, ControlType.kSmartMotion);
    }
    else{
      mpidController.setReference(desiredPosition, ControlType.kSmartMotion);
    }
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
    SmartDashboard.putNumber("get Position", RobotMap.hoodMotor.getEncoder().getPosition());

  }
  public void teleopPeriodic(){
  }
}
