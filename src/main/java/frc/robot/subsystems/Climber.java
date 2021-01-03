// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class Climber extends SubsystemBase {

    /*
     * TODO Adi, give this one the works, same as I've done with all the others.
     * TODO You're the one who knows it the best.
     */

    private final TalonFX winch = new TalonFX(Constants.WINCH_ID);
    private final DoubleSolenoid ratchetPiston = new DoubleSolenoid(2, 3);
    private final DoubleSolenoid climberRelease = new DoubleSolenoid(4, 5);
    private final CANSparkMax armMotor =
            new CANSparkMax(Constants.CLIMBER_MOTOR_ID, MotorType.kBrushless);

    public Climber() {}

    public void init() {}

    public void ratchetPistonForward() {
        ratchetPiston.set(Value.kForward);
    }

    public void ratchetPistonReverse() {
        ratchetPiston.set(Value.kReverse);
    }

    public void climberReleaseForward() {
        climberRelease.set(Value.kForward);
    }

    public void climberReleaseReverse() {
        climberRelease.set(Value.kReverse);
    }

    public void setArmMotor(double val) {
        armMotor.set(val);
    }

    public void setWinchMotor(double val) {
        winch.set(ControlMode.PercentOutput, val);
    }

    @Override
    public void periodic() {}
}
