// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANDigitalInput.LimitSwitch;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import frc.robot.OI;

public class Hood extends SubsystemBase {

    private final CANSparkMax hoodMotor = new CANSparkMax(Constants.HOOD_ID, MotorType.kBrushless);
    private final CANDigitalInput lowerHoodSwitch =
            new CANDigitalInput(hoodMotor, LimitSwitch.kReverse, LimitSwitchPolarity.kNormallyOpen);
    private final CANDigitalInput upperHoodSwitch =
            new CANDigitalInput(hoodMotor, LimitSwitch.kForward, LimitSwitchPolarity.kNormallyOpen);

    private double kf = .02;
    private double kp = 0.00009;
    private double ki = 0.0027;
    private double kd = 0.08;
    private double hoodTarget = 0.0;
    private float maxpoint = 22;
    private float minpoint = 0;
    private CANPIDController pidController = new CANPIDController(hoodMotor);
    private CANEncoder hoodEncoder;

    public void init() {
        hoodMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        hoodMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
        hoodMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, maxpoint);
        hoodMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, minpoint);
        hoodMotor.enableVoltageCompensation(11.3);
        pidController = hoodMotor.getPIDController();
        hoodEncoder = hoodMotor.getEncoder();
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
    }

    public Hood() {}

    public void setHoodTarget(double target) {
        hoodTarget = target;
        pidController.setReference(hoodTarget, ControlType.kSmartMotion);
    }

    public double getHoodPosition() {
        return hoodEncoder.getPosition();
    }

    @Override
    public void periodic() {
        if (lowerHoodSwitch.get()) {
            hoodEncoder.setPosition(minpoint);
        } else if (upperHoodSwitch.get()) {
            hoodEncoder.setPosition(maxpoint);
        }
        if ((hoodTarget == 0) && (hoodMotor.getEncoder().getPosition() <= 0.5)) {
            hoodMotor.set(-0.1);
        }
        SmartDashboard.putNumber("Current Value", hoodMotor.getEncoder().getPosition());
        SmartDashboard.putNumber("Output Current", hoodMotor.getOutputCurrent());
        SmartDashboard.putNumber("Target Point", hoodTarget);
        SmartDashboard.putBoolean("XReleased", OI.operatorX.get());
        SmartDashboard.putBoolean("TopLimit", upperHoodSwitch.get());
        SmartDashboard.putBoolean("LowerLimit", lowerHoodSwitch.get());
    }
}
