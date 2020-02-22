/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.controls;

import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotMap;

public class InitiationLineFiringSequence extends CommandBase {
  /**
   * Creates a new FiringSequence.
   */
  private MagazineAutomation magazineAutomation;
  private boolean ableToFire;

  public InitiationLineFiringSequence() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    new SequentialCommandGroup(new SetFlyWheelVelocity(4500), new SetHoodPosition(10.5)).schedule();
    ableToFire = false;
    RobotMap.visionRelay1.set(Value.kForward);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(!ableToFire){
      if(RobotMap.drive.trackVisionTape()&&RobotMap.shooter.flyWheelSpeedClose()){
        ableToFire = true;
      }
    }
    else{
      magazineAutomation = new MagazineAutomation(0.8, .55, 1.0, 1.0);
      magazineAutomation.schedule();
    }

    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotMap.visionRelay1.set(Value.kReverse);
    new SequentialCommandGroup(new SetFlyWheelVelocity(0), new SetHoodPosition(0)).schedule();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return ableToFire && magazineAutomation.isFinished();
  }
}
