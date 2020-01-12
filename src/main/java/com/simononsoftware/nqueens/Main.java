package com.simononsoftware.nqueens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

/**
 * The main class to run the code from console.
 */
public class Main {

    public static void main(String args[]) throws IOException {
        System.out.println("Program for solving the n-queens problem.\n\n" +
                "  Place N queens on an NxN chess board so that none of them attack each other (the classic n-queens problem). " +
                "  Additionally, please make sure that no three queens are in a straight line at ANY angle, " +
                "  so queens on A1, C2 and E3, despite not attacking each other, form a straight line at some angle.\n\n");

        while (true) {
            System.out.println("Provide the size of the board:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                Integer problemSize = Integer.valueOf(reader.readLine());
                System.out.println("Searching for a solution... this can take a while...");
                NQueensSolver solver = new NQueensSolver(problemSize);
                Optional<Board> solution = solver.findSolution();
                if (solution.isPresent()) {
                    Board board = solution.get();
                    System.out.println(String.format("\nThe solution for %d queens is:\n", problemSize));
                    System.out.println(String.format("The board with queens looks like this:\n\n%s", board));
                    System.out.println(String.format("The queens are at points: %s\n", board.getFields()));

                } else {
                    System.out.println(String.format("\nDidn't find any solution for %d queens\n", problemSize));
                }

            } catch (NumberFormatException e) {
                System.out.println("This not a correct integer number.");
            }
        }
    }
}
