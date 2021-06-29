package com.shubhamvashishth.sudokusolver.algo;

import com.shubhamvashishth.sudokusolver.boardview.Cell;

import java.util.List;



public class solveinjava {

    public static boolean isSafe(int[][] board,
                                 int row, int col,
                                 int num)
    {
        for (int d = 0; d < board.length; d++)
        {

            if (board[row][d] == num) {
                return false;
            }
        }


        for (int r = 0; r < board.length; r++)
        {


            if (board[r][col] == num)
            {
                return false;
            }
        }


        int sqrt = 3;
        int boxRowStart = row - row % sqrt;
        int boxColStart = col - col % sqrt;

        for (int r = boxRowStart;
             r < boxRowStart + sqrt; r++)
        {
            for (int d = boxColStart;
                 d < boxColStart + sqrt; d++)
            {
                if (board[r][d] == num)
                {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean solveSudoku(
            int[][] board, int n)
    {
        int row = -1;
        int col = -1;
        boolean isEmpty = true;
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (board[i][j] == 0)
                {
                    row = i;
                    col = j;


                    isEmpty = false;
                    break;
                }
            }
            if (!isEmpty) {
                break;
            }
        }


        if (isEmpty)
        {
            return true;
        }


        for (int num = 1; num <= n; num++)
        {
            if (isSafe(board, row, col, num))
            {
                board[row][col] = num;
                if (solveSudoku(board, n))
                {
                    // print(board, n);
                    return true;
                }
                else
                {

                    board[row][col] = 0;
                }
            }
        }
        return false;
    }

    public static void print(
            int[][] board, int N)
    {


        for (int r = 0; r < N; r++)
        {
            for (int d = 0; d < N; d++)
            {
                System.out.print(board[r][d]);
                System.out.print(" ");
            }
            System.out.print("\n");

            if ((r + 1) % (int)Math.sqrt(N) == 0)
            {
                System.out.print("");
            }
        }
    }

    public int[][] board= new int[9][9];

    public void getBoard(List<Cell> varia){

        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++){
                if(varia.get(i*9+j).getValue()==null) board[i][j]=0;
                else board[i][j]= varia.get(i*9+j).getValue();}

    }

    public void setBoard(List<Cell> varia){

        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++)
                 varia.get(i*9+j).setValue(board[i][j]);

    }




}
