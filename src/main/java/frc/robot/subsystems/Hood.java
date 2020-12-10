// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.revrobotics.EncoderType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.RobotMap;
import frc.robot.commands.universalcommands.SetHoodPosition;
import frc.robot.tools.controlloops.PID;

public class Hood extends SubsystemBase {

    private double encValue;
    private double hoodTarget;
    private double kP = 0.15;
    private double kI = 0.00003;
    private double kD = 0;
    private PID hoodPID;

    public Hood() {}

    public void init() {
        RobotMap.hoodMotor.setInverted(true);
        hoodPID = new PID(kP, kI, kD);
        hoodPID.setMaxOutput(0.2);
        hoodPID.setMinOutput(-0.2);
        hoodPID.setSetPoint(0);
        setDefaultCommand(new SetHoodPosition(0));
    }

    public void setHoodPosition(double target) {
        hoodTarget = target;
    }

    @Override
    public void periodic() {
        encValue = RobotMap.hoodMotor.getEncoder(EncoderType.kHallSensor, 42).getPosition();
        if (RobotMap.lowerHoodSwitch.get()) {
            encValue = 0;
        } else if (RobotMap.upperHoodSwitch.get()) {
            encValue = 22;
        }
        hoodPID.updatePID(encValue);
        hoodPID.setSetPoint(hoodTarget);
        RobotMap.hoodMotor.set(hoodPID.getResult());
    }
}
