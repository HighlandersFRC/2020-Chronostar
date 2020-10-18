// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import frc.robot.RobotMap;

public class SetFlywheelPercent extends InstantCommand {

    private double percent;

    public SetFlywheelPercent(double percentage) {
        percent = percentage;
    }

    @Override
    public void initialize() {
        RobotMap.leftFlywheel.set(ControlMode.PercentOutput, percent);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {}
}