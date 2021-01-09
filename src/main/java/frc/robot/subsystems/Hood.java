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

    /*private double encValue;
        private double hoodTarget = 0;
        private double kP = 0.00009;
        private double kI = 0.0027;
        private double kD = 0.08;
        private double kF = 0.08;
        private final float maxPoint = 22;
        private final float minPoint = 0;
        private CANPIDController hoodPIDController = new CANPIDController(hoodMotor);
    */
    private double kf = .02;
    private double kp = 0.00009;
    private double ki = 0.0027;
    private double kd = 0.08;
    private double hoodTarget = 0.0;
    private float maxpoint = 22;
    private float minpoint = 0;
    private CANPIDController mpidController = new CANPIDController(hoodMotor);
    private CANDigitalInput m_forwardLimit;
    private CANDigitalInput m_reverseLimit;
    private double dPosition;
    private CANEncoder hoodEncoder;
    private double encValue = 0;

    public void init() {
        m_forwardLimit = hoodMotor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen);
        m_reverseLimit = hoodMotor.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyOpen);
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

    /*public void init() {
            hoodMotor.setInverted(true);
            // hoodPIDController = hoodMotor.getPIDController();
            hoodPIDController.setP(kP);
            hoodPIDController.setI(kI);
            hoodPIDController.setD(kD);
            hoodPIDController.setFF(kF);
            hoodPIDController.setOutputRange(-1, 1);
            hoodMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
            hoodMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
            hoodMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, maxPoint);
            hoodMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, maxPoint);
            hoodMotor.enableVoltageCompensation(11.3);
            hoodPIDController.setSmartMotionMaxVelocity(160, 0);
            hoodPIDController.setSmartMotionMinOutputVelocity(-160, 0);
            hoodPIDController.setSmartMotionMaxAccel(100, 0);
            hoodPIDController.setSmartMotionAllowedClosedLoopError(0.1, 0);
            hoodPIDController.setIZone(0.2);
            // setDefaultCommand(new HoodDefault(this));
        }
    */
    public void setHoodTarget(double target) {
        hoodTarget = target;
        mpidController.setReference(hoodTarget, ControlType.kSmartMotion);
    }

    public double getHoodPosition() {
        return encValue;
    }

    @Override
    public void periodic() {
        if (lowerHoodSwitch.get()) {
            hoodMotor.getEncoder().setPosition(minpoint);
        } else if (upperHoodSwitch.get()) {
            hoodMotor.getEncoder().setPosition(maxpoint);
        } else {
            encValue = hoodMotor.getEncoder().getPosition();
        }
        /*hoodPID.updatePID(encValue);
        hoodPID.setSetPoint(hoodTarget);
        hoodMotor.set(hoodPID.getResult());
        */
        SmartDashboard.putNumber("Current Value", hoodMotor.getEncoder().getPosition());
        SmartDashboard.putNumber("Output Current", hoodMotor.getOutputCurrent());
        SmartDashboard.putNumber("Target Point", hoodTarget);
        SmartDashboard.putBoolean("XReleased", OI.operatorX.get());
    }
}
