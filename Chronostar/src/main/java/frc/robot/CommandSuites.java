/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.autos.CenterHighGoalAuto;

/**
 * Add your docs here.
 */
public class CommandSuites {
    private CenterHighGoalAuto centerBasicHighGoalAuto;
    private boolean center = true;
    
    public CommandSuites(){
    }
    public void startAutoCommands(){
        if(center){
            RobotMap.drive.startAutoOdometry(false, 10, -8 );
            centerBasicHighGoalAuto = new CenterHighGoalAuto();
            centerBasicHighGoalAuto.schedule();
        }


    }
    public void endAutoCommands(){

    }
    public void startTeleopCommands(){
    }
    public void endTeleopCommands(){

    }
}
