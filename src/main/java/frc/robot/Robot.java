// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.commands.universalcommands.Fire;
import frc.robot.commands.universalcommands.SetMags;

public class Robot extends TimedRobot {

    private RobotConfig config = new RobotConfig();

    @Override
    public void robotInit() {
        config.startBaseConfig();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        RobotMap.magazine.periodic();
        RobotMap.intake.periodic();
        SmartDashboard.putBoolean("beam break 1", RobotMap.magazine.getBeamBreak1());
        SmartDashboard.putBoolean("beam break 2", RobotMap.magazine.getBeamBreak2());
        SmartDashboard.putBoolean("beam break 3", RobotMap.magazine.getBeamBreak3());
        try {
            RobotMap.visionCam.updateVision();
            SmartDashboard.putNumber("distance", RobotMap.visionCam.getDistance());
        } catch (Exception e) {
        }

        SmartDashboard.putNumber("lidar lite dist", RobotMap.lidar.getDistance());
    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    @Override
    public void autonomousInit() {
        config.startAutoConfig();
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopInit() {
        config.startTeleopConfig();
        OI.operatorA.whileHeld(new Fire(true));
    }

    @Override
    public void teleopPeriodic() {
        SmartDashboard.putNumber("leftHeat", RobotMap.leftFlywheel.getTemperature());
        SmartDashboard.putNumber("rightHeat", RobotMap.rightFlywheel.getTemperature());
        RobotMap.magazine.teleopPeriodic();
        RobotMap.shooter.teleopPeriodic();
        RobotMap.drive.teleopPeriodic();
        if (OI.getOperatorLeftTrigger() >= 0.5) {
            new SetMags(-1, 0.75).schedule();
        }
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {}
}
