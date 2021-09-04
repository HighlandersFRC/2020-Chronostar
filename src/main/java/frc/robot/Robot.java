// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;

import frc.robot.commands.basic.CancelMagazine;
import frc.robot.commands.basic.ClimberDown;
import frc.robot.commands.basic.ClimberUp;
import frc.robot.commands.basic.DriveBackwards;
import frc.robot.commands.basic.EjectMagazine;
import frc.robot.commands.basic.Outtake;
import frc.robot.commands.basic.SetHoodPosition;
import frc.robot.commands.basic.SmartIntake;
import frc.robot.commands.composite.AutoShooting;
import frc.robot.commands.composite.Fire;
import frc.robot.sensors.Navx;
import frc.robot.subsystems.*;
import frc.robot.subsystems.MagIntake.BeamBreakID;
import frc.robot.tools.pathing.Odometry;

public class Robot extends TimedRobot {

    private final MagIntake magIntake = new MagIntake();
    private final Drive drive = new Drive();
    private final Shooter shooter = new Shooter();
    private final Hood hood = new Hood();
    private final Climber climber = new Climber();
    private final Peripherals peripherals = new Peripherals();
    private final LightRing lightRing = new LightRing();
    private AHRS imu = new AHRS(Port.kMXP);
    private Navx navx = new Navx(imu);
    private UsbCamera camera;
    private VideoSink server;
    private UsbCamera camera2;
    private VideoSink server2;
    private boolean cameraBoolean;
    private boolean ableToSwitch;

    DriveBackwards driveBackwards = new DriveBackwards(drive);
    SmartIntake smartIntake = new SmartIntake(magIntake);
    ParallelRaceGroup autonomous = new ParallelRaceGroup(driveBackwards, smartIntake);
    AutoShooting autoShooting =
            new AutoShooting(drive, shooter, hood, magIntake, lightRing, peripherals);

    private final SubsystemBaseEnhanced[] subsystems = {
        hood, magIntake, drive, shooter, climber, lightRing, peripherals
    };
    private final Odometry odometry = new Odometry(drive, peripherals);

    public Robot() {}

    @Override
    public void robotInit() {
        for (SubsystemBaseEnhanced s : subsystems) {
            s.init();
        }
        navx.softResetAngle();
        odometry.zero();
        camera = CameraServer.getInstance().startAutomaticCapture("VisionCamera1", "/dev/video0");
        camera.setResolution(320, 240);
        camera.setFPS(10);

        camera2 = CameraServer.getInstance().startAutomaticCapture("VisionCamera2", "/dev/video1");
        camera2.setResolution(320, 240);
        camera2.setFPS(10);

        server = CameraServer.getInstance().addSwitchedCamera("driverVisionCameras");
        server.setSource(camera);
        Shuffleboard.update();
        SmartDashboard.updateValues();
    }

    @Override
    public void robotPeriodic() {
        if (OI.driverStart.get() && ableToSwitch) {
            if (cameraBoolean) {
                server.setSource(camera2);
                cameraBoolean = false;
            } else if (!cameraBoolean) {
                server.setSource(camera);
                cameraBoolean = true;
            }
            ableToSwitch = false;
        } else if (!OI.driverStart.get()) {
            ableToSwitch = true;
        }
        SmartDashboard.putNumber("Vision Angle", peripherals.getCamAngle());
        hood.periodic();
        SmartDashboard.putNumber("Lidar Dist", peripherals.getLidarDistance());
        SmartDashboard.putNumber("HoodValue", hood.getHoodPosition());
        SmartDashboard.putBoolean("BeamBreak1", magIntake.getBeamBreak(BeamBreakID.ONE));
        SmartDashboard.putBoolean("BeamBreak2", magIntake.getBeamBreak(BeamBreakID.TWO));
        SmartDashboard.putBoolean("BreamBreak3", magIntake.getBeamBreak(BeamBreakID.THREE));
        SmartDashboard.putNumber("Navx", peripherals.getNavxAngle());
        CommandScheduler.getInstance().run();
        SmartDashboard.putNumber("navx value", odometry.getTheta());
        SmartDashboard.putNumber("x", odometry.getX());
        SmartDashboard.putNumber("y", odometry.getY());
        SmartDashboard.putNumber("drive feet", drive.getDriveFeet());
    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    @Override
    public void autonomousInit() {
        for (SubsystemBaseEnhanced s : subsystems) {
            s.autoInit();
        }
        navx.softResetAngle();
        autoShooting.schedule();

        odometry.zero();
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopInit() {
        for (SubsystemBaseEnhanced s : subsystems) {
            s.teleopInit();
        }
        navx.softResetAngle();
        OI.driverA.whileHeld(
                new Fire(shooter, hood, magIntake, drive, lightRing, peripherals, 1.0, 2500, 4));
        OI.driverY.whenPressed(
                new Fire(shooter, hood, magIntake, drive, lightRing, peripherals, 0.3, 1500, 0));

        OI.driverB.whenPressed(new EjectMagazine(magIntake, drive));
        OI.driverX.whenPressed(
                ((new Fire(
                        shooter, hood, magIntake, drive, lightRing, peripherals, 1.1, 3500, 10))));

        OI.driverA.whenReleased(new SetHoodPosition(hood, 0));
        OI.driverA.whenReleased(new CancelMagazine(magIntake));
        //  OI.driverB.whenReleased(new SetHoodPosition(hood, 0));
        //  OI.driverB.whenReleased(new CancelMagazine(magIntake));
        OI.driverY.whenReleased(new SetHoodPosition(hood, 0));
        OI.driverY.whenReleased(new CancelMagazine(magIntake));
        OI.driverX.whenReleased(new SetHoodPosition(hood, 0));
        OI.driverX.whenReleased(new CancelMagazine(magIntake));
        OI.driverLT.whileHeld(new Outtake(magIntake));
        OI.driverRT.whileHeld(new SmartIntake(magIntake));
        OI.operatorX.whenPressed(new ClimberUp(climber));
        OI.operatorB.whenPressed(new ClimberDown(climber));
    }

    @Override
    public void teleopPeriodic() {

        hood.periodic();
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {}
}
