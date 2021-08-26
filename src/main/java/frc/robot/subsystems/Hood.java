// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;

import frc.robot.Constants;
import frc.robot.commands.defaults.HoodDefault;

public class Hood extends SubsystemBaseEnhanced {

    private double hoodTarget = 0.0;
    private float maxpoint = 3;
    private float minpoint = 0;
    private double kF = 0.0;
    private double kP = 0.0;
    private double kI = 0.0;
    private double kD = 0.0;
    // private DutyCycleEncoder encoder = new DutyCycleEncoder(4);

    private final CANSparkMax hoodMotor = new CANSparkMax(Constants.HOOD_ID, MotorType.kBrushless);
    private final CANDigitalInput lowerSwitch =
            hoodMotor.getForwardLimitSwitch(CANDigitalInput.LimitSwitchPolarity.kNormallyOpen);
    private final CANDigitalInput upperSwitch =
            hoodMotor.getReverseLimitSwitch(CANDigitalInput.LimitSwitchPolarity.kNormallyOpen);
    private final CANPIDController pidController;
    private final CANEncoder hoodEncoder;

    public Hood() {
        pidController = hoodMotor.getPIDController();
        hoodEncoder = hoodMotor.getEncoder();
    }

    @Override
    public void init() {
        hoodMotor.getEncoder().setPosition(0);
        hoodMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        hoodMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
        hoodMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, maxpoint);
        hoodMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, minpoint);
        hoodMotor.enableVoltageCompensation(11.3);
        pidController.setFF(kF);
        pidController.setP(kP);
        pidController.setI(kI);
        pidController.setD(kD);
        pidController.setIZone(.2);
        pidController.setOutputRange(-0.3, 0.3);
        pidController.setSmartMotionMaxVelocity(200, 0);
        pidController.setSmartMotionMinOutputVelocity(-200, 0);
        pidController.setSmartMotionMaxAccel(200, 0);
        pidController.setSmartMotionAllowedClosedLoopError(.1, 0);
        hoodMotor.setInverted(true);
        hoodMotor.enableVoltageCompensation(11.3);
        hoodMotor.setIdleMode(IdleMode.kCoast);
        setDefaultCommand(new HoodDefault(this));
    }

    @Override
    public void autoInit() {}

    @Override
    public void teleopInit() {
        setDefaultCommand(new HoodDefault(this));
    }

    public void setHoodPercent(double power) {
        hoodMotor.set(power);
    }

    public double getHoodPosition() {
        return hoodEncoder.getPosition();
    }

    public void setHoodTarget(double target) {
        hoodTarget = target;
        pidController.setReference(hoodTarget, ControlType.kPosition);
    }

    @Override
    public void periodic() {
        // Zeroing off the limit switches
        if (lowerSwitch.get()) {
            // hoodEncoder.setPosition(0);

            // System.out.println("Hit bottom limit");
        } else if (upperSwitch.get()) {
            // hoodEncoder.setPosition(22);
        }
        // Ensures that the hood is at lowest position
        if ((hoodTarget == 0) && (hoodMotor.getEncoder().getPosition() <= 0.5)) {
            hoodMotor.set(-0.1);
        }
    }
}
