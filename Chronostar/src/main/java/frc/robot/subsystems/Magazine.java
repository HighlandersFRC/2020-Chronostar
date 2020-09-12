package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.*;
import frc.robot.commands.teleopcommands.*;
import frc.robot.commands.universalcommands.*;

public class Magazine extends SubsystemBase {

    private boolean beambreak1;
    private boolean beambreak2;
    private boolean beambreak3;
    private int catchCounter;
    private int tryCounter;
    public static boolean stuck;
    private Fire fire;

    public Magazine() {
        fire = new Fire(4000);
    }

    @Override
    public void periodic() {
        beambreak1 = !RobotMap.beambreak1.get();
        beambreak2 = !RobotMap.beambreak2.get();
        beambreak3 = !RobotMap.beambreak3.get();

        if (!ButtonMap.shoot() && ButtonMap.getOperatorLeftTrigger() <= 0.5) {
            if (beambreak3) {
                new HighMagBump(0, 0.15).schedule();
            } else if (beambreak2) {
                new HighMagBump(-0.425, 0.15).schedule();
                new LowMagBump(0.4, 0.15).schedule();
            }
            if (beambreak1) {
                if (catchCounter <= 50) {
                    new LowMagBump(0.4, 0.15).schedule();
                } else {
                    if (tryCounter <= 25) {
                        stuck = true;
                        tryCounter++;
                    } else {
                        new LowMagBump(-0.4, 0.15).schedule();
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

    public void fire() {
        if (!fire.isScheduled() && !fire.isFinished()) fire.schedule();
    }

    public void outtake() {
        new Outtake().schedule();
    }

    public void teleopPeriodic() {
        if (ButtonMap.shoot()) {
            fire();
        } else if (ButtonMap.getOperatorLeftTrigger() >= 0.5) {
            outtake();
        }
    }
}
