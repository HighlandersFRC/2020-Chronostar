package frc.robot.commands.defaultcommands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Magazine;

public class MagazineDefaultCommand extends CommandBase {

    private final Magazine magazine;

    public MagazineDefaultCommand(Magazine magazine) {
        this.magazine = magazine;
        addRequirements(magazine);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        if (magazine.getBeamBreak1() & magazine.getBeamBreak2() & magazine.getBeamBreak3()) {
            magazine.setMagPercent(0, 0);
        } else if (!magazine.getBeamBreak3()) {
            magazine.setMagPercent(0, 0);
        } else if (!magazine.getBeamBreak1() & magazine.getBeamBreak2()) {
            magazine.setMagPercent(0.5, 0);
        } else if (!magazine.getBeamBreak2() & magazine.getBeamBreak1()) {
            magazine.setMagPercent(-0.3, 0.4);
        } else if (!magazine.getBeamBreak2() & !magazine.getBeamBreak1()) {
            magazine.setMagPercent(0.4, -0.3);
        } else if (!magazine.getBeamBreak3() & !magazine.getBeamBreak2()) {
            magazine.setMagPercent(0.2, 0);
        } else if (!magazine.getBeamBreak2() & magazine.getBeamBreak3()) {
            magazine.setMagPercent(0.3, -0.4);
        } else {
            magazine.setMagPercent(-0.3, 0.2);
        }
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
