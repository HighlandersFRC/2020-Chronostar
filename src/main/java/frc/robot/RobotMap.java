// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANDigitalInput.LimitSwitch;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.Relay;

import frc.robot.sensors.LidarLite;
import frc.robot.sensors.Navx;
import frc.robot.subsystems.*;

public class RobotMap {

    public static TalonFX leftDriveLead = new TalonFX(Constants.leftDriveLeadID);
    public static TalonFX rightDriveLead = new TalonFX(Constants.rightDriveLeadID);
    public static TalonFX leftDriveFollower = new TalonFX(Constants.leftDriveFollower1ID);
    public static TalonFX rightDriveFollower = new TalonFX(Constants.rightDriveFollower1ID);
    public static TalonFX intakeMotor = new TalonFX(Constants.intakeMotorID);
    public static TalonFX leftFlywheel = new TalonFX(Constants.leftFlywheelID);
    public static TalonFX rightFlywheel = new TalonFX(Constants.rightFlywheelID);
    public static VictorSPX intake2Motor = new VictorSPX(Constants.intake2MotorID);
    public static VictorSPX lowMag = new VictorSPX(Constants.magBeltID);
    public static CANSparkMax highMag = new CANSparkMax(Constants.magWheelID, MotorType.kBrushless);
    public static CANSparkMax hood = new CANSparkMax(Constants.hoodID, MotorType.kBrushless);

    public static DoubleSolenoid intakePiston = new DoubleSolenoid(0, 1);
    public static DoubleSolenoid climberPiston = new DoubleSolenoid(2, 3);
    public static DoubleSolenoid winchRelease = new DoubleSolenoid(4, 5);

    public static DigitalInput beambreak1 = new DigitalInput(Constants.beamBreak1Port);
    public static DigitalInput beambreak2 = new DigitalInput(Constants.beamBreak3Port);
    public static DigitalInput beambreak3 = new DigitalInput(Constants.beamBreak2Port);

    public static CANDigitalInput lowerHoodSwitch =
            new CANDigitalInput(hood, LimitSwitch.kReverse, LimitSwitchPolarity.kNormallyClosed);
    public static CANDigitalInput upperHoodSwitch =
            new CANDigitalInput(hood, LimitSwitch.kForward, LimitSwitchPolarity.kNormallyOpen);

    public static Drive drive = new Drive();
    public static Magazine magazine = new Magazine();
    public static Intake intake = new Intake();
    public static Shooter shooter = new Shooter();

    public static TalonFX[] allMotors = {
        leftDriveLead, rightDriveLead, leftDriveFollower, rightDriveFollower
    };

    public static AHRS ahrs = new AHRS(Port.kMXP);
    public static Navx navx = new Navx(ahrs);

    public static Counter lidarPort = new Counter(2);
    public static LidarLite lidar = new LidarLite(lidarPort);

    public static Relay visionRelay = new Relay(0);
}
