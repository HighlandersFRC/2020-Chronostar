// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Relay.Value;
//import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.VisionAlignmentPID;
import frc.robot.sensors.VisionCamera;
import frc.robot.subsystems.Shooter;

public class Robot extends TimedRobot {
    private Command m_autonomousCommand;

    public static SerialPort jevois;
    public static VisionCamera visionCam;

    private RobotConfig config = new RobotConfig();

    @Override
    public void robotInit() {
        config.startBaseConfig();
        try {
            jevois = new SerialPort(115200, Port.kUSB1);
            visionCam = new VisionCamera(jevois);
        } catch (Exception e) {
            System.err.println("vision cam failed to connect");
        }
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        RobotMap.magazine.periodic();
        RobotMap.intake.periodic();
        SmartDashboard.putBoolean("beam break 1", RobotMap.beambreak1.get());
        SmartDashboard.putBoolean("beam break 2", RobotMap.beambreak2.get());
        SmartDashboard.putBoolean("beam break 3", RobotMap.beambreak3.get());
        try {
            visionCam.updateVision();
            SmartDashboard.putNumber("distance", visionCam.getDistance());
        } catch (Exception e) {
        }

        SmartDashboard.putNumber("lidar lite dist", RobotMap.lidarlite.getDistance());
    }

    /** This function is called once each time the robot enters Disabled mode. */
    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    /** This autonomous runs the autonomous command selected by your {@link RobotMap} class. */
    @Override
    public void autonomousInit() {

        // schedule the autonomous command (example)
        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
        }
        config.startAutoConfig();
    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {
        RobotMap.highMag.set(1);
    }

    @Override
    public void teleopInit() {
        if (m_autonomousCommand != null) {
            m_autonomousCommand.cancel();
        }
        config.startTeleopConfig();
    }

    /** This function is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {
        SmartDashboard.putNumber("leftHeat", RobotMap.leftFlywheel.getTemperature());
        SmartDashboard.putNumber("rightHeat", RobotMap.rightFlywheel.getTemperature());
        RobotMap.magazine.teleopPeriodic();
        SmartDashboard.putNumber(
                "rpm",
                Shooter.unitsPer100MsToRpm(RobotMap.leftFlywheel.getSelectedSensorVelocity()));
        RobotMap.shooter.teleopPeriodic();
        RobotMap.drive.teleopPeriodic();
        RobotMap.climber.teleopPeriodic();
        if(OI.operatorController.getYButton()) {
            RobotMap.climberMotor.set(0.1);
        }
        else if(OI.operatorController.getXButton()) {
            RobotMap.climberMotor.set(-0.1);
        }
        else {
            RobotMap.climberMotor.set(0.0);
        }

        try{
            visionCam.updateVision();
            SmartDashboard.putNumber("Vision Angle",visionCam.getAngle());
            SmartDashboard.putBoolean("Has Camera", true);
        }catch(Exception e){
            SmartDashboard.putBoolean("Has Camera", false);
        }


        if(OI.driverController.getXButton()){
           //RobotMap.VisionAlignmentPID.schedule();
            //visionPID.schedule();
            RobotMap.visionRelay.set(Value.kForward);
        }
        else{
            RobotMap.visionRelay.set(Value.kReverse);
        }
    }

    @Override
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }

    /** This function is called periodically during test mode. */
    @Override
    public void testPeriodic() {}
}
