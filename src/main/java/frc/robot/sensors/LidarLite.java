// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.sensors;

import edu.wpi.first.wpilibj.Counter;

public class LidarLite {
    private static final int distOffset = -28;
    private Counter counter;

    public LidarLite(Counter lidarCounter) {
        counter = lidarCounter;
        counter.setMaxPeriod(1.0);
        counter.setSemiPeriodMode(true);
        counter.reset();
    }

    public double getDistance() {
        double cmDist;
        double inDist;
        if (counter.get() < 1) {
            return -1;
        }
        cmDist = (counter.getPeriod() * 1000000.0 / 10.0) + distOffset;
        inDist = cmDist * 0.0328084;
        return inDist;
    }
}
