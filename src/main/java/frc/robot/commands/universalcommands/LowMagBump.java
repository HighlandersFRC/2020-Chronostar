// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import edu.wpi.first.wpilibj2.command.*;

public class LowMagBump extends SequentialCommandGroup {
    public LowMagBump(double power, double duration) {
        super(
                new SetLowMag(power),
                new WaitCommand(duration),
                new SetLowMag(0),
                new WaitCommand(0.05));
    }
}
