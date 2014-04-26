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
        stateCount = player - 1;
    }

    public void endGame(TicTacToe finalBoard)
    {
        HashEntry entry;
        if(table.containsKey(finalBoard.hashCode()))
        {
            if(finalBoard.getWinner() == player)
            {
                for(int i : gameStates)
                {
                    if(table.containsKey(i))
                    {
                        entry = table.get(i);
                        entry.incrementWins();
                        entry.incrementTimesSeen();
                        table.put(i, entry);
                    }
                    else
                    {
                        entry = new HashEntry();
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
                    else
                    {
                        entry = new HashEntry();
                        table.put(i, entry);
                    }
                }
            }
        }
    }

    public TicTacToe[] getSuccessors(TicTacToe t)
    {

        return new TicTacToe[1];
    }

    public void move(TicTacToe t)
    {
        int row = rand.nextInt(3);
        int column = rand.nextInt(3);
        t.setPlayerMark(marker, player);

        while(t.getTurn() == player)
        {
            if(table.containsKey(t.hashCode()))
            {
                HashEntry move = table.get(t.hashCode());
                if(move.wins > move.losses)
                {
                    getSuccessors(t);
                    t.move(move.row, move.column);
                }
                else
                {
                    t.move(row, column);
                }
            }
            else
            {
                if(t.move(row, column))
                {
                    table.put(t.hashCode(), new HashEntry(t.hashCode(), 0, 0, row, column));
                }
                else
                {
                    move(t);
                }
            }
        }
        stateCount += 2;
    }

    public void newGame(int player)
    {
        this.player = player;
        stateCount = player - 1;
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

    private class HashEntry
    {
        int timesSeen;
        int wins;
        int losses;
        int row;
        int column;

        HashEntry()
        {
            timesSeen = 0;
            wins = 0;
            losses = 0;
            row = 0;
            column = 0;
        }

        HashEntry(int timesSeen, int wins, int losses, int row, int column)
        {
            this.timesSeen = timesSeen;
            this.wins = wins;
            this.losses = losses;
            this.row = row;
            this.column = column;
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
