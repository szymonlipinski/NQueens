package com.simononsoftware.nqueens;

import org.junit.Test;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

import static org.junit.Assert.*;

public class TestBoard {

    @Test
    public void testConstructingBoard() {
        Board board = new Board(10);
        assertEquals(board.getSize(), 10);
    }

    @Test
    public void testToStringForEmptyBoard() {
        Board board = new Board(4);
        String expectedString = "" +
                "3  | · · · · \n" +
                "2  | · · · · \n" +
                "1  | · · · · \n" +
                "0  | · · · · \n" +
                "   | 0 1 2 3 \n";

        assertEquals(expectedString, board.toString());
    }

    @Test
    public void testToStringForFilledBoard() {
        Board board = new Board(4);
        board.setField(new Field(3, 2));

        String expectedString = "" +
                "3  | · · · · \n" +
                "2  | · · · * \n" +
                "1  | · · · · \n" +
                "0  | · · · · \n" +
                "   | 0 1 2 3 \n";

        assertEquals(expectedString, board.toString());
    }

    @Test
    public void testSettingFieldWithControlledFields() {
        Board board = new Board(4);
        board.setFieldWithControlledFields(new Field(2, 1));

        String expectedString = "" +
                "3  | * · * · \n" +
                "2  | · * * * \n" +
                "1  | * * * * \n" +
                "0  | · * * * \n" +
                "   | 0 1 2 3 \n";

        assertEquals(expectedString, board.toString());
    }

    @Test
    public void testCalculatingBitboardIndexFromField() {
        Board board = new Board(3);

        assertEquals(0, board.fieldToBitboardIndex(new Field(0, 0)));
        assertEquals(1, board.fieldToBitboardIndex(new Field(1, 0)));
        assertEquals(2, board.fieldToBitboardIndex(new Field(2, 0)));

        assertEquals(5, board.fieldToBitboardIndex(new Field(2, 1)));
        assertEquals(8, board.fieldToBitboardIndex(new Field(2, 2)));
    }

    @Test
    public void testCalculatingFieldFromBitboardIndex() {
        Board board = new Board(3);

        assertEquals(new Field(0, 0), board.bitboardIndexToField(0));
        assertEquals(new Field(1, 0), board.bitboardIndexToField(1));
        assertEquals(new Field(2, 0), board.bitboardIndexToField(2));

        assertEquals(new Field(2, 1), board.bitboardIndexToField(5));
        assertEquals(new Field(2, 2), board.bitboardIndexToField(8));

    }

    @Test(expected = BadFieldException.class)
    public void testSettingAFieldTwice() {
        Board board = new Board(3);
        board.setField(new Field(1, 1));
        board.setField(new Field(1, 1));
    }

    @Test
    public void testMergingBitboards() {
        Board board1 = new Board(5);
        Board board2 = new Board(5);
        Board board3 = new Board(5);
        Board board4 = new Board(5);

        Field field1 = new Field(1, 1);
        Field field2 = new Field(3, 3);
        Field field3 = new Field(2, 1);
        Field field4 = new Field(3, 0);

        board1.setField(field1);
        board2.setField(field2);
        board3.setField(field3);
        board4.setField(field4);

        List<Board> boards = Arrays.asList(board1, board2, board3, board4);

        BitSet result = Board.combineBitboards(boards, 4);

        int[] setBits = result.stream().toArray();
        int[] expectedBits = {3, 6, 7, 18};
        assertArrayEquals(expectedBits, setBits);

        List<Field> fields = Board.getSetFields(boards);
        Field[] expectedFields = {field1, field2, field3, field4};

        assertArrayEquals(expectedFields, fields.toArray());
    }

    @Test
    public void testBuildingBoardFromFields() {

        List<Field> fields = Arrays.asList(
                new Field(0,1), new Field(2,3)
        );

        Board board = Board.buildFromFields(fields, 4);


    }

}
