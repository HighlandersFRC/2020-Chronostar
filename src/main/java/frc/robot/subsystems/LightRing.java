// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;

import frc.robot.commands.defaults.LightRingDefault;

public class LightRing extends SubsystemBaseEnhanced {
    private final Relay visionRelay = new Relay(0);
    private final Relay ballRelay = new Relay(1);

    public LightRing() {}

    @Override
    public void init() {
        setDefaultCommand(new LightRingDefault(this));
    }

    public void turnVisionOn() {
        visionRelay.set(Value.kForward);
    }

    public void turnVisionOff() {
        visionRelay.set(Value.kReverse);
    }

    public void turnBallOn() {
        ballRelay.set(Value.kForward);
    }

    public void turnBallOff() {
        ballRelay.set(Value.kReverse);
    }

    @Override
    public void periodic() {}

    @Override
    public void autoInit() {}

    @Override
    public void teleopInit() {}
}
