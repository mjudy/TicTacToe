package proj4;

/**
 * Main class for the tic tac toe project.
 *
 * @author Mark Judy
 * @version 1.0 Date: 4/23/14 Time: 8:54 PM
 */
public class Project4
{
    /**
     * Main method to run the program.
     *
     * @param args argument list
     */
    public static void main(String[] args)
    {
        Project4 test = new Project4();

        RandomAI p1 = new RandomAI('O', 1);
        SmartPlayer p2 = new SmartPlayer('X', 2);

        test.play(p1, p2, 1000);
    }

    /**
     * Plays the tic tac toe game with the given players a given number of times.
     *
     * @param p1 the starting player in the tic tac toe game
     * @param p2 the second player in the tic tac toe game
     * @param numGames the number of games to be played
     */
    public static void play(RandomAI p1, SmartPlayer p2, int numGames)
    {
        int p1Wins = 0;
        int p2Wins = 0;
        for(int i = 0; i < numGames; i++)
        {
            TicTacToe game = new TicTacToe();
            p2.newGame(2);

            for(int j=0; j < 9; j++)
            {
                if(game.getTurn() == 1)
                    p1.move(game);
                else if (game.getTurn() == 2)
                    p2.move(game);

                if(game.isOver() != -1 && j > 3)
                {

                    if(game.isOver() == 1)
                    {
                        p1Wins++;
                    }
                    else if(game.isOver() == 2)
                    {
                        p2Wins++;
                    }

                    break;
                }
            }
            p2.endGame(game);
        }

        System.out.println("FINAL REPORT:\n");
        p2.report();
        float winPerc = ((float)p1Wins/numGames) * 100;
        System.out.println("The Random Player has won " + p1Wins + " times which is " + winPerc + " percent.");
        winPerc = ((float)p2Wins/numGames) * 100;
        System.out.println("The Smart Player has won " + p2Wins + " times which is " + winPerc + " percent.");
        p2.favoriteMove();
    }
}
