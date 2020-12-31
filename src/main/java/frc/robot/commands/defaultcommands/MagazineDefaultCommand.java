package frc.robot.commands.defaultcommands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Magazine;

public class MagazineDefaultCommand extends CommandBase {

    public MagazineDefaultCommand(Magazine m_magazine) {
        addRequirements(m_magazine);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        if (Magazine.beamBreak1.get() == true
                & Magazine.beamBreak2.get() == true
                & Magazine.beamBreak3.get() == true) {
            Magazine.highMag.set(0);
            Magazine.lowMag.set(ControlMode.PercentOutput, 0);
        } else if (Magazine.beamBreak3.get() == false) {
            Magazine.highMag.set(0);
            Magazine.lowMag.set(ControlMode.PercentOutput, 0);
        } else if (Magazine.beamBreak1.get() == false & Magazine.beamBreak2.get() == true) {
            Magazine.lowMag.set(ControlMode.PercentOutput, 0.5);
        } else if (Magazine.beamBreak2.get() == false & Magazine.beamBreak1.get() == true) {
            Magazine.lowMag.set(ControlMode.PercentOutput, 0.3);
            Magazine.highMag.set(0);
        } else if (Magazine.beamBreak2.get() == false & Magazine.beamBreak1.get() == false) {
            Magazine.lowMag.set(ControlMode.PercentOutput, 0.3);
            Magazine.highMag.set(-0.3);
        } else if (Magazine.beamBreak3.get() == false & Magazine.beamBreak2.get() == false) {
            Magazine.highMag.set(0);
            Magazine.lowMag.set(ControlMode.PercentOutput, 0.2);
        } else if (Magazine.beamBreak2.get() == false & Magazine.beamBreak3.get() == true) {
            Magazine.highMag.set(-0.3);
            Magazine.lowMag.set(ControlMode.PercentOutput, 0.3);
        } else {
            Magazine.lowMag.set(ControlMode.PercentOutput, -0.3);
            Magazine.highMag.set(0.2);
        }
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
