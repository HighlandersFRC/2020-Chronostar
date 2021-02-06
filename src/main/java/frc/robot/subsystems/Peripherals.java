// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    private VisionCamera testCamera;
    private VisionCamera ballCam;

    public void init() {

        SerialPort jevois = null;
        SerialPort ballTrackingJevois = null;
        try {
            jevois = new SerialPort(115200, SerialPort.Port.kUSB1);
            SmartDashboard.putBoolean("gotCamera", true);
        } catch (final Exception e) {
            SmartDashboard.putBoolean("gotCamera", false);
            System.out.println("CV cam's serial port failed to connect. Reason: " + e);
        }
        try {
            ballTrackingJevois = new SerialPort(115200, SerialPort.Port.kUSB2);
            SmartDashboard.putBoolean("gotBallCam", true);
        } catch (final Exception e) {
            SmartDashboard.putBoolean("gotBallCam", false);
            System.out.println("CV cam's serial port failed to connect. Reason: " + e);
        }

        visionCam = new VisionCamera(jevois);
        try {
            visionCam.getAngle();
        } catch (final Exception e) {
            System.err.println("TestCamera could not get angle. Reason: " + e);
        }
        ballCam = new VisionCamera(ballTrackingJevois);
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

    public double getBallAngle() {
        ballCam.updateBallVision();
        return ballCam.getAngle();
    }

    public double getBallDistance() {
        ballCam.updateBallVision();
        return ballCam.getDistance();
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
