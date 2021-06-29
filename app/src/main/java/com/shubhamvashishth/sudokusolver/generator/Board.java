package com.shubhamvashishth.sudokusolver.generator;

import com.shubhamvashishth.sudokusolver.boardview.Cell;

import java.util.List;

public class Board {

    static public int SIZE = 9; // Must be a squared number

    private int [][] boardNumbers = new int [SIZE][SIZE];
    private boolean [][] boardConst = new boolean [SIZE][SIZE];

    public Board() {

    }

    /**
     * Copy constructor.
     * @param otherBoard Board to make a copy from.
     */
    public Board(Board otherBoard) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                boardNumbers[i][j] = otherBoard.boardNumbers[i][j];
                boardConst[i][j] = otherBoard.boardConst[i][j];
            }
        }
    }

    /**
     * Converts a board to string, mostly used for display, but could also be used
     * for serialization (storing in a file or a db, for example).
     * @return A string representation of the board.
     */
    public String toString() {
        String str = "\n";
        for (int i = 0; i < Board.SIZE; i++) {
            if (i%((int)Math.sqrt(Board.SIZE))==0) {
                str += addLine(true);
            } else {
                str += addLine(false);
            }

            for (int j = 0; j < Board.SIZE; j++) {
                if(j%((int)Math.sqrt(Board.SIZE))==0) {
                    str += "| ";
                } else {
                    str += "  ";
                }

                if (getNumber(j,i) == 0 ) {
                    str += "  ";
                } else if (getNumber(j, i) == 10){
                    str += "X ";
                } else {
                    str += getNumber(j,i) + " ";
                }
            }
            str += "|" + "\n";
        }
        str += addLine(true);

        return str;
    }

    /**
     * REturns a separating row-wise line depending on the boolean passed.
     * If the bool is true, then the line is a square separation line.
     * Else, it is just a regular filling line.
     * @param show Determines if we generate a square line or a filling line.
     * @return A String representation of the line.
     */
    private String addLine(boolean show) {
        String str = "";
        for ( int j = 0; j < Board.SIZE; j++) {
            if (show) {
                if ( j%((int)Math.sqrt(Board.SIZE))==0 ) {
                    str += "+---";
                } else {
                    str += "----";
                }
            } else {
                if ( j%((int)Math.sqrt(Board.SIZE))==0 ) {
                    str += "|   ";
                } else {
                    str += "    ";
                }
            }
        }

        if (show) {
            str += "+" + "\n";
        } else {
            str += "|" + "\n";
        }

        return str;
    }

    public void setNumber ( int x, int y, int num ) {
        boardNumbers[y][x] = num;
    }

    public void setConst( int x, int y, boolean isConstant) {
        boardConst[y][x] = isConstant;
    }

    public int getNumber ( int x, int y ) {
        return boardNumbers[y][x];
    }

    public boolean isConst( int x, int y ) {
        return boardConst[y][x];
    }

    public void eraseNumber ( int x, int y ) {
        setNumber(x, y, 0);
    }

    /**
     * Checks if the board is solved or not.
     * @return If the board is solved or not.
     */
    public boolean isSolved() {
        for (int i=0; i<Board.SIZE;i++){
            for (int j=0; j<Board.SIZE;j++){
                if  (getNumber(i,j)==0 ) {
                    return false;
                }
            }
        }
        return true;
    }



    public void setBoard(List<Cell> varia){

        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++)
            {if(boardNumbers[i][j]==0) varia.get(i*9+j).setValue(null);
            else {varia.get(i*9+j).setValue(boardNumbers[i][j]);
                varia.get(i*9+j).setStartingCell(true);}
            }

    }
}


