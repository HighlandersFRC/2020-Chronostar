// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.composite;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.robot.commands.basic.BallTracking;
import frc.robot.commands.basic.SmartIntake;
import frc.robot.subsystems.*;
import frc.robot.subsystems.MagIntake.BeamBreakID;

public class GalacticSearch extends SequentialCommandGroup {
    private MagIntake magIntake;
    // private int hoodsPosition = 0;

    public GalacticSearch(
            Drive drive,
            Peripherals peripherals,
            MagIntake magIntake,
            LightRing lightRing,
            double angleOffset) {
        this.magIntake = magIntake;
        addRequirements(drive, magIntake);
        addCommands(
                new BallTracking(lightRing, drive, peripherals, angleOffset, magIntake),
                new WaitCommand(1),
                new SmartIntake(magIntake));
    }

    public boolean isFinished() {
        return magIntake.getBeamBreak(BeamBreakID.ONE);
    }
}
