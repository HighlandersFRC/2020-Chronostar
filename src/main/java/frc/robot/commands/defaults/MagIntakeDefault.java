// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.defaults;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.commands.basic.StopHighMag;
import frc.robot.commands.basic.StopLowMag;
import frc.robot.commands.composite.BumpHighMag;
import frc.robot.commands.composite.BumpLowMag;
import frc.robot.subsystems.MagIntake;

public class MagIntakeDefault extends CommandBase {

    private MagIntake magIntake;
    private int catchCounter = 0;
    private int tryCounter = 0;

    public MagIntakeDefault(MagIntake magIntake) {
        this.magIntake = magIntake;
        addRequirements(this.magIntake);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        // Intake stuff
        magIntake.setIntake(0, 0);
        magIntake.intakePistonDown();

        // Magazine stuff
        if (!magIntake.getBeamBreak3()) {
            new StopHighMag(magIntake).schedule();
        } else if (!magIntake.getBeamBreak2()) {
            new BumpHighMag(magIntake).schedule();
            new BumpLowMag(magIntake).schedule();
        } else {
            new StopHighMag(magIntake).schedule();
        }
        if (!magIntake.getBeamBreak1()) {
            if (catchCounter <= 50) {
                new BumpLowMag(magIntake).schedule();
            } else {
                if (tryCounter <= 25) {
                    tryCounter++;
                } else {
                    new BumpLowMag(magIntake).schedule();
                    catchCounter = 0;
                    tryCounter = 0;
                }
            }
            catchCounter++;
        } else {
            catchCounter = 0;
            new StopLowMag(magIntake).schedule();
        }
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
