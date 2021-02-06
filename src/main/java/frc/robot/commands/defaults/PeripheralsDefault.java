// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.defaults;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Peripherals;

public class PeripheralsDefault extends CommandBase {
    private Peripherals peripherals;

    public PeripheralsDefault(Peripherals peripherals) {
        this.peripherals = peripherals;
        addRequirements(this.peripherals);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        SmartDashboard.putNumber("Ball Angle", peripherals.getBallAngle());
        SmartDashboard.putNumber("Ball distance", peripherals.getBallDistance());
    }

    @Override
    public void end(final boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
