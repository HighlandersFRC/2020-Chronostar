// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.LightRing;
import frc.robot.subsystems.MagIntake;
import frc.robot.subsystems.Peripherals;
import frc.robot.subsystems.Shooter;
import frc.robot.tools.pathing.Odometry;

import java.io.IOException;
import java.nio.file.Paths;

public class AutoSuite {

    private Drive drive;
    private Command auto;
    private Trajectory testTrajectory;

    public AutoSuite(
            Drive drive,
            Odometry odometry,
            Peripherals peripherals,
            Shooter shooter,
            MagIntake magIntake,
            Hood hood,
            LightRing lightRing) {
        this.drive = drive;
        auto = new SequentialCommandGroup();
        try {
            testTrajectory =
                    TrajectoryUtil.fromPathweaverJson(
                            Paths.get("/home/lvuser/deploy/output/Test.wpilib.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void schedule() {
        auto =
                new RamseteCommand(
                        testTrajectory,
                        drive::getPose,
                        new RamseteController(),
                        new SimpleMotorFeedforward(
                                Constants.ramseteKS, Constants.ramseteKV, Constants.ramseteKA),
                        Drive.kinematics,
                        drive::getWheelSpeeds,
                        new PIDController(Constants.ramseteKP, 0, 0),
                        new PIDController(Constants.ramseteKP, 0, 0),
                        drive::tankDriveVolts,
                        drive);
        drive.resetOdometry(testTrajectory.getInitialPose());
        auto.schedule();
    }

    public void beginTeleop() {
        auto.cancel();
    }
}
