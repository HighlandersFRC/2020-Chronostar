/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.SerialPort;

import frc.robot.sensors.LidarLite;
import frc.robot.sensors.Navx;
import frc.robot.sensors.VisionCamera;

public class Peripherals extends SubsystemBaseEnhanced {
    /** Creates a new Peripherals. */
    private final AHRS ahrs = new AHRS(Port.kMXP);

    private final Navx navx = new Navx(ahrs);
    private final Counter lidarPort = new Counter(2);
    private final LidarLite lidar = new LidarLite(lidarPort);
    private VisionCamera visionCam;

    public void init() {

        SerialPort jevois = null;
        try {
            jevois = new SerialPort(115200, SerialPort.Port.kUSB2);
        } catch (final Exception e) {
            System.err.println("CV cam's serial port failed to connect. Reason: " + e);
        }
        visionCam = new VisionCamera(jevois);
    }

    public Peripherals() {}

    public double getCamAngle() {
        return visionCam.getAngle();
    }

    public double getCamDistance() {
        return visionCam.getDistance();
    }

    public double getLidarDistance() {
        return lidar.getDistance();
    }

    public double getNavxAngle() {
        return navx.currentAngle();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    @Override
    public void autoInit() {
        // TODO Auto-generated method stub

    }

    @Override
    public void teleopInit() {
        // TODO Auto-generated method stub

    }
}
