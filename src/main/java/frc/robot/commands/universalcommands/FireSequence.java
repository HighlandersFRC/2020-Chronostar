// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.ButtonMap;

public class FireSequence extends SequentialCommandGroup {
    public FireSequence(double rpm, double hoodPosition) {
        super(
                new SetFlywheelVelocity(rpm),
                new ParallelCommandGroup(new SetLowMag(0.5), new SetHighMag(-1)));
    }

    public boolean isFinished() {
        return !ButtonMap.shoot();
    }

    public void end(boolean interrupted) {
        new SetLowMag(0).schedule();
        new SetHighMag(0).schedule();
        new SetFlywheelVelocity(0).schedule();
    }
}
