// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.defaults;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.MagIntake;

public class MagIntakeDefault extends CommandBase {

    private MagIntake magIntake;

    public MagIntakeDefault(MagIntake magIntake) {
        this.magIntake = magIntake;
        addRequirements(this.magIntake);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        magIntake.setIntakePercent(0, 0);
        magIntake.intakePistonUp();
        magIntake.setMagPercent(0, 0);
    }

    @Override
    public void end(boolean interrupted) {    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
