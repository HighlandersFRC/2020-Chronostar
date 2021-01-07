// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.MagIntake;

public class Intake extends CommandBase {

    private MagIntake magIntake;

    public Intake(MagIntake magIntake) {
        this.magIntake = magIntake;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        magIntake.setIntake(0.8, 0.6);
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return true;
    }
}
