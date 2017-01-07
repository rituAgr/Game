package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Ritu on 12/29/16.
 */
public class ComputerMoves {


    boolean make_move(block[][] gameBoard, ArrayList<position> computerMoves, ArrayList<position> playerMoves, File file)throws IOException
    {
        //1. Checking if computer can make a win move
        position p = CanMatch(gameBoard, computerMoves);
        if(p!=null)
        {
            gameBoard[p.row][p.col].setMark(mark.X);
            computerMoves.add(p);
            return true;
        }

        //2. Checking if computer can avoid player winning move
        p = CanMatch(gameBoard, playerMoves);
        if(p!=null)
        {
            gameBoard[p.row][p.col].setMark(mark.X);
            computerMoves.add(p);
            return false;
        }

        //3. checking if any empty space is remaining to make move
        position empty_block=null;
        loop: for(int i=1;i<=3;i++)
            for(int j=1;j<=3;j++)
                if(gameBoard[i][j].isOccupied()==false) {
                    empty_block = new position(i, j);
                    break loop;
                }

        //4. Now randomly generating moves and using AI to check if that move should be made
        AI intelligent=new AI();
        HashSet<position> movesNotMake = new HashSet<>();
        position pos=null;
        if(computerMoves.size()>0)
            pos=computerMoves.get(computerMoves.size()-1);
       movesNotMake=intelligent.failingMoves(computerMoves, file);//lingMoves(computerMoves);
        for(int i=0;i<80;i++)
        {
            int row = (int) (Math.random() * (4 - 1)) + 1;
            int col = (int) (Math.random() * (4 - 1)) + 1;
            p = new position(row, col);
            if (gameBoard[row][col].isOccupied())
                continue;
            if(isPresent(p,movesNotMake))
                continue;
            computerMoves.add(p);
            gameBoard[row][col].setMark(mark.X);
            if(checkVertical(mark.X,gameBoard)||checkHorizontal(mark.X,gameBoard)||checkDiagonal(mark.X,gameBoard))
                return true;
            return false;
        }
        computerMoves.add(empty_block);
        gameBoard[empty_block.row][empty_block.col].setMark(mark.X);
        return false;
    }
    //helper func
    boolean isPresent(position p ,HashSet<position> movesNotMake)
    {
        for(position pos:movesNotMake)
        {
            if(p.row==pos.row&&p.col==pos.col)
                return true;
        }
        return false;
    }
// check if there can be any match
    position CanMatch(block[][] gameBoard, ArrayList<position> q)
    {
        if(q.size()==0)
            return null;
        for(int i=0;i<q.size()-1;i++)
        {
            position first=q.get(i);
            for(int j=i+1;j<q.size();j++)
            {
                position second=q.get(j);
                //checking hoizontal
                if(first.row==second.row)
                {
                    int y=6-first.col-second.col;
                    if(gameBoard[first.row][y].isOccupied()==false)
                        return new position(first.row,y);
                }
                //checking vertical
                if(first.col==second.col)
                {
                    int x=6-first.row-second.row;
                    if(gameBoard[x][first.col].isOccupied()==false)
                        return new position(x,first.col);
                }
                //checking left top to right bottom  diagonal
                if(first.row==first.col&&second.row==second.col)
                {
                    int x=6-first.row-second.row;
                    if(gameBoard[x][x].isOccupied()==false)
                        return new position(x,x);
                }

                //checking right top to left bottom diagonal
                int x=Math.abs(first.row-second.row);
                int y=Math.abs(first.col-second.col);
                if(x==2&&y==2)
                {
                     x=6-first.row-second.row;
                    y=6-first.col-second.col;
                    if(gameBoard[x][y].isOccupied()==false)
                        return new position(x,y);
                }
                if((x==1&&y==1)&&((first.row==2&&first.col==2)||(second.row==2&&second.col==2)))
                {
                    x=6-first.row-second.row;
                    y=6-first.col-second.col;
                    if(gameBoard[x][y].isOccupied()==false)
                        return new position(x,y);
                }
            }
        }
        return null;
    }
    //helper func
    boolean checkVertical(mark marking,block[][] gameBoard)
    {
        for(int c=1;c<=3;c++) {
            int check=0;
            for (int r = 1; r <=3; r++)
                if(gameBoard[r][c].M==marking)
                    check++;
            if(check==3)
                return true;
        }
        return false;
    }
    //helper func
    boolean checkHorizontal(mark marking,block[][] gameBoard)
    {
        for(int r=1;r<=3;r++) {
            int check=0;
            for (int c = 1; c<=3; c++)
                if(gameBoard[r][c].M==marking)
                    check++;
            if(check==3)
                return true;
        }
        return false;
    }
    //helper func
    boolean checkDiagonal(mark marking,block[][] gameBoard)
    {
        int check=0;
        for(int r=1;r<=3;r++)
            if(gameBoard[r][r].M==marking)
                check++;
        if(check==3)
            return true;
        int r=1,c=3;
        check=0;
        while(r<=3)
        {
            if(gameBoard[r][c].M==marking)
                check++;
            r++;
            c--;
        }
        if(check==3)
            return true;
        return false;
    }
}
