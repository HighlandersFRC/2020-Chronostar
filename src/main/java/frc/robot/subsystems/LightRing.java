/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LightRing extends SubsystemBase {
    /** Creates a new LightRing. */
    private final Relay visionRelay = new Relay(0);

    public LightRing() {}

    public void lightRingOn() {
        visionRelay.set(Relay.Value.kForward);
    }

    public void lightRingOff() {
        visionRelay.set(Relay.Value.kReverse);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
