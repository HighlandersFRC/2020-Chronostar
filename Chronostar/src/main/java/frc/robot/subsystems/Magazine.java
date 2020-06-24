package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.*;
import frc.robot.commands.teleopcommands.*;
import frc.robot.commands.universalcommands.*;

public class Magazine extends SubsystemBase {

  private boolean position1;
  private boolean position3;
  private MagPulse magPulse = new MagPulse();
  private LowMagPulse lowMagPulse = new LowMagPulse();
  private int catchCounter;
  private int tryCounter;  
  private boolean stuck;

  public Magazine() {
  }

  @Override
  public void periodic() {
    position1 = !RobotMap.beambreak1.get();
    position3 = !RobotMap.beambreak2.get();
    
    if (!ButtonMap.shoot()) {
      if (position1 && position3 && !magPulse.isScheduled()) {
        magPulse = new MagPulse();
        magPulse.schedule();
      }
      if (position3) {
        magPulse.cancel();
        
      }
      if (position1) {
        lowMagPulse.schedule();
      }
    } else if (ButtonMap.shoot()) fire();
    else if (ButtonMap.getOperatorLeftTrigger() >= 0.5) {
      outtake();
    } else stopAll();
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
}
