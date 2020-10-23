// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.revrobotics.EncoderType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.ButtonMap;
import frc.robot.RobotMap;
import frc.robot.tools.controlloops.PID;

public class Hood extends SubsystemBase {

    private double encValue;
    private double kP = 0.15;
    private double kI = 0.000025;
    private double kD = 0;
    private PID hoodPID;

    public Hood() {}

    public void init() {
        RobotMap.hoodMotor.setInverted(true);
        hoodPID = new PID(kP, kI, kD);
        hoodPID.setMaxOutput(0.2);
        hoodPID.setMinOutput(-0.2);
        hoodPID.setSetPoint(0);
    }

    @Override
    public void periodic() {
        if (ButtonMap.getOperatorBButton()) {
            hoodPID.setSetPoint(0);
        }
        if (ButtonMap.getOperatorXButton()) {
            hoodPID.setSetPoint(11);
        }
        if (ButtonMap.getOperatorYButton()) {
            hoodPID.setSetPoint(22);
        }
        encValue = RobotMap.hoodMotor.getEncoder(EncoderType.kHallSensor, 42).getPosition();
        if (RobotMap.lowerHoodSwitch.get()) {
            encValue = 0;
        } else if (RobotMap.upperHoodSwitch.get()) {
            encValue = 22;
        }
        hoodPID.updatePID(encValue);
        RobotMap.hoodMotor.set(hoodPID.getResult());
        SmartDashboard.putNumber("enc value", encValue);
        SmartDashboard.putNumber("hood power", hoodPID.getResult());
    }
}
