// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.autos.SixBallAuto;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.LightRing;
import frc.robot.subsystems.MagIntake;
import frc.robot.subsystems.Peripherals;
import frc.robot.subsystems.Shooter;
import frc.robot.tools.pathing.Odometry;

import java.io.IOException;
import java.nio.file.Paths;

public class AutoSuite {

    private SixBallAuto sixBall;
    private Command auto;
    private RamseteCommand slalom;
    private Trajectory slalomTrajectory;
    private RamseteCommand barrel;
    private Trajectory barrelTrajectory;

    public AutoSuite(
            Drive drive,
            Odometry odometry,
            Peripherals peripherals,
            Shooter shooter,
            MagIntake magIntake,
            Hood hood,
            LightRing lightRing) {
        sixBall =
                new SixBallAuto(drive, peripherals, odometry, magIntake, shooter, hood, lightRing);
        try {
            slalomTrajectory =
                    TrajectoryUtil.fromPathweaverJson(Paths.get("/home/lvuser/deploy/Slalom.json"));
            barrelTrajectory =
                    TrajectoryUtil.fromPathweaverJson(Paths.get("/home/lvuser/deploy/Barrel.json"));
            drive.resetOdometry(slalomTrajectory.getInitialPose());
            slalom =
                    new RamseteCommand(
                            slalomTrajectory,
                            drive::getPose,
                            new RamseteController(),
                            new SimpleMotorFeedforward(
                                    Constants.ramseteKS, Constants.ramseteKV, Constants.ramseteKA),
                            Drive.kinematics,
                            drive::getWheelSpeeds,
                            new PIDController(Constants.ramseteKP, 0, 0),
                            new PIDController(Constants.ramseteKP, 0, 0),
                            drive::tankDriveVolts,
                            drive);
            barrel =
                    new RamseteCommand(
                            barrelTrajectory,
                            drive::getPose,
                            new RamseteController(),
                            new SimpleMotorFeedforward(
                                    Constants.ramseteKS, Constants.ramseteKV, Constants.ramseteKA),
                            Drive.kinematics,
                            drive::getWheelSpeeds,
                            new PIDController(Constants.ramseteKP, 0, 0),
                            new PIDController(Constants.ramseteKP, 0, 0),
                            drive::tankDriveVolts,
                            drive);
        } catch (IOException e) {
            e.printStackTrace();
        }
        auto = new SequentialCommandGroup();
    }

    public void schedule() {
        if (OI.sixBall.get()) {
            auto = sixBall;
        } else if (OI.slalom.get()) {
            auto = slalom;
        } else if (OI.barrel.get()) {
            auto = barrel;
        }
        auto.schedule();
    }

    public void beginTeleop() {
        auto.cancel();
    }
}
