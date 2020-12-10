// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.teleopcommands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.*;

public class Outtake extends CommandBase {
    public Outtake() {}

    @Override
    public void initialize() {
        RobotMap.lowMag.set(ControlMode.PercentOutput, -1);
        RobotMap.highMag.set(0.75);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {
        RobotMap.lowMag.set(ControlMode.PercentOutput, 0);
        RobotMap.highMag.set(0);
    }

    @Override
    public boolean isFinished() {
        return OI.getOperatorLeftTrigger() <= 0.5;
    }
}
