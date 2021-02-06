// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.composite;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.robot.commands.basic.EjectMagazine;
import frc.robot.commands.basic.SetHoodPosition;
import frc.robot.commands.basic.SpinFlywheel;
import frc.robot.commands.basic.VisionAlignment;
import frc.robot.subsystems.*;

public class Fire extends SequentialCommandGroup {

    public Fire(
            Shooter shooter,
            Hood hood,
            MagIntake magIntake,
            Drive drive,
            LightRing lightRing) {
        addRequirements(shooter, hood, magIntake, drive, lightRing);
        addCommands(
                new ParallelCommandGroup(
                        new SetHoodPosition(hood, 8),
                        new SpinFlywheel(shooter, magIntake, 5000)),
                new EjectMagazine(magIntake));
                
    }
}
