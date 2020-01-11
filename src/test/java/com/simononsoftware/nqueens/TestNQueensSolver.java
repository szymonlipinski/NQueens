package com.simononsoftware.nqueens;

import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestNQueensSolver {

    @Test
    public void testSolvingProblemForThreeQueens() {
        NQueensSolver solver = new NQueensSolver(4);

        Optional<Board> solution = solver.findSolution();

        assertTrue(solution.isPresent());

        Board board = solution.get();

        String expectedString = "";
        assertEquals(expectedString, board.toString());
    }
}
