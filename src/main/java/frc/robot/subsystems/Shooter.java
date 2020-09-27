// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.ButtonMap;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.commands.universalcommands.FireSequence;

public class Shooter extends SubsystemBase {

    private FireSequence averageFiringSequence;
    private FireSequence closeUpFiringSequence;

    public Shooter() {
        averageFiringSequence = new FireSequence(5000, 0);
    }

    public void init() {
        RobotMap.rightFlywheel.setNeutralMode(NeutralMode.Coast);
        RobotMap.leftFlywheel.setNeutralMode(NeutralMode.Coast);
        RobotMap.rightFlywheel.set(ControlMode.Follower, Constants.leftFlywheelID);
        RobotMap.leftFlywheel.setInverted(true);
        RobotMap.rightFlywheel.setInverted(InvertType.OpposeMaster);
        RobotMap.leftFlywheel.configClosedLoopPeakOutput(0, Constants.maxPercentage);
        RobotMap.leftFlywheel.configPeakOutputForward(0.6);
        RobotMap.leftFlywheel.configPeakOutputReverse(0);
        RobotMap.leftFlywheel.configVoltageCompSaturation(11.7);
        RobotMap.leftFlywheel.enableVoltageCompensation(true);
        RobotMap.leftFlywheel.setSensorPhase(true);
        RobotMap.leftFlywheel.selectProfileSlot(0, 0);
        RobotMap.leftFlywheel.config_kF(0, 0.05);
        RobotMap.leftFlywheel.config_kP(0, 0.45);
        RobotMap.leftFlywheel.config_kI(0, 0.0009);
        RobotMap.leftFlywheel.config_kD(0, 10);
        RobotMap.leftFlywheel.config_IntegralZone(0, Constants.shooterIntegralRange);
    }

    @Override
    public void periodic() {
        if (ButtonMap.shoot() && !averageFiringSequence.isScheduled()) {
            averageFiringSequence.schedule();
        }
        SmartDashboard.putNumber("speed", getShooterRPM());
        SmartDashboard.putBoolean("close", Math.abs(getShooterRPM()) < 100);
    }

    public void setVelocity(double desiredVelocity) {
        if (desiredVelocity > Constants.maxRPM) desiredVelocity = Constants.maxRPM;
        else if (desiredVelocity < 0) desiredVelocity = 0;
        RobotMap.leftFlywheel.set(ControlMode.Velocity, rpmToUnitsPer100Ms(desiredVelocity));
    }

    public void teleopPeriodic() {}

    public static double unitsPer100MsToRpm(double units) {
        return (units * 600) / Constants.ticksPerShooterRotation;
    }

    public double getShooterRPM() {
        return unitsPer100MsToRpm(RobotMap.leftFlywheel.getSelectedSensorVelocity());
    }

    public static int rpmToUnitsPer100Ms(double rpm) {
        return (int) Math.round((rpm / 600) * Constants.ticksPerShooterRotation);
    }
}
