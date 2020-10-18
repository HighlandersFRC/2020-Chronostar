// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.ButtonMap;
import frc.robot.RobotMap;

public class SetFlywheelRPM extends CommandBase {

    private static double rpm;

    public SetFlywheelRPM(double rpm) {
        this.rpm = rpm;
    }

    @Override
    public void initialize() {
        RobotMap.leftFlywheel.set(ControlMode.Velocity, rpm);
    }

    @Override
    public void execute() {
        new RunMags(0.5, -1).schedule();
    }

    @Override
    public void end(boolean interrupted) {
        RobotMap.leftFlywheel.set(ControlMode.Velocity, 0);
        new RunMags(0, 0).schedule();
    }

    public boolean isFinished() {
        return isAtTargetRPM() && !ButtonMap.shoot();
    }

    public static boolean isAtTargetRPM() {
        return Math.abs(rpm - RobotMap.shooter.getShooterRPM()) <= 100;
    }
}
