// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.commands.basic.SetHoodPosition;
import frc.robot.commands.basic.SpinFlywheel;
import frc.robot.commands.defaults.DriveDefault;
import frc.robot.commands.defaults.HoodDefault;
import frc.robot.commands.defaults.MagIntakeDefault;
import frc.robot.commands.defaults.PeripheralsDefault;
import frc.robot.commands.defaults.ShooterDefault;
import frc.robot.subsystems.*;

public class Robot extends TimedRobot {

    public static MagIntake magIntake = new MagIntake();
    public static Drive drive = new Drive();
    public static Shooter shooter = new Shooter();
    public static Hood hood = new Hood();
    public static Climber climber = new Climber();
    public static Peripherals peripherals = new Peripherals();
    private final SpinFlywheel spinFlywheel4500 = new SpinFlywheel(shooter, 4500);
    ;

    @Override
    public void robotInit() {
        magIntake.init();
        shooter.init();
        drive.init();
        hood.init();
        peripherals.init();
        drive.setDefaultCommand(new DriveDefault(drive));
        magIntake.setDefaultCommand(new MagIntakeDefault(magIntake));
        hood.setDefaultCommand(new HoodDefault(hood));
        shooter.setDefaultCommand(new ShooterDefault(shooter));
        peripherals.setDefaultCommand(new PeripheralsDefault(peripherals));
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
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
        OI.operatorX.whileHeld(spinFlywheel4500);
        OI.operatorA.whenPressed(new SetHoodPosition(hood, 0));
        OI.operatorB.whenPressed(new SetHoodPosition(hood, 11));
        OI.operatorY.whenPressed(new SetHoodPosition(hood, 22));
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
