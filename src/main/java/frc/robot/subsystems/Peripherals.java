// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.sensors.LidarLite;
import frc.robot.sensors.VisionCamera;

public class Peripherals extends SubsystemBaseEnhanced {

    private final Counter lidarPort = new Counter(0);
    private final LidarLite lidar = new LidarLite(lidarPort);
    private VisionCamera visionCam;

    @Override
    public void init() {
        SerialPort jevois = null;
        try {
            jevois = new SerialPort(115200, SerialPort.Port.kUSB1);
            SmartDashboard.putBoolean("Got Camera", true);
        } catch (final Exception e) {
            SmartDashboard.putBoolean("Got Camera", false);
            System.out.println("CV cam's serial port failed to connect. Reason: " + e);
        }

        visionCam = new VisionCamera(jevois);
        try {
            visionCam.getAngle();
        } catch (final Exception e) {
            System.err.println("TestCamera could not get angle. Reason: " + e);
        }
    }

    public Peripherals() {}

    public double getCamAngle() {
        visionCam.updateVision();
        return visionCam.getAngle();
    }

    public double getCamDistance() {
        visionCam.updateVision();
        return visionCam.getDistance();
    }

    public double getLidarDistance() {
        return lidar.getDistance();
    }

    @Override
    public void periodic() {}

    @Override
    public void autoInit() {}

    @Override
    public void teleopInit() {}
}
