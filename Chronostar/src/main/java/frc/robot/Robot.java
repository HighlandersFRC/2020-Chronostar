/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.sensors.VisionCamera;

public class Robot extends TimedRobot {
  public static OI m_oi;
  Command m_autonomousCommand;
  private CommandSuites commandSuites;
  private RobotConfig robotConfig;
  private UsbCamera camera;
  private UsbCamera camera2;
  private VideoSink server;

  private boolean ableToSwitch;
  private static SerialPort cameraPort;
  public static VisionCamera visionCamera;
  public void robotInit() {
    commandSuites = new CommandSuites();
    robotConfig = new RobotConfig();
    robotConfig.setStartingConfig();
    RobotMap.hood.inithood();
    m_oi = new OI();
    try {
      cameraPort = new SerialPort(115200, Port.kUSB);
      visionCamera = new VisionCamera(cameraPort);
    } catch (Exception e) {

    }
    camera = CameraServer.getInstance().startAutomaticCapture("VisionCamera1", "/dev/video0");
		camera.setResolution(320, 240);
		camera.setFPS(15);

		camera2 = CameraServer.getInstance().startAutomaticCapture("VisionCamera2", "/dev/video1");
		camera2.setResolution(320, 240);
		camera2.setFPS(15);
		RobotMap.visionRelay1.set(Value.kOn);
	

		server = CameraServer.getInstance().addSwitchedCamera("driverVisionCameras");
		server.setSource(camera);
		Shuffleboard.update();
		SmartDashboard.updateValues(); 
    CommandScheduler.getInstance().enable();
  }
  @Override
  public void robotPeriodic() {
    try{

      SmartDashboard.putNumber("lidarDist", RobotMap.lidar1.getDistance());
    } catch(Exception e){

    }
    CommandScheduler.getInstance().run();

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
    commandSuites.startAutoCommands();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }
  @Override
  public void teleopInit() {
    RobotMap.drive.startAutoOdometry(false, 0, 0);
    robotConfig.setTeleopConfig();
    commandSuites.startTeleopCommands();
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }
  @Override
  public void teleopPeriodic() {
    SmartDashboard.putNumber("x", RobotMap.drive.getDriveTrainX());
    SmartDashboard.putNumber("y", RobotMap.drive.getDriveTrainY());

    RobotMap.drive.teleopPeriodic();
    RobotMap.shooter.teleopPeriodic();
    RobotMap.hood.teleopPeriodic();
    RobotMap.magazine.teleopPeriodic();
    Scheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {
  }
}
