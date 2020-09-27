// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.Relay;

import frc.robot.sensors.LidarLite;
import frc.robot.subsystems.*;

public class RobotMap {

    public static TalonFX leftDriveLead = new TalonFX(Constants.leftDriveLeadID);
    public static TalonFX rightDriveLead = new TalonFX(Constants.rightDriveLeadID);
    public static TalonFX leftDriveFollower1 = new TalonFX(Constants.leftDriveFollower1ID);
    public static TalonFX rightDriveFollower1 = new TalonFX(Constants.rightDriveFollower1ID);
    public static TalonFX intakeMotor = new TalonFX(Constants.intakeMotorID);
    public static TalonFX leftFlywheel = new TalonFX(Constants.leftFlywheelID);
    public static TalonFX rightFlywheel = new TalonFX(Constants.rightFlywheelID);
    public static TalonFX winch = new TalonFX(Constants.winchID);
    public static VictorSPX intake2Motor = new VictorSPX(Constants.intake2MotorID);
    public static VictorSPX lowMag = new VictorSPX(Constants.magBeltID);
    public static CANSparkMax highMag = new CANSparkMax(Constants.magWheelID, MotorType.kBrushless);

    public static DoubleSolenoid intakePiston = new DoubleSolenoid(0, 1);
    public static DoubleSolenoid ratchetPiston = new DoubleSolenoid(2, 3);
    public static DoubleSolenoid climberRelease = new DoubleSolenoid(4, 5);

    public static DigitalInput beambreak1 = new DigitalInput(Constants.beamBreak1Port);
    public static DigitalInput beambreak2 = new DigitalInput(Constants.beamBreak3Port);
    public static DigitalInput beambreak3 = new DigitalInput(Constants.beamBreak2Port);

    public static Drive drive = new Drive();
    public static Magazine magazine = new Magazine();
    public static Intake intake = new Intake();
    public static Shooter shooter = new Shooter();
    public static Climber climber = new Climber();

    public static TalonFX[] driveMotors = {
        leftDriveLead, rightDriveLead, leftDriveFollower1, rightDriveFollower1
    };

    public static AHRS ahrs = new AHRS(Port.kMXP);

    public static Counter lidar = new Counter(2);
    public static LidarLite lidarlite = new LidarLite(lidar);

    public static Relay visionRelay = new Relay(0);
}
