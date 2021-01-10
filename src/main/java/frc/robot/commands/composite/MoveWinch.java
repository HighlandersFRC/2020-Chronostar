// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.composite;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.robot.commands.basic.SetWinchPiston;
import frc.robot.commands.basic.SetWinchSpeed;
import frc.robot.subsystems.Climber;

public class MoveWinch extends SequentialCommandGroup {

    public MoveWinch(Climber climber, boolean direction) {
        addRequirements(climber);
        addCommands(
                new SetWinchPiston(climber, direction),
                new WaitCommand(0.1),
                new SetWinchSpeed(climber, direction));
    }
}
