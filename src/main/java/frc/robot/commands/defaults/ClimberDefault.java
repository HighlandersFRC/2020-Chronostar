// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.defaults;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.robot.OI;
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
        if (OI.getDriverRightTrigger() >= 0.5) {
            climber.ratchetPistonReverse();
            climber.setWinchMotor(0.8);
        } else if (OI.getDriverLeftTrigger() >= 0.5) {
            climber.ratchetPistonForward();
            new SequentialCommandGroup(new WaitCommand(0.1), new SetWinchSpeed(-0.8)).schedule();
        } else {
            climber.ratchetPistonReverse();
            // this.climber.winch.set(ControlMode.PercentOutput, 0);
        }
    }

    @Override
    public void end(final boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
