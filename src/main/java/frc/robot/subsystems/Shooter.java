// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class Shooter extends SubsystemBase {

    private final TalonFX leftFlywheel = new TalonFX(Constants.LEFT_FLYWHEEL_ID);
    private final TalonFX rightFlywheel = new TalonFX(Constants.RIGHT_FLYWHEEL_ID);

    public Shooter() {}

    public void init() {
        rightFlywheel.setNeutralMode(NeutralMode.Coast);
        leftFlywheel.setNeutralMode(NeutralMode.Coast);
        rightFlywheel.set(ControlMode.Follower, Constants.LEFT_FLYWHEEL_ID);
        leftFlywheel.setInverted(true);
        rightFlywheel.setInverted(InvertType.OpposeMaster);
        leftFlywheel.configClosedLoopPeakOutput(0, Constants.MAX_SHOOTER_PERCENTAGE);
        leftFlywheel.configPeakOutputForward(0.7);
        leftFlywheel.configPeakOutputReverse(0);
        leftFlywheel.configVoltageCompSaturation(11.7);
        leftFlywheel.enableVoltageCompensation(true);
        leftFlywheel.setSensorPhase(true);
        leftFlywheel.selectProfileSlot(0, 0);
        leftFlywheel.config_kF(0, 0.05);
        leftFlywheel.config_kP(0, 0.45);
        leftFlywheel.config_kI(0, 0.001);
        leftFlywheel.config_kD(0, 10);
        leftFlywheel.config_IntegralZone(0, Constants.SHOOTER_INTEGRAL_RANGE);
    }

    @Override
    public void periodic() {}

    public double getShooterRPM() {
        return Constants.unitsPer100MsToRPM(leftFlywheel.getSelectedSensorVelocity());
    }

    public void setRPM(double rpm) {
        leftFlywheel.set(ControlMode.Velocity, Constants.shooterRPMToUnitsPer100MS(rpm));
    }
}
