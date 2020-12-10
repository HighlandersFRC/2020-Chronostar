// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.commands.universalcommands;

import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.*;

public class TrackVisionTape extends CommandBase {

    public TrackVisionTape(boolean isAuto) {
        addRequirements(RobotMap.drive);
        if (isAuto) {}
    }

    @Override
    public void initialize() {
        RobotMap.visionRelay.set(Value.kForward);
    }

    @Override
    public void execute() {
        RobotMap.drive.orientToTarget();
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return RobotMap.visionRelay.get() == Value.kReverse;
    }
}
