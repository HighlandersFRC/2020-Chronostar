package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.subsystems.Drive;

public class RobotMap {

  public static final TalonSRX leftDriveLead = new TalonSRX(Constants.leftDriveLeadID);
  public static final TalonSRX rightDriveLead = new TalonSRX(Constants.rightDriveLeadID);
  public static final TalonSRX leftDriveFollower1 = new TalonSRX(Constants.leftDriveFollower1ID);
  public static final TalonSRX rightDriveFollower1 = new TalonSRX(Constants.rightDriveFollower1ID);
  public static final TalonSRX leftDriveFollower2 = new TalonSRX(Constants.leftDriveFollower2ID);
  public static final TalonSRX rightDriveFollower2 = new TalonSRX(Constants.rightDriveFollower2ID);
  public static Drive drive = new Drive();

  public static TalonSRX[] allMotors = {leftDriveLead, rightDriveLead,
  leftDriveFollower1, rightDriveFollower1,leftDriveFollower2, rightDriveFollower2};
}
