/// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.composite;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.robot.commands.basic.SetHighMag;
import frc.robot.commands.basic.StopHighMag;
import frc.robot.subsystems.MagIntake;

public class BumpHighMag extends SequentialCommandGroup {

    private final double waitTime = 0.15;

    public BumpHighMag(MagIntake magIntake) {
        addCommands(
                new SetHighMag(magIntake, true),
                new WaitCommand(waitTime),
                new StopHighMag(magIntake));
    }
}
