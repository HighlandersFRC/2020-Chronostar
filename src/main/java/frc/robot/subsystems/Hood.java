// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.RobotMap;

public class Hood extends SubsystemBase {
    public Hood() {}

    double power = 0.05;

    public void init() {
        RobotMap.hoodMotor.setInverted(true);
    }

    @Override
    public void periodic() {

        if (RobotMap.upperHoodSwitch.get()) {
            power = -0.05;
        } else if (RobotMap.lowerHoodSwitch.get()) {
            power = 0.05;
        }
        RobotMap.hoodMotor.set(power);
    }
}
