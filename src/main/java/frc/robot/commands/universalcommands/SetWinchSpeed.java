// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.RobotMap;

public class SetWinchSpeed extends CommandBase {
    double power;

    public SetWinchSpeed(double power) {
        this.power = power;
    }

    @Override
    public void initialize() {
        RobotMap.winch.set(ControlMode.PercentOutput, power);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return true;
    }
}
