// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.composite;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.robot.commands.basic.EjectMagazine;
import frc.robot.commands.basic.SetHoodPosition;
import frc.robot.commands.basic.SpinFlywheel;
import frc.robot.subsystems.*;

public class Fire extends SequentialCommandGroup {
    public Fire(Shooter shooter, Hood hood, MagIntake magIntake, double waitTime) {
        addRequirements(shooter, hood, magIntake);
        addCommands(
                new ParallelCommandGroup(
                        new SetHoodPosition(hood, 8), new SpinFlywheel(shooter, 4000)),
                new EjectMagazine(magIntake),
                new WaitCommand(waitTime));
    }
}
