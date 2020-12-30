// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.SerialPort;

import frc.robot.sensors.*;
import frc.robot.subsystems.*;

public class RobotMap {

    /*
     * TODO Om, you need to move your subsystems' hardware declarations into their
     * TODO own subsystems. You'll also have to create classes for your subsystems,
     * TODO and create default commands. Use Adi's and my code as a reference.
     */

    public static Climber climber = new Climber();
    public static Hood hood = new Hood();
    public static Shooter shooter = new Shooter();
    public static Drive drive = new Drive();

    public static AHRS ahrs = new AHRS(Port.kMXP);
    public static Navx navx = new Navx(ahrs);

    public static Counter lidarPort = new Counter(2);
    public static LidarLite lidar = new LidarLite(lidarPort);

    public static Relay visionRelay = new Relay(0);

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
}
