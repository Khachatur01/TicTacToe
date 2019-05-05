package com.SoftBit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private static char human = 'o', comp = 'x';
    private static byte WIN = -1, LOSE = -2, NO_ONE = -3, OK = -4;
    private static char board[] = {
            '0', '1', '2',
            '3', '4', '5',
            '6', '7', '8'
    };
    private static MiniMax miniMax = new MiniMax(human, comp, board);;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

	    int choose;
        HashMap<String, Integer> res;

        while(true) {
            for (byte i = 0; i < board.length; i++) {
                cleanScreen();
                printScreen();

                byte gameState = game();
                if (gameState != OK)
                    break;

                do {
                    System.out.print("Choose: ");
                    choose = input.nextInt();
                } while (choose >= board.length || choose < 0 || (board[choose] == comp || board[choose] == human));

                board[choose] = human;

                try {
                    res = miniMax.minimax(board, comp);
                    board[res.get("index")] = comp;
                } catch (NullPointerException e) {

                }
            }

            System.out.print("Play Again(y,n): ");
            if(!input.next().equals("y"))
                break;

            reset();
        }


    }



    private static boolean noOne() {
        for(int i = 0; i < board.length; i++)
            if(board[i] != human && board[i] != comp)
                return false;

        return true;
    }

    private static byte game() {
        if(miniMax.win(board, comp)){
            System.out.println("YOU LOSE!!!");
            return LOSE;
        }
        else if(miniMax.win(board, human)){
            System.out.println("YOU WIN!!!");
            return WIN;
        }
        else if(noOne()){
            System.out.println("NO ONE!!!");
            return NO_ONE;
        }
        else return OK;
    }

    private static void cleanScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {}
    }

    private static void printScreen(){
        for(int i = 0; i < board.length; i++){
            if( (i + 1) % 3 == 0)
                System.out.println(board[i]);
            else
                System.out.print(board[i] + " ");
        }
    }

    private static void reset(){
        for(byte i=0; i<board.length; i++)
            board[i] = Character.forDigit(i, 10);
    }
}
