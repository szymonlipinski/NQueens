package com.simononsoftware.nqueens;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestLine {

    @Test
    public void testLinearFunction() {
        Line line = new Line(10, 20);

        assertEquals(10, line.getA());
        assertEquals(20, line.getB());
        assertEquals(20, line.calculateValue(0));
        assertEquals(30, line.calculateValue(1));
        assertEquals(60, line.calculateValue(4));
    }

    @Test
    public void testToString() {
        Line line = new Line(4, 5);
        assertEquals("y=4x+5", line.toString());
    }

}
