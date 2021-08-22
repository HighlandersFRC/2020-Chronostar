// Copyrights (c) 2018-2019 FIRST, 2020 Highlanders FRC. All Rights Reserved.

package frc.robot.tools.math;

public class Vector {

    private double i;
    private double j;

    public Vector() {
        i = 0;
        j = 0;
    }

    public Vector(double i, double j) {
        this.i = i;
        this.j = j;
    }

    public double getI() {
        return i;
    }

    public double getJ() {
        return j;
    }

    public Vector multiply(Vector u, double mult) {
        // System.out.println(u.getI();
        Vector e = new Vector(u.getI() * mult, u.getJ() * mult);
        // System.out.println(e.getI());
        return e;
    }

    public double dot(Vector u) {
        return i * u.getI() + j * u.getJ();
    }

    public double magnitude() {
        return Math.sqrt(this.dot(this));
    }

    public void setI(double i) {
        this.i = i;
    }

    public void setJ(double j) {
        this.j = j;
    }
}