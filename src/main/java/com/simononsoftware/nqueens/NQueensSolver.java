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
     * The only thing which would be made some other way is the additional check for the three point lines.
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

        int lastSearchedColumn = 0;

        while (true) {
            boolean foundNewField = findNextEmptyFieldInColumn(boards, lastSearchedColumn);
            if (foundNewField) {

                if (boards.size() == size) {
                    return Optional.of(Board.buildFromFields(Board.getSetFields(boards), size));
                }
                lastSearchedColumn++;
            } else {
                if (lastSearchedColumn == 0) {
                    break;
                }
                lastSearchedColumn--;
            }
        }

        return Optional.empty();
    }

    /**
     * Tries to find the next empty field in the given column.
     * <p>
     * If the column has no field marked, it goes from the bottom and checks each field. The main checks are made
     * using bitboards, then there is additional check made for already existing lines for every pair of points.
     * <p>
     * If the column has a set field, the checks are made from the field above the already set one.     *
     *
     * @param boards List of boards, each with one field set.
     * @param column The column to search the field for.
     * @return True if a field has been found, false otherwise.
     */
    boolean findNextEmptyFieldInColumn(List<Board> boards, int column) {
        boolean theColumnExists = column < boards.size();
        Field lastField = new Field(column, -1);

        if (theColumnExists) {
            lastField = boards.get(column).getField();
            for (int i = boards.size() - 1; i >= column; i--) {
                boards.remove(i);
            }
        }

        // all the previous bitboards combined to get a list of controlled fields
        BitSet combinedBoards = Board.combineBitboards(boards, size);

        for (int i = lastField.getRank() + 1; i < size; i++) {
            Board newQueenBoard = new Board(size);
            Field nextQueenField = new Field(lastField.getFile(), i);
            newQueenBoard.setField(nextQueenField);
            if (newQueenBoard.getBitboard().intersects(combinedBoards)) {
                // this means the new queen is placed on an already controlled field
                continue;
            } else {
                if (!checkLines(boards, nextQueenField)) {
                    continue;
                }

                // we have a good place, add the field, and go to the next column
                newQueenBoard.setControlledFields(nextQueenField);
                boards.add(newQueenBoard);
                return true;
            }
        }
        // found no valid fields for this column
        return false;
    }

    /**
     * Makes a line for the given fields.
     *
     * @param field1 A field on the line.
     * @param field2 A field on the line.
     * @return A linear function containing the two given fields.
     */
    private Line makeLine(Field field1, Field field2) {
        double a = ((double) (field1.getRank() - field2.getRank())) / (field1.getFile() - field2.getFile());
        double b = field1.getRank() - a * field1.getFile();
        return new Line(a, b);
    }

    /**
     * Checks if there is a line between two fields already marked on the board with the nextQueenField.
     *
     * @param boards         List of boards, each with one field marked.
     * @param nextQueenField A potential field for the next queen.
     * @return True if the nextQueenField is fine, false otherwise.
     */
    private boolean checkLines(List<Board> boards, Field nextQueenField) {
        List<Field> fields = Board.getSetFields(boards);
        for (int i = 0; i < fields.size() - 1; i++) {
            for (int y = i + 1; y < fields.size(); y++) {
                Line line = makeLine(fields.get(i), fields.get(y));
                double calculatedRank = line.calculateValue(nextQueenField.getFile());
                if (Math.abs(calculatedRank - nextQueenField.getRank()) < 0.1) {
                    return false;
                }
            }
        }
        return true;
    }


}
