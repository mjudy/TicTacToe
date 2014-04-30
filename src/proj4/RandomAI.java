package proj4;

import java.util.Random;

/**
 * An AI that acts randomly. If it can, it will make some legal move on the board it is given.
 *
 * @author Mark Judy
 * @version 1.0 Date: 4/23/14 Time: 8:54 PM
 */
public class RandomAI
{
    private char marker;
    private int player;
    private Random rand;

    /**
     * Default constructor for the RandomAI.
     */
    public RandomAI()
    {
        marker = 'z';
        player = 0;
        rand = new Random(new Random().nextLong());
    }

    /**
     * Constructor for the random AI that allows the user to set a mark and whether the player will go first or second.
     *
     * @param mark The character that the random ai will use to mark its moves.
     * @param player The player number that this AI will play. Can be 1 or 2.
     */
    public RandomAI(char mark, int player)
    {
        this.player = player;
        rand = new Random(new Random().nextLong());
        marker = mark;
    }

    /**
     * Attempts to make a random legal move on the tic tac toe board it is given.
     *
     * @param t the board to make a move on
     */
    public void move(TicTacToe t)
    {
        int row = rand.nextInt(3);
        int column = rand.nextInt(3);

        t.setPlayerMark(marker, player);
        while(t.getTurn() == player)
        {
            if(t.move(row, column))
            {
            }
            else
            {
                move(t);
            }
        }
    }
}
