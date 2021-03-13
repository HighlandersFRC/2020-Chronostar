// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.autos;

import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.basic.NavxTurn;
import frc.robot.commands.basic.PurePursuit;
import frc.robot.commands.basic.SmartIntake;
import frc.robot.commands.composite.Fire;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.LightRing;
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
    private PurePursuit sixBallPart3Follower;

    public SixBallAuto(
            Drive drive,
            Peripherals peripherals,
            Odometry odometry,
            MagIntake magIntake,
            Shooter shooter,
            Hood hood,
            LightRing lightRing) {
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
        sixBallPart3Follower = new PurePursuit(drive, odometry, sixBallPart2, 2.5, 5.0, false);
        addCommands(
                new Fire(
                        shooter, hood, magIntake, drive, lightRing, peripherals, 12.55, 5200, 8, 2),
                new NavxTurn(drive, peripherals, 0),
                sixBallPart1Follower,
                new NavxTurn(drive, peripherals, 0),
                new ParallelRaceGroup(new SmartIntake(magIntake, 8), sixBallPart2Follower),
                new NavxTurn(drive, peripherals, 0),
                sixBallPart3Follower,
                new Fire(
                        shooter,
                        hood,
                        magIntake,
                        drive,
                        lightRing,
                        peripherals,
                        14.75,
                        5500,
                        10,
                        3));
    }
}
