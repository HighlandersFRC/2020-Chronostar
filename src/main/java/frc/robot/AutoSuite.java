// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.autos.BarrelRun;
import frc.robot.commands.autos.BounceRun;
import frc.robot.commands.autos.GalacticA1;
import frc.robot.commands.autos.GalacticA2;
import frc.robot.commands.autos.GalacticB1;
import frc.robot.commands.autos.GalacticB2;
import frc.robot.commands.autos.SixBallAuto;
import frc.robot.commands.autos.SlalomRun;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.LightRing;
import frc.robot.subsystems.MagIntake;
import frc.robot.subsystems.Peripherals;
import frc.robot.subsystems.Shooter;
import frc.robot.tools.pathing.Odometry;

public class AutoSuite {

    private SlalomRun slalom;
    private BarrelRun barrel;
    private BounceRun bounce;
    private SixBallAuto sixBall;
    private GalacticA1 galacticA1;
    private GalacticA2 galacticA2;
    private GalacticB1 galacticB1;
    private GalacticB2 galacticB2;
    private Command auto;
    private Drive drive;
    private Odometry odometry;
    private Peripherals peripherals;
    private Shooter shooter;
    private MagIntake magIntake;
    private Hood hood;
    private LightRing lightRing;

    public AutoSuite(
            Drive drive,
            Odometry odometry,
            Peripherals peripherals,
            Shooter shooter,
            MagIntake magIntake,
            Hood hood,
            LightRing lightRing) {
        this.drive = drive;
        this.odometry = odometry;
        this.peripherals = peripherals;
        this.shooter = shooter;
        this.magIntake = magIntake;
        this.hood = hood;
        this.lightRing = lightRing;
        slalom = new SlalomRun(drive, peripherals, odometry);
        barrel = new BarrelRun(drive, peripherals, odometry);
        bounce = new BounceRun(drive, peripherals, odometry);
        galacticA1 = new GalacticA1(drive, magIntake, peripherals, odometry);
        galacticA2 = new GalacticA2(drive, magIntake, peripherals, odometry);
        galacticB1 = new GalacticB1(drive, magIntake, peripherals, odometry);
        galacticB2 = new GalacticB2(drive, magIntake, peripherals, odometry);
        sixBall =
                new SixBallAuto(drive, peripherals, odometry, magIntake, shooter, hood, lightRing);
        auto = new SequentialCommandGroup();
    }

    public void schedule() {
        if (OI.sixBall.get()) {
            auto = sixBall;
        } else if (OI.slalom.get()) {
            auto = slalom;
        } else if (OI.barrel.get()) {
            auto = barrel;
        } else if (OI.bounce.get()) {
            auto = bounce;
        } else if (OI.ga1.get()) {
            auto = galacticA1;
        } else if (OI.ga2.get()) {
            auto = galacticA2;
        } else if (OI.gb1.get()) {
            auto = galacticB1;
        } else if (OI.gb2.get()) {
            auto = galacticB2;
        }
        auto.schedule();
    }

    public void cancel() {
        if (auto.isScheduled()) {
            auto.cancel();
        }
    }

    public void setGalacticSearch(int disabledCounter) {
        lightRing.turnBallOn();
        double angle = peripherals.getCamAngle();
        double distance = peripherals.getCamDistance();
        double[] distances = new double[5];
        int i = 0;
        while (i < 4) {
            distance = peripherals.getCamDistance();
            angle = peripherals.getCamAngle();
            if (Math.abs(angle) < 5 && distance < 20 && distance > 3) {
                distances[i] = distance;
                SmartDashboard.putNumber("yes", Math.random());
                i++;
            }
        }
        double avgDistance = 0.0;
        for (double d : distances) {
            avgDistance += d;
        }
        avgDistance /= 5.0;
        SmartDashboard.putNumber("avg distance", avgDistance);
        if (avgDistance > 3.5 && avgDistance < 8.0) {
            if (disabledCounter % 2 == 0) {
                auto = galacticA1;
            } else {
                auto = galacticB1;
            }
        } else if (avgDistance > 8.0 && avgDistance < 16.0) {
            if (disabledCounter % 2 == 0) {
                auto = galacticA2;
            } else {
                auto = galacticB2;
            }
        }
    }
}
