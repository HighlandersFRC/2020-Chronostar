// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.defaults;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.commands.basic.StopHighMag;
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
        magIntake.setMagazine(0, 0);
        SmartDashboard.putBoolean("Beam Break 1", magIntake.getBeamBreak1());
        SmartDashboard.putBoolean("Beam Break 2", magIntake.getBeamBreak2());
        SmartDashboard.putBoolean("Beam Break 3", magIntake.getBeamBreak3());
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
