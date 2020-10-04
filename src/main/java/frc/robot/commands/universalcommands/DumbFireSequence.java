// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.robot.ButtonMap;

public class DumbFireSequence extends SequentialCommandGroup {

    public DumbFireSequence() {
        super(new SetFlywheelPercent(0.5), new WaitCommand(1.5), new SetMags(-1, 0.2));
    }

    public boolean isFinished() {
        return !ButtonMap.shoot();
    }

    public void end() {
        new SetFlywheelPercent(0).schedule();
        new SetMags(0, 0).schedule();
    }
}
