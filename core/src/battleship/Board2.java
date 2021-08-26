package battleship;

public class Board2 {
    public Cell[][] board;
    public GameStatus player = GameStatus.STARTED;
    public Board2() {
        board = new Cell[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = new Cell(i, j, CellStatus.FOG, player);
            }
        }
    }

    public Cell getCell(int row, int col) {
        if (row < 0 || row > 9 || col < 0 || col > 9) {
            return null;
        }

        return board[row][col];
    }

    public boolean setCell(CellStatus status, int row, int col, GameStatus ship) {
        if (row < 0 || row > 9 || col < 0 || col > 9) {
            return false;
        }

        board[row][col].status = status;
        board[row][col].shipName = ship;
        return true;
    }
}
