package com.simononsoftware.nqueens;

import java.util.HashSet;
import java.util.Set;

/**
 * Class for solving the n-queens problem.
 * <p>
 * Place N queens on an NxN chess board so that none of them attack each other (the classic n-queens problem).
 * Additionally, please make sure that no three queens are in a straight line at ANY angle,
 * so queens on A1, C2 and E3, despite not attacking each other, form a straight line at some angle.
 */
//public class NQueensSolver {
//
//    // The N in the NQueens
//    private int size;
//
//    // Lines
//    private Set<Line> lines = new HashSet<>();
//
//    // Columns
//    private Set<Integer> files = new HashSet<>();
//
//    // Rows
//    private Set<Integer> ranks = new HashSet<>();
//
//    public NQueensSolver(int size) {
//        this.size = size;
//    }
//
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
//}
