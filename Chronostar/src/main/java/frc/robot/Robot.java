/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.sensors.VisionCamera;

public class Robot extends TimedRobot {
  public static OI m_oi;
  Command m_autonomousCommand;
  private CommandSuites commandSuites;
  private RobotConfig robotConfig;
  private static SerialPort cameraPort = new SerialPort(115200, Port.kUSB);
  public static VisionCamera visionCamera = new VisionCamera(cameraPort);
  @Override
  public void robotInit() {
    commandSuites = new CommandSuites();
    robotConfig = new RobotConfig();
    robotConfig.setStartingConfig();
    m_oi = new OI();
  }
  @Override
  public void robotPeriodic() {
    RobotMap.shooter.periodic();
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
    Scheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {
  }
}
