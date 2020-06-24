package frc.robot.commands.universalcommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class LowMagPulse extends SequentialCommandGroup {
  public LowMagPulse() {
    super(new WaitCommand(0.05), new LowMagBump(0.85, 0.3));
  }
}
