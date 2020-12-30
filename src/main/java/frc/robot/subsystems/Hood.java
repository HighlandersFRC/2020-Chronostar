// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANDigitalInput.LimitSwitch;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.EncoderType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import frc.robot.commands.defaults.HoodDefault;
import frc.robot.tools.controlloops.PID;

public class Hood extends SubsystemBase {

    private final CANSparkMax hoodMotor = new CANSparkMax(Constants.HOOD_ID, MotorType.kBrushless);
    private final CANDigitalInput lowerHoodSwitch =
            new CANDigitalInput(hoodMotor, LimitSwitch.kReverse, LimitSwitchPolarity.kNormallyOpen);
    private final CANDigitalInput upperHoodSwitch =
            new CANDigitalInput(hoodMotor, LimitSwitch.kForward, LimitSwitchPolarity.kNormallyOpen);

    private double encValue;
    private double hoodTarget = 0;
    private double kP = 0.15;
    private double kI = 0.00003;
    private double kD = 0;
    private PID hoodPID;

    public Hood() {}

    public void init() {
        hoodMotor.setInverted(true);
        hoodPID = new PID(kP, kI, kD);
        hoodPID.setMaxOutput(0.2);
        hoodPID.setMinOutput(-0.2);
        hoodPID.setSetPoint(0);
        setDefaultCommand(new HoodDefault(this));
    }

    public void setHoodTarget(double target) {
        hoodTarget = target;
    }

    @Override
    public void periodic() {
        encValue = hoodMotor.getEncoder(EncoderType.kHallSensor, 42).getPosition();
        if (lowerHoodSwitch.get()) {
            encValue = 0;
        } else if (upperHoodSwitch.get()) {
            encValue = 22;
        }
        hoodPID.updatePID(encValue);
        hoodPID.setSetPoint(hoodTarget);
        hoodMotor.set(hoodPID.getResult());
    }
}
