package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.WaitCommand;


public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotConfig config = new RobotConfig();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
  config.startBaseConfig();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
    RobotMap.drive.periodic();
    RobotMap.magazine.periodic();
    RobotMap.intake.periodic();
    SmartDashboard.putBoolean("beam break 1", RobotMap.beambreak1.get());
    SmartDashboard.putBoolean("beam break 2", RobotMap.beambreak2.get());
    SmartDashboard.putBoolean("beam break 3", RobotMap.beambreak3.get());
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotMap} class.
   */
  @Override
  public void autonomousInit() {

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
    config.startAutoConfig();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    RobotMap.highMag.set(1);
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    config.startTeleopConfig();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    if(ButtonMap.shoot()){
      RobotMap.leftFlyWheel.set(ControlMode.PercentOutput, -0.30);
      RobotMap.rightFlyWheel.set(ControlMode.PercentOutput,0.00);
    }
    else{
      RobotMap.leftFlyWheel.set(ControlMode.PercentOutput, 0);
      RobotMap.rightFlyWheel.set(ControlMode.PercentOutput,0);
      RobotMap.magazine.stopAll();
    }
    SmartDashboard.putNumber("leftHeat", RobotMap.leftFlyWheel.getTemperature());
    SmartDashboard.putNumber("rightHeat", RobotMap.rightFlyWheel.getTemperature());
    RobotMap.magazine.teleopPeriodic();
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
