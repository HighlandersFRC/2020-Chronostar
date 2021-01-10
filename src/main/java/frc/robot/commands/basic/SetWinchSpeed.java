// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Climber;

public class SetWinchSpeed extends CommandBase {

    private Climber climber;
    private boolean direction;
    private double speed = 0.8;

    public SetWinchSpeed(Climber climber, boolean direction) {
        this.climber = climber;
        this.direction = direction;
    }

    @Override
    public void initialize() {
        if (direction) {
            climber.setWinchMotor(speed);
        } else {
            climber.setWinchMotor(-speed);
        }
    }

    @Override
    public void execute() {}

    public void end(boolean interrupted) {}

    public boolean isFinished() {
        return true;
    }
}
