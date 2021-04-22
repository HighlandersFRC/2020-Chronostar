// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.autos;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.Constants;
import frc.robot.subsystems.Drive;

import java.util.List;

public class SixBallAuto extends SequentialCommandGroup {
    private Trajectory sixBallPart1;
    private TrajectoryConfig sixBallPart1Config;
    private RamseteCommand sixBallPart1Follower;

    public SixBallAuto(Drive drive) {
        sixBallPart1Config = new TrajectoryConfig(3, 3);
        sixBallPart1Config.setReversed(true);
        sixBallPart1 =
                TrajectoryGenerator.generateTrajectory(
                        new Pose2d(
                                Units.feetToMeters(10),
                                Units.feetToMeters(-4),
                                new Rotation2d(Units.feetToMeters(3), Units.feetToMeters(2))),
                        List.of(
                                new Translation2d(
                                        Units.feetToMeters(15), Units.feetToMeters(-2.5))),
                        new Pose2d(
                                Units.feetToMeters(29),
                                Units.feetToMeters(-2.5),
                                new Rotation2d(Units.feetToMeters(3), Units.feetToMeters(0))),
                        sixBallPart1Config);
        sixBallPart1Follower =
                new RamseteCommand(
                        sixBallPart1,
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
        addCommands(sixBallPart1Follower);
    }
}
