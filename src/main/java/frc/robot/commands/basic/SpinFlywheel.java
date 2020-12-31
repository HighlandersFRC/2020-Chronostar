// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class SpinFlywheel extends CommandBase {

    private Shooter shooter;
    private double rpm;

    public SpinFlywheel(Shooter shooter, double rpm) {
        this.shooter = shooter;
        this.rpm = rpm;
        addRequirements(this.shooter);
    }

    @Override
    public void initialize() {
        shooter.setRPM(rpm);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return Constants.shooterRPMToUnitsPer100MS(Math.abs(shooter.getShooterRPM() - rpm)) < 100;
    }
}
