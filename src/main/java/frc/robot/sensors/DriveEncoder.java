// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.sensors;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.robot.Constants;

public class DriveEncoder {
    private TalonFX masterTalon;
    private int startingValue;

    public DriveEncoder(TalonFX talon, int startingValue) {
        masterTalon = talon;
    }

    public double getEncoderValue() {
        return masterTalon.getSelectedSensorPosition(0) - startingValue;
    }

    public double getEncoderVelocity() {
        return masterTalon.getSelectedSensorVelocity(0);
    }

    public double getDistance() {
        return ((((getEncoderValue()) / Constants.TICKS_PER_WHEEL_ROTATION)
                * Constants.WHEEL_CIRCUMFERENCE));
    }

    public double getVelocity() {
        return (((((getEncoderVelocity() * 10)) / Constants.TICKS_PER_WHEEL_ROTATION)
                * Constants.WHEEL_CIRCUMFERENCE));
    }

    public void softReset() {
        startingValue = masterTalon.getSelectedSensorPosition(0);
    }

    public double convertftpersToNativeUnitsper100ms(double feetPerSecond) {
        return (((feetPerSecond / 10) / (Constants.WHEEL_CIRCUMFERENCE))
                * Constants.TICKS_PER_WHEEL_ROTATION);
    }
}
