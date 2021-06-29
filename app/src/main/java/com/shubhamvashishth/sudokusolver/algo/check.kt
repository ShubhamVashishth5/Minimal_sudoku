package com.shubhamvashishth.sudokusolver.algo

import com.shubhamvashishth.sudokusolver.boardview.showboard
import com.shubhamvashishth.sudokusolver.boardview.useClass



fun check() : Boolean

{

    var iter=0
    for(i in 0..8)
        for(j in 0..8)

        {
            var global= i*9+j
            var index: Int

            if(useClass.sugrid[global].value==null)return false

            for(k in 0..8) {
                index= i*9+k
                if(index!=global)
                if (useClass.sugrid[index].value == useClass.sugrid[global].value)
                    return false
            }

            for(k in 0..8) {
                index= k*9+j
                if(index!=global)
                    if (useClass.sugrid[index].value == useClass.sugrid[global].value)
                        return false
            }

            iter++
            var row= i-i%3
            var col= j-j%3

            for(l in row..(row+2))
                for(m in col..(col+2)){
                    index= l*9+m
                    if(index!=global)
                        if (useClass.sugrid[index].value == useClass.sugrid[global].value)
                            return false

                }



        }


    return true
}



fun wrongFiller() {
    var tosolve: solveinjava = solveinjava()
    tosolve.getBoard(useClass.qgrid)
    tosolve.solveSudoku(tosolve.board,9)
    tosolve.setBoard(useClass.qgrid)

    for(i in 0..8)
        for (j in 0..8)
            if(useClass.sugrid[i*9+j].value!=null)
                if(useClass.sugrid[i*9+j].value!=useClass.qgrid[i*9+j].value)
                    useClass.sugrid[i*9+j].isWrong=true;



}