package com.shubhamvashishth.sudokusolver.boardview



class useClass{

   companion object{ var sugrid= initCells0()

       var qgrid = initCells0()

       var ugrid= initCells0()

       var time="0"

       var timeins=0.0

       public fun initCells0(): List<Cell> {
           var cells= mutableListOf<Cell>()

           for ( i in 0..8){
               for(j in 0..8)
                   cells.add(Cell(i,j, null, false, false))}
           return cells

       }

       fun cleargrid(sugri:List<Cell>){

           for(i in 0..8)
               for(j in 0..8)
               {sugri[i*9+j].value=null
               sugri[i*9+j].isStartingCell=false
                   sugri[i*9+j].isWrong= false
                   sugrid[i*9+j].notes.clear()
               }
       }

       fun equal(one:List<Cell>, two : List<Cell>){
           for(i in 0..8)
            for(j in 0..8)
               {one[i*9+j].value=two[i*9+j].value
                   one[i*9+j].isWrong=two[i*9+j].isWrong
               }

       }

       fun equalUser(one:List<Cell>, two : List<Cell>){
           for(i in 0..8)
               for(j in 0..8)
                   if(two[i*9+j].value!= null)one[i*9+j].value=two[i*9+j].value

       }

       fun setfalse(){
           for(i in 0..8)
               for(j in 0..8) {
                   sugrid[i * 9 + j].isWrong = false
                   ugrid[i * 9 + j].isWrong = false
                   qgrid[i * 9 + j].isWrong = false
               }
       }


   }





    fun getCells(): List<Cell> {
        return sugrid
    }



}