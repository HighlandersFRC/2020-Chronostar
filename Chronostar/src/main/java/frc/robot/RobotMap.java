/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.SPI.Port;
import frc.robot.sensors.DriveEncoder;
import frc.robot.sensors.Navx;
import frc.robot.subsystems.DriveTrain;
import frc.robot.tools.pathTools.PathList;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.fasterxml.jackson.databind.ser.std.StdArraySerializers.IntArraySerializer;
import com.revrobotics.SparkMax;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

  public static AHRS navx = new AHRS(Port.kMXP);

  public static Navx mainNavx = new Navx(navx);
  
  public static PowerDistributionPanel pdp = new PowerDistributionPanel();
  
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
  
  public static SparkMax sparky = new SparkMax(1);


  
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


  public static DriveTrain drive = new DriveTrain();
  
}
