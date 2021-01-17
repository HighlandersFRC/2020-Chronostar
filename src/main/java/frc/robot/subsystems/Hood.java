// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;

import frc.robot.Constants;
import frc.robot.commands.defaults.HoodDefault;

public class Hood extends SubsystemBaseEnhanced {

    private double kf = .02;
    private double kp = 0.00009;
    private double ki = 0.0027;
    private double kd = 0.08;
    private double hoodTarget = 0.0;
    private float maxpoint = 22;
    private float minpoint = 0;

    private final CANSparkMax hoodMotor = new CANSparkMax(Constants.HOOD_ID, MotorType.kBrushless);
    private final CANDigitalInput lowerHoodSwitch =
            hoodMotor.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyOpen);
    private final CANDigitalInput upperHoodSwitch =
            hoodMotor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen);
    private final CANPIDController pidController;
    private final CANEncoder hoodEncoder;

    public Hood() {
        pidController = hoodMotor.getPIDController();
        hoodEncoder = hoodMotor.getEncoder();
    }

    @Override
    public void init() {
        hoodMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        hoodMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
        hoodMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, maxpoint);
        hoodMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, minpoint);
        hoodMotor.enableVoltageCompensation(11.3);
        pidController.setFF(kf);
        pidController.setP(kp);
        pidController.setI(ki);
        pidController.setD(kd);
        pidController.setIZone(.2);
        pidController.setOutputRange(-1, 1);
        pidController.setSmartMotionMaxVelocity(160, 0);
        pidController.setSmartMotionMinOutputVelocity(-160, 0);
        pidController.setSmartMotionMaxAccel(100, 0);
        pidController.setSmartMotionAllowedClosedLoopError(.1, 0);
        setDefaultCommand(new HoodDefault(this));
    }

    @Override
    public void autoInit() {}

    @Override
    public void teleopInit() {}

    public void setHoodTarget(double target) {
        hoodTarget = target;
        pidController.setReference(hoodTarget, ControlType.kSmartMotion);
    }

    public double getHoodPosition() {
        return hoodEncoder.getPosition();
    }

    @Override
    public void periodic() {
        // Zeroing off the limit switches
        if (lowerHoodSwitch.get()) {
            hoodMotor.getEncoder().setPosition(minpoint);
        } else if (upperHoodSwitch.get()) {
            hoodMotor.getEncoder().setPosition(maxpoint);
        }
        // Ensures that the hood is at lowest position
        if ((hoodTarget == 0) && (hoodMotor.getEncoder().getPosition() <= 0.5)) {
            hoodMotor.set(-0.1);
        }
    }
}
