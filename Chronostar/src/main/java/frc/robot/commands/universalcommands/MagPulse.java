package frc.robot.commands.universalcommands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
public class MagPulse extends SequentialCommandGroup {
  public MagPulse() {
    super(new WaitCommand(0.05), new ParallelCommandGroup(new LowMagBump(0.6, 0.2), new HighMagBump(-0.68, 0.2)));
  }
}
