// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.revrobotics.EncoderType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.RobotMap;
import frc.robot.tools.controlloops.PID;

public class Hood extends SubsystemBase {

    double encValue;
    double kP = 0.15;
    double kI = 0.00015;
    double kD = 0;

    public Hood() {}

    PID hoodPosition;

    public void init() {
        RobotMap.hoodMotor.setInverted(true);
        hoodPosition = new PID(kP, kI, kD);
        hoodPosition.setSetPoint(10);
        hoodPosition.setMaxOutput(0.2);
        hoodPosition.setMinOutput(-0.2);
    }

    @Override
    public void periodic() {
        encValue = RobotMap.hoodMotor.getEncoder(EncoderType.kHallSensor, 42).getPosition();
        if (RobotMap.lowerHoodSwitch.get()) {
            encValue = 0;
        } else if (RobotMap.upperHoodSwitch.get()) {
            encValue = 21;
        }
        hoodPosition.updatePID(encValue);
        RobotMap.hoodMotor.set(hoodPosition.getResult());
        SmartDashboard.putNumber("enc value", encValue);
        SmartDashboard.putNumber("hood power", hoodPosition.getResult());
    }
}
