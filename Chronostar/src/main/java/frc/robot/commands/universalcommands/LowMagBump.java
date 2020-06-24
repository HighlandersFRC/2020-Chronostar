package frc.robot.commands.universalcommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class LowMagBump extends SequentialCommandGroup {
  public LowMagBump(double power, double duration) {
    super(new SetLowMag(power), new WaitCommand(duration), new SetLowMag(0));
  }
}
