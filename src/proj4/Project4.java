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

        test.play(p1, p2, 1000);
    }

    public static void play(RandomAI p1, SmartPlayer p2, int numGames)
    {
        float p1Wins = 0;
        float p2Wins = 0;
        for(int i = 0; i < numGames; i++)
        {
//            System.out.println("New Game!");
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
//                    System.out.println("Player " + game.getWinner() + " Wins!");
                    p2.endGame(game);

                    if(game.getWinner() == 1)
                        p1Wins++;
                    else if(game.getWinner() == 2)
                        p2Wins++;

                    break;
                }
            }
        }

        System.out.println("FINAL REPORT:\n");
        p2.report();
        float winPerc = (p1Wins/numGames) * 100;
        System.out.println("The Random Player has won " + p1Wins + " times which is " + winPerc + " percent.");
        winPerc = (p2Wins/numGames) * 100;
        System.out.println("The Smart Player has won " + p2Wins + " times which is " + winPerc + " percent.");
        p2.favoriteMove();
    }
}
