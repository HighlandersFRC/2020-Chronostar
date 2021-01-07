// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.tools.extrabuttons;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Button;

public class TriggerButton extends Button {
    private final double THRESHOLD = 0.5;
    private Joystick joystick;
    private int axis;

    public TriggerButton(Joystick joystick, int axisNumber) {
        this.joystick = joystick;
        axis = axisNumber;
    }

    public boolean get() {
        return joystick.getRawAxis(axis) >= THRESHOLD;
    }
}
