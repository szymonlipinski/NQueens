package com.simononsoftware.nqueens;

import java.util.Objects;

/**
 * Linear function representation.
 */
public class Line {

    private final double a;
    private final double b;

    public Line(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    /**
     * Calculates the linear function value.
     */
    double calculateValue(double x) {
        return a * x + b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return a == line.a &&
                b == line.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    @Override
    public String toString() {
        return String.format("y=%fx+%f", a, b);
    }
}
