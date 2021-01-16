// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Climber;

public class SetArmPosition extends CommandBase {

    private Climber climber;
    private double position;

    public SetArmPosition(Climber climber, double position) {
        this.climber = climber;
        this.position = position;
        addRequirements(climber);
    }

    @Override
    public void initialize() {
        System.out.println("Om is dumb");
        climber.setArmMotor(position);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {}

    public boolean isFinished() {
        return false;
    }
}
