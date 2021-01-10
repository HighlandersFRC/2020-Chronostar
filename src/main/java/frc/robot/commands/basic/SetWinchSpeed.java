// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Climber;

public class SetWinchSpeed extends CommandBase {

    private Climber climber;
    private boolean direction;

    public SetWinchSpeed(Climber climber, boolean direction) {
        this.climber = climber;
        this.direction = direction;
    }

    @Override
    public void initialize() {
        climber.setWinchMotor(direction ? 0.8 : -0.8);
    }

    @Override
    public void execute() {}

    public void end(boolean interrupted) {}

    public boolean isFinished() {
        return true;
    }
}
