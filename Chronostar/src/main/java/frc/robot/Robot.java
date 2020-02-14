/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Relay.Value;
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
    RobotMap.hood.inithood();
    RobotMap.visionRelay1.set(Value.kOn);
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

      SmartDashboard.putNumber("lidarDist", RobotMap.lidar1.getDistance());
    } catch(Exception e){

    }

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
    robotConfig.setTeleopConfig();
    commandSuites.startTeleopCommands();
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }
  @Override
  public void teleopPeriodic() {
    //RobotMap.drive.teleopPeriodic();
    RobotMap.shooter.teleopPeriodic();
    RobotMap.hood.teleopPeriodic();
    RobotMap.magazine.teleopPeriodic();

    if(ButtonMap.testButton()){
		  RobotMap.visionRelay1.set(Value.kForward);
	  }
	  else{
		  RobotMap.visionRelay1.set(Value.kReverse);
    }
    Scheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {
  }
}
