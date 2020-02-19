/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ButtonMap;
import frc.robot.Robot;
import frc.robot.RobotConfig;
import frc.robot.RobotMap;
import frc.robot.sensors.DriveEncoder;
import frc.robot.tools.controlLoops.PID;
import frc.robot.tools.pathTools.Odometry;

public class DriveTrain extends SubsystemBase {

	private double deadZone = 0.01;
	private double turn =0;
	private double throttel = 0;
	private static DriveEncoder leftMainDrive = new DriveEncoder(RobotMap.leftDriveLead,RobotMap.leftDriveLead.getSelectedSensorPosition(0));
	private static DriveEncoder rightMainDrive = new DriveEncoder(RobotMap.rightDriveLead,RobotMap.rightDriveLead.getSelectedSensorPosition(0));
	private double vKF = 0.0455925;
	private double vKP = 0.21;
	private double vKI = 0.000000;
	private double vKD = 0;
	private int profile = 0;
	private PID alignmentPID;
	private double aKP = 0.1;
	private double aKI = 0.0000;
	private double aKD = 0.00;
	private double visionOffset = 0;
	private double visionAcceptablilityZone = 1.5;
	private double visionDeadzone = 0.5;
	private Odometry autoOdometry;
  	public DriveTrain() {

  	}
	public void startAutoOdometry(boolean reversed, double x, double y){
		autoOdometry = new Odometry(reversed, x, y);
		autoOdometry.start();
	};
	public double getDriveTrainX(){
		return autoOdometry.getX();
	}
	public double getDriveTrainY(){
		return autoOdometry.getY();
	}
	public double getDriveTrainHeading(){
		return autoOdometry.gettheta();
	}
	public void setDriveTrainX(double x){
		 autoOdometry.setX(x);
	}
	public void setDriveTrainY(double y){
		 autoOdometry.setY(y);
	}
	public void setDriveTrainHeading(double theta){
		 autoOdometry.setTheta(theta);
	}
	public void setOdometryReversed(boolean reversed){
		 autoOdometry.setReversed(reversed);
	}

	public void initVelocityPIDs(){
		RobotMap.leftDriveLead.selectProfileSlot(profile, 0);
		RobotMap.leftDriveLead.config_kF(profile, vKF, 0);
		RobotMap.leftDriveLead.config_kP(profile, vKP, 0);
		RobotMap.leftDriveLead.config_kI(profile, vKI, 0);
		RobotMap.leftDriveLead.config_kD(profile, vKD, 0);
		RobotMap.rightDriveLead.selectProfileSlot(profile, 0);
		RobotMap.rightDriveLead.config_kF(profile, vKF, 0);
		RobotMap.rightDriveLead.config_kP(profile, vKP, 0);
		RobotMap.rightDriveLead.config_kI(profile, vKI, 0);
		RobotMap.rightDriveLead.config_kD(profile, vKD, 0);
	}
	
	public void initAlignmentPID(){
		alignmentPID = new PID(aKP, aKD, aKI);
		alignmentPID.setSetPoint(0);
		alignmentPID.setMaxOutput(6);
		alignmentPID.setMinInput(-6);
	}
	
	public void arcadeDrive(){
		RobotConfig.setDriveMotorsBrake();
		double leftPower;
		double rightPower;
		double differential;
		if(Math.abs(ButtonMap.getDriveThrottle())>deadZone){
			throttel = Math.tanh(ButtonMap.getDriveThrottle())*(4/Math.PI); 
		}
		else{
			throttel = 0;
		}

		if(Math.abs(ButtonMap.getRotation())>deadZone){
			turn = ButtonMap.getRotation();
		}
		else{
			turn = 0;
		}
		turn = ButtonMap.getRotation();
		differential = turn;
		SmartDashboard.putNumber("differential", differential);
		leftPower = (throttel - (differential));
		rightPower = (throttel + (differential));
	
		if(Math.abs(leftPower)>1) {
			rightPower = Math.abs(rightPower/leftPower)*Math.signum(rightPower);
			leftPower = Math.signum(leftPower);
		}
		else if(Math.abs(rightPower)>1) {
			leftPower = Math.abs(leftPower/rightPower)*Math.signum(leftPower);
			rightPower = Math.signum(rightPower);
		}
		RobotMap.leftDriveLead.set(ControlMode.PercentOutput, leftPower);
		RobotMap.rightDriveLead.set(ControlMode.PercentOutput, rightPower);
	}
	public boolean trackVisionTape(){
		RobotConfig.setDriveMotorsBrake();
		Robot.visionCamera.updateVision();

		if(Timer.getFPGATimestamp()-Robot.visionCamera.lastParseTime>0.25||Math.abs(Robot.visionCamera.getAngle()-visionOffset)<visionDeadzone){
			alignmentPID.updatePID(visionOffset);
			setLeftSpeed(0);
			setRightSpeed(0);
		}
		else{
			alignmentPID.updatePID(Robot.visionCamera.getAngle());
		}
			
		setLeftSpeed(alignmentPID.getResult());
		setRightSpeed(-alignmentPID.getResult());
		if(Math.abs(Robot.visionCamera.getAngle()-visionOffset)<visionAcceptablilityZone){
			return true;
		}
		else{
			return false;
		}
		
		
	}
	public void Stop(){
		RobotMap.leftDriveLead.set(ControlMode.PercentOutput, 0);
		RobotMap.rightDriveLead.set(ControlMode.PercentOutput, 0);

	}
	public void setLeftSpeed(double speed){
		SmartDashboard.putNumber("LeftSpeed", leftMainDrive.getVelocity());
		SmartDashboard.putNumber("RightSpeed", rightMainDrive.getVelocity());
		RobotMap.leftDriveLead.set(ControlMode.Velocity, leftMainDrive.convertftpersToNativeUnitsper100ms(speed));
	}
	public void setRightSpeed(double speed){
		RobotMap.rightDriveLead.set(ControlMode.Velocity, rightMainDrive.convertftpersToNativeUnitsper100ms(speed));

	}
	public void setLeftPercent(double percent){
		RobotMap.leftDriveLead.set(ControlMode.PercentOutput, percent);
	}
	public void setRightPercent(double percent){
		RobotMap.rightDriveLead.set(ControlMode.PercentOutput, percent);
	}
	public void stopDriveTrainMotors(){
		for(TalonFX talon : RobotMap.driveMotorLeads){
			talon.set(ControlMode.PercentOutput, 0);
		}
	}

  @Override
  public void periodic() {

  }
	public void shiftVisionLeft(){
		visionOffset = visionOffset +0.5;
		alignmentPID.setSetPoint(visionOffset);
	}
	public void shiftVisionRight(){
		System.out.println(visionOffset);
		visionOffset = visionOffset-0.5;
	}
  public void teleopPeriodic(){
	if(ButtonMap.startFiringSequence()){
		SmartDashboard.putNumber("offset", visionOffset);
		RobotMap.blinkin.set(0.71);
		RobotMap.visionRelay1.set(Value.kForward);
		trackVisionTape();
		if(ButtonMap.adjustTargetTrackingLeft()){
			shiftVisionLeft();
		}
		else if(ButtonMap.adjustTargetTrackingRight()){
			shiftVisionRight();
		}
	}
	else{
		RobotMap.blinkin.set(0.4);
		RobotMap.visionRelay1.set(Value.kReverse);
		arcadeDrive();
	}
  }
}
