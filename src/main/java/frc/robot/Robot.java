// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.commands.basic.Outtake;
import frc.robot.commands.basic.PurePursuit;
import frc.robot.commands.basic.SetHoodPosition;
import frc.robot.commands.basic.SmartIntake;
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
    private Trajectory tenFeetForward;
    private Trajectory wideTurn;
    private PurePursuit wideTurnPurePursuit;
    private PurePursuit tenFeetPurePursuit;
    private final SubsystemBaseEnhanced[] subsystems = {
        hood, magIntake, drive, shooter, climber, peripherals, lightRing
    };
    private Odometry odometry = new Odometry(drive, peripherals);

    public Robot() {}

    @Override
    public void robotInit() {
        for (SubsystemBaseEnhanced s : subsystems) {
            s.init();
        }
        try {
            wideTurn =
                    TrajectoryUtil.fromPathweaverJson(
                            Paths.get("/home/lvuser/deploy/WideTurn.json"));
            tenFeetForward =
                    TrajectoryUtil.fromPathweaverJson(
                            Paths.get("/home/lvuser/deploy/TenFeetForward.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        SmartDashboard.putNumber("navx angle", peripherals.getNavxAngle());
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
        wideTurnPurePursuit = new PurePursuit(drive, odometry, 2.5, wideTurn);
        tenFeetPurePursuit = new PurePursuit(drive, odometry, 2.5, tenFeetForward);
        wideTurnPurePursuit.schedule();
        odometry = new Odometry(drive, peripherals);
    }

    @Override
    public void autonomousPeriodic() {
        SmartDashboard.putNumber("left fps", drive.getLeftSpeed());
        SmartDashboard.putNumber("right fps", drive.getRightSpeed());
    }

    @Override
    public void teleopInit() {
        for (SubsystemBaseEnhanced s : subsystems) {
            s.teleopInit();
        }
        OI.driverX.whileHeld(new Fire(shooter, hood, magIntake, drive, lightRing, 0));
        OI.driverX.whenReleased(new SetHoodPosition(hood, 0));
        OI.driverLT.whileHeld(new Outtake(magIntake));
        OI.driverRT.whileHeld(new SmartIntake(magIntake));
        odometry = new Odometry(drive, peripherals);
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
