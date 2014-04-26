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
    private int turn;

    public TicTacToe()
    {
        board = new char[ROWS][COLUMNS];
        turn = 1;
    }

    public TicTacToe(String boardStr)
    {
        boardStr = boardStr.replaceAll("\n", "");
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                board[i][j] = boardStr.charAt((i+j)%2);
            }
        }
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
        if(checkWin(player1))
        {
            return 1;
        }
        else if(checkWin(player2))
        {
            return 2;
        }
        else
            return -1;
    }

    public int hashCode()
    {
        int hash = 0;
        int count = 1;
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                if(board[i][j] != 0)
                {
                    hash += board[i][j] + i + j;
                }
            }
        }
        hash = (int)((hash*hash) * 1.618);
        return hash;
    }

    public boolean move(int row, int column)
    {
        if(board[row][column] == 0)
        {
            if(turn == 1)
            {
                board[row][column] = player1;
                turn = 2;
            }
            else if (turn == 2)
            {
                board[row][column] = player2;
                turn = 1;
            }
            return true;
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

    public int getTurn()
    {
        return turn;
    }

    public void printBoard()
    {
        System.out.println("-------");
        System.out.print(toString());
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
