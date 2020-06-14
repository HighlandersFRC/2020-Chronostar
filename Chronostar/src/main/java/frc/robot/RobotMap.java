package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C.Port;
import frc.robot.subsystems.*;

public class RobotMap {

  public static TalonFX leftDriveLead = new TalonFX(Constants.leftDriveLeadID);
  public static TalonFX rightDriveLead = new TalonFX(Constants.rightDriveLeadID);
  public static TalonFX leftDriveFollower1 = new TalonFX(Constants.leftDriveFollower1ID);
  public static TalonFX rightDriveFollower1 = new TalonFX(Constants.rightDriveFollower1ID); 
  public static TalonFX lowIntake = new TalonFX(Constants.lowIntakeID);
  public static TalonFX leftFlyWheel = new TalonFX(Constants.leftFlywheelID);
  public static TalonFX rightFlyWheel = new TalonFX(Constants.rightFlyWheelID);
  public static VictorSPX highIntake = new VictorSPX(Constants.highIntakeID);
  public static VictorSPX lowMag = new VictorSPX(Constants.magBeltID);
  public static CANSparkMax highMag = new CANSparkMax(Constants.magWheelID, MotorType.kBrushless);

  public static DigitalInput beambreak1 = new DigitalInput(Constants.beamBreak1Port);
  public static DigitalInput beambreak2 = new DigitalInput(Constants.beamBreak3Port);
  public static DigitalInput beambreak3 = new DigitalInput(Constants.beamBreak2Port);

  public static Drive drive = new Drive();
  public static Magazine magazine = new Magazine();
  public static Intake intake = new Intake();

  public static TalonFX[] allMotors = {leftDriveLead, rightDriveLead,
  leftDriveFollower1, rightDriveFollower1};

  public static AHRS navx = new AHRS(Port.kMXP);
}
