// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;

import frc.robot.commands.defaults.LightRingDefault;

public class LightRing extends SubsystemBaseEnhanced {
    private final Relay visionRelay = new Relay(1);

    public LightRing() {}

    @Override
    public void init() {
        setDefaultCommand(new LightRingDefault(this));
    }

    public void turnOff() {
        visionRelay.set(Value.kReverse);
    }

    public void turnOn() {
        visionRelay.set(Value.kForward);
    }

    @Override
    public void periodic() {}

    @Override
    public void autoInit() {}

    @Override
    public void teleopInit() {}
}
