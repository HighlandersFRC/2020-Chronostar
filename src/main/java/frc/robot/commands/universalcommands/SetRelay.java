// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class SetRelay extends InstantCommand {

    private Relay relay;
    private Value value;

    public SetRelay(Relay relay, Value value) {
        this.relay = relay;
        this.value = value;
    }

    @Override
    public void initialize() {
        relay.set(value);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {}
}
