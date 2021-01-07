/// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.composite;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.robot.commands.basic.SetLowMag;
import frc.robot.commands.basic.StopLowMag;
import frc.robot.subsystems.MagIntake;

public class BumpLowMag extends SequentialCommandGroup {

    private final double waitTime = 0.15;

    public BumpLowMag(MagIntake magIntake) {
        addCommands(
                new SetLowMag(magIntake, true),
                new WaitCommand(waitTime),
                new StopLowMag(magIntake));
    }
}
