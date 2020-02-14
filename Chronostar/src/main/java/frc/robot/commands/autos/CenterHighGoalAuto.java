/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotMap;
import frc.robot.commands.controls.setFlyWheelVelocity;
import frc.robot.commands.controls.setHoodPosition;
import frc.robot.commands.controls.timedMagazineRun;
import frc.robot.tools.controlLoops.PurePursuitController;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class CenterHighGoalAuto extends SequentialCommandGroup {
  /**
   * Creates a new CenterHighGoalAuto.
   */
  public CenterHighGoalAuto() {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    //super(new setFlyWheelVelocity(4500), new setHoodPosition(10.5), new WaitCommand(1.5), new timedMagazineRun(1.25), new setFlyWheelVelocity(0), new setHoodPosition(0));
    super( new PurePursuitController(RobotMap.pathList.centerAutoPath1, 10.2, 5.0, true, false));
  }
}
