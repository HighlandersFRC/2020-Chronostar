// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.robot.ButtonMap;
import frc.robot.RobotMap;

public class DumbFireSequence extends SequentialCommandGroup {
    public DumbFireSequence() {
        super(new WaitCommand(1.5), new RunMags(0.5, -1));
    }

    public void initialize() {
        RobotMap.leftFlywheel.set(ControlMode.PercentOutput, 0.5);
    }

    public boolean isFinished() {
        return !ButtonMap.shoot();
    }

    public void end() {
        RobotMap.leftFlywheel.set(ControlMode.PercentOutput, 0);
        new RunMags(0, 0).schedule();
    }
}
