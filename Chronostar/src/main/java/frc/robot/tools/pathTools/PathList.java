/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.tools.pathTools;

import java.io.File;

import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import java.nio.file.Paths;

public class PathList {
  private static Trajectory traj1;
  public static PathSetup path1;


   //remember that for all paths if the first point is at (0,0,0) for some reason the end y value is revesred in the coordinate plane
  //for example for a path from (x,y,h) to (0,0,0) a path that goes from (0,0,0) to (x,y,h) would look the same but for one you would 
  // be decreasing y units on the coordinate plane, while in the other you would be increasing y units
  public PathList() {
    try{
      traj1 = TrajectoryUtil.fromPathweaverJson(Paths.get("/home/lvuser/deploy/protostar-Test1.wpilib.json"));
      path1 = new PathSetup(traj1, false);
    }
    catch(Exception e){

    }
	}
    
  
  public void resetAllPaths(){
  }
}
 
