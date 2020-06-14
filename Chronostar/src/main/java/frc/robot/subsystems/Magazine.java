package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.*;
import frc.robot.commands.teleopcommands.*;
import frc.robot.commands.universalcommands.*;

public class Magazine extends SubsystemBase {

  public boolean position1;
  public boolean position2;
  public boolean position3;
  public boolean position4;
  public boolean position5;

  public Magazine() {
  }

  @Override
  public void periodic() {
    position5 = !RobotMap.beambreak3.get();
    position3 = !RobotMap.beambreak2.get();
    position1 = !RobotMap.beambreak1.get();
    if (RobotMap.intake.isOuttaking) {
      outtake();
    } else {
      if (ButtonMap.shoot()) {
        fire();
      } else {
        if (position5) {
          stopAll();
          return;
        } else {
          if (RobotMap.intake.isIntaking) {
            if (position1) {
              position2 = true;
              new LowMagStep(0.2).schedule();
            }
            if (position2 && position1) {
              new LowMagStep(0.2).schedule();
            }
            if (position3 && position2 && position1) {
              new HighMagStep(0.2).schedule();
              new LowMagStep(0.2).schedule();
              position4 = true;
            }
            if (position4 && position3 && position2 && position1) {
              new HighMagStep(0.2).schedule();
              new LowMagStep(0.2).schedule();
            }
          } else if (RobotMap.intake.isOuttaking) {
            outtake();
          }
        }
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
}
