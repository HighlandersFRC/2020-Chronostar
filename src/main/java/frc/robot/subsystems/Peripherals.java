// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.SerialPort;

import frc.robot.commands.defaults.PeripheralsDefault;
import frc.robot.sensors.LidarLite;
import frc.robot.sensors.Navx;
import frc.robot.sensors.VisionCamera;

public class Peripherals extends SubsystemBaseEnhanced {
    private final AHRS ahrs = new AHRS(Port.kMXP);

    private final Navx navx = new Navx(ahrs);
    private final Counter lidarPort = new Counter(2);
    private final LidarLite lidar = new LidarLite(lidarPort);
    private VisionCamera visionCam;

    @Override
    public void init() {
        SerialPort jevois = null;
        try {
            jevois = new SerialPort(115200, SerialPort.Port.kUSB);
        } catch (final Exception e) {
            System.err.println("CV cam's serial port failed to connect. Reason: " + e);
        }
        visionCam = new VisionCamera(jevois);
        setDefaultCommand(new PeripheralsDefault(this));
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

    public double getNavxAngle() {
        return navx.currentAngle();
    }

    @Override
    public void periodic() {}

    @Override
    public void autoInit() {}

    @Override
    public void teleopInit() {}
}
