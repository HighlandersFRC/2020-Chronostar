// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.teleopcommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotMap;

public class ClimberUp extends CommandBase {

    public static double startTime;

    public ClimberUp() {}

    @Override
    public void initialize() {
        startTime = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        RobotMap.climberRelease.set(Value.kForward);
        if(Timer.getFPGATimestamp() - startTime > 1 && Timer.getFPGATimestamp() - startTime < 4) {
            RobotMap.climberMotor.set(-0.3);
        }
    }

    @Override
    public void end(boolean interrupted) {
        RobotMap.climberMotor.set(0);
    }

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() - startTime > 4;
    }
}
