// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;

import frc.robot.Constants;
import frc.robot.commands.defaults.DriveDefault;
import frc.robot.tools.controlloops.PID;

public class Drive extends SubsystemBaseEnhanced {

    private final WPI_TalonFX leftDriveLead = new WPI_TalonFX(Constants.LEFT_DRIVE_LEAD_ID);
    private final WPI_TalonFX rightDriveLead = new WPI_TalonFX(Constants.RIGHT_DRIVE_LEAD_ID);
    private final WPI_TalonFX leftDriveFollower = new WPI_TalonFX(Constants.LEFT_DRIVE_FOLLOWER_ID);
    private final WPI_TalonFX rightDriveFollower =
            new WPI_TalonFX(Constants.RIGHT_DRIVE_FOLLOWER_ID);
    private final SpeedControllerGroup leftMotors =
            new SpeedControllerGroup(leftDriveLead, leftDriveFollower);
    private final SpeedControllerGroup rightMotors =
            new SpeedControllerGroup(rightDriveLead, rightDriveFollower);
    private final DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);
    private final AHRS navx = new AHRS(Port.kMXP);
    private DifferentialDriveOdometry odometry;

    private final WPI_TalonFX[] driveMotors = {
        leftDriveLead, rightDriveLead, leftDriveFollower, rightDriveFollower
    };

    private final double vkF = 0.0455925;
    private final double vkP = 0.21;
    private final double vkI = 0.000002;
    private final double vkD = 0;
    private final double akP = 0.01;
    private final double akI = 0.00006;
    private final double akD = 0.01;
    private PID aPID;

    public Drive() {
        odometry = new DifferentialDriveOdometry(navx.getRotation2d());
    }

    @Override
    public void init() {
        aPID = new PID(akP, akI, akD);
        aPID.setMaxOutput(0.75);
        aPID.setMinOutput(-0.75);
        leftDriveLead.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
        rightDriveLead.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
        leftDriveFollower.set(ControlMode.Follower, Constants.LEFT_DRIVE_LEAD_ID);
        rightDriveFollower.set(ControlMode.Follower, Constants.RIGHT_DRIVE_LEAD_ID);
        rightDriveLead.setInverted(true);
        rightDriveFollower.setInverted(InvertType.FollowMaster);
        leftDriveLead.setInverted(false);
        leftDriveFollower.setInverted(InvertType.FollowMaster);
        leftDriveLead.setSensorPhase(false);
        rightDriveLead.setSensorPhase(false);
        leftDriveLead.setSelectedSensorPosition(0);
        rightDriveLead.setSelectedSensorPosition(0);
        leftDriveLead.selectProfileSlot(0, 0);
        rightDriveLead.selectProfileSlot(0, 0);
        leftDriveLead.config_kF(0, vkF);
        rightDriveLead.config_kF(0, vkF);
        leftDriveLead.config_kP(0, vkP);
        rightDriveLead.config_kP(0, vkP);
        leftDriveLead.config_kI(0, vkI);
        rightDriveLead.config_kI(0, vkI);
        leftDriveLead.config_kD(0, vkD);
        rightDriveLead.config_kD(0, vkD);
        setCurrentLimitsEnabled();
        setDefaultCommand(new DriveDefault(this));
    }

    @Override
    public void autoInit() {
        setVoltageCompensation(Constants.DRIVE_MAX_VOLTAGE);
        setDriveBrake();
        leftDriveLead.setSelectedSensorPosition(0);
        rightDriveLead.setSelectedSensorPosition(0);
    }

    @Override
    public void teleopInit() {
        setDriveCoast();
        leftDriveLead.setSelectedSensorPosition(0);
        rightDriveLead.setSelectedSensorPosition(0);
    }

    public void setVoltageCompensation(double volts) {
        for (WPI_TalonFX t : driveMotors) {
            t.configVoltageCompSaturation(volts);
        }
    }

    public void setCurrentLimitsEnabled() {
        for (WPI_TalonFX t : driveMotors) {
            t.configSupplyCurrentLimit(Constants.currentLimitEnabled);
        }
    }

    public void setDriveBrake() {
        for (WPI_TalonFX t : driveMotors) {
            t.setNeutralMode(NeutralMode.Brake);
        }
    }

    public void setDriveCoast() {
        for (WPI_TalonFX t : driveMotors) {
            t.setNeutralMode(NeutralMode.Coast);
        }
    }

    public void setLeftPercent(double percent) {
        leftDriveLead.set(ControlMode.PercentOutput, percent);
    }

    public void setRightPercent(double percent) {
        rightDriveLead.set(ControlMode.PercentOutput, percent);
    }

    public void setLeftSpeed(double fps) {
        leftDriveLead.set(ControlMode.Velocity, Constants.driveFPSToUnitsPer100MS(fps));
    }

    public void setRightSpeed(double fps) {
        rightDriveLead.set(ControlMode.Velocity, Constants.driveFPSToUnitsPer100MS(fps));
    }

    public double getLeftPosition() {
        return Constants.driveUnitsToFeet(leftDriveLead.getSelectedSensorPosition());
    }

    public double getRightPosition() {
        return Constants.driveUnitsToFeet(rightDriveLead.getSelectedSensorPosition());
    }

    public double getLeftSpeed() {
        return Constants.driveUnitsPer100MSToFPS(leftDriveLead.getSelectedSensorVelocity());
    }

    public double getRightSpeed() {
        return Constants.driveUnitsPer100MSToFPS(rightDriveLead.getSelectedSensorVelocity());
    }

    public double getNavxAngle() {
        return navx.getAngle();
    }

    public boolean isNavxConnected() {
        return navx.isConnected();
    }

    public void resetEncoders() {
        leftDriveLead.setSelectedSensorPosition(0);
        rightDriveLead.setSelectedSensorPosition(0);
    }

    public double safelyDivide(double i, double j) {
        if (j == 0 || j == Double.NaN || i == Double.NaN) {
            return 0;
        }
        return i / j;
    }

    public void arcadeDrive(double throttle, double turn) {
        drive.arcadeDrive(throttle, turn);
    }

    public void tankDrive(double left, double right) {
        drive.tankDrive(left, right);
    }

    public void tankDriveVolts(double leftVolts, double rightVolts) {
        leftMotors.setVoltage(leftVolts);
        rightMotors.setVoltage(-rightVolts);
        drive.feed();
    }

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(getLeftSpeed() * 10, getRightSpeed() * 10);
    }

    public void resetOdometry(Pose2d pose) {
        resetEncoders();
        odometry.resetPosition(pose, navx.getRotation2d());
    }

    public double getAverageEncoderDistance() {
        return (leftDriveLead.getSelectedSensorPosition()
                        + rightDriveLead.getSelectedSensorPosition())
                / 2.0;
    }

    public void setMaxOutput(double maxOutput) {
        drive.setMaxOutput(maxOutput);
    }

    public void resetNavx() {
        navx.reset();
    }

    public double getTurnRate() {
        return -navx.getRate();
    }

    @Override
    public void periodic() {
        odometry.update(navx.getRotation2d(), getLeftPosition(), getRightPosition());
    }
}
