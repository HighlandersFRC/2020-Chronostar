// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.robot.*;

public class SetFlywheelRPM extends CommandBase {

    private double velocity;
    private double position;
    private WaitCommand waitCommand;
    private boolean isAutonomous;
    private static final int TARGET_RPM_THRESHOLD = 100;

    public SetFlywheelRPM(double rpm, double hoodPos, boolean isAuto) {
        velocity = rpm;
        position = hoodPos;
        isAutonomous = isAuto;
    }

    public SetFlywheelRPM(double rpm, double hoodPos, double seconds, boolean isAuto) {
        velocity = rpm;
        position = hoodPos;
        waitCommand = new WaitCommand(seconds);
        isAutonomous = isAuto;
    }

    @Override
    public void initialize() {
        RobotMap.hood.setHoodTarget(position);
        RobotMap.leftFlywheel.set(ControlMode.Velocity, Constants.rpmToUnitsPer100Ms(velocity));
        if (isAutonomous) {
            waitCommand.schedule();
        }
    }

    @Override
    public void execute() {
        RobotMap.drive.trackVisionTape();
        if (isAtTargetRPM()) {
            new SetMags(0.5, 1).schedule();
            RobotMap.intake2Motor.set(ControlMode.PercentOutput, 0.8);
        } else {
            new SetMags(0, 0).schedule();
            RobotMap.intake2Motor.set(ControlMode.PercentOutput, 0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        RobotMap.leftFlywheel.set(ControlMode.PercentOutput, 0);
        new SetMags(0, 0).schedule();
        RobotMap.intake2Motor.set(ControlMode.PercentOutput, 0);
        RobotMap.hood.setHoodTarget(0);
    }

    public boolean isFinished() {
        if (!isAutonomous) {
            return !ButtonMap.shoot();
        } else {
            return waitCommand.isFinished();
        }
    }

    public boolean isAtTargetRPM() {
        return velocity - RobotMap.shooter.getShooterRPM() <= TARGET_RPM_THRESHOLD;
    }
}
