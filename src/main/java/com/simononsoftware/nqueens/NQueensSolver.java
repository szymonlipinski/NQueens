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

        while (true) {
            boolean foundNewField = findNextEmptyFieldInColumn(boards, boards.size());
            if (foundNewField) {
                if (boards.size() == size) {
                    return Optional.of(Board.buildFromFields(Board.getSetFields(boards), size));
                }
                continue;
            } else {
                if (boards.size() == 0) {
                    break;
                }
                findNextEmptyFieldInColumn(boards, boards.size() - 1);
            }
        }

        return Optional.empty();
    }

    boolean findNextEmptyFieldInColumn(List<Board> boards, int column) {

        boolean theColumnExists = column < boards.size();
        Field lastField = new Field(column, -1);

        if (theColumnExists) {
            lastField = boards.get(column).getField();
            boards.remove(boards.subList(column, boards.size() - 1));
        }

        System.out.println(lastField);

        // all the previous bitboards combined to get a list of controlled fields
        BitSet combinedBoards = Board.combineBitboards(boards, size);

        // get number of queens already set
        int nextQueenFile = boards.size();

        // find field to place the queen in the next column
        for (int i = lastField.getRank() + 1; i < size; i++) {
            Board newQueenBoard = new Board(size);
            Field nextQueenField = new Field(nextQueenFile, i);
            newQueenBoard.setField(nextQueenField);
            if (newQueenBoard.getBitboard().intersects(combinedBoards)) {
                // this means the new queen is placed on an already controller field
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

//    /**
//     * Every queen will be in one column, so it's enough to iterate through the columns to find the good place
//     * of placing the queens.
//     *
//     * @return If a solution is found - a Board with the positions of the queens. If is not found - an empty Board.
//     */
//    public Board findSolution() {
//
//        Board board = new Board(size);
//
//        for (int rank = 0; rank < size; rank++) { // ranks aka rows
//            for (int file = 0; file < size; file++) { // files aka columns
//                Field field = new Field(rank, file);
//                if (checkAddingQueen(board, field)) {
//                    addQueenToBoard(board, field);
//                    System.out.println(String.format("Added queen to field %s", field));
//
//                    // check if we added all N queens
//                    if (board.getQueensNumber() == size) return board;
//                }
//            }
//        }
//
//        System.out.print(board.getQueensNumber());
//        if (board.getQueensNumber() != size) {
//            return Board.emptyBoard();
//        }
//
//        return board;
//    }
//
//
//    /**
//     * Checks if a queen can be added to this field.
//     *
//     * @param board Board to add the queen to.
//     * @param field Field to check.
//     * @return True if the queen can be added at the field, false otherwise.
//     */
//    private boolean checkAddingQueen(Board board, Field field) {
//
//        // queen cannot be places on a field that a queen already is placed
//        if (board.isSet(field)) return false;
//
//        for (Line line : lines) {
//            if (checkFieldIsOnLine(field, line)) return false;
//        }
//
//        for (Integer rank : ranks) {
//            if (checkFieldIsOnRank(field, rank)) return false;
//        }
//
//        for (Integer file : files) {
//            if (checkFieldIsOnFile(field, file)) return false;
//        }
//
//        System.out.print(lines);
//
//        return true;
//    }
//
//    /**
//     * Checks if the given field is on the given line.
//     *
//     * @param field
//     * @param line
//     * @return
//     */
//    private boolean checkFieldIsOnLine(Field field, Line line) {
//        return field.getRank() == field.getFile() * line.getA() + line.getB();
//    }
//
//    private boolean checkFieldIsOnFile(Field field, Integer file) {
//        return field.getFile() == file;
//    }
//
//    private boolean checkFieldIsOnRank(Field field, Integer rank) {
//        return field.getRank() == rank;
//    }
//
//    private void addQueenToBoard(Board board, Field field) {
//        files.add(field.getFile());
//        ranks.add(field.getRank());
//
//        // add diagonal lines for this new queen
//        lines.add(new Line(+1, field.getRank() - field.getFile()));
//        lines.add(new Line(-1, field.getRank() + field.getFile()));
//
//        // board.getAllPieces();
//        board.set(field);
//    }
}
