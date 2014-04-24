package proj4;

import java.util.Random;

/**
 * @author theghv
 * @version 1.0 Date: 4/23/14 Time: 8:54 PM
 */
public class RandomAI
{
    char marker;
    int player;

    public RandomAI()
    {
        marker = 'z';
        player = 0;
    }

    public RandomAI(char mark, int player)
    {
        marker = mark;
        this.player = player;
    }

    public void move(TicTacToe t)
    {
        Random r = new Random(player);
        int row = r.nextInt(3);
        int column = r.nextInt(3);

        t.setPlayerMark(marker, player);
        t.move(row, column);
    }
}
