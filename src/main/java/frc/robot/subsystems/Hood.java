// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANDigitalInput.LimitSwitch;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.ControlType;
import com.revrobotics.EncoderType;

import frc.robot.Constants;
import frc.robot.commands.basic.SetHoodPosition;
import frc.robot.commands.defaults.HoodDefault;

public class Hood extends SubsystemBaseEnhanced {

    private double kf = 0;
    private double kp = 0.5;
    private double ki = 0;
    private double kd = 0;
    private double hoodTarget = 0.0;
    private float maxpoint = 5;
    private float minpoint = 0;

    private final CANSparkMax hoodMotor = new CANSparkMax(Constants.HOOD_ID, MotorType.kBrushless);
    private final CANDigitalInput lowerHoodSwitch = hoodMotor.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyOpen);
    private final CANDigitalInput upperHoodSwitch = hoodMotor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen);
    private final CANPIDController pidController;
    private final CANEncoder hoodEncoder = hoodMotor.getAlternateEncoder(4096);

    public Hood() {
        pidController = hoodMotor.getPIDController();
    }

    @Override
    public void init() {
        hoodMotor.getEncoder().setPosition(0);
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
        pidController.setOutputRange(-0.75, 0.75);
        pidController.setSmartMotionMaxVelocity(160, 0);
        pidController.setSmartMotionMinOutputVelocity(-160, 0);
        pidController.setSmartMotionMaxAccel(100, 0);
        pidController.setSmartMotionAllowedClosedLoopError(.1, 0);
        hoodMotor.setInverted(true);
        pidController.setFeedbackDevice(hoodEncoder);
        setDefaultCommand(new HoodDefault(this));
    }

    @Override
    public void autoInit() {}

    @Override
    public void teleopInit() {
        setDefaultCommand(new HoodDefault(this));
        new SetHoodPosition(this, 0).schedule();
    }

    public void setHoodTarget(double target) {
        hoodTarget = target;
        pidController.setReference(hoodTarget, ControlType.kSmartMotion);
    }

    public void setHoodPercent(double percent) {
        hoodMotor.set(percent);
    }

    public double getHoodPosition() {
        return hoodEncoder.getPosition();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Hood position", hoodEncoder.getPosition());
        // Zeroing off the limit switches
        if (lowerHoodSwitch.get()) {
            hoodEncoder.setPosition(minpoint);
            System.out.println("Hit bottom limit");
        } else if (upperHoodSwitch.get()) {
            System.out.println("Hit top limit switch");
            hoodEncoder.setPosition(maxpoint);
        }
        // Ensures that the hood is at lowest position
        if ((hoodTarget == 0) && (hoodMotor.getEncoder().getPosition() <= 0.5)) {
            hoodMotor.set(-0.05);
        }
    }
}
