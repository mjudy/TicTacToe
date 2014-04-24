package proj4;

/**
 * @author theghv
 * @version 1.0 Date: 4/23/14 Time: 8:54 PM
 */
public class TicTacToe
{
    private static final int ROWS = 3;
    private static final int COLUMNS = 3;

    private char[][] board;
    private char player1;
    private char player2;
    private boolean turn;

    public TicTacToe()
    {
        board = new char[ROWS][COLUMNS];
        turn = true;
    }

    public void setPlayerMark(char mark, int player)
    {
        if(player == 1)
        {
            player1 = mark;
        }
        else if(player == 2)
        {
            player2 = mark;
        }
    }

    public int getWinner()
    {
        return -1;
    }

    public int hashcode()
    {
        return -1;
    }

    public boolean move(int row, int column)
    {
        if(playerAt(row, column) != 0)
        {
            if(turn)
            {
                board[row][column] = player1;
                turn = false;
                return checkWin(player1);
            }
            else
            {
                board[row][column] = player2;
                turn = true;
                return checkWin(player2);
            }
        }
        else
            return false;
    }

    public int playerAt(int row, int column)
    {
        if (board[row][column] == player1)
            return 1;
        else if (board[row][column] == player2)
            return 2;
        else
            return 0;
    }

    private boolean checkWin(char player)
    {
        if(board[0][0] == player && board[0][1] == player && board[0][2] == player)
            return true;
        else if(board[1][0] == player && board[1][1] == player && board[1][2] == player)
            return true;
        else if(board[2][0] == player && board[2][1] == player && board[2][2] == player)
            return true;
        else if(board[0][0] == player && board[1][0] == player && board[2][0] == player)
            return true;
        else if(board[0][1] == player && board[1][1] == player && board[2][1] == player)
            return true;
        else if(board[0][2] == player && board[1][2] == player && board[2][2] == player)
            return true;
        else if(board[0][0] == player && board[1][1] == player && board[2][2] == player)
            return true;
        else if(board[0][2] == player && board[1][1] == player && board[2][0] == player)
            return true;
        else
            return false;
    }

    public boolean getTurn()
    {
        return turn;
    }

    public void printBoard()
    {
        System.out.println("-------");
        System.out.print(toString());
        System.out.println("-------");
    }

    public String toString()
    {
        String str = " ";
        str += board[0][0] + "|" + board[0][1] + "|" + board[0][2] + "\n ";
        str += board[1][0] + "|" + board[1][1] + "|" + board[1][2] + "\n ";
        str += board[2][0] + "|" + board[2][1] + "|" + board[2][2] + "\n";
        return str;
    }
}
