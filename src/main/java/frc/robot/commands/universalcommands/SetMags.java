// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

import frc.robot.RobotMap;

public class SetMags extends ParallelCommandGroup {
    public SetMags(double lowPower, double highPower) {
        addRequirements(RobotMap.magazine);
        addCommands(new SetLowMag(lowPower), new SetHighMag(highPower));
    }
}
