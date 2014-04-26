package proj4;

import java.util.Random;

/**
 * @author theghv
 * @version 1.0 Date: 4/23/14 Time: 8:54 PM
 */
public class RandomAI
{
    private char marker;
    private int player;
    private Random rand;

    public RandomAI()
    {
        marker = 'z';
        player = 0;
        rand = new Random(new Random().nextLong());
    }

    public RandomAI(char mark, int player)
    {
        marker = mark;
        this.player = player;
        rand = new Random(new Random().nextLong());
    }

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
