package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ritu on 12/26/16.
 */

enum mark
{
    X,O;
}
class Board
{
    boolean gameOver;
    block[][] gameBoard;
    AI intelligence;
    PlayerMoves pMoves;
    ComputerMoves cMoves;
    ArrayList<position> playerMoves;
    ArrayList<position> computerMoves;

    Board()
    {
        gameOver=false;
        gameBoard=new block[4][4];
        intelligence=new AI();
        pMoves=new PlayerMoves();
        cMoves=new ComputerMoves();
        playerMoves=new ArrayList<position>();
        computerMoves=new ArrayList<position>();
    }
//Setting start board
    void setStartPos()
    {
        for(int i=1;i<4;i++)
            for(int j=1;j<4;j++)
            {
                position pos=new position(i,j);
                gameBoard[i][j]=new block(pos);
            }
    }
    void MakeMove(int choice,File file) throws IOException
    {
        //1 -> human ; 2 -> Computer
        if(choice==1)
        {   if(!pMoves.make_move(gameBoard,playerMoves))
            return;
            gameOver=true;
            intelligence.UpdateFailedMoves(computerMoves,file);// Updating computer moves if it loses
        }
        else
        {
            if(!cMoves.make_move(gameBoard,computerMoves,playerMoves,file ))
                return;
            gameOver=true;
        }
    }

    //Printing the board
    void printBoard()
    {
        for(int i=1;i<4;i++)
        {
            for(int j=1;j<4;j++)
            { if(gameBoard[i][j].M==null)
                    System.out.print("- ");
            else
            System.out.print(gameBoard[i][j].M+" ");
            }
            System.out.println();
        }
    }
}



