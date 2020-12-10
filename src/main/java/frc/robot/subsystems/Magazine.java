// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.*;
import frc.robot.commands.defaultcommands.MagazineDefaultCommand;

public class Magazine extends SubsystemBase {

    private boolean beambreak1;
    private boolean beambreak2;
    private boolean beambreak3;
    public static boolean stuck;

    public Magazine() {}

    public void init() {
        RobotMap.lowMag.setNeutralMode(NeutralMode.Brake);
        RobotMap.highMag.setIdleMode(IdleMode.kBrake);
        RobotMap.lowMag.configVoltageCompSaturation(11.7);
        RobotMap.lowMag.enableVoltageCompensation(true);
        RobotMap.intakeMotor.setNeutralMode(NeutralMode.Brake);
        RobotMap.intake2Motor.setNeutralMode(NeutralMode.Brake);
        RobotMap.intakeMotor.setInverted(true);
        RobotMap.intake2Motor.setInverted(true);
        setDefaultCommand(new MagazineDefaultCommand());
    }

    public boolean getBeamBreak3() {
        return beambreak3;
    }

    public boolean getBeamBreak2() {
        return beambreak2;
    }

    public boolean getBeamBreak1() {
        return beambreak1;
    }

    public void setLowMagPercent(double power) {
        RobotMap.lowMag.set(ControlMode.PercentOutput, power);
    }

    public void setHighMagPercent(double power) {
        RobotMap.highMag.set(power);
    }

    @Override
    public void periodic() {
        beambreak1 = !RobotMap.beambreak1.get();
        beambreak2 = !RobotMap.beambreak2.get();
        beambreak3 = !RobotMap.beambreak3.get();
    }

    public void teleopPeriodic() {}
}
