// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.ButtonMap;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.commands.universalcommands.SetFlywheelRPM;

public class Shooter extends SubsystemBase {

    public SetFlywheelRPM trenchRPM;
    public SetFlywheelRPM initiationRPM;

    public Shooter() {}

    public void init() {
        RobotMap.rightFlywheel.setNeutralMode(NeutralMode.Coast);
        RobotMap.leftFlywheel.setNeutralMode(NeutralMode.Coast);
        RobotMap.rightFlywheel.set(ControlMode.Follower, Constants.LEFT_FLYWHEEL_ID);
        RobotMap.leftFlywheel.setInverted(true);
        RobotMap.rightFlywheel.setInverted(InvertType.OpposeMaster);
        RobotMap.leftFlywheel.configClosedLoopPeakOutput(0, Constants.MAX_SHOOTER_PERCENTAGE);
        RobotMap.leftFlywheel.configPeakOutputForward(0.7);
        RobotMap.leftFlywheel.configPeakOutputReverse(0);
        RobotMap.leftFlywheel.configVoltageCompSaturation(11.7);
        RobotMap.leftFlywheel.enableVoltageCompensation(true);
        RobotMap.leftFlywheel.setSensorPhase(true);
        RobotMap.leftFlywheel.selectProfileSlot(0, 0);
        RobotMap.leftFlywheel.config_kF(0, 0.05);
        RobotMap.leftFlywheel.config_kP(0, 0.45);
        RobotMap.leftFlywheel.config_kI(0, 0.0009);
        RobotMap.leftFlywheel.config_kD(0, 10);
        RobotMap.leftFlywheel.config_IntegralZone(0, Constants.SHOOTER_INTEGRAL_RANGE);
        trenchRPM = new SetFlywheelRPM(5000, 7.5, false);
        initiationRPM = new SetFlywheelRPM(4500, 8.2, false);
    }

    @Override
    public void periodic() {}

    public void teleopPeriodic() {
        if (ButtonMap.shoot()) {
            if (!initiationRPM.isScheduled()
                    && Math.round(RobotMap.lidar.getDistance()) >= 8.0
                    && Math.round(RobotMap.lidar.getDistance()) <= 12.0) {
                initiationRPM.schedule();
            }
        } else if (!isShooting()) {
            RobotMap.hood.setHoodTarget(0);
        }
    }

    public double getShooterRPM() {
        return Constants.unitsPer100MsToRpm(RobotMap.leftFlywheel.getSelectedSensorVelocity());
    }

    public boolean isShooting() {
        return RobotMap.leftFlywheel.getClosedLoopTarget() >= 0;
    }
}
