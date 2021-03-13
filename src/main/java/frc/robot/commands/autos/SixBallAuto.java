// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.autos;

import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.basic.NavxTurn;
import frc.robot.commands.basic.PurePursuit;
import frc.robot.commands.basic.SmartIntake;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.MagIntake;
import frc.robot.subsystems.Peripherals;
import frc.robot.subsystems.Shooter;
import frc.robot.tools.pathing.Odometry;

import java.io.IOException;
import java.nio.file.Paths;

public class SixBallAuto extends SequentialCommandGroup {

    private Trajectory sixBallPart1;
    private Trajectory sixBallPart2;
    private PurePursuit sixBallPart1Follower;
    private PurePursuit sixBallPart2Follower;

    public SixBallAuto(
            Drive drive,
            Peripherals peripherals,
            Odometry odometry,
            MagIntake magIntake,
            Shooter shooter) {
        try {
            sixBallPart1 =
                    TrajectoryUtil.fromPathweaverJson(
                            Paths.get("/home/lvuser/deploy/SixBallPart1.json"));
            sixBallPart2 =
                    TrajectoryUtil.fromPathweaverJson(
                            Paths.get("/home/lvuser/deploy/SixBallPart2.json"));
        } catch (IOException e) {
            cancel();
        }
        sixBallPart1Follower = new PurePursuit(drive, odometry, sixBallPart1, 2.5, 5.0, true);
        sixBallPart2Follower = new PurePursuit(drive, odometry, sixBallPart2, 2.5, 5.0, true);
        addCommands(
                sixBallPart1Follower,
                new NavxTurn(drive, peripherals, 10),
                new ParallelCommandGroup(new SmartIntake(magIntake, 10), sixBallPart2Follower));
    }
}
