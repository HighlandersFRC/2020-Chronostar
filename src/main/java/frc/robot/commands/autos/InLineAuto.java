// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.autos;

import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.basic.PurePursuit;
import frc.robot.commands.basic.SmartIntake;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.MagIntake;
import frc.robot.subsystems.Peripherals;
import frc.robot.subsystems.Shooter;
import frc.robot.tools.pathing.Odometry;

import java.io.IOException;
import java.nio.file.Paths;

public class InLineAuto extends SequentialCommandGroup {

    private Trajectory inLinePart1;
    private PurePursuit inLinePart1Follower;
    private PurePursuit inLinePart2Follower;

    public InLineAuto(
            Drive drive,
            Odometry odometry,
            Peripherals peripherals,
            MagIntake magIntake,
            Shooter shooter) {
        try {
            inLinePart1 =
                    TrajectoryUtil.fromPathweaverJson(
                            Paths.get("/home/lvuser/deploy/ThreePart1.json"));
        } catch (IOException e) {

        }
        inLinePart1Follower = new PurePursuit(drive, odometry, inLinePart1, 2.5, 5.0, true);
        inLinePart2Follower = new PurePursuit(drive, odometry, inLinePart1, 2.5, 5.0, false);
        addCommands(
                new ParallelCommandGroup(inLinePart1Follower, new SmartIntake(magIntake, 9)),
                inLinePart2Follower);
    }
}
