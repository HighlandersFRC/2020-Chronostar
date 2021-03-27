// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;

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

    public AutoSuite(
            Drive drive,
            Odometry odometry,
            Peripherals peripherals,
            Shooter shooter,
            MagIntake magIntake,
            Hood hood,
            LightRing lightRing) {
        slalom = new SlalomRun(drive, peripherals, odometry);
        barrel = new BarrelRun(drive, peripherals, odometry);
        bounce = new BounceRun(drive, peripherals, odometry);
        galacticA1 = new GalacticA1(drive, peripherals, odometry);
        galacticA2 = new GalacticA2(drive, peripherals, odometry);
        galacticB1 = new GalacticB1(drive, peripherals, odometry);
        galacticB2 = new GalacticB2(drive, peripherals, odometry);
        sixBall =
                new SixBallAuto(drive, peripherals, odometry, magIntake, shooter, hood, lightRing);
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
        } else {
            auto = new SequentialCommandGroup();
        }
        auto.schedule();
    }

    public void cancel() {
        if (auto.isScheduled()) {
            auto.cancel();
        }
    }
}
