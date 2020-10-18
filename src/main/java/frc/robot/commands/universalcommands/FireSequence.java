// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.ButtonMap;

public class FireSequence extends SequentialCommandGroup {
    public FireSequence(double rpm) {
        super(new SetFlywheelRPM(rpm));
    }

    public boolean isFinished() {
        return !ButtonMap.shoot();
    }

    public void end(boolean interrupted) {
        new SetFlywheelRPM(0).schedule();
    }
}
