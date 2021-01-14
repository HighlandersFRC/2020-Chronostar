// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.commands.basic.Intake;
import frc.robot.commands.basic.Outtake;
import frc.robot.commands.basic.SetArmPosition;
import frc.robot.commands.basic.SetClimberPiston;
import frc.robot.commands.basic.SpinFlywheel;
import frc.robot.subsystems.*;

public class Robot extends TimedRobot {

    private final MagIntake magIntake = new MagIntake();
    private final Drive drive = new Drive();
    private final Shooter shooter = new Shooter();
    private final Hood hood = new Hood();
    private final Climber climber = new Climber();
    private final SubsystemBaseEnhanced[] subsystems = {magIntake, drive, shooter, hood, climber};
    private final SpinFlywheel spinFlywheel4500 = new SpinFlywheel(shooter, 4500);

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
        OI.operatorX.whileHeld(spinFlywheel4500);
        OI.operatorRT.whileHeld(new Intake(magIntake));
        OI.operatorLT.whileHeld(new Outtake(magIntake));
        OI.driverLB.whenPressed(new SetClimberPiston(climber, true));
        OI.driverRB.whenPressed(new SetClimberPiston(climber, false));
        OI.driverA.whenPressed(new SetArmPosition(climber, 20));
        OI.driverY.whenPressed(new SetArmPosition(climber, 35));
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
