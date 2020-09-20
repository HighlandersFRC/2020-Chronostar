// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.RobotMap;
import frc.robot.RobotStats;

public class Shooter extends SubsystemBase {
    /** Creates a new Shooter. */
    public Shooter() {}

    public void init() {
        RobotMap.rightFlywheel.setNeutralMode(NeutralMode.Coast);
        RobotMap.leftFlywheel.setNeutralMode(NeutralMode.Coast);
        RobotMap.rightFlywheel.set(ControlMode.Follower, RobotStats.leftFlywheelID);
        RobotMap.leftFlywheel.setInverted(true);
        RobotMap.rightFlywheel.setInverted(InvertType.OpposeMaster);
        RobotMap.leftFlywheel.configClosedLoopPeakOutput(0, RobotStats.maxPercentage);
        RobotMap.leftFlywheel.configPeakOutputForward(0.415);
        RobotMap.leftFlywheel.configPeakOutputReverse(0);
        RobotMap.leftFlywheel.configVoltageCompSaturation(11.7);
        RobotMap.leftFlywheel.enableVoltageCompensation(true);
        RobotMap.leftFlywheel.setSensorPhase(true);
        RobotMap.leftFlywheel.selectProfileSlot(0, 0);
        RobotMap.leftFlywheel.config_kF(0, 0.075);
        RobotMap.leftFlywheel.config_kP(0, 3);
        RobotMap.leftFlywheel.config_kI(0, 0.025);
        RobotMap.leftFlywheel.config_kD(0, 0);
        RobotMap.leftFlywheel.config_IntegralZone(0, RobotStats.shooterIntegralRange);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void setVelocity(double desiredVelocity) {
        if (desiredVelocity > RobotStats.maxRPM) desiredVelocity = RobotStats.maxRPM;
        else if (desiredVelocity < 0) desiredVelocity = 0;
        RobotMap.leftFlywheel.set(ControlMode.Velocity, rpmToUnitsPer100Ms(desiredVelocity));
    }

    public void teleopPeriodic() {
        System.out.println(RobotMap.leftFlywheel.getClosedLoopTarget());
        System.out.println(RobotMap.leftFlywheel.getMotorOutputPercent());
    }

    public static double unitsPer100MsToRpm(double units) {
        return (units * 600) / RobotStats.ticksPerShooterRotation;
    }

    public double getShooterRPM() {
        return unitsPer100MsToRpm(RobotMap.leftFlywheel.getSelectedSensorVelocity());
    }

    public static int rpmToUnitsPer100Ms(double rpm) {
        return (int) Math.round((rpm / 600) * RobotStats.ticksPerShooterRotation);
    }
}
