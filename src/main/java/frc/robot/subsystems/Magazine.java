package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.*;
import frc.robot.commands.teleopcommands.Outtake;
import frc.robot.commands.universalcommands.*;

public class Magazine extends SubsystemBase {

    private boolean beambreak1;
    private boolean beambreak2;
    private boolean beambreak3;
    private int catchCounter;
    private int tryCounter;
    public static boolean stuck;
    private static double PULSE_TIME = 0.15;
    private static double LOW_MAG_POWER = 0.4;
    private static double HIGH_MAG_POWER = 0.425;

    public Magazine() {}

    public void init() {
        RobotMap.lowMag.setNeutralMode(NeutralMode.Brake);
        RobotMap.highMag.setIdleMode(IdleMode.kBrake);
        RobotMap.lowMag.configVoltageCompSaturation(11.7);
        RobotMap.lowMag.enableVoltageCompensation(true);
        RobotMap.lowMag.setInverted(false);
        RobotMap.highMag.setInverted(true);
    }

    @Override
    public void periodic() {
        beambreak1 = !RobotMap.beambreak1.get();
        beambreak2 = !RobotMap.beambreak2.get();
        beambreak3 = !RobotMap.beambreak3.get();

        if (!RobotMap.shooter.isShooting() && ButtonMap.getOperatorLeftTrigger() <= 0.5) {
            if (beambreak3) {
                new HighMagBump(0, PULSE_TIME).schedule();
            } else if (beambreak2) {
                new HighMagBump(HIGH_MAG_POWER, PULSE_TIME).schedule();
                new LowMagBump(LOW_MAG_POWER, PULSE_TIME).schedule();
            }
            if (beambreak1) {
                if (catchCounter <= 50) {
                    new LowMagBump(LOW_MAG_POWER, PULSE_TIME).schedule();
                } else {
                    if (tryCounter <= 25) {
                        stuck = true;
                        tryCounter++;
                    } else {
                        new LowMagBump(-LOW_MAG_POWER, PULSE_TIME).schedule();
                        catchCounter = 0;
                        tryCounter = 0;
                        stuck = false;
                    }
                }
                catchCounter++;
            } else {
                catchCounter = 0;
                stuck = false;
            }
        }
    }

    public void stopAll() {
        RobotMap.highMag.set(0);
        RobotMap.lowMag.set(ControlMode.PercentOutput, 0);
    }

    public void teleopPeriodic() {
        if (ButtonMap.getOperatorLeftTrigger() >= 0.5) {
            new Outtake().schedule();
        }
    }
}
