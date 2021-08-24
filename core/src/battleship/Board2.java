package battleship;

public class Board2 {
    public Cell[][] board;

    public Board2() {
        board = new Cell[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = new Cell(i, j, CellStatus.FOG, GameStatus.STARTED);
            }
        }
    }

    public void printStatus() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < 10; i++) {
            char rowMark = (char) ('A' + i);
            System.out.print(rowMark + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print(board[i][j].status.label + " ");
            }
            System.out.println(" ");
        }
        System.out.println(" ");
    }

    public Cell getCell(String cell) {
        int row = cell.charAt(0) - 'A';
        int col = Integer.parseInt(cell.substring(1)) - 1;

        return getCell(row, col);
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
