// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.OI;
import frc.robot.RobotMap;

public class Climber extends SubsystemBase {
    /** Creates a new Climber. */
    public Climber() {}

    public void init() {
        RobotMap.ratchetPiston.set(Value.kReverse);
    }

    public void periodic() {}

    public void teleopPeriodic() {
        if (OI.driverController.getTriggerAxis(Hand.kRight) >= 0.5) {
            RobotMap.ratchetPiston.set(Value.kReverse);
            RobotMap.winch.set(ControlMode.PercentOutput, 0.1);
        } else if (OI.driverController.getTriggerAxis(Hand.kLeft) >= 0.5) {
            RobotMap.ratchetPiston.set(Value.kForward);
            RobotMap.winch.set(ControlMode.PercentOutput, -0.1);
        } else {
            RobotMap.ratchetPiston.set(Value.kReverse);
            RobotMap.winch.set(ControlMode.PercentOutput, 0);
        }
    }
}
