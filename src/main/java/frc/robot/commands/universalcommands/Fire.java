// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;


import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.robot.*;

public class Fire extends CommandBase {

    private double rpm;
    private double position;
    private WaitCommand waitCommand;
    private boolean isAutonomous;
    private static final int TARGET_RPM_THRESHOLD = 100;
    private static final int INIT_RPM = 4500;
    private static final int TRENCH_RPM = 5000;
    private static final double TRENCH_HOOD_POS = 7.5;
    private static final double INIT_HOOD_POS = 9.5;

    public Fire(boolean isAuto) {
        isAutonomous = isAuto;
    }

    public Fire(double seconds, boolean isAuto) {
        waitCommand = new WaitCommand(seconds);
        isAutonomous = isAuto;
    }

    @Override
    public void initialize() {
        new TrackVisionTape(false).schedule();
        if ((RobotMap.visionCam.getDistance() > 12.0 || RobotMap.lidar.getDistance() > 12.0)) {
            // TODO get the hood regression written up
            rpm = TRENCH_RPM;
            position = TRENCH_HOOD_POS;
        } else {
            rpm = INIT_RPM;
            position = INIT_HOOD_POS;
        }
        new SetHoodPosition(position).schedule();
        new SetFlywheelRPM(rpm).schedule();
        if (isAutonomous) {
            waitCommand.schedule();
        }
    }

    @Override
    public void execute() {
        RobotMap.drive.orientToTarget();
        if (isAtTargetRPM()) {
            new SetMags(0.5, 1).schedule();
            new SetHighIntakePercent(0.8).schedule();
        } else {
            new SetMags(0, 0).schedule();
            new SetHighIntakePercent(0).schedule();
        }
    }

    @Override
    public void end(boolean interrupted) {
        new SetRelay(RobotMap.visionRelay, Value.kReverse).schedule();
        new SetFlywheelRPM(0).schedule();
        new SetMags(0, 0).schedule();
        new SetHighIntakePercent(0).schedule();
        new SetHoodPosition(0).schedule();
    }

    public boolean isFinished() {
        if (waitCommand != null) {
            return waitCommand.isFinished();
        }
        return false;
    }

    public boolean isAtTargetRPM() {
        return rpm - RobotMap.shooter.getShooterRPM() <= TARGET_RPM_THRESHOLD;
    }
}
