package frc.robot.subsystems;

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
        setDefaultCommand(new DriveDefaultCommand());
    }

    @Override
    public void periodic() {}
}
