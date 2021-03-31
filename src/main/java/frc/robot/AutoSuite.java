// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.autos.BarrelRun;
import frc.robot.commands.autos.BounceRun;
import frc.robot.commands.autos.SixBallAuto;
import frc.robot.commands.autos.SlalomRun;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.LightRing;
import frc.robot.subsystems.MagIntake;
import frc.robot.subsystems.Peripherals;
import frc.robot.subsystems.Shooter;
import frc.robot.tools.pathing.Odometry;

import java.util.List;

public class AutoSuite {

    private SlalomRun slalom;
    private BarrelRun barrel;
    private BounceRun bounce;
    private SixBallAuto sixBall;
    private Command auto;
    private RamseteCommand ramseteCommand;

    public AutoSuite(
            Drive drive,
            Odometry odometry,
            Peripherals peripherals,
            Shooter shooter,
            MagIntake magIntake,
            Hood hood,
            LightRing lightRing) {
        slalom = new SlalomRun(drive, peripherals, odometry);
        barrel = new BarrelRun(drive, peripherals, odometry);
        bounce = new BounceRun(drive, peripherals, odometry);
        sixBall =
                new SixBallAuto(drive, peripherals, odometry, magIntake, shooter, hood, lightRing);
        ramseteCommand =
                new RamseteCommand(
                        TrajectoryGenerator.generateTrajectory(
                                new Pose2d(0, 0, new Rotation2d(0)),
                                List.of(
                                        new Translation2d(1, 0),
                                        new Translation2d(2, 0),
                                        new Translation2d(3, 0),
                                        new Translation2d(4, 0),
                                        new Translation2d(5, 0),
                                        new Translation2d(6, 0),
                                        new Translation2d(7, 0),
                                        new Translation2d(8, 0),
                                        new Translation2d(9, 0)),
                                new Pose2d(10, 0, new Rotation2d(0)),
                                Drive.autoTrajectoryConfig),
                        drive::getPose,
                        new RamseteController(),
                        new edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward(
                                Constants.ramseteKS, Constants.ramseteKV, Constants.ramseteKA),
                        Drive.kinematics,
                        drive::getWheelSpeeds,
                        new PIDController(0.1, 0, 0),
                        new PIDController(0.1, 0, 0),
                        drive::tankDriveVolts,
                        drive);
        auto = new SequentialCommandGroup();
    }

    public void schedule() {
        if (OI.sixBall.get()) {
            auto = sixBall;
        } else if (OI.slalom.get()) {
            auto = slalom;
        } else if (OI.barrel.get()) {
            auto = barrel;
        } else if (OI.bounce.get()) {
            auto = bounce;
        } else if (OI.ramTest.get()) {
            auto = ramseteCommand;
        }
        auto.schedule();
    }

    public void beginTeleop() {
        auto.cancel();
    }
}
