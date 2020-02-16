/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * Add your docs here.
 */
public class ButtonMap {
    public static OI oi = new OI();
    //driver controller buttons
    public static double getDriveThrottle(){
        return -oi.driverController.getRawAxis(1);
    } 
    public static double getRotation(){
        return -oi.driverController.getRawAxis(4);
    }
    public static boolean testButton(){
        return oi.driverController.getBumper(Hand.kRight);
    }
    public static boolean switchCamera(){
        return oi.driverController.getTriggerAxis(Hand.kRight)>=0.3;
    }
    //slightly less important person buttons
    public static boolean shooterUp(){
        return oi.operatorController.getYButton();
    }
    public static boolean shooterDown(){
        return oi.operatorController.getAButton();
    }
    public static boolean runMagBelt(){
        return oi.operatorController.getBumper(Hand.kLeft);
    }
    public static boolean runMagWheel(){
        return oi.operatorController.getBumper(Hand.kRight);
    }
    public static boolean runIndexer(){
        return oi.operatorController.getXButton();
    }
    public static boolean reverseMag(){
        return oi.operatorController.getBButton();
    }
}
