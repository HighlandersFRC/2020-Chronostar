// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class SetPiston extends InstantCommand {

    private DoubleSolenoid piston;
    private Value value;

    public SetPiston(DoubleSolenoid piston, Value value) {
        this.piston = piston;
        this.value = value;
    }

    @Override
    public void initialize() {
        piston.set(value);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {}
}
