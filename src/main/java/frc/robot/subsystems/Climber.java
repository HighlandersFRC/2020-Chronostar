// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import frc.robot.commands.defaults.ClimberDefault;

public class Climber extends SubsystemBase {

    /*
     * TODO Adi, give this one the works, same as I've done with all the others.
     * TODO You're the one who knows it the best.
     */

    public final DoubleSolenoid ratchetPiston = new DoubleSolenoid(2, 3);
    // private final DoubleSolenoid rachetPiston = new DoubleSolenoid(2, 3);
    private final DoubleSolenoid climberRelease = new DoubleSolenoid(4, 5);
    private final CANSparkMax climberMotor =
            new CANSparkMax(Constants.CLIMBER_MOTOR_ID, MotorType.kBrushless);

    public Climber() {}

    public void init() {
        ClimberDefault();
    }

    @Override
    public void periodic() {}
}
