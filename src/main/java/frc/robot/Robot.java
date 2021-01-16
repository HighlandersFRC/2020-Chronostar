// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.commands.basic.Outtake;
import frc.robot.commands.basic.SmartIntake;
import frc.robot.commands.composite.Fire;
import frc.robot.subsystems.*;

public class Robot extends TimedRobot {

    private final MagIntake magIntake = new MagIntake();
    private final Drive drive = new Drive();
    private final Shooter shooter = new Shooter();
    private final Hood hood = new Hood();
    private final Climber climber = new Climber();
    private final Peripherals peripherals = new Peripherals();
    private final LightRing lightRing = new LightRing();
    private final SubsystemBaseEnhanced[] subsystems = {magIntake, drive, shooter, hood, climber};
    private final Fire teleopFire = new Fire(shooter, hood, magIntake, drive, lightRing, 0);

    public Robot() {}

    @Override
    public void robotInit() {
        for (SubsystemBaseEnhanced s : subsystems) {
            s.init();
        }
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
        for (SubsystemBaseEnhanced s : subsystems) {
            s.teleopInit();
        }
        OI.operatorX.whileHeld(teleopFire);
        OI.operatorLT.whileHeld(new Outtake(magIntake));
        OI.operatorRT.whileHeld(new SmartIntake(magIntake));
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
