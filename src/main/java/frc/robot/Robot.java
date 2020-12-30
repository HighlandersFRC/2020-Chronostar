// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.subsystems.*;

public class Robot extends TimedRobot {

    public static MagIntake magIntake = new MagIntake();
    public static Drive drive = new Drive();
    public static Shooter shooter = new Shooter();
    public static Hood hood = new Hood();
    public static Climber climber = new Climber();

    @Override
    public void robotInit() {
        magIntake.init();
        shooter.init();
        drive.init();
        hood.init();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        RobotMap.visionCam.updateVision();
        SmartDashboard.putNumber("distance", RobotMap.visionCam.getDistance());
        SmartDashboard.putNumber("lidar lite dist", RobotMap.lidar.getDistance());
    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    @Override
    public void autonomousInit() {
        drive.autoInit();
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopInit() {
        drive.teleopInit();
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
