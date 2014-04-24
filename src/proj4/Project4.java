package proj4;

/**
 * @author theghv
 * @version 1.0 Date: 4/23/14 Time: 8:54 PM
 */
public class Project4
{
    public Project4()
    {

    }

    public void play()
    {
        RandomAI p1 = new RandomAI('o', 1);
        RandomAI p2 = new RandomAI('x', 2);

        TicTacToe game = new TicTacToe();

        for(int i=0; i < 9; i++)
        {
            if(game.getTurn())
                p1.move(game);
            else
                p2.move(game);

            game.printBoard();
        }
    }
}
