package frc.robot.commands.defaultcommands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.RobotMap;
import frc.robot.commands.universalcommands.*;

public class MagazineDefaultCommand extends CommandBase {

    private int catchCounter;
    private int tryCounter;
    private double PULSE_TIME = 0.15;
    private double LOW_MAG_POWER = 0.4;
    private double HIGH_MAG_POWER = 0.425;

    public MagazineDefaultCommand() {
        addRequirements(RobotMap.magazine);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        if (RobotMap.magazine.getBeamBreak3()) {
            new HighMagBump(0, PULSE_TIME).schedule();
        } else if (RobotMap.magazine.getBeamBreak2()) {
            new HighMagBump(HIGH_MAG_POWER, PULSE_TIME).schedule();
            new LowMagBump(LOW_MAG_POWER, PULSE_TIME).schedule();
        }
        if (RobotMap.magazine.getBeamBreak1()) {
            if (catchCounter <= 50) {
                new LowMagBump(LOW_MAG_POWER, PULSE_TIME).schedule();
            } else {
                if (tryCounter <= 25) {
                    tryCounter++;
                } else {
                    new LowMagBump(-LOW_MAG_POWER, PULSE_TIME).schedule();
                    catchCounter = 0;
                    tryCounter = 0;
                }
            }
            catchCounter++;
        } else {
            catchCounter = 0;
        }
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
