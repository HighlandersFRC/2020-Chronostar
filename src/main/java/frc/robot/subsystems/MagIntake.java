// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class MagIntake extends SubsystemBase {
    public MagIntake() {}

    private final DigitalInput beamBreak1 = new DigitalInput(Constants.BEAM_BREAK_1_ID);
    private final DigitalInput beamBreak2 = new DigitalInput(Constants.BEAM_BREAK_3_ID);
    private final DigitalInput beamBreak3 = new DigitalInput(Constants.BEAM_BREAK_2_ID);
    private final VictorSPX lowMag = new VictorSPX(Constants.MAG_BELT_ID);
    private final CANSparkMax highMag =
            new CANSparkMax(Constants.MAG_WHEEL_ID, MotorType.kBrushless);

    private final TalonFX lowIntake = new TalonFX(Constants.LOW_INTAKE_ID);
    private final VictorSPX highIntake = new VictorSPX(Constants.HIGH_INTAKE_ID);
    private final DoubleSolenoid intakePiston = new DoubleSolenoid(0, 1);

    public void init() {
        lowMag.setNeutralMode(NeutralMode.Brake);
        highMag.setIdleMode(IdleMode.kBrake);
        lowMag.configVoltageCompSaturation(11.7);
        lowMag.enableVoltageCompensation(true);
        highMag.setInverted(true);
        lowIntake.setInverted(true);
        highIntake.setInverted(true);
    }

    public void setLowMagPercent(double power) {
        lowMag.set(ControlMode.PercentOutput, power);
    }

    public void setHighMagPercent(double power) {
        highMag.set(power);
    }

    public void setMagazine(double lowPower, double highPower) {
        setLowMagPercent(lowPower);
        setHighMagPercent(highPower);
    }

    public boolean getBeamBreak1() {
        return !beamBreak1.get();
    }

    public boolean getBeamBreak2() {
        return !beamBreak2.get();
    }

    public boolean getBeamBreak3() {
        return !beamBreak3.get();
    }

    public void setIntake(double lowIntakePercent, double highIntakePercent) {
        lowIntake.set(ControlMode.PercentOutput, lowIntakePercent);
        highIntake.set(ControlMode.PercentOutput, highIntakePercent);
    }

    public void setLowIntakePercent(double power) {
        lowIntake.set(ControlMode.PercentOutput, power);
    }

    public void setHighIntakePercent(double power) {
        highIntake.set(ControlMode.PercentOutput, power);
    }

    public void intakePistonUp() {
        intakePiston.set(Value.kForward);
    }

    public void intakePistonDown() {
        intakePiston.set(Value.kReverse);
    }

    @Override
    public void periodic() {}
}
