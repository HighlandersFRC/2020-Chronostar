// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;

import frc.robot.commands.basic.CancelMagazine;
import frc.robot.commands.basic.ClimberDown;
import frc.robot.commands.basic.ClimberUp;
import frc.robot.commands.basic.DriveBackwards;
import frc.robot.commands.basic.Outtake;
import frc.robot.commands.basic.SetHoodPosition;
import frc.robot.commands.basic.SmartIntake;
import frc.robot.commands.composite.Fire;
import frc.robot.subsystems.*;
import frc.robot.subsystems.MagIntake.BeamBreakID;
import frc.robot.tools.pathing.Odometry;

public class Robot extends TimedRobot {

    private final MagIntake magIntake = new MagIntake();
    private final Drive drive = new Drive();
    private final Shooter shooter = new Shooter();
    private final Hood hood = new Hood();
    private final Climber climber = new Climber();
    private final Peripherals peripherals = new Peripherals();
    private final LightRing lightRing = new LightRing();

    DriveBackwards driveBackwards = new DriveBackwards(drive);
    SmartIntake smartIntake = new SmartIntake(magIntake);
    ParallelRaceGroup autonomous = new ParallelRaceGroup(driveBackwards, smartIntake);

    private final SubsystemBaseEnhanced[] subsystems = {
        hood, magIntake, drive, shooter, climber, lightRing, peripherals
    };
    private final Odometry odometry = new Odometry(drive, peripherals);

    public Robot() {}

    @Override
    public void robotInit() {
        for (SubsystemBaseEnhanced s : subsystems) {
            s.init();
        }
        odometry.zero();
    }

    @Override
    public void robotPeriodic() {
        SmartDashboard.putNumber("Vision Angle", peripherals.getCamAngle());
        hood.periodic();
        SmartDashboard.putNumber("Lidar Dist", peripherals.getLidarDistance());
        SmartDashboard.putNumber("HoodValue", hood.getHoodPosition());
        SmartDashboard.putBoolean("BeamBreak1", magIntake.getBeamBreak(BeamBreakID.ONE));
        SmartDashboard.putBoolean("BeamBreak2", magIntake.getBeamBreak(BeamBreakID.TWO));
        SmartDashboard.putBoolean("BreamBreak3", magIntake.getBeamBreak(BeamBreakID.THREE));
        CommandScheduler.getInstance().run();
        SmartDashboard.putNumber("navx value", odometry.getTheta());
        SmartDashboard.putNumber("x", odometry.getX());
        SmartDashboard.putNumber("y", odometry.getY());
        SmartDashboard.putNumber("drive feet", drive.getDriveFeet());
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
        odometry.zero();
        autonomous.schedule();
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopInit() {
        for (SubsystemBaseEnhanced s : subsystems) {
            s.teleopInit();
        }
        OI.driverA.whileHeld(
                // new FireBack(shooter, hood, magIntake, drive, lightRing, peripherals, 3, 3400,
                // 4));
                new SetHoodPosition(hood, 0.368));
        OI.driverB.whileHeld(
                // new FireBack(
                // shooter, hood, magIntake, drive, lightRing, peripherals, 11.15, 4600, 8));
                new SetHoodPosition(hood, 1.368));
        OI.driverY.whenPressed(
                new Fire(shooter, hood, magIntake, drive, lightRing, peripherals, 1.1, 4900, 10));
        OI.driverX.whenPressed(
                (new Fire(
                        shooter, hood, magIntake, drive, lightRing, peripherals, 1.25, 5400, 10)));

        OI.driverA.whenReleased(new SetHoodPosition(hood, 0));
        OI.driverA.whenReleased(new CancelMagazine(magIntake));
        OI.driverB.whenReleased(new SetHoodPosition(hood, 0));
        OI.driverB.whenReleased(new CancelMagazine(magIntake));
        OI.driverY.whenReleased(new SetHoodPosition(hood, 0));
        OI.driverY.whenReleased(new CancelMagazine(magIntake));
        // OI.driverX.whenReleased(new SetHoodPosition(hood, 0));
        OI.driverX.whenReleased(new CancelMagazine(magIntake));
        OI.driverLT.whileHeld(new Outtake(magIntake));
        OI.driverRT.whileHeld(new SmartIntake(magIntake));
        OI.operatorX.whenPressed(new ClimberUp(climber));
        OI.operatorB.whenPressed(new ClimberDown(climber));
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
