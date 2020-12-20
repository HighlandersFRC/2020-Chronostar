// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Magazine;

public class SetLowMag extends CommandBase {

    private double power;

    public SetLowMag(double power) {
        this.power = power;
    }

    @Override
    public void initialize() {
        Magazine.lowMag.set(ControlMode.PercentOutput, power);
    }

    @Override
    public void execute() {}

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }
}
