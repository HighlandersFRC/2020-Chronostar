/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.sensors.VisionCamera;

public class Robot extends TimedRobot {
  public static OI m_oi;
  Command m_autonomousCommand;
  private CommandSuites commandSuites;
  private RobotConfig robotConfig;
  private static SerialPort cameraPort;
  public static VisionCamera visionCamera;
  public void robotInit() {
    commandSuites = new CommandSuites();
    robotConfig = new RobotConfig();
    robotConfig.setStartingConfig();
    m_oi = new OI();
    try {
      cameraPort = new SerialPort(115200, Port.kUSB);
      visionCamera = new VisionCamera(cameraPort);
    } catch (Exception e) {

    }
  }
  @Override
  public void robotPeriodic() {
    try{
      SmartDashboard.putNumber("leftHeat", RobotMap.leftDriveLead.getTemperature());
      SmartDashboard.putNumber("rightHeat", RobotMap.rightDriveLead.getTemperature());

    }
    catch(Exception e){

    }
    RobotMap.drive.periodic();
  }
  @Override
  public void disabledInit() {
  }
  @Override
  public void disabledPeriodic() {

    Scheduler.getInstance().run();
  }
  @Override
  public void autonomousInit() {
    robotConfig.setAutoConfig();
    //commandSuites.startAutoCommands();
    //RobotMap.drive.startAutoOdometry(0, 0, 0, false);


    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }
  @Override
  public void autonomousPeriodic() {
    //SmartDashboard.putNumber("rightSpeed", RobotMap.drive.rightMainDrive.getVelocity());
    //SmartDashboard.putNumber("leftSpeed", RobotMap.drive.leftMainDrive.getVelocity());
    //SmartDashboard.putNumber("rx", RobotMap.drive.getDriveTrainX());
    //SmartDashboard.putNumber("ry", RobotMap.drive.getDriveTrainY());
    Scheduler.getInstance().run();
  }
  @Override
  public void teleopInit() {
    robotConfig.setTeleopConfig();
    commandSuites.startTeleopCommands();
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }
  @Override
  public void teleopPeriodic() {
    RobotMap.shooterMaster.set(ControlMode.PercentOutput, ButtonMap.shooterAxis()*RobotStats.maxShooterPercentVoltage);
    Scheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {
  }
}
