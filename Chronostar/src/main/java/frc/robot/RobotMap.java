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
import frc.robot.subsystems.Shooter;
import frc.robot.tools.pathTools.PathList;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class RobotMap {

  public static AHRS navx = new AHRS(Port.kMXP);

  public static Navx mainNavx = new Navx(navx);
    
	public static Relay visionRelay1 = new Relay(0);

  
	public static int rightDriveLeadID = 3;
  public static int leftDriveLeadID = 1;

  public static int rightFollowerFalconID = 4;
  public static int leftFollowerFalconID = 2;

  public static int shooterMasterID = 5;
  public static int shooterFollowerID = 6;

  public static int indexerMotor =  7;
  
  public static TalonFX leftDriveLead = new TalonFX(leftDriveLeadID);
  public static TalonFX rightDriveLead = new TalonFX(rightDriveLeadID);

	public static TalonFX leftDriveFollowerOne = new TalonFX(leftFollowerFalconID);
  public static TalonFX rightDriveFollowerOne = new TalonFX(rightFollowerFalconID);  

  public static TalonFX shooterMaster = new TalonFX(shooterMasterID);
  public static TalonFX shooterFollower = new TalonFX(shooterFollowerID);

  
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
  public static Shooter shooter = new Shooter();
  
}
