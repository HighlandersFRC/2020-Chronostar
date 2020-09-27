// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.ButtonMap;

public class FireSequence extends SequentialCommandGroup {
    public FireSequence(double rpm, double hoodPosition) {
        super(new SetFlywheelVelocity(rpm), new SetMags(-1, 0.6));
    }

    public boolean isFinished() {
        return !ButtonMap.shoot();
    }

    public void end(boolean interrupted) {
        new SetMags(0, 0).schedule();
        new SetFlywheelVelocity(0).schedule();
    }
}
