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
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class Climber extends SubsystemBase {

    private final TalonFX winch = new TalonFX(Constants.WINCH_ID);
    private final DoubleSolenoid ratchetPiston = new DoubleSolenoid(2, 3);
    private final DoubleSolenoid climberRelease = new DoubleSolenoid(4, 5);
    private final CANSparkMax armMotor =
            new CANSparkMax(Constants.CLIMBER_MOTOR_ID, MotorType.kBrushless);
    private final CANPIDController pidController = new CANPIDController(armMotor);
    private final CANEncoder armEncoder = armMotor.getEncoder();
    private final float minValue = 0;
    private final float maxValue = 44;
    private final double kf = 0;
    private final double kp = 1;
    private final double ki = 0;
    private final double kd = 0;

    public Climber() {}

    public void init() {
        armEncoder.setPosition(0);
        armMotor.setInverted(false);
        armMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, maxValue);
        armMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, minValue);
        pidController.setFF(kf);
        pidController.setP(kp);
        pidController.setI(ki);
        pidController.setD(kd);
        pidController.setOutputRange(-0.1, 0.1);
        pidController.setSmartMotionMaxVelocity(5, 0);
        pidController.setSmartMotionMinOutputVelocity(-5, 0);
        pidController.setSmartMotionMaxAccel(.1, 0);
        pidController.setSmartMotionAllowedClosedLoopError(.1, 0);
        armMotor.setSmartCurrentLimit(1);
        armMotor.setIdleMode(IdleMode.kBrake);
    }

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
    }
}
