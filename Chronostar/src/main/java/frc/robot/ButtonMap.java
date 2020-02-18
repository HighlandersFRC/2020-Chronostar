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
    public static boolean switchCamera(){
        return oi.driverController.getTriggerAxis(Hand.kRight)>=0.3;
    }
    public static boolean startFiringSequence(){
        return oi.driverController.getBumper(Hand.kRight);
    }
    public static boolean adjustTargetTrackingLeft(){
        return oi.driverController.getXButton();
    }
    public static boolean adjustTargetTrackingRight(){
        return oi.driverController.getBButton();
    }
    public static boolean RunIntake(){
        return oi.operatorController.getBumper(Hand.kLeft);
    }
    public static boolean runMag(){
        return oi.operatorController.getBumper(Hand.kRight);
    }
    public static boolean runIndexer(){
        return oi.operatorController.getXButton();
    }
    public static boolean reverseMag(){
        return oi.operatorController.getBButton();
    }
    public static boolean stopReverseMag(){
        return oi.operatorController.getBButtonReleased();
    }
        
    public static boolean armUp(){
        return oi.operatorController.getAButton();
    }
    public static boolean armDown(){
        return oi.operatorController.getYButton();
    }
}
