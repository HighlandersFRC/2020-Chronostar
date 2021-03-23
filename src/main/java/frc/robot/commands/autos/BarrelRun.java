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

    private Trajectory barrel;
    private PurePursuit barrelFollower;

    public BarrelRun(Drive drive, Peripherals peripherals, Odometry odometry) {
        try {
            barrel =
                    TrajectoryUtil.fromPathweaverJson(Paths.get("/home/lvuser/deploy/Barrel.json"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        barrelFollower = new PurePursuit(drive, odometry, barrel, 2.5, 5.0, false);
        addCommands(barrelFollower);
    }
}
