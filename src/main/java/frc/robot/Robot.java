// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.basic.CancelMagazine;
import frc.robot.commands.basic.LightRingOff;
import frc.robot.commands.basic.Outtake;
import frc.robot.commands.basic.PurePursuit;
import frc.robot.commands.basic.SetHoodPosition;
import frc.robot.commands.basic.SmartIntake;
import frc.robot.commands.basic.VisionAlignment;
import frc.robot.commands.composite.Fire;
import frc.robot.subsystems.*;
import frc.robot.tools.pathing.Odometry;

import java.io.IOException;
import java.nio.file.Paths;

public class Robot extends TimedRobot {

    private final MagIntake magIntake = new MagIntake();
    private final Drive drive = new Drive();
    private final Shooter shooter = new Shooter();
    private final Hood hood = new Hood();
    private final Climber climber = new Climber();
    private final Peripherals peripherals = new Peripherals();
    private final LightRing lightRing = new LightRing();
    private Trajectory slalomPart1;
    private Trajectory slalomPart2;
    private Trajectory slalomPart4;
    private Trajectory barrelPart1;
    private Trajectory barrelPart2;
    private Trajectory barrelPart3;
    private Trajectory barrelPart4;
    private Trajectory bouncePart1;
    private Trajectory bouncePart2;
    private Trajectory bouncePart3;
    private Trajectory bouncePart4;
    private PurePursuit slalomPart1Follower;
    private PurePursuit slalomPart2Follower;
    private PurePursuit slalomPart3Follower;
    private PurePursuit slalomPart4Follower;
    private PurePursuit barrelPart1Follower;
    private PurePursuit barrelPart2Follower;
    private PurePursuit barrelPart3Follower;
    private PurePursuit barrelPart4Follower;
    private PurePursuit bouncePart1Follower;
    private PurePursuit bouncePart2Follower;
    private PurePursuit bouncePart3Follower;
    private PurePursuit bouncePart4Follower;
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
        try {
            slalomPart1 =
                    TrajectoryUtil.fromPathweaverJson(
                            Paths.get("/home/lvuser/deploy/SlalomPart1.json"));
            slalomPart2 =
                    TrajectoryUtil.fromPathweaverJson(
                            Paths.get("/home/lvuser/deploy/SlalomPart2.json"));
            slalomPart4 =
                    TrajectoryUtil.fromPathweaverJson(
                            Paths.get("/home/lvuser/deploy/SlalomPart4.json"));
            barrelPart1 =
                    TrajectoryUtil.fromPathweaverJson(
                            Paths.get("/home/lvuser/deploy/BarrelPart1.json"));
            barrelPart2 =
                    TrajectoryUtil.fromPathweaverJson(
                            Paths.get("/home/lvuser/deploy/BarrelPart2.json"));
            barrelPart3 =
                    TrajectoryUtil.fromPathweaverJson(
                            Paths.get("/home/lvuser/deploy/BarrelPart3.json"));
            barrelPart4 =
                    TrajectoryUtil.fromPathweaverJson(
                            Paths.get("/home/lvuser/deploy/BarrelPart4.json"));
            bouncePart1 =
                    TrajectoryUtil.fromPathweaverJson(
                            Paths.get("/home/lvuser/deploy/BouncePart1.json"));
            bouncePart2 =
                    TrajectoryUtil.fromPathweaverJson(
                            Paths.get("/home/lvuser/deploy/BouncePart2.json"));
            bouncePart3 =
                    TrajectoryUtil.fromPathweaverJson(
                            Paths.get("/home/lvuser/deploy/BouncePart3.json"));
            bouncePart4 =
                    TrajectoryUtil.fromPathweaverJson(
                            Paths.get("/home/lvuser/deploy/BouncePart4.json"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        odometry.zero();
    }

    @Override
    public void robotPeriodic() {
        hood.periodic();
        CommandScheduler.getInstance().run();
        SmartDashboard.putNumber("navx value", odometry.getTheta());
        SmartDashboard.putNumber("x", odometry.getX());
        SmartDashboard.putNumber("y", odometry.getY());
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
        slalomPart1Follower = new PurePursuit(drive, odometry, slalomPart1, 2.5, 5.0, false);
        slalomPart2Follower = new PurePursuit(drive, odometry, slalomPart2, 2.5, 5.0, false);
        slalomPart3Follower = new PurePursuit(drive, odometry, slalomPart1, 2.5, 5.0, false);
        slalomPart4Follower = new PurePursuit(drive, odometry, slalomPart4, 2.5, 5.0, false);
        barrelPart1Follower = new PurePursuit(drive, odometry, barrelPart1, 2.5, 5.0, false);
        barrelPart2Follower = new PurePursuit(drive, odometry, barrelPart2, 2.5, 5.0, false);
        barrelPart3Follower = new PurePursuit(drive, odometry, barrelPart3, 2.5, 5.0, false);
        barrelPart4Follower = new PurePursuit(drive, odometry, barrelPart4, 2.5, 5.0, false);
        bouncePart1Follower = new PurePursuit(drive, odometry, bouncePart1, 2.5, 5.0, false);
        bouncePart2Follower = new PurePursuit(drive, odometry, bouncePart2, 2.5, 5.0, true);
        bouncePart3Follower = new PurePursuit(drive, odometry, bouncePart3, 2.5, 5.0, false);
        bouncePart4Follower = new PurePursuit(drive, odometry, bouncePart4, 2.5, 5.0, true);

        new SequentialCommandGroup(bouncePart2Follower).schedule();
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopInit() {
        for (SubsystemBaseEnhanced s : subsystems) {
            s.teleopInit();
        }
        OI.driverX.whenPressed(new Fire(shooter, hood, magIntake, drive, lightRing, peripherals));
        OI.driverX.whenReleased(new SetHoodPosition(hood, 0));
        OI.driverX.whenReleased(new CancelMagazine(magIntake));
        OI.driverLT.whileHeld(new Outtake(magIntake));
        OI.driverRT.whileHeld(new SmartIntake(magIntake));
        OI.driverA.whileHeld(new VisionAlignment(lightRing, drive, peripherals));
        OI.driverA.whenReleased(new LightRingOff(lightRing));
    }

    @Override
    public void teleopPeriodic() {
        SmartDashboard.putNumber("Hood Val", hood.getHoodPosition());
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {}
}
