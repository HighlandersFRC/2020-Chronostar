// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.Relay;

import frc.robot.subsystems.*;

public class RobotMap {

    public static TalonFX leftDriveLead = new TalonFX(RobotStats.leftDriveLeadID);
    public static TalonFX rightDriveLead = new TalonFX(RobotStats.rightDriveLeadID);
    public static TalonFX leftDriveFollower1 = new TalonFX(RobotStats.leftDriveFollower1ID);
    public static TalonFX rightDriveFollower1 = new TalonFX(RobotStats.rightDriveFollower1ID);
    public static TalonFX intakeMotor = new TalonFX(RobotStats.intakeMotorID);
    public static TalonFX leftFlywheel = new TalonFX(RobotStats.leftFlywheelID);
    public static TalonFX rightFlywheel = new TalonFX(RobotStats.rightFlywheelID);
    public static VictorSPX intake2Motor = new VictorSPX(RobotStats.intake2MotorID);
    public static VictorSPX lowMag = new VictorSPX(RobotStats.magBeltID);
    public static CANSparkMax highMag =
            new CANSparkMax(RobotStats.magWheelID, MotorType.kBrushless);
    public static DoubleSolenoid intakePiston = new DoubleSolenoid(0, 1);

    public static DigitalInput beambreak1 = new DigitalInput(RobotStats.beamBreak1Port);
    public static DigitalInput beambreak2 = new DigitalInput(RobotStats.beamBreak3Port);
    public static DigitalInput beambreak3 = new DigitalInput(RobotStats.beamBreak2Port);

    public static Drive drive = new Drive();
    public static Magazine magazine = new Magazine();
    public static Intake intake = new Intake();
    public static Shooter shooter = new Shooter();

    public static TalonFX[] allMotors = {
        leftDriveLead, rightDriveLead, leftDriveFollower1, rightDriveFollower1
    };

    public static AHRS ahrs = new AHRS(Port.kMXP);

    public static Relay visionRelay = new Relay(0);
}
