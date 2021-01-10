// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Climber;

public class SetArmMotor extends CommandBase {

    private Climber climber;
    private double position;

    public SetArmMotor(Climber climber, double position) {
        this.climber = climber;
        this.position = position;
        addRequirements(climber);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        climber.setArmMotor(position);
    }

    @Override
    public void end(boolean interrupted) {}

    public boolean isFinished() {
        return false;
    }
}
