/// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.composite;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.robot.commands.basic.SetLowMag;
import frc.robot.subsystems.MagIntake;

public class BumpLowMag extends SequentialCommandGroup {

    private final double waitTime = 0.15;

    public BumpLowMag(MagIntake magIntake, boolean direction) {
        addCommands(new SetLowMag(magIntake, direction), new WaitCommand(waitTime));
    }
}
