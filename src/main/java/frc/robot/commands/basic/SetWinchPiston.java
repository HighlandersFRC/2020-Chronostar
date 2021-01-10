package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import frc.robot.subsystems.Climber;

public class SetWinchPiston extends InstantCommand {

    private Climber climber;
    private boolean direction;

    public SetWinchPiston(Climber climber, boolean direction) {
        this.climber = climber;
        this.direction = direction;
    }

    @Override
    public void initialize() {
        if (direction) {
            climber.ratchetPistonForward();
        } else {
            climber.ratchetPistonReverse();
        }
    }

    @Override
    public void execute() {}
}
