/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ButtonMap;
import frc.robot.RobotMap;
import frc.robot.commands.controls.DeployClimber;

public class Climber extends SubsystemBase {
  /**
   * Creates a new Climber.
   */
  private DeployClimber deployClimber;
  private boolean saftey = false;
  public Climber() {
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void initClimber(){
    saftey = false;
    RobotMap.climberReleasePiston.set(RobotMap.constrainArm);
    deployClimber = new DeployClimber();
  }
  public void teleopPeriodic(){
    if(ButtonMap.SafetyButton()) {
      saftey = true;
    }
    if(saftey){
      if(ButtonMap.deployClimber() == true&&!deployClimber.isFinished()){
        deployClimber.schedule();
      }
      if(RobotMap.climberReleasePiston.get() == RobotMap.constrainArm){
        if(ButtonMap.winchDown() == true){
          RobotMap.winchMotor.set(-0.4);
        }
        else{
          RobotMap.winchMotor.set(0);
        }
      }
    }
    SmartDashboard.putBoolean("Climber Safety", saftey);
  }
}
