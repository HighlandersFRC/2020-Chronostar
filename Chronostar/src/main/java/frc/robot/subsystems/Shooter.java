package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {

  public boolean isFiring = false;

  public Shooter() {

  }

  @Override
  public void periodic() {
  }

  public void fire() {
    isFiring = true;
  }
}
