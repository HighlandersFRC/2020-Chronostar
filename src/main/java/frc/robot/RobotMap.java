// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import edu.wpi.first.wpilibj.*;

import frc.robot.sensors.*;

public class RobotMap {

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
