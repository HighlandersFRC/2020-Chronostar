// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import frc.robot.subsystems.Climber;

public class SetClimberPiston extends InstantCommand {

    private Climber climber;
    private boolean direction;

    public SetClimberPiston(Climber climber, boolean direction) {
        this.climber = climber;
        this.direction = direction;
        addRequirements(climber);
    }

    @Override
    public void initialize() {
        if (direction) {
            climber.engageClimberRelease();
        } else {
            climber.disengageClimberRelease();
        }
    }

    @Override
    public void execute() {}
}
