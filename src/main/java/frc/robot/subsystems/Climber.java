// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Constants;
import frc.robot.commands.defaults.ClimberDefault;

public class Climber extends SubsystemBaseEnhanced {

    private final TalonFX winch = new TalonFX(Constants.WINCH_ID);
    private final DoubleSolenoid ratchetPiston = new DoubleSolenoid(2, 3);
    private final DoubleSolenoid climberRelease = new DoubleSolenoid(4, 5);
    private final CANSparkMax armMotor =
            new CANSparkMax(Constants.CLIMBER_MOTOR_ID, MotorType.kBrushless);
    private final CANPIDController pidController = new CANPIDController(armMotor);
    private final CANEncoder armEncoder = armMotor.getEncoder();
    private final float minValue = 0;
    private final float maxValue = 44;
    private double kf = 0;
    private double kp = 2;
    private double ki = 0.005;
    private double kd = 20;

    public Climber() {}

    @Override
    public void init() {
        armEncoder.setPosition(0);
        armMotor.setInverted(false);
        armMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, maxValue);
        armMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, minValue);

        pidController.setOutputRange(-0.25, 0.25);
        pidController.setSmartMotionMaxVelocity(120, 0);
        pidController.setSmartMotionMinOutputVelocity(-120, 0);
        pidController.setSmartMotionMaxAccel(10, 0);
        pidController.setSmartMotionAllowedClosedLoopError(10, 0);
        armMotor.setSmartCurrentLimit(15);
        armMotor.setIdleMode(IdleMode.kBrake);
        setDefaultCommand(new ClimberDefault(this));
    }

    @Override
    public void autoInit() {}

    @Override
    public void teleopInit() {}

    public void engageRatchetPiston() {
        ratchetPiston.set(Value.kForward);
    }

    public void disengageRatchetPiston() {
        ratchetPiston.set(Value.kReverse);
    }

    public void engageClimberRelease() {
        climberRelease.set(Value.kForward);
    }

    public void disengageClimberRelease() {
        climberRelease.set(Value.kReverse);
    }

    public void setArmMotor(double percent) {
        pidController.setReference(percent, ControlType.kSmartMotion);
    }

    public void setWinchMotor(double val) {
        winch.set(ControlMode.PercentOutput, val);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("encoder value", armEncoder.getPosition());
        SmartDashboard.putNumber("kF", kf);
        SmartDashboard.putNumber("kP", kp);
        SmartDashboard.putNumber("kI", ki);
        SmartDashboard.putNumber("kD", kd);
        /*kf = SmartDashboard.getNumber("kF", kf);
            kp = SmartDashboard.getNumber("kP", kp);
            ki = SmartDashboard.getNumber("kI", ki);
            kd = SmartDashboard.getNumber("kD", kd);

            pidController.setFF(kf);
            pidController.setP(kp);
            pidController.setI(ki);
            pidController.setD(kd);
        */ }
}
