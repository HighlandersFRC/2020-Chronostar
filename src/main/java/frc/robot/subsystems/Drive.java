package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import frc.robot.commands.defaultcommands.DriveDefaultCommand;

public class Drive extends SubsystemBase {
    public Drive() {}

    public static TalonFX leftDriveLead = new TalonFX(Constants.LEFT_DRIVE_LEAD_ID);
    public static TalonFX rightDriveLead = new TalonFX(Constants.RIGHT_DRIVE_LEAD_ID);
    public static TalonFX leftDriveFollower1 = new TalonFX(Constants.LEFT_DRIVE_FOLLOWER_ID);
    public static TalonFX rightDriveFollower1 = new TalonFX(Constants.RIGHT_DRIVE_FOLLOWER_ID);

    public void init() {
        rightDriveLead.setInverted(true);
        leftDriveFollower1.set(ControlMode.Follower, Constants.LEFT_DRIVE_LEAD_ID);
        rightDriveFollower1.set(ControlMode.Follower, Constants.RIGHT_DRIVE_LEAD_ID);
        leftDriveFollower1.setInverted(InvertType.FollowMaster);
        rightDriveFollower1.setInverted(InvertType.FollowMaster);
        setDefaultCommand(new DriveDefaultCommand(this));
    }

    public void setRightPercent(double percent) {
        rightDriveLead.set(ControlMode.PercentOutput, percent);
    }

    public void setLeftPercent(double percent) {
        leftDriveLead.set(ControlMode.PercentOutput, percent);
    }

    @Override
    public void periodic() {}
}
