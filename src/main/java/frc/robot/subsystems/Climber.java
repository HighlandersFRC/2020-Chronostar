// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.commands.universalcommands.SetWinchSpeed;

public class Climber extends SubsystemBase {
    
    public Climber() {}

    public void init() {
        RobotMap.ratchetPiston.set(Value.kReverse);   
    }

    public void periodic() {}

    public void teleopPeriodic() {
        if (OI.driverController.getTriggerAxis(Hand.kRight) >= 0.5) {
            RobotMap.winch.set(ControlMode.PercentOutput, 0.8);
        } else if (OI.driverController.getTriggerAxis(Hand.kLeft) >= 0.5) {
            new SequentialCommandGroup(new WaitCommand(0.1), new SetWinchSpeed(-0.8)).schedule();
        } else {
            RobotMap.ratchetPiston.set(Value.kReverse);
            RobotMap.winch.set(ControlMode.PercentOutput, 0);
        }
        if(OI.operatorController.getBumper(Hand.kRight)) {
            RobotMap.climberRelease.set(Value.kReverse);
        }
        else if(OI.operatorController.getBumper(Hand.kLeft)){
            RobotMap.climberRelease.set(Value.kForward);
        }
        if(OI.operatorController.getYButton()) {
            RobotMap.climberMotor.set(0.3);
        }
        else if(OI.operatorController.getXButton()) {
            RobotMap.climberMotor.set(-0.3);
        }
        else {
            RobotMap.climberMotor.set(0.0);
        }
    }
}