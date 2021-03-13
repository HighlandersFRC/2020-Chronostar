// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.autos;

import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.basic.NavxTurn;
import frc.robot.commands.basic.PurePursuit;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Peripherals;
import frc.robot.tools.pathing.Odometry;

import java.io.IOException;
import java.nio.file.Paths;

public class SlalomRun extends SequentialCommandGroup {
    private Trajectory slalomPart1;
    private Trajectory slalomPart2;
    private Trajectory slalomPart4;
    private PurePursuit slalomPart1Follower;
    private PurePursuit slalomPart2Follower;
    private PurePursuit slalomPart3Follower;
    private PurePursuit slalomPart4Follower;

    public SlalomRun(Drive drive, Peripherals peripherals, Odometry odometry) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        slalomPart1Follower = new PurePursuit(drive, odometry, slalomPart1, 2.5, 5.0, false);
        slalomPart2Follower = new PurePursuit(drive, odometry, slalomPart2, 2.5, 5.0, false);
        slalomPart3Follower = new PurePursuit(drive, odometry, slalomPart1, 2.5, 5.0, false);
        slalomPart4Follower = new PurePursuit(drive, odometry, slalomPart4, 2.5, 5.0, false);
        addCommands(
                slalomPart1Follower,
                slalomPart2Follower,
                new NavxTurn(drive, peripherals, -180),
                slalomPart3Follower,
                slalomPart4Follower);
    }
}
