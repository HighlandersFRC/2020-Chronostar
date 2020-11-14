// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.tools.controlloops.PID;

public class VisionAlignmentPID extends CommandBase {

    public VisionAlignmentPID() {}

    private static final double kP = 0.01;
    private static final double kI = 0.00006;
    private static final double kD = 0.02;
    private PID pid;
    private double jevoisAngle;

    @Override
    public void initialize() {
        pid = new PID(kP, kI, kD);
        pid.setSetPoint(0);
        pid.setMinOutput(-0.75);
        pid.setMaxOutput(0.75);
        RobotMap.visionRelay.set(Value.kForward);
    }

    @Override
    public void execute() {
        Robot.visionCam.updateVision();
        jevoisAngle = Robot.visionCam.getAngle();
        SmartDashboard.putNumber("Jevois Angle", jevoisAngle);
        SmartDashboard.putNumber("Get result", pid.getResult());
        if (Timer.getFPGATimestamp() - Robot.visionCam.lastParseTime < 0.25) {
            pid.updatePID(jevoisAngle + 9.5);
            RobotMap.leftDriveLead.set(ControlMode.PercentOutput, pid.getResult());
            RobotMap.rightDriveLead.set(ControlMode.PercentOutput, -pid.getResult());
        } else {
            pid.updatePID(0);
            RobotMap.leftDriveLead.set(ControlMode.PercentOutput, 0);
            RobotMap.rightDriveLead.set(ControlMode.PercentOutput, 0);
            RobotMap.visionRelay.set(Value.kForward);
        }
    }

    @Override
    public void end(boolean interrupted) {
        RobotMap.leftDriveLead.set(ControlMode.PercentOutput, 0);
        RobotMap.rightDriveLead.set(ControlMode.PercentOutput, 0);
        RobotMap.visionRelay.set(Value.kReverse);
    }

    @Override
    public boolean isFinished() {
        return (!OI.driverController.getXButton());
    }
}
