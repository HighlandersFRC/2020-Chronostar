/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.controls.AutoFiringSequence;
import frc.robot.commands.controls.FireSequence;
import frc.robot.commands.controls.SetFlyWheelVelocity;
import frc.robot.commands.controls.SetHoodPosition;
import frc.robot.commands.controls.SetIntakeSpeed;
import frc.robot.commands.controls.TrackVisionTarget;
import frc.robot.tools.controlLoops.PurePursuitController;
import frc.robot.tools.pathTools.PathList;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class RightAdvancedAuto extends SequentialCommandGroup {
  /**
   * Creates a new RightAdvancedAuto.
   */
  public RightAdvancedAuto() {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new TrackVisionTarget(), new FireSequence(4500, 10, 1.0), new PurePursuitController(PathList.rightAutoPath1, 2.5, 4.0,true, true ),new ParallelCommandGroup( new SetFlyWheelVelocity(5500), new SetHoodPosition(13.5),new PurePursuitController(PathList.rightAutoPath2, 2.5, 4.0,true, true )), new TrackVisionTarget(), new FireSequence(5500, 13.5, 1.8));
  }
}
