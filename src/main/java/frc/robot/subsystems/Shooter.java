// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.*;
import frc.robot.commands.universalcommands.SetFlywheelRPM;

public class Shooter extends SubsystemBase {
    public SetFlywheelRPM trenchRPM;
    public SetFlywheelRPM initiationRPM;

    public Shooter() {}

    public void init() {
        RobotMap.rightFlywheel.setNeutralMode(NeutralMode.Coast);
        RobotMap.leftFlywheel.setNeutralMode(NeutralMode.Coast);
        RobotMap.rightFlywheel.set(ControlMode.Follower, Constants.leftFlywheelID);
        RobotMap.leftFlywheel.setInverted(true);
        RobotMap.rightFlywheel.setInverted(InvertType.OpposeMaster);
        RobotMap.leftFlywheel.configClosedLoopPeakOutput(0, Constants.maxPercentage);
        RobotMap.leftFlywheel.configPeakOutputForward(0.7);
        RobotMap.leftFlywheel.configPeakOutputReverse(0);
        RobotMap.leftFlywheel.configVoltageCompSaturation(11.7);
        RobotMap.leftFlywheel.enableVoltageCompensation(true);
        RobotMap.leftFlywheel.setSensorPhase(true);
        RobotMap.leftFlywheel.selectProfileSlot(0, 0);
        RobotMap.leftFlywheel.config_kF(0, 0.125);
        RobotMap.leftFlywheel.config_kP(0, 4.5);
        RobotMap.leftFlywheel.config_kI(0, 0.015);
        RobotMap.leftFlywheel.config_kD(0, 10);
        RobotMap.leftFlywheel.config_IntegralZone(0, Constants.shooterIntegralRange);
        trenchRPM = new SetFlywheelRPM(5000, 7.2);
        initiationRPM = new SetFlywheelRPM(4500, 7.2);
    }

    @Override
    public void periodic() {}

    public void teleopPeriodic() {
        if (ButtonMap.shoot()
                && !initiationRPM.isScheduled()
                && Math.round(RobotMap.lidar.getDistance()) == 10) {
            initiationRPM.schedule();
        } else if (ButtonMap.shoot()) {
            if (Math.abs(Math.round(Robot.visionCam.getDistance() - RobotMap.lidar.getDistance()))
                    <= 2) {
                trenchRPM =
                        new SetFlywheelRPM(
                                5000, Constants.convertLidar(RobotMap.lidar.getDistance()));
            } else {
                trenchRPM =
                        new SetFlywheelRPM(
                                5000, Constants.convertCamera(Robot.visionCam.getDistance()));
            }
            trenchRPM.schedule();
        }
    }

    public double getShooterRPM() {
        return Constants.unitsPer100MsToRpm(RobotMap.leftFlywheel.getSelectedSensorVelocity());
    }
}
