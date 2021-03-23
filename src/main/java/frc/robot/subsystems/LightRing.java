// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;

import frc.robot.Constants;
import frc.robot.commands.defaults.LightRingDefault;

public class LightRing extends SubsystemBaseEnhanced {
    private final Relay visionRelay = new Relay(Constants.VISION_TARGET_LIGHT_RING_ID);
    private final Relay ballRelay = new Relay(Constants.BALL_TRACKING_LIGHT_RING_ID);

    public LightRing() {}

    @Override
    public void init() {
        setDefaultCommand(new LightRingDefault(this));
    }

    public void turnVisionOff() {
        visionRelay.set(Value.kForward);
    }

    public void turnVisionOn() {
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
