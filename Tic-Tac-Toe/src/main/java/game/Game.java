package game;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class Game
{
    public Game() {
    }

    public static void main(String[] args )throws IOException
    {
        File file = new File("LoseMoves.txt");
        if (!file.exists()) {
            file.createNewFile();
        }

        while (true) {
            Board board = new Board();
            System.out.println("To Start Game Press 1");
            System.out.println("To restart Game Press 2");
            System.out.println("To close Game Press 3");
            Scanner reader = new Scanner(System.in);
            String c = reader.next();
            if (c.length() > 1 || !Character.isDigit(c.charAt(0))){
                System.out.println("InValid entry. PLease reenter one among following :");
                continue;
            }
            int choice=Integer.parseInt(c);
            if(choice>3||choice<1)
            {
                System.out.println("InValid entry. PLease reenter one among following :");
                continue;
            }
            if(choice==3)
                break;
            if (choice == 1 || choice == 2) {
                board.setStartPos();
                int count = 0;

                //if turn=true human makes  move;if turn=false; computer makes first move
                boolean turn=false;
                System.out.println("Following is the empty board; The first move is randomly selected to make move");
                System.out.println("O is your mark; X is my mark");
                board.printBoard();
                while (count < 9) {
                    if (turn) {
                        System.out.println("**********  Your(Human) turn  ************");
                        board.MakeMove(1,file);

                        if (board.gameOver) {
                            board.printBoard();
                            System.out.println("You (human Player) Won");
                            break;
                        }
                        turn =false;
                    }
                    else
                    {
                        System.out.println("**********  My(Computer) turn  ************");
                        board.MakeMove(2,file);

                        if (board.gameOver) {
                            board.printBoard();
                            System.out.println("I (computer) Won");
                            break;
                        }
                        turn=true;
                    }
                    count++;
                    board.printBoard();
                }
                if(board.gameOver==false)
                    System.out.println("Game Draw");
            }
        }
        System.out.println("Game Exit");
    }
}
