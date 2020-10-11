// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.RobotMap;

public class SetFlywheelPercent extends CommandBase {
    double targetPercent;

    public SetFlywheelPercent(double percent) {
        targetPercent = percent;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        RobotMap.leftFlywheel.set(ControlMode.PercentOutput, targetPercent);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {}

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }
}
