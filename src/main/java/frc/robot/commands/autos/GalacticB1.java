// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.autos;

import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;

import frc.robot.commands.basic.PurePursuit;
import frc.robot.commands.basic.SmartIntake;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.MagIntake;
import frc.robot.subsystems.Peripherals;
import frc.robot.tools.pathing.Odometry;

import java.io.IOException;
import java.nio.file.Paths;

public class GalacticB1 extends ParallelRaceGroup {
    private Trajectory trajectory;
    private PurePursuit purePursuit;

    public GalacticB1(
            Drive drive, MagIntake magIntake, Peripherals peripherals, Odometry odometry) {
        try {
            trajectory =
                    TrajectoryUtil.fromPathweaverJson(
                            Paths.get("/home/lvuser/deploy/GalacticSearchB1.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        purePursuit = new PurePursuit(drive, odometry, trajectory, 2.5, 5.0, true);
        addCommands(purePursuit, new SmartIntake(magIntake, 20));
    }
}
