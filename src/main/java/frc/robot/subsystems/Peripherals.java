// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Constants;
import frc.robot.commands.defaults.PeripheralsDefault;
import frc.robot.sensors.LidarLite;
import frc.robot.sensors.Navx;
import frc.robot.sensors.VisionCamera;

public class Peripherals extends SubsystemBaseEnhanced {

    private final AHRS ahrs = new AHRS(Port.kMXP);
    private final Navx navx = new Navx(ahrs);
    private final Counter lidarPort = new Counter(0);
    private final LidarLite lidar = new LidarLite(lidarPort);
    private VisionCamera visionCam;
    private VisionCamera testCamera;
    private VisionCamera ballCam;

    @Override
    public void init() {
        SerialPort jevois = null;
        try {
            jevois = new SerialPort(115200, SerialPort.Port.kUSB1);
            SmartDashboard.putBoolean("gotCamera", true);
        } catch (final Exception e) {
            SmartDashboard.putBoolean("gotCamera", false);
            System.out.println("CV cam's serial port failed to connect. Reason: " + e);
        }

        visionCam = new VisionCamera(jevois);
        navx.softResetAngle();
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
        return Constants.degreesToHalfDegrees(navx.currentAngle());
    }

    public void resetNavxAngle() {
        navx.softResetAngle();
    }

    @Override
    public void periodic() {}

    @Override
    public void autoInit() {
        navx.softResetAngle();
    }

    @Override
    public void teleopInit() {
        navx.softResetAngle();
    }
}
