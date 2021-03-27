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

public class GalacticA1 extends SequentialCommandGroup {
    private Trajectory trajectory;
    private PurePursuit purePursuit;

    public GalacticA1(Drive drive, Peripherals peripherals, Odometry odometry) {
        try {
            trajectory =
                    TrajectoryUtil.fromPathweaverJson(
                            Paths.get("/home/lvuser/deploy/GalacticSearchA1.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        purePursuit = new PurePursuit(drive, odometry, trajectory, 2.5, 5.0, true);
        addCommands(purePursuit);
    }
}
