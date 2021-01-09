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
    private CANPIDController mpidController = new CANPIDController(hoodMotor);
    private CANEncoder hoodEncoder;

    public void init() {
        hoodMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        hoodMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
        hoodMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, maxpoint);
        hoodMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, minpoint);
        hoodMotor.enableVoltageCompensation(11.3);
        mpidController = hoodMotor.getPIDController();
        hoodEncoder = hoodMotor.getEncoder();
        mpidController.setFF(kf);
        mpidController.setP(kp);
        mpidController.setI(ki);
        mpidController.setD(kd);
        mpidController.setIZone(.2);
        mpidController.setOutputRange(-1, 1);
        mpidController.setSmartMotionMaxVelocity(160, 0);
        mpidController.setSmartMotionMinOutputVelocity(-160, 0);
        mpidController.setSmartMotionMaxAccel(100, 0);
        mpidController.setSmartMotionAllowedClosedLoopError(.1, 0);
    }

    public Hood() {}

    public void setHoodTarget(double target) {
        hoodTarget = target;
        mpidController.setReference(hoodTarget, ControlType.kSmartMotion);
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
        SmartDashboard.putNumber("Current Value", hoodMotor.getEncoder().getPosition());
        SmartDashboard.putNumber("Output Current", hoodMotor.getOutputCurrent());
        SmartDashboard.putNumber("Target Point", hoodTarget);
        SmartDashboard.putBoolean("XReleased", OI.operatorX.get());
    }
}
