// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import frc.robot.Constants;

public class Climber extends SubsystemBaseEnhanced {

    private final DoubleSolenoid climberPiston = new DoubleSolenoid(4, 5);
    // private final DoubleSolenoid winchRelease = new DoubleSolenoid(4, 5);
    private final CANSparkMax climberMotor =
            new CANSparkMax(Constants.CLIMBER_ID, MotorType.kBrushless);

    public Climber() {}

    public void teleopPeriodic() {}

    public void climberPistonUp() {
        climberPiston.set(Value.kForward);
    }

    public void climberPistonDown() {
        climberPiston.set(Value.kReverse);
    }

    public void climberMotorUp() {
        climberMotor.set(0.25);
    }

    public void climberMotorDown() {
        climberMotor.set(-0.25);
    }

    public void climberMotorStop() {
        climberMotor.set(0.0);
    }

    @Override
    public void init() {}

    @Override
    public void autoInit() {}

    @Override
    public void teleopInit() {}

    @Override
    public void periodic() {}
}
