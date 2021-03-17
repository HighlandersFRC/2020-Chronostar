// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.autos;

import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.basic.PurePursuit;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Peripherals;
import frc.robot.tools.pathing.Odometry;

import java.io.IOException;
import java.nio.file.Paths;

public class BarrelRun extends SequentialCommandGroup {

    private Trajectory barrelPart1;
    private Trajectory barrelPart2;
    private Trajectory barrelPart3;
    private Trajectory barrelPart4;
    private PurePursuit barrelPart1Follower;
    private PurePursuit barrelPart2Follower;
    private PurePursuit barrelPart3Follower;
    private PurePursuit barrelPart4Follower;

    public BarrelRun(Drive drive, Peripherals peripherals, Odometry odometry) {
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        barrelPart1Follower = new PurePursuit(drive, odometry, barrelPart1, 2.5, 5.0, false);
        barrelPart2Follower = new PurePursuit(drive, odometry, barrelPart2, 2.5, 5.0, false);
        barrelPart3Follower = new PurePursuit(drive, odometry, barrelPart3, 2.5, 5.0, false);
        barrelPart4Follower = new PurePursuit(drive, odometry, barrelPart4, 2.5, 5.0, false);

        addCommands(
                barrelPart1Follower, barrelPart2Follower, barrelPart3Follower, barrelPart4Follower);
    }
}
