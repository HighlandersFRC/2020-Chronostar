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

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import frc.robot.sensors.LidarLite;
import frc.robot.sensors.Navx;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Magazine;
import frc.robot.subsystems.Shooter;
import frc.robot.tools.pathTools.PathList;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class RobotMap {
  public static AHRS navx = new AHRS(Port.kMXP);

  public static Navx mainNavx = new Navx(navx);

  public static Relay visionRelay1 = new Relay(0);

  public static int beamBreak1Port = 0;

  public static int rightDriveLeadID = 1;
  public static int leftDriveLeadID = 3;

  public static int rightFollowerID = 2;
  public static int leftFollowerID = 4;

  public static int shooterMasterID = 5;
  public static int shooterFollowerID = 6;

  public static int hoodID = 7;

  public static int magazineBeltID = 8;
  public static int indexerID = 9;
  public static int magazineWheelID = 10;

  public static TalonFX leftDriveLead = new TalonFX(leftDriveLeadID);
  public static TalonFX rightDriveLead = new TalonFX(rightDriveLeadID);

  public static TalonFX leftDriveFollowerOne = new TalonFX(leftFollowerID);
  public static TalonFX rightDriveFollowerOne = new TalonFX(rightFollowerID);

  public static TalonFX shooterMaster = new TalonFX(shooterMasterID);
  public static TalonFX shooterFollower = new TalonFX(shooterFollowerID);

  public static VictorSPX magazineBelt = new VictorSPX(magazineBeltID);
  public static VictorSPX magazineWheel = new VictorSPX(magazineWheelID);

  public static CANSparkMax indexer = new CANSparkMax(indexerID, MotorType.kBrushless);

  public static CANSparkMax hoodMotor = new CANSparkMax(hoodID, MotorType.kBrushless);

  public static DigitalInput beamBreakOne = new DigitalInput(beamBreak1Port);

  public static PathList pathList = new PathList();
  
  public static SupplyCurrentLimitConfiguration robotCurrentConfigurationEnabled = new SupplyCurrentLimitConfiguration(true, RobotStats.driveTrainMaxCurrent, RobotStats.driveTrainPeakThreshold, RobotStats.driveTrainPeakTime);
  public static SupplyCurrentLimitConfiguration robotCurrentConfigurationDisabled = new SupplyCurrentLimitConfiguration(false, RobotStats.driveTrainMaxCurrent, RobotStats.driveTrainPeakThreshold, RobotStats.driveTrainPeakTime);
  public static Counter lidarCounter = new Counter(9);
  public static LidarLite lidar1 = new LidarLite(lidarCounter);
  

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
    RobotMap.shooterMaster,
    RobotMap.shooterFollower
  };
  public static TalonFX shooterMotors[] = {
    RobotMap.shooterMaster,
    RobotMap.shooterFollower
  };
  public static DriveTrain drive = new DriveTrain();
  public static Shooter shooter = new Shooter();
  public static Hood hood = new Hood();
  public static Magazine magazine = new Magazine();
  
}
