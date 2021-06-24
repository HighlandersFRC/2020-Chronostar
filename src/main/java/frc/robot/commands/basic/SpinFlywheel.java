// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.MagIntake;
import frc.robot.subsystems.Shooter;

public class SpinFlywheel extends CommandBase {

    private int spinShooterCount = 0;
    private Shooter shooter;
    private MagIntake magIntake;
    private double rpm;
    private int counter = 0;

    public SpinFlywheel(Shooter shooter, MagIntake magIntake, double rpm) {
        this.shooter = shooter;
        this.magIntake = magIntake;
        this.rpm = rpm;
        addRequirements(this.shooter);
    }

    @Override
    public void initialize() {
        SmartDashboard.putBoolean("FinishedFlywheelCommand", false);
        spinShooterCount = 0;
        shooter.setRPM(rpm);
        counter = 0;
    }

    @Override
    public void execute() {
        counter++;
        spinShooterCount++;
        SmartDashboard.putNumber("Shooter Count", spinShooterCount);
        SmartDashboard.putNumber("Shooter rpm", shooter.getShooterRPM());
        System.out.println("got to here 2");
    }

    @Override
    public void end(boolean interrupted) {
        SmartDashboard.putBoolean("FinishedFlywheelCommand", true);
        // System.out.println("Hola Amigo");
    }

    @Override
    public boolean isFinished() {
        return Math.abs(shooter.getShooterRPM() - rpm) < 100 || counter >= 30;
        // return true;
    }
}
