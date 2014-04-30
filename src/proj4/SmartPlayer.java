package proj4;

import java.util.Random;

/**
 * @author Mark Judy
 * @version 1.0 Date: 4/23/14 Time: 8:54 PM
 */
public class SmartPlayer
{
    private char marker;
    private int player;
    private Random rand;
    private Hashtable<Integer, HashEntry> table;
    private int[] gameStates;
    private int stateCount;

    /**
     * Default constructor for the Smart AI.
     */
    public SmartPlayer()
    {
        marker = 'Q';
        player = 0;
        rand = new Random(new Random().nextLong());
        table = new Hashtable<Integer, HashEntry>();
        gameStates = new int[9];
        stateCount = 0;
    }

    /**
     * Constructor for the smart AI that accepts a character marker and the player number for this player.
     *
     * @param mark a character to represent this player on the tic tac toe board
     * @param player the player number for this player
     */
    public SmartPlayer(char mark, int player)
    {
        this.player = player;
        marker = mark;
        rand = new Random(new Random().nextLong());
        table = new Hashtable<Integer, HashEntry>();
        gameStates = new int[9];
        stateCount = 0;
    }

    /**
     * Lets the smart player know that the most recent game has ended. Increments the wins and losses for the moves made
     * during this game.
     *
     * @param finalBoard The final state of the most recent tic tac toe game
     */
    public void endGame(TicTacToe finalBoard)
    {
        HashEntry entry;
        if(finalBoard.isOver() == player)
        {
            for(int i : gameStates)
            {
                if(table.containsKey(i))
                {
//                    System.out.println(i);
                    entry = table.get(i);
                    entry.incrementWins();
                    entry.incrementTimesSeen();
                    table.put(i, entry);
                }
            }
        }
        else
        {
            for(int i : gameStates)
            {
                if(table.containsKey(i))
                {
                    entry = table.get(i);
                    entry.incrementLosses();
                    entry.incrementTimesSeen();
                    table.put(i, entry);
                }
            }
        }

    }

    /**
     * This should return every board the smart player has played that came after a given move.
     *
     * @param t The tic tac toe board to search for following moves from.
     * @return an array of tic tac toe boards seen after the current board
     */
    public TicTacToe[] getSuccessors(TicTacToe t)
    {
        TicTacToe guess = new TicTacToe(t.toString(), player);
        TicTacToe[] boards = new TicTacToe[9];
        int count = 0;
        while (count < 9)
        {
            if(successorTest(rand.nextInt(3), rand.nextInt(3), guess))
            {
                boards[count] = guess;
                count++;
                guess = new TicTacToe(t.toString(), player);
            }
            else
                break;
        }
        return boards;
    }

    /**
     * Checks to see if a move is legal following the current tic tac toe board
     *
     * @param row the row to check the move in
     * @param column the column to check the move in
     * @param t the board on which to check the move
     * @return Returns true if the move is legal, false otherwise
     */
    private boolean successorTest(int row, int column, TicTacToe t)
    {
        return t.move(row, column);
    }

    /**
     *
     *
     * @param t
     */
    public void move(TicTacToe t)
    {
        gameStates[stateCount++] = t.hashCode();
        makeMove(t);
    }

    /**
     * Makes a move on the current tic tac toe board based on past experience if possible.
     *
     * @param t the current tic tac toe board
     */
    public void makeMove(TicTacToe t)
    {
        int row = rand.nextInt(3);
        int column = rand.nextInt(3);
        t.setPlayerMark(marker, player);
        HashEntry move = table.get(t.hashCode());
        TicTacToe[] moveList = getSuccessors(t);

        while(t.getTurn() == player)
        {
            if(table.containsKey(t.hashCode()))
            {
//                for(TicTacToe m : moveList)
                {
//                    if(m != null)
                    {
                        if(table.containsKey(t.hashCode()))
                        {
                            move = table.get(t.hashCode());
                        }

                        if(move.wins > move.losses)
                        {
                            if(t.move(move.row, move.column))
                            {
                                break;
                            }
                            else
                            {
                                while(!t.move(row, column))
                                {
                                    row = rand.nextInt(3);
                                    column = rand.nextInt(3);
                                }
                            }
                        }
                        else
                        {
                            while(!t.move(row, column))
                            {
                                row = rand.nextInt(3);
                                column = rand.nextInt(3);
                            }
                        }
                    }
                }
            }
            else
            {
                if(t.move(row, column))
                {
                    table.put(t.hashCode(), new HashEntry(1, 0, 0, row, column, t.toString(), t.hashCode()));
                }
                else
                {
                    makeMove(t);
                }
            }
        }
    }

    /**
     * Ensures the player is ready to start a new game of tic tac toe
     *
     * @param player sets the smart ai's player number if necessary
     */
    public void newGame(int player)
    {
        this.player = player;
        stateCount = 0;
        gameStates = new int[9];
    }

    /**
     * Returns the number of times the current tic tac toe board has been seen
     *
     * @param t the current tic tac toe board
     * @return the number of times the board has been seen
     */
    public int numberOfTimesSeen(TicTacToe t)
    {
        if(table.containsKey(t.hashCode()))
        {
            return table.get(t.hashCode()).timesSeen;
        }
        else
        {
            return 0;
        }
    }

    /**
     * Prints information about the hash table where the smart player stores its moves.
     */
    public void report()
    {
        System.out.println("The number of slots is: " + table.numSlots());
        System.out.println("The number of entries is: " + table.numEntries());
        System.out.println("The % full is: " + (((float)table.numEntries()/(float)table.numSlots()) * 100));
        System.out.println("The number of collisions is: " + table.numCollisions());
    }

    /**
     * Prints information about the smart player's favorite moves.
     */
    public void favoriteMove()
    {
        int mostSeen = 0;
        int index = 0;
        for(int i = 0; i < table.numSlots(); i++)
        {
            if(table.get(i) != null && table.get(i).timesSeen > mostSeen && hashCode() < 80)
            {
                mostSeen = table.get(i).timesSeen;
                index = i;
            }
        }

        System.out.println("My favorite first move is: \n" + table.get(index).boardStr);
        float winPerc = ((float)table.get(index).wins/table.get(index).timesSeen) * 100;
        System.out.println("Won " + table.get(index).wins + " of " + table.get(index).timesSeen + " which is " + winPerc);
    }

    /**
     * Helper class that tracks move information to be stored in the hash table.
     */
    private class HashEntry
    {
        int timesSeen;
        int wins;
        int losses;
        int row;
        int column;
        int hashCode;
        String boardStr;

        /**
         * Creates a hash entry based on the given move information.
         *
         * @param timesSeen The number of times the move has been seen.
         * @param wins The number of times the move has won.
         * @param losses The number of times the move has lost.
         * @param row The row where the move was made.
         * @param column The column where the move was made.
         * @param boardStr The string representation of the board.
         * @param hashCode The hash code for the board.
         */
        HashEntry(int timesSeen, int wins, int losses, int row, int column, String boardStr, int hashCode)
        {
            this.timesSeen = timesSeen;
            this.wins = wins;
            this.losses = losses;
            this.row = row;
            this.column = column;
            this.hashCode = hashCode;
            this.boardStr = boardStr;
        }

        /**
         * Increments the number of wins the move has made.
         */
        void incrementWins()
        {
            wins++;
        }

        /**
         * Increments the number of losses the move has made.
         */
        void incrementLosses()
        {
            losses++;
        }

        /**
         * Increments the number of times the move was seen.
         */
        void incrementTimesSeen()
        {
            timesSeen++;
        }
    }
}
