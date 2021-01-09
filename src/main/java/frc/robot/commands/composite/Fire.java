// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.composite;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.basic.EjectMagazine;
import frc.robot.commands.basic.SetHoodPosition;
import frc.robot.commands.basic.SpinFlywheel;
import frc.robot.commands.basic.VisionAlignment;
import frc.robot.subsystems.*;

public class Fire extends SequentialCommandGroup {
    public Fire(
            Shooter shooter,
            Hood hood,
            MagIntake magIntake,
            Drive drive,
            LightRing lightRing,
            double waitTime) {
        addRequirements(shooter, hood, magIntake);
        addCommands(
                new ParallelCommandGroup(
                        new SetHoodPosition(hood, 5),
                        new SpinFlywheel(shooter, 4000),
                        new VisionAlignment(lightRing, drive)),
                new EjectMagazine(magIntake),
                // new WaitCommand(waitTime));
                new SetHoodPosition(hood, 0));
    }
}
