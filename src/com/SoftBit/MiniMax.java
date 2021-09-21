package com.SoftBit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MiniMax {

    private char comp, human, board[];

    public MiniMax(char human, char comp, char[] board){
        this.comp = comp;
        this.human = human;
        this.board = board;
    }

    public boolean win(char[] board, char player){
        if(
            (board[0] == player && board[1] == player && board[2] == player) ||
            (board[3] == player && board[4] == player && board[5] == player) ||
            (board[6] == player && board[7] == player && board[8] == player) ||
            (board[0] == player && board[3] == player && board[6] == player) ||
            (board[1] == player && board[4] == player && board[7] == player) ||
            (board[2] == player && board[5] == player && board[8] == player) ||
            (board[0] == player && board[4] == player && board[8] == player) ||
            (board[2] == player && board[4] == player && board[6] == player)
        )
            return true;
        return false;
    }

    private List<Integer> emptyFieldsIndex(char[] board){
        List<Integer> indexList = new ArrayList<>();
        for(int i = 0; i < board.length; i++)
            if(board[i] != comp && board[i] != human)
                indexList.add(i);
        return indexList;
    }

    public HashMap<String, Integer> minimax(char[] board, char player){
        List<Integer> emptyFieldsIndex = emptyFieldsIndex(board);

        if (win(board, human)) {
            HashMap<String, Integer> hashScore = new HashMap<>();

            hashScore.put("score", -10);
            return hashScore;
        }
        else if (win(board, comp)) {
            HashMap<String, Integer> hashScore = new HashMap<>();

            hashScore.put("score", 10);
            return hashScore;
        }
        else if (emptyFieldsIndex.size() == 0){
            HashMap<String, Integer> hashScore = new HashMap<>();

            hashScore.put("score", 0);
            return hashScore;
        }

        List<HashMap<String, Integer>> moves = new ArrayList<>();

        for(int i = 0; i < emptyFieldsIndex.size(); i++){

            //create an object for each and store the index of that spot
            HashMap<String, Integer> move = new HashMap<>();

            move.put( "index", Character.getNumericValue( board[emptyFieldsIndex.get(i)] ) );

            board[emptyFieldsIndex.get(i)] = player;


            if (player == comp){
                int result = minimax(board, human).get("score");
                move.put("score", result);
            }
            else{
                int result = minimax(board, comp).get("score");
                move.put("score", result);
            }

            board[emptyFieldsIndex.get(i)] = Character.forDigit(move.get("index"), 10);

            moves.add(move);
        }

        int bestMove = 0;
        if(player == comp){
            int bestScore = moves.get(0).get("score");
            for(int i = 1; i < moves.size(); i++){
                if(moves.get(i).get("score") > bestScore){
                    bestScore = moves.get(i).get("score");
                    bestMove = i;
                }
            }
        }else{
            int bestScore = moves.get(0).get("score");
            for(int i = 1; i < moves.size(); i++){
                if(moves.get(i).get("score") < bestScore){
                    bestScore = moves.get(i).get("score");
                    bestMove = i;
                }
            }
        }

        return moves.get(bestMove);
    }
}
