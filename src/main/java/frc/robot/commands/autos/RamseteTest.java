// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.autos;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.Constants;
import frc.robot.subsystems.Drive;

import java.io.IOException;
import java.nio.file.Paths;

public class RamseteTest extends SequentialCommandGroup {

    public RamseteTest(Drive drive) {
        try {
            addCommands(
                    new RamseteCommand(
                                    TrajectoryUtil.fromPathweaverJson(
                                            Paths.get("/home/lvuser/deploy/RamseteTest.json")),
                                    drive::getPose,
                                    new RamseteController(),
                                    new SimpleMotorFeedforward(
                                            Constants.ramseteKS,
                                            Constants.ramseteKV,
                                            Constants.ramseteKA),
                                    Drive.kinematics,
                                    drive::getWheelSpeeds,
                                    new PIDController(Constants.ramseteKP, 0, 0),
                                    new PIDController(Constants.ramseteKP, 0, 0),
                                    drive::tankDriveVolts,
                                    drive)
                            .andThen(() -> drive.tankDriveVolts(0, 0)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
