// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.commands.basic.CancelMagazine;
import frc.robot.commands.basic.Outtake;
import frc.robot.commands.basic.SetHoodPosition;
import frc.robot.commands.basic.SmartIntake;
import frc.robot.commands.composite.Fire;
import frc.robot.commands.composite.FireBack;
import frc.robot.subsystems.*;
import frc.robot.tools.pathing.Odometry;

public class Robot extends TimedRobot {

    private final MagIntake magIntake = new MagIntake();
    private final Drive drive = new Drive();
    private final Shooter shooter = new Shooter();
    private final Hood hood = new Hood();
    private final Climber climber = new Climber();
    private final Peripherals peripherals = new Peripherals();
    private final LightRing lightRing = new LightRing();
    private final SubsystemBaseEnhanced[] subsystems = {
        hood, magIntake, drive, shooter, climber, lightRing, peripherals
    };
    private final Odometry odometry = new Odometry(drive, peripherals);

    private AutoSuite autoSuite;

    public Robot() {}

    public void robotInit() {
        for (SubsystemBaseEnhanced s : subsystems) {
            s.init();
        }
        autoSuite =
                new AutoSuite(drive, odometry, peripherals, shooter, magIntake, hood, lightRing);
        odometry.zero();
    }

    @Override
    public void robotPeriodic() {
        drive.periodic();
        SmartDashboard.putNumber("Vision Angle", peripherals.getCamAngle());
        CommandScheduler.getInstance().run();
        SmartDashboard.putNumber("Hood Value", hood.getHoodPosition());
        SmartDashboard.putNumber("x", drive.getPose().getX());
        SmartDashboard.putNumber("y", drive.getPose().getY());
        SmartDashboard.putNumber("left", drive.getLeftPosition());
        SmartDashboard.putNumber("right", drive.getRightPosition());
        SmartDashboard.putNumber("theta", drive.getNavxAngle());
    }

    @Override
    public void disabledInit() {
        drive.setDriveCoast();
    }

    @Override
    public void disabledPeriodic() {}

    @Override
    public void autonomousInit() {
        drive.resetNavx();
        for (SubsystemBaseEnhanced s : subsystems) {
            s.autoInit();
        }
        odometry.zero();
        if (drive.isNavxConnected()) {
            autoSuite.schedule();
        }
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopInit() {
        autoSuite.beginTeleop();
        for (SubsystemBaseEnhanced s : subsystems) {
            s.teleopInit();
        }
        OI.driverA.whenPressed(
                new Fire(shooter, hood, magIntake, drive, lightRing, peripherals, 4, 3800, 4));
        OI.driverB.whenPressed(
                new Fire(shooter, hood, magIntake, drive, lightRing, peripherals, 12.55, 5200, 8));
        OI.driverY.whenPressed(
                new Fire(shooter, hood, magIntake, drive, lightRing, peripherals, 14.5, 5500, 9));
        OI.driverX.whenPressed(
                new FireBack(
                        shooter,
                        hood,
                        magIntake,
                        drive,
                        lightRing,
                        peripherals,
                        14.75,
                        5700,
                        10.5));
        OI.driverA.whenReleased(new SetHoodPosition(hood, 0));
        OI.driverA.whenReleased(new CancelMagazine(magIntake));
        OI.driverB.whenReleased(new SetHoodPosition(hood, 0));
        OI.driverB.whenReleased(new CancelMagazine(magIntake));
        OI.driverY.whenReleased(new SetHoodPosition(hood, 0));
        OI.driverY.whenReleased(new CancelMagazine(magIntake));
        OI.driverX.whenReleased(new SetHoodPosition(hood, 0));
        OI.driverX.whenReleased(new CancelMagazine(magIntake));
        OI.driverLT.whileHeld(new Outtake(magIntake));
        OI.driverRT.whileHeld(new SmartIntake(magIntake));
    }

    @Override
    public void teleopPeriodic() {

        hood.periodic();
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {}
}
