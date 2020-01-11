package com.simononsoftware.nqueens;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestField {

    @Test
    public void testField() {
        Field field = new Field(1, 2);

        assertEquals(1, field.getFile());
        assertEquals(2, field.getRank());

        Field fieldX = new Field(1, 2);
        assertEquals(field, fieldX);

        assertEquals("(1,2)", field.toString());

        List<Field> fields = Arrays.asList(new Field(1, 1), new Field(2, 1), new Field(0, 1), new Field(0, 0));

        Collections.sort(fields);
        List<Field> expectedFields = Arrays.asList(new Field(0, 0), new Field(0, 1), new Field(1, 1), new Field(2, 1));
        assertEquals(expectedFields, fields);
    }
}
