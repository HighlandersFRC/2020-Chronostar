// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class HighMagBump extends SequentialCommandGroup {
    public HighMagBump(double power, double duration) {
        super(
                new SetHighMag(power),
                new WaitCommand(duration),
                new SetHighMag(0),
                new WaitCommand(0.05));
    }
}
