// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;

import frc.robot.commands.universalcommands.SetFlywheelRPM;

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
        RobotMap.hood.periodic();
        SmartDashboard.putBoolean("beam break 1", RobotMap.beambreak1.get());
        SmartDashboard.putBoolean("beam break 2", RobotMap.beambreak2.get());
        SmartDashboard.putBoolean("beam break 3", RobotMap.beambreak3.get());
        SmartDashboard.putNumber("cam distance", RobotMap.visionCam.getDistance());
        SmartDashboard.putNumber("cam angle", RobotMap.visionCam.getAngle());
        SmartDashboard.putNumber("lidar dist", RobotMap.lidar.getDistance());
        SmartDashboard.putBoolean("lower limit switch", RobotMap.lowerHoodSwitch.get());
        SmartDashboard.putBoolean("upper limit switch", RobotMap.upperHoodSwitch.get());
        SmartDashboard.putNumber(
                "rpm",
                Constants.unitsPer100MsToRpm(RobotMap.leftFlywheel.getSelectedSensorVelocity()));
    }

    @Override
    public void disabledInit() {
        RobotMap.intakePiston.set(Value.kReverse);
    }

    @Override
    public void disabledPeriodic() {}

    @Override
    public void autonomousInit() {
        config.startAutoConfig();
        new SetFlywheelRPM(4500, Constants.INITIATION_HOOD_POSITION, 3, true).schedule();
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
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {}
}
