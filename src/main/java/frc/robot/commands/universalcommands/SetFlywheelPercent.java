// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.robot.RobotMap;

public class SetFlywheelPercent extends CommandBase {

    private double percent;

    public SetFlywheelPercent(double percentage) {
        percent = percentage;
    }

    @Override
    public void initialize() {
        RobotMap.leftFlywheel.set(ControlMode.PercentOutput, percent);
        new WaitCommand(1.5).schedule();
    }

    @Override
    public void execute() {
        new RunMags(0.5, -1).schedule();
    }

    @Override
    public void end(boolean interrupted) {
        RobotMap.leftFlywheel.set(ControlMode.PercentOutput, 0);
        new RunMags(0, 0).schedule();
    }

    public boolean isFinished() {
        return true;
    }
}
