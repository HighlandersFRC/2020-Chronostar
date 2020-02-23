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
        return oi.driverController.getTriggerAxis(Hand.kLeft)>=0.3;
    }

    public static boolean trackVisionTarget(){
        return oi.driverController.getBumper(Hand.kRight);
    }
    public static boolean turnOnLightRing(){
        return oi.driverController.getStartButton();
    }
    public static boolean winchUp(){
        return oi.driverController.getYButton();
    }
    public static boolean winchDown(){
        return oi.driverController.getAButton();
    }
    public static boolean adjustTargetTrackingLeft(){
        return oi.driverController.getPOV() == 270;
    }
    public static boolean adjustTargetTrackingRight(){
        return oi.driverController.getPOV() == 90;
    }
    //operator controller
    public static boolean RunIntake(){
        return oi.operatorController.getBumper(Hand.kLeft);
    }
    public static boolean reverseMag(){
        return oi.operatorController.getBButton();
    }
    public static boolean stopReverseMag(){
        return oi.operatorController.getBButtonReleased();
    }
    public static boolean armUp(){
        return oi.operatorController.getTriggerAxis(Hand.kLeft)>0.3;
    }
    public static boolean armDown(){
        return oi.operatorController.getTriggerAxis(Hand.kRight)>0.3;
    }
    public static boolean SafetyButton(){
        return oi.operatorController.getStartButton() && oi.driverController.getStartButton();
    }
    public static boolean moveShooterPowerUp(){
        return oi.operatorController.getPOV() ==0;
    }
    public static boolean moveShooterPowerDown(){
        return oi.operatorController.getPOV() ==180;
    }
    public static boolean moveHoodDown(){
        return oi.operatorController.getPOV() == 270;
    }
    public static boolean moveHoodUP(){
        return oi.operatorController.getPOV() == 90;
    }
    public static boolean startInitiaionLineFiringSequence(){
        return oi.operatorController.getXButtonPressed();
    }
    public static boolean startTrenchRunFiringSequence(){
        return oi.operatorController.getAButtonPressed();
    }
    public static boolean startCloseUpFiringSequence(){
        return oi.operatorController.getYButtonPressed();
    }
    public static boolean runFeedingMechanism(){
        return oi.operatorController.getBumper(Hand.kRight);
    }
    public static boolean stoprunFeedingMechanism(){
        return oi.operatorController.getBumperReleased(Hand.kRight);
    }
}
