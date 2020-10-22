// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FireSequence extends SequentialCommandGroup {
    public FireSequence(int rpm, double lowPower, double highPower) {
        super(new SetFlywheelRPM(rpm), new SetMags(lowPower, highPower));
    }
}
