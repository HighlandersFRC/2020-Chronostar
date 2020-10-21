// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

import frc.robot.RobotMap;

public class SetMags extends ParallelCommandGroup {
    public SetMags(double lowPower, double highPower) {
        RobotMap.lowMag.set(ControlMode.PercentOutput, lowPower);
        RobotMap.highMag.set(highPower);
    }
}
