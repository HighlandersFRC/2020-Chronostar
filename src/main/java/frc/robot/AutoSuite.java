// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import frc.robot.commands.autos.BarrelRun;
import frc.robot.commands.autos.BounceRun;
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
    private SixBallAuto sixBallAuto;

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
        sixBallAuto =
                new SixBallAuto(drive, peripherals, odometry, magIntake, shooter, hood, lightRing);
    }

    public void schedule() {
        sixBallAuto.schedule();
    }

    public void cancel() {
        sixBallAuto.cancel();
    }
}
