/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SPI.Port;
import frc.robot.sensors.Navx;
import frc.robot.subsystems.DriveTrain;
import frc.robot.tools.pathTools.PathList;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class RobotMap {

  public static AHRS navx = new AHRS(Port.kMXP);

  public static Navx mainNavx = new Navx(navx);
    
	public static Relay visionRelay1 = new Relay(0);

  
	public static int rightMasterFalcon = 3;
  public static int leftMasterFalcon = 1;

  public static int rightFollowerFalcon = 4;
  public static int leftFollowerFalcon = 2;

  public static int shooterMotor = 5;

  public static int indexerMotor =  6;
  
  public static TalonFX leftDriveLead = new TalonFX(leftMasterFalcon);
  public static TalonFX rightDriveLead = new TalonFX(rightMasterFalcon);

	public static TalonFX leftDriveFollowerOne = new TalonFX(leftFollowerFalcon);
  public static TalonFX rightDriveFollowerOne = new TalonFX(rightFollowerFalcon);  

  public static TalonFX shooterMaster = new TalonFX(shooterMotor);

  public static CANSparkMax sparkMax = new CANSparkMax(7, MotorType.kBrushless);
  
  public static PathList pathlist = new PathList();

  public static TalonFX driveMotors[] = {
    RobotMap.leftDriveLead,
    RobotMap.rightDriveLead,
    RobotMap.leftDriveFollowerOne,
    RobotMap.rightDriveFollowerOne,
  };
  public static TalonFX driveMotorLeads[] = {
    RobotMap.leftDriveLead,
    RobotMap.rightDriveLead,
  };
  public static TalonFX allTalonMotorLeads[] = {
    RobotMap.leftDriveLead,
    RobotMap.rightDriveLead,
  };
  public static TalonFX allFalcons[] = {
    RobotMap.leftDriveLead,
    RobotMap.rightDriveLead,
    RobotMap.leftDriveFollowerOne,
    RobotMap.rightDriveFollowerOne,
    RobotMap.shooterMaster
  };
  public static DriveTrain drive = new DriveTrain();
  
}
