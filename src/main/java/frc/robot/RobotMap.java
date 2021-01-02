// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.I2C.Port;

import frc.robot.sensors.*;

public class RobotMap {

<<<<<<< HEAD
    public static TalonFX intakeMotor = new TalonFX(Constants.INTAKE_1_ID);
    public static VictorSPX intake2Motor = new VictorSPX(Constants.INTAKE_2_ID);
    public static TalonFX leftFlywheel = new TalonFX(Constants.LEFT_FLYWHEEL_ID);
    public static TalonFX rightFlywheel = new TalonFX(Constants.RIGHT_FLYWHEEL_ID);
    public static VictorSPX lowMag = new VictorSPX(Constants.MAG_BELT_ID);
    public static CANSparkMax highMag =
            new CANSparkMax(Constants.MAG_WHEEL_ID, MotorType.kBrushless);
    public static CANSparkMax hoodMotor = new CANSparkMax(Constants.HOOD_ID, MotorType.kBrushless);

    public static DoubleSolenoid intakePiston = new DoubleSolenoid(0, 1);
    public static DoubleSolenoid climberPiston = new DoubleSolenoid(2, 3);
    public static DoubleSolenoid winchRelease = new DoubleSolenoid(4, 5);

    public static DigitalInput beambreak1 = new DigitalInput(Constants.BEAM_BREAK_1_ID);
    public static DigitalInput beambreak2 = new DigitalInput(Constants.BEAM_BREAK_3_ID);
    public static DigitalInput beambreak3 = new DigitalInput(Constants.BEAM_BREAK_2_ID);

    public static CANDigitalInput lowerHoodSwitch =
            new CANDigitalInput(hoodMotor, LimitSwitch.kReverse, LimitSwitchPolarity.kNormallyOpen);
    public static CANDigitalInput upperHoodSwitch =
            new CANDigitalInput(hoodMotor, LimitSwitch.kForward, LimitSwitchPolarity.kNormallyOpen);

    public static Drive drive = new Drive();
    public static Magazine magazine = new Magazine();
    public static Intake intake = new Intake();
=======
    /*
     * TODO Om, you need to move your subsystems' hardware declarations into their
     * TODO own subsystems. You'll also have to create classes for your subsystems,
     * TODO and create default commands. Use Adi's and my code as a reference.
     */
>>>>>>> 8e0565b9ed40c562f07f411e066bf1934f0862e6

    public static AHRS ahrs = new AHRS(Port.kMXP);
    public static Navx navx = new Navx(ahrs);

    public static Counter lidarPort = new Counter(2);
    public static LidarLite lidar = new LidarLite(lidarPort);

    public static Relay visionRelay = new Relay(0);
<<<<<<< HEAD
=======

    public static VisionCamera visionCam;

    static {
        SerialPort jevois = null;
        try {
            jevois = new SerialPort(115200, SerialPort.Port.kUSB);
        } catch (Exception e) {
            System.err.println("CV cam's serial port failed to connect. Reason: " + e);
        }
        visionCam = new VisionCamera(jevois);
    }
>>>>>>> 8e0565b9ed40c562f07f411e066bf1934f0862e6
}
