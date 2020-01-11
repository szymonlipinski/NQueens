package com.simononsoftware.nqueens;

import java.util.*;

/**
 * Class for solving the n-queens problem.
 * <p>
 * Place N queens on an NxN chess board so that none of them attack each other (the classic n-queens problem).
 * Additionally, please make sure that no three queens are in a straight line at ANY angle,
 * so queens on A1, C2 and E3, despite not attacking each other, form a straight line at some angle.
 */
public class NQueensSolver {

    // The N in the NQueens
    private int size;

    // Lines
    private Set<Line> lines = new HashSet<>();

    public NQueensSolver(int size) {
        this.size = size;
    }

    /**
     * Tries to find a solution to the problem. If found - it returns a board with the solution.
     * <p>
     * The idea is to have a list of boards, each for one queen in one column. This way for N=1000 we would need to have
     * 1k bitboards. Each with a field for one column with all controlled fields set. Then I will make one bitboard
     * with all the boards combined. Then finding the next free field in a column would a matter of a simple bitboard check.
     * <p>
     * The only thing which would be made some other way is the additional check for the three point lines. The list of lines
     * is kept separately.
     * <p>
     * The algorithm will place a queen in each column in the first free field. Then it will try to go to the next column,
     * till the last one. If it's done, we have a solution. If it's not doable for a column, then it will go the previous
     * column and will try to place the queen on the next free field. If it's not doable, it will go the previous column,
     * and so on.
     * <p>
     * At the end we should be able toe get a solution or be sure there is none.
     *
     * @return A board with the solution or null if not found.
     */
    public Optional<Board> findSolution() {
        List<Board> boards = new ArrayList<Board>(size);

        // TODO remove it
        // while (true) {
        for (int a = 0; a < 30; a++) {
            boolean foundNewField = findNextEmptyFieldInColumn(boards, boards.size());
            if (foundNewField) {

                System.out.println(String.format(">>> Found a new field %s", boards.get(boards.size() - 1).getField()));
                System.out.println(Board.buildFromFields(Board.getSetFields(boards), size));

                if (boards.size() == size) {
                    return Optional.of(Board.buildFromFields(Board.getSetFields(boards), size));
                }
                continue;
            } else {
                System.out.println(">>> Didn't find a new field");
                if (boards.size() == 0) {
                    break;
                }
                System.out.println(Board.buildFromFields(Board.getSetFields(boards), size));
                findNextEmptyFieldInColumn(boards, boards.size() - 1);
            }
        }

        return Optional.empty();
    }

    boolean findNextEmptyFieldInColumn(List<Board> boards, int column) {

        System.out.println(String.format("Called findNextEmptyFieldInColumn(column=%d)", column));

        boolean theColumnExists = column < boards.size();
        Field lastField = new Field(column, -1);

        if (theColumnExists) {
            lastField = boards.get(column).getField();
            for (int i = boards.size() - 1; i >= column; i--) {
                System.out.println(String.format("Removing column %d", i));
                boards.remove(i);
            }
        }

        System.out.println(String.format("lastField; %s", lastField));

        // all the previous bitboards combined to get a list of controlled fields
        BitSet combinedBoards = Board.combineBitboards(boards, size);

        // get number of queens already set
        // int nextQueenFile = boards.size();

        // find field to place the queen in the next column
        for (int i = lastField.getRank() + 1; i < size; i++) {
            Board newQueenBoard = new Board(size);
            Field nextQueenField = new Field(lastField.getFile(), i);
            System.out.println(String.format("nextQueenField; %s", nextQueenField));
            newQueenBoard.setField(nextQueenField);
            if (newQueenBoard.getBitboard().intersects(combinedBoards)) {
                // this means the new queen is placed on an already controlled field
                // TODO add checking lines
                continue;
            } else {
                // we have a good place, add the field, and go to the next column
                newQueenBoard.setControlledFields(nextQueenField);
                boards.add(newQueenBoard);
                return true;
            }
        }
        // found no valid fields for this column
        return false;
    }

}
