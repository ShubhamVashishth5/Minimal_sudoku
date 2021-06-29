package com.shubhamvashishth.sudokusolver.generator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Generator {

    private static final int UNICITY_ITERATIONS = 5;


    public enum DIFFICULTY {
        EASY,
        MEDIUM,
        HARD,
        EXTREME
    }



    public Board generateSudoku (DIFFICULTY difficulty) {
        int lowestSudoku = Board.SIZE * Board.SIZE;
        int attempts = 0;

        while (true) {
            ArrayList < Board > steps = new ArrayList < Board > ();

            Board generatedSudoku = new Board();
            Solver solver = new Solver ( generatedSudoku );
            solver.solveBoard();

            for (int i = 0 ; i < Board.SIZE; i++) {
                for (int j = 0; j < Board.SIZE; j++) {
                    generatedSudoku.setConst(i, j, true);
                }
            }

            steps.add(generatedSudoku);

            while(true){
                Board b = new Board(generatedSudoku);
                b = deleteNumber(b);
                if (verifyUnicity(b)) {
                    steps.add(b);
                    generatedSudoku = b;
                } else {
                    break;
                }
            }

            // LOGCAT DEBUGGING
            attempts++;
            if (lowestSudoku > (Board.SIZE * Board.SIZE - steps.size())) {
                lowestSudoku = (Board.SIZE * Board.SIZE - steps.size());
                System.out.println("\nFound a sudoku with " + lowestSudoku + " (" + steps.size() + " numbers removed).");
                System.out.print("Tried " + attempts + " sudokus.");
            } else {
                System.out.print("\rTried " + attempts + " sudokus.");
            }

            if (steps.size() > (Board.SIZE * Board.SIZE) / 1.58) {
                if (difficulty == DIFFICULTY.EXTREME) {
                    return steps.get(steps.size() -1);
                }
            }
            if (steps.size() > (Board.SIZE * Board.SIZE) / 1.6) {
                if (difficulty == DIFFICULTY.HARD) {
                    return steps.get((int)((steps.size() -1)));
                } else if (difficulty == DIFFICULTY.MEDIUM) {
                    return steps.get((int)((steps.size() - 1) * 0.92));
                } else if (difficulty == DIFFICULTY.EASY) {
                    return steps.get((int)((steps.size() - 1) * 0.74));
                }
            }
            if (steps.size() > (Board.SIZE * Board.SIZE) / 1.8) {
                if (difficulty == DIFFICULTY.MEDIUM) {
                    return steps.get((int)((steps.size() - 1) * 0.95));
                } else if (difficulty == DIFFICULTY.EASY) {
                    return steps.get((int)((steps.size() - 1) * 0.75));
                }
            }
            if (steps.size() > (Board.SIZE * Board.SIZE) / 2.2) {
                if (difficulty == DIFFICULTY.EASY) {
                    return steps.get((int)((steps.size() - 1) * 0.9));
                }
            }
        }
    }


    public boolean verifyUnicity (Board b ) {
        Board[] boards = new Board[UNICITY_ITERATIONS];

        for ( int i = 0; i< boards.length; i++ ){
            Board board = new Board(b);
            Solver solver = new Solver(board);
            solver.solveBoard();
            boards[i] = board;
        }

        for (int i = 1; i < boards.length; i++) {
            for (int j = 0; j < Board.SIZE; j++) {
                for (int k = 0; k < Board.SIZE; k++) {
                    if (boards[i].getNumber(j, k) != boards[0].getNumber(j, k))
                        return false;
                }
            }
        }

        return true;
    }


    public Board deleteNumber (Board b) {
        Board board = new Board(b);
        int x;
        int y;
        while (true){
            x = (int)(Math.random()*Board.SIZE);
            y = (int)(Math.random()*Board.SIZE);
            if (board.getNumber(x, y) != 0 ) {
                board.setNumber(x, y, 0);
                board.setConst(x, y, false);
                break;
            }
        }

        return board;
    }

}




class Solver {

    private ArrayList<ArrayList<Set<Integer>>> testedNumbers = new ArrayList< ArrayList< Set<Integer> >>(Board.SIZE);

    private Board board;

    private int current_x = 0;
    private int current_y = 0;

    private boolean lastBacktrack = false;

    Solver(Board b) {
        for ( int i = 0; i< Board.SIZE; i++) {
            testedNumbers.add(i, new ArrayList < Set<Integer>>(Board.SIZE));
            for ( int j = 0; j < Board.SIZE; j++ ) {
                testedNumbers.get(i).add(j, new HashSet<Integer>());
            }
        }

        board = b;
    }


    private boolean isValid ( int x, int y, int num ) {
        for (int i = 0; i < Board.SIZE; i++) {
            if (i == y)
                continue;

            if (board.getNumber(x, i) == num)
                return false;
        }



        for (int i = 0; i < Board.SIZE; i++) {
            if (i == x)
                continue;

            if (board.getNumber(i, y) == num)
                return false;
        }


        final int square_x = x / (int)Math.sqrt(Board.SIZE);
        final int square_y = y / (int)Math.sqrt(Board.SIZE);

        final int start_x = square_x * (int)Math.sqrt(Board.SIZE);
        final int start_y = square_y * (int)Math.sqrt(Board.SIZE);

        final int end_x = (square_x + 1) * (int)Math.sqrt(Board.SIZE);
        final int end_y = (square_y + 1) * (int)Math.sqrt(Board.SIZE);

        for (int i = start_x; i < end_x; i++) {
            for (int j = start_y; j < end_y; j++) {
                if (x == i && y == j)
                    continue;

                if (board.getNumber(i, j) == num)
                    return false;
            }
        }

        return true;
    }


    private boolean isEmpty ( int x, int y ) {
        return (board.getNumber(x, y) == 0);
    }


    public boolean nextStep () {
        if (board.isConst(current_x, current_y)) {
            if (lastBacktrack)
                return backtrack();
            lastBacktrack = false;

            return advanceCursor();
        } else {
            if (board.getNumber(current_x, current_y) == 0) {
                int a;
                a = (int)(Math.random()*Board.SIZE+1);
                board.setNumber(current_x, current_y, a);
                testedNumbers.get(current_y).get(current_x).add (a);
                return false;
            }

            if (isValid(current_x, current_y, board.getNumber(current_x, current_y)) && !lastBacktrack)
                return advanceCursor();
            else {
                if (testedNumbers.get(current_y).get(current_x).size() == Board.SIZE) {
                    lastBacktrack = true;
                    board.setNumber(current_x, current_y, 0);
                    testedNumbers.get(current_y).get(current_x).clear();
                    return backtrack();
                } else {
                    lastBacktrack = false;
                    int a;
                    while (true){
                        a = (int)(Math.random()*Board.SIZE+1);
                        if (!testedNumbers.get(current_y).get(current_x).contains (a)){
                            break;
                        }
                    }
                    board.setNumber(current_x, current_y, a);
                    testedNumbers.get(current_y).get(current_x).add (a);
                }
            }
        }

        return false;
    }


    private boolean advanceCursor() {
        if (current_x == Board.SIZE - 1) {
            if (current_y == Board.SIZE - 1)
                return true;

            current_x = 0;
            current_y++;
        } else {
            current_x++;
        }

        return false;
    }


    private boolean backtrack() {
        if (current_x == 0) {
            if (current_y == 0)
                return true;

            current_x = Board.SIZE - 1;
            current_y--;
        } else {
            current_x--;
        }

        return false;
    }

    public Board getBoard () {
        return board;
    }


    public void solveBoard () {
        while (!nextStep());
    }


}

