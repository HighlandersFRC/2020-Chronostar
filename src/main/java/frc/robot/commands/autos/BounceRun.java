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

public class BounceRun extends SequentialCommandGroup {
    private Trajectory bouncePart1;
    private Trajectory bouncePart2;
    private Trajectory bouncePart3;
    private Trajectory bouncePart4;
    private PurePursuit bouncePart1Follower;
    private PurePursuit bouncePart2Follower;
    private PurePursuit bouncePart3Follower;
    private PurePursuit bouncePart4Follower;

    public BounceRun(Drive drive, Peripherals peripherals, Odometry odometry) {
        try {
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

            TrajectoryUtil.fromPathweaverJson(Paths.get("/home/lvuser/deploy/ThreePart1.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        bouncePart1Follower = new PurePursuit(drive, odometry, bouncePart1, 2.5, 5.0, false);
        bouncePart2Follower = new PurePursuit(drive, odometry, bouncePart2, 2.5, 5.0, true);
        bouncePart3Follower = new PurePursuit(drive, odometry, bouncePart3, 2.5, 5.0, false);
        bouncePart4Follower = new PurePursuit(drive, odometry, bouncePart4, 2.5, 5.0, true);
        addCommands();
    }
}
