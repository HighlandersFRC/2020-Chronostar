// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.commands.basic.Outtake;
import frc.robot.commands.basic.SetHoodPosition;
import frc.robot.commands.basic.SmartIntake;
import frc.robot.commands.basic.SpinFlywheel;
import frc.robot.commands.composite.Fire;
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
        SmartDashboard.putBoolean("Beam Break 1", magIntake.getBeamBreak(1));
        SmartDashboard.putBoolean("Beam Break 2", magIntake.getBeamBreak(2));
        SmartDashboard.putBoolean("Beam Break 3", magIntake.getBeamBreak(3));
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
        OI.operatorRB.whileHeld(new SmartIntake(magIntake));
        OI.operatorLB.whileHeld(new Outtake(magIntake));
        OI.operatorX.whileHeld(new Fire(shooter, hood, magIntake, drive, 0));
        OI.operatorX.whenReleased(new SetHoodPosition(hood, 0));
        /*OI.operatorLT.whileHeld(new SmartIntake(magIntake, SmartIntake.IntakeDirection.OUT));
            OI.operatorRT.whileHeld(new SmartIntake(magIntake, SmartIntake.IntakeDirection.IN));
        */ }

    @Override
    public void teleopPeriodic() {}

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {}
}
