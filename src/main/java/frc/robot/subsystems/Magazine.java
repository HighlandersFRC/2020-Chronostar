// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.*;
import frc.robot.commands.defaultcommands.MagazineDefaultCommand;

public class Magazine extends SubsystemBase {

    public static DigitalInput beamBreak1 = new DigitalInput(Constants.BEAM_BREAK_1_ID);
    public static DigitalInput beamBreak2 = new DigitalInput(Constants.BEAM_BREAK_3_ID);
    public static DigitalInput beamBreak3 = new DigitalInput(Constants.BEAM_BREAK_2_ID);
    public static VictorSPX lowMag = new VictorSPX(Constants.MAG_BELT_ID);
    public static CANSparkMax highMag =
            new CANSparkMax(Constants.MAG_WHEEL_ID, MotorType.kBrushless);

    public Magazine() {}

    public void init() {
        lowMag.setNeutralMode(NeutralMode.Brake);
        highMag.setIdleMode(IdleMode.kBrake);
        lowMag.configVoltageCompSaturation(11.7);
        lowMag.enableVoltageCompensation(true);
        setDefaultCommand(new MagazineDefaultCommand(RobotMap.magazine));
    }

    public void setLowMagPercent(double power) {
        lowMag.set(ControlMode.PercentOutput, power);
    }

    public void setHighMagPercent(double power) {
        highMag.set(power);
    }

    @Override
    public void periodic() {}

    public void teleopPeriodic() {}
}
