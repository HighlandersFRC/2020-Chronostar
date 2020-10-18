// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import frc.robot.RobotMap;

public class SetFlywheelRPM extends InstantCommand {

    private static double rpm;

    public SetFlywheelRPM(double rpm) {
        this.rpm = rpm;
    }

    @Override
    public void initialize() {
        RobotMap.leftFlywheel.set(ControlMode.Velocity, rpm);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {}

    public static boolean isAtTargetRPM() {
        return Math.abs(rpm - RobotMap.shooter.getShooterRPM()) <= 100;
    }
}
