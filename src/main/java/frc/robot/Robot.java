// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;

import frc.robot.commands.universalcommands.SetFlywheelRPM;
import frc.robot.sensors.VisionCamera;

public class Robot extends TimedRobot {

    public static SerialPort jevois;
    public static VisionCamera visionCam;

    private RobotConfig config = new RobotConfig();

    @Override
    public void robotInit() {
        config.startBaseConfig();
        try {
            jevois = new SerialPort(115200, Port.kUSB1);
            visionCam = new VisionCamera(jevois);
        } catch (Exception e) {
            System.err.println("vision cam failed to connect");
        }
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        RobotMap.magazine.periodic();
        RobotMap.intake.periodic();
        RobotMap.hood.periodic();
        SmartDashboard.putBoolean("beam break 1", RobotMap.beambreak1.get());
        SmartDashboard.putBoolean("beam break 2", RobotMap.beambreak2.get());
        SmartDashboard.putBoolean("beam break 3", RobotMap.beambreak3.get());
        SmartDashboard.putNumber("distance", visionCam.getDistance());
        SmartDashboard.putNumber("lidar dist", RobotMap.lidar.getDistance());
        SmartDashboard.putBoolean("lower limit switch", RobotMap.lowerHoodSwitch.get());
        SmartDashboard.putBoolean("upper limit switch", RobotMap.upperHoodSwitch.get());
        SmartDashboard.putNumber(
                "rpm",
                Constants.unitsPer100MsToRpm(RobotMap.leftFlywheel.getSelectedSensorVelocity()));
    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    @Override
    public void autonomousInit() {
        config.startAutoConfig();
        new SetFlywheelRPM(4500, 8, 3, true).schedule();
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopInit() {
        config.startTeleopConfig();
    }

    @Override
    public void teleopPeriodic() {
        RobotMap.shooter.teleopPeriodic();
        RobotMap.drive.teleopPeriodic();
        RobotMap.magazine.teleopPeriodic();
        SmartDashboard.putNumber("Vision Angle", visionCam.getAngle());
        // TODO: Actually look for existence of camera
        SmartDashboard.putBoolean("Has Camera", true);

        if (OI.driverController.getXButton()) {
            RobotMap.visionPID.schedule();
            RobotMap.visionRelay.set(Value.kForward);
        }
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {}
}
