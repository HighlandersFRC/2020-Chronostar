// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

<<<<<<< HEAD
import frc.robot.commands.basic.Intake;
import frc.robot.commands.basic.Outtake;
import frc.robot.commands.composite.Fire;
import frc.robot.commands.defaults.DriveDefault;
import frc.robot.commands.defaults.HoodDefault;
import frc.robot.commands.defaults.LightRingDefault;
import frc.robot.commands.defaults.MagIntakeDefault;
import frc.robot.commands.defaults.ShooterDefault;
=======
import frc.robot.commands.basic.SimpleIntake;
import frc.robot.commands.basic.SmartIntake;
import frc.robot.commands.basic.SpinFlywheel;
>>>>>>> dev-rebuild
import frc.robot.subsystems.*;

public class Robot extends TimedRobot {

<<<<<<< HEAD
    public static MagIntake magIntake = new MagIntake();
    public static Drive drive = new Drive();
    public static Shooter shooter = new Shooter();
    public static Hood hood = new Hood();
    public static LightRing lightRing = new LightRing();
=======
    private final MagIntake magIntake = new MagIntake();
    private final Drive drive = new Drive();
    private final Shooter shooter = new Shooter();
    private final Hood hood = new Hood();
    private final Climber climber = new Climber();
    private final SubsystemBaseEnhanced[] subsystems = {magIntake, drive, shooter, hood, climber};
    private final SpinFlywheel spinFlywheel4500 = new SpinFlywheel(shooter, 4500);
>>>>>>> dev-rebuild

    public Robot() {}

    @Override
    public void robotInit() {
<<<<<<< HEAD
        magIntake.init();
        shooter.init();
        drive.init();
        hood.init();
        drive.setDefaultCommand(new DriveDefault(drive));
        magIntake.setDefaultCommand(new MagIntakeDefault(magIntake));
        hood.setDefaultCommand(new HoodDefault(hood));
        shooter.setDefaultCommand(new ShooterDefault(shooter));
        lightRing.setDefaultCommand(new LightRingDefault(lightRing));
=======
        for (SubsystemBaseEnhanced s : subsystems) {
            s.init();
        }
>>>>>>> dev-rebuild
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        hood.periodic();
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
<<<<<<< HEAD
        drive.teleopInit();
        OI.operatorX.whileHeld(new Fire(shooter, hood, magIntake, drive, lightRing, 0));
        OI.operatorRT.whileHeld(new Intake(magIntake));
        OI.operatorLT.whileHeld(new Outtake(magIntake));
=======
        for (SubsystemBaseEnhanced s : subsystems) {
            s.teleopInit();
        }
        OI.operatorX.whileHeld(spinFlywheel4500);
        OI.operatorLB.whileHeld(new SimpleIntake(magIntake, SimpleIntake.IntakeDirection.OUT));
        OI.operatorRB.whileHeld(new SimpleIntake(magIntake, SimpleIntake.IntakeDirection.IN));
        OI.operatorLT.whileHeld(new SmartIntake(magIntake, SmartIntake.IntakeDirection.OUT));
        OI.operatorRT.whileHeld(new SmartIntake(magIntake, SmartIntake.IntakeDirection.IN));
>>>>>>> dev-rebuild
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
