package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.*;
import frc.robot.commands.teleopcommands.*;
import frc.robot.commands.universalcommands.*;

public class Magazine extends SubsystemBase {

  private boolean beambreak1;
  private boolean beambreak2;
  private boolean beambreak3;
  private LowMagBump lowMagBump = new LowMagBump(0.75, 0.2); 
  private int catchCounter;
  private int tryCounter;  
  public static boolean stuck;

  public Magazine() {
  }

  @Override
  public void periodic() {
    beambreak1 = !RobotMap.beambreak1.get();
    beambreak2 = !RobotMap.beambreak2.get();
    beambreak3 = !RobotMap.beambreak3.get();
    
    if (!ButtonMap.shoot() && ButtonMap.getOperatorLeftTrigger() <= 0.5) {
      if (beambreak3 && !beambreak2 && !lowMagBump.isScheduled()) {
        lowMagBump = new LowMagBump(0.75, 0.2);
        lowMagBump.schedule();
      }
      if (beambreak2) {
        lowMagBump.cancel();
        new HighMagBump(-1, 0.2).schedule();
        new LowMagBump(0.75, 0.2).schedule();
      }
      if (beambreak3 && beambreak2) {
        new HighMagBump(0, 0.2).schedule();
      }
      if (beambreak1) {
        if (catchCounter <= 50) {
          new LowMagBump(0.75, 0.2).schedule();
        } else {
          if (tryCounter <= 25) {
            stuck = true;
            tryCounter++;
          } else {
            new LowMagBump(-0.8, 0.2).schedule();
            catchCounter = 0;
            tryCounter = 0;
            stuck = false;
          }
        }
        catchCounter++;
      } else {
        catchCounter = 0;
        stuck = false;
      }
    }
  }

  public void stopAll() {
    RobotMap.highMag.set(0);
    RobotMap.lowMag.set(ControlMode.PercentOutput, 0);
  }

  public void fire() {
    new Fire().schedule();
  }

  public void outtake() {
    new Outtake().schedule();
  }

  public void teleopPeriodic() {
    if (ButtonMap.shoot()) {
      fire();
    } else if (ButtonMap.getOperatorLeftTrigger() >= 0.5) {
      outtake();
    }
  }
}
