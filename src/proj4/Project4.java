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

    public static void main(String[] args)
    {
        Project4 test = new Project4();

        RandomAI p1 = new RandomAI('O', 1);
        SmartPlayer p2 = new SmartPlayer('X', 2);

        test.play(p1, p2, 10);
    }

    public static void play(RandomAI p1, SmartPlayer p2, int numGames)
    {
        for(int i = 0; i < numGames; i++)
        {
            System.out.println("New Game!");
            TicTacToe game = new TicTacToe();
            p2.newGame(2);

            for(int j=0; j < 9; j++)
            {
                if(game.getTurn() == 1)
                    p1.move(game);
                else if (game.getTurn() == 2)
                    p2.move(game);

                if(game.getWinner() != -1 && j > 3)
                {
//                    game.printBoard();
                    System.out.println("Player " + game.getWinner() + " Wins!");
                    p2.endGame(game);
                    System.out.println("-------");
                    break;
                }
                else
                {
//                    game.printBoard();
                }
            }
        }
        //p2.toString();
    }
}
