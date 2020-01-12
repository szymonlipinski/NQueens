package com.simononsoftware.nqueens;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class TestNQueensSolver {

    private final int size;
    private final List<Field> fields;
    private final String boardRepresentation;

    static List<Field> emptyList = Arrays.asList();

    static String solutionFor1 = "" +
            "0  | * \n" +
            "   | 0 \n";

    static List<Field> fieldsFor1 = Arrays.asList(new Field(0, 0));

    static String solutionFor4 = "" +
            "3  | · * · · \n" +
            "2  | · · · * \n" +
            "1  | * · · · \n" +
            "0  | · · * · \n" +
            "   | 0 1 2 3 \n";

    static List<Field> fieldsFor4 = Arrays.asList(
            new Field(0, 1),
            new Field(1, 3),
            new Field(2, 0),
            new Field(3, 2));

    static String solutionFor8 = "" +
            "7  | · · * · · · · · \n" +
            "6  | · · · · · * · · \n" +
            "5  | · · · · · · · * \n" +
            "4  | · * · · · · · · \n" +
            "3  | · · · * · · · · \n" +
            "2  | * · · · · · · · \n" +
            "1  | · · · · · · * · \n" +
            "0  | · · · · * · · · \n" +
            "   | 0 1 2 3 4 5 6 7 \n";

    static List<Field> fieldsFor8 = Arrays.asList(
            new Field(0, 2),
            new Field(1, 4),
            new Field(2, 7),
            new Field(3, 3),
            new Field(4, 0),
            new Field(5, 6),
            new Field(6, 1),
            new Field(7, 5)
    );

    static String solutionFor9 = "" +
            "8  | · · · · · * · · · \n" +
            "7  | · · · · · · · * · \n" +
            "6  | · · * · · · · · · \n" +
            "5  | · · · · · · * · · \n" +
            "4  | · · · · · · · · * \n" +
            "3  | · * · · · · · · · \n" +
            "2  | · · · · * · · · · \n" +
            "1  | * · · · · · · · · \n" +
            "0  | · · · * · · · · · \n" +
            "   | 0 1 2 3 4 5 6 7 8 \n";

    static List<Field> fieldsFor9 = Arrays.asList(
            new Field(0, 1),
            new Field(1, 3),
            new Field(2, 6),
            new Field(3, 0),
            new Field(4, 2),
            new Field(5, 8),
            new Field(6, 5),
            new Field(7, 7),
            new Field(8, 4)
    );

    static String solutionFor10 = "" +
            "9   | ·  ·  ·  ·  ·  *  ·  ·  ·  ·  \n" +
            "8   | ·  ·  *  ·  ·  ·  ·  ·  ·  ·  \n" +
            "7   | ·  ·  ·  ·  *  ·  ·  ·  ·  ·  \n" +
            "6   | ·  ·  ·  ·  ·  ·  ·  ·  ·  *  \n" +
            "5   | ·  ·  ·  ·  ·  ·  ·  *  ·  ·  \n" +
            "4   | ·  ·  ·  *  ·  ·  ·  ·  ·  ·  \n" +
            "3   | ·  *  ·  ·  ·  ·  ·  ·  ·  ·  \n" +
            "2   | ·  ·  ·  ·  ·  ·  *  ·  ·  ·  \n" +
            "1   | ·  ·  ·  ·  ·  ·  ·  ·  *  ·  \n" +
            "0   | *  ·  ·  ·  ·  ·  ·  ·  ·  ·  \n" +
            "    | 0  1  2  3  4  5  6  7  8  9  \n";

    static List<Field> fieldsFor10 = Arrays.asList(
            new Field(0, 0),
            new Field(1, 3),
            new Field(2, 8),
            new Field(3, 4),
            new Field(4, 7),
            new Field(5, 9),
            new Field(6, 2),
            new Field(7, 5),
            new Field(8, 1),
            new Field(9, 6)
    );

    @Parameterized.Parameters(name = "{index}: Test with size={0}, expected fields={1}, board: {2}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {1, fieldsFor1, solutionFor1},
                {2, emptyList, ""},
                {3, emptyList, ""},
                {4, fieldsFor4, solutionFor4},
                {5, emptyList, ""},
                {6, emptyList, ""},
                {7, emptyList, ""},
                {8, fieldsFor8, solutionFor8},
                {9, fieldsFor9, solutionFor9},
                {10, fieldsFor10, solutionFor10},
        });
    }

    public TestNQueensSolver(int size, List<Field> fields, String boardRepresentation) {
        this.size = size;
        this.fields = fields;
        this.boardRepresentation = boardRepresentation;
    }

    @Test
    public void testSolvingProblemForThreeQueens() {
        NQueensSolver solver = new NQueensSolver(size);
        Optional<Board> solution = solver.findSolution();

        if (fields.size() == 0) {
            assertTrue(solution.isEmpty());

        } else {
            assertTrue(solution.isPresent());
            Board board = solution.get();
            assertEquals(fields, board.getFields());
            assertEquals(boardRepresentation, board.toString());

        }
    }
}
