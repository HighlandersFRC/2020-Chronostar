// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.defaults;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Climber;

public class ClimberDefault extends CommandBase {

    private final Climber climber;

    public ClimberDefault(final Climber climber) {
        this.climber = climber;
        addRequirements(this.climber);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        climber.disengageRatchetPiston();
        climber.setWinchMotor(0);
        climber.setArmMotor(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
