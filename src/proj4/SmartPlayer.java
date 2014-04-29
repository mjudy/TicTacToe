package proj4;

import java.util.Random;

/**
 * @author theghv
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

    public SmartPlayer()
    {
        marker = 'Q';
        player = 0;
        rand = new Random(new Random().nextLong());
        table = new Hashtable<Integer, HashEntry>();
        gameStates = new int[9];
        stateCount = 0;
    }

    public SmartPlayer(char mark, int player)
    {
        this.player = player;
        marker = mark;
        rand = new Random(new Random().nextLong());
        table = new Hashtable<Integer, HashEntry>();
        gameStates = new int[9];
        stateCount = 0;
    }

    public void endGame(TicTacToe finalBoard)
    {
        HashEntry entry;
        if(finalBoard.getWinner() == player)
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

    private boolean successorTest(int row, int column, TicTacToe t)
    {
        return t.move(row, column);
    }

    public void move(TicTacToe t)
    {
        gameStates[stateCount++] = t.hashCode();
        makeMove(t);
    }

    public void makeMove(TicTacToe t)
    {
        int row = rand.nextInt(3);
        int column = rand.nextInt(3);
        t.setPlayerMark(marker, player);
        HashEntry move;
        TicTacToe[] moveList = getSuccessors(t);

        while(t.getTurn() == player)
        {
            if(table.containsKey(t.hashCode()))
            {
//                for(int i = 0; i < moveList.length && moveList[i] != null; i++)
                {
                    move = table.get(t.hashCode());
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
            else
            {
                if(t.move(row, column))
                {
                    table.put(t.hashCode(), new HashEntry(1, 0, 0, row, column, t.toString()));
                }
                else
                {
                    makeMove(t);
                }
            }
        }
    }

    public void newGame(int player)
    {
        this.player = player;
        stateCount = 0;
        gameStates = new int[9];
    }

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

    public String toString()
    {
        return table.toString();
    }

    public void report()
    {
        System.out.println("The number of slots is: " + table.numSlots());
        System.out.println("The number of entries is: " + table.numEntries());
        System.out.println("The % full is: " + (((float)table.numEntries()/(float)table.numSlots()) * 100));
        System.out.println("The number of collisions is: " + table.numCollisions());
    }

    public void favoriteMove()
    {
        int mostSeen = 0;
        int index = 0;
        for(int i = 0; i < table.numSlots(); i++)
        {
            if(table.get(i) != null && table.get(i).timesSeen > mostSeen)
            {
                mostSeen = table.get(i).timesSeen;
                index = i;
            }
        }

        System.out.println("My favorite first move is: \n" + table.get(index).boardStr);
        float winPerc = (float)table.get(index).wins/table.get(index).timesSeen;
        System.out.println("Won " + table.get(index).wins + " of " + table.get(index).timesSeen + " which is " + winPerc);
    }

    private class HashEntry
    {
        int timesSeen;
        int wins;
        int losses;
        int row;
        int column;
        String boardStr;

        HashEntry()
        {
            timesSeen = 0;
            wins = 0;
            losses = 0;
            row = 0;
            column = 0;
            boardStr = "";
        }

        HashEntry(int timesSeen, int wins, int losses, int row, int column, String boardStr)
        {
            this.timesSeen = timesSeen;
            this.wins = wins;
            this.losses = losses;
            this.row = row;
            this.column = column;
            this.boardStr = boardStr;
        }

        void incrementWins()
        {
            wins++;
        }

        void incrementLosses()
        {
            losses++;
        }

        void incrementTimesSeen()
        {
            timesSeen++;
        }
    }
}
