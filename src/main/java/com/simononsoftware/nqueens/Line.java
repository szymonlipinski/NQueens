package com.simononsoftware.nqueens;

import java.util.Objects;

/**
 * Linear function representation.
 */
public class Line {

    private final int a;
    private final int b;

    public Line(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    /**
     * Calculates the linear function value.
     */
    int calculateValue(int x) {
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
        return String.format("y=%dx+%d", a, b);
    }
}
