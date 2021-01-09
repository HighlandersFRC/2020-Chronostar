// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.commands.basic.*;
import frc.robot.commands.composite.*;
import frc.robot.commands.defaults.*;
import frc.robot.subsystems.*;

public class Robot extends TimedRobot {

    public static MagIntake magIntake = new MagIntake();
    public static Drive drive = new Drive();
    public static Shooter shooter = new Shooter();
    public static Hood hood = new Hood();
    public static LightRing lightRing = new LightRing();
    private final Fire teleopFire = new Fire(shooter, hood, magIntake, drive, lightRing, 0.02);
    private final Intake intake = new Intake(magIntake);
    private final Outtake outtake = new Outtake(magIntake);

    @Override
    public void robotInit() {
        magIntake.init();
        shooter.init();
        drive.init();
        hood.init();
        drive.setDefaultCommand(new DriveDefault(drive));
        magIntake.setDefaultCommand(new MagIntakeDefault(magIntake));
        hood.setDefaultCommand(new HoodDefault(hood));
        shooter.setDefaultCommand(new ShooterDefault(shooter));
        lightRing.setDefaultCommand(new LightRingDefault(lightRing));
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        hood.periodic();
        // hood.periodic();
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
        OI.operatorX.whileHeld(teleopFire);
        OI.operatorRB.whileHeld(intake);
        OI.operatorLB.whileHeld(outtake);
        OI.operatorX.whenReleased(new SetHoodPosition(hood, 0));
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
