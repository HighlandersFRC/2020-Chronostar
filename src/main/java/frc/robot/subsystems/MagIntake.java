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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Constants;
import frc.robot.commands.defaults.MagIntakeDefault;

public class MagIntake extends SubsystemBaseEnhanced {

    public enum BeamBreakID {
        ONE,
        TWO,
        THREE
    }

    private final DigitalInput beamBreak1 = new DigitalInput(Constants.BEAM_BREAK_1_ID);
    private final DigitalInput beamBreak2 = new DigitalInput(Constants.BEAM_BREAK_3_ID);
    private final DigitalInput beamBreak3 = new DigitalInput(Constants.BEAM_BREAK_2_ID);
    private final VictorSPX lowMag = new VictorSPX(Constants.MAG_BELT_ID);
    private final CANSparkMax highMag =
            new CANSparkMax(Constants.MAG_WHEEL_ID, MotorType.kBrushless);

    private final TalonFX lowIntake = new TalonFX(Constants.LOW_INTAKE_ID);
    private final VictorSPX highIntake = new VictorSPX(Constants.HIGH_INTAKE_ID);
    private final DoubleSolenoid intakePiston = new DoubleSolenoid(0, 1);

    public MagIntake() {}

    @Override
    public void init() {
        lowMag.setNeutralMode(NeutralMode.Brake);
        highMag.setIdleMode(IdleMode.kBrake);
        lowMag.configVoltageCompSaturation(11.7);
        lowMag.enableVoltageCompensation(true);
        highMag.setInverted(true);
        lowIntake.setInverted(true);
        highIntake.setInverted(true);
        highMag.setInverted(true);
        setDefaultCommand(new MagIntakeDefault(this));
    }

    @Override
    public void autoInit() {}

    @Override
    public void teleopInit() {}

    public void setLowMagPercent(double power) {
        lowMag.set(ControlMode.PercentOutput, power);
    }

    public void setHighMagPercent(double power) {
        highMag.set(power);
    }

    public void setMagPercent(double lowPower, double highPower) {
        setLowMagPercent(lowPower);
        setHighMagPercent(highPower);
    }

    public boolean getBeamBreak(BeamBreakID id) {
        switch (id) {
            case ONE:
                return beamBreak1.get();
            case TWO:
                return beamBreak2.get();
            case THREE:
                return beamBreak3.get();
        }
        return false;
    }

    public void setIntakePercent(double lowIntakePercent, double highIntakePercent) {
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
    public void periodic() {
        SmartDashboard.putBoolean("beam break 1", getBeamBreak(BeamBreakID.ONE));
        SmartDashboard.putBoolean("beam break 2", getBeamBreak(BeamBreakID.TWO));
        SmartDashboard.putBoolean("beam break 3", getBeamBreak(BeamBreakID.THREE));
    }
}
