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
  private static Trajectory centerAutoTraj1;
  public static PathSetup centerAutoPath1;
  private static Trajectory rightAutoTraj1;
  public static PathSetup rightAutoPath1;
  private static Trajectory rightAutoTraj2;
  public static PathSetup rightAutoPath2;


   //remember that for all paths if the first point is at (0,0,0) for some reason the end y value is revesred in the coordinate plane
  //for example for a path from (x,y,h) to (0,0,0) a path that goes from (0,0,0) to (x,y,h) would look the same but for one you would 
  // be decreasing y units on the coordinate plane, while in the other you would be increasing y units
  public PathList() {
    try{
      centerAutoTraj1 = TrajectoryUtil.fromPathweaverJson(Paths.get("/home/lvuser/deploy/Center3Highgoal.wpilib.json"));
      centerAutoPath1 = new PathSetup(centerAutoTraj1, true);
      rightAutoTraj1 = TrajectoryUtil.fromPathweaverJson(Paths.get("/home/lvuser/deploy/Right3Highgoal1.wpilib.json"));
      rightAutoPath1 = new PathSetup(rightAutoTraj1, true);
      rightAutoTraj2 = TrajectoryUtil.fromPathweaverJson(Paths.get("/home/lvuser/deploy/Right3HighGoal2.wpilib.json"));
      rightAutoPath2 = new PathSetup(rightAutoTraj2, false);
    }      
    
    catch(Exception e){

    }
	}
    
  
  public void resetAllPaths(){
  }
}
 
