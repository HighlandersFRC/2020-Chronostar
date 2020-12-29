// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.subsystems.MagIntake;

public class Robot extends TimedRobot {

    public static MagIntake magIntake = new MagIntake();
    ;

    @Override
    public void robotInit() {}

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        try {
            RobotMap.visionCam.updateVision();
            SmartDashboard.putNumber("distance", RobotMap.visionCam.getDistance());
        } catch (Exception e) {
        }

        SmartDashboard.putNumber("lidar lite dist", RobotMap.lidar.getDistance());
        magIntake.init();
        RobotMap.shooter.init();
        RobotMap.drive.init();
    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    @Override
    public void autonomousInit() {
        RobotMap.drive.autoInit();
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopInit() {
        RobotMap.drive.teleopInit();
    }

    @Override
    public void teleopPeriodic() {}

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {}
}
