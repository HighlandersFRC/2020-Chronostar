package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drive;

public class RobotContainer {

  public static final TalonSRX leftDriveLead = new TalonSRX(Constants.leftDriveLeadID);
  public static final TalonSRX rightDriveLead = new TalonSRX(Constants.rightDriveLeadID);
  public static final TalonSRX leftDriveFollower1 = new TalonSRX(Constants.leftDriveFollower1ID);
  public static final TalonSRX rightDriveFollower1 = new TalonSRX(Constants.rightDriveFollower1ID);
  public static final TalonSRX leftDriveFollower2 = new TalonSRX(Constants.leftDriveFollower2ID);
  public static final TalonSRX rightDriveFollower2 = new TalonSRX(Constants.rightDriveFollower2ID);
  public static Drive drive = new Drive();

  // Controllers
  public static XboxController driverController = new XboxController(0);


  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  public static double getThrottle() {
    return driverController.getY(Hand.kLeft);
  }

  public static double getTurn() {
    return driverController.getX(Hand.kRight);
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
  }
}
