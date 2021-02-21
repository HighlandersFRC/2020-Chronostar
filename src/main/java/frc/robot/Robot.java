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
import frc.robot.subsystems.*;

public class Robot extends TimedRobot {

    private int initCount = 0;

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

    public Robot() {}

    @Override
    public void robotInit() {
        for (SubsystemBaseEnhanced s : subsystems) {
            s.init();
        }
    }

    @Override
    public void robotPeriodic() {
        hood.periodic();
        SmartDashboard.putNumber("Lidar Dist", peripherals.getLidarDistance());
        SmartDashboard.putNumber("HoodValue", hood.getHoodPosition());
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    @Override
    public void autonomousInit() {
        for (SubsystemBaseEnhanced s : subsystems) {
            s.autoInit();
        }
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopInit() {
        for (SubsystemBaseEnhanced s : subsystems) {
            s.teleopInit();
        }
        OI.driverA.whenPressed(
                new Fire(shooter, hood, magIntake, drive, lightRing, peripherals, 4, 3800, 0));
        OI.driverB.whenPressed(
                new Fire(shooter, hood, magIntake, drive, lightRing, peripherals, 12.45, 5200, 8));
        OI.driverY.whenPressed(
                new Fire(shooter, hood, magIntake, drive, lightRing, peripherals, 13.9, 5500, 9));
        OI.driverX.whenPressed(
                new Fire(
                        shooter, hood, magIntake, drive, lightRing, peripherals, 14.5, 5700, 10.5));
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
        // OI.driverA.whileHeld(new VisionAlignment(lightRing, drive, peripherals));
        // OI.driverA.whenReleased(new LightRingOff(lightRing));
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
