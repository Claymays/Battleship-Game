package battleship;

public class BattleShip {

    public Board2 board = new Board2();
    private GameStatus status = GameStatus.STARTED;

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }


    public void showMessage(GameStatus status) {
        String message = "";
        switch (status) {
            case AIRCRAFT:
                message = "Place the Aircraft Carrier (5 cells):\n";
                break;
            case BATTLESHIP:
                message = "Place the Battleship (4 cells):\n";
                break;
            case SUBMARINE:
                message = "Place the Submarine (3 cells):\n";
                break;
            case CRUISER:
                message = "Place the Cruiser (3 cells):\n";
                break;
            case DESTROYER:
                message = "Place the Destroyer (2 cells):\n";
                break;
            default:
        }
        System.out.println(message);
    }

    public int getSizeOfShip(GameStatus status) {
        int size = 0;
        switch (status) {
            case AIRCRAFT:
                size = 5;
                break;
            case BATTLESHIP:
                size = 4;
                break;
            case SUBMARINE:
            case CRUISER:
                size = 3;
                break;
            case DESTROYER:
                size = 2;
                break;
            default:
        }

        return size;
    }

    boolean checkLocation(Cell start, Cell end) {
        return start.row == end.row || start.col == end.col;
    }

    boolean checkShipLength(int size, Cell start, Cell end) {
        return Math.abs(start.row - end.row) + 1 == size || Math.abs(start.col - end.col) + 1 == size;
    }

    boolean checkNear(Cell start, Cell end) {
        if (start.row == end.row) {
            for (int i = start.col; i <= end.col; i++) {
                Cell upperRowCell = board.getCell(start.row - 1, i);
                Cell lowerRowCell = board.getCell(start.row + 1, i);
                Cell curRowCell = board.getCell(start.row, i);
                if (upperRowCell != null && upperRowCell.status.label == 'O') {
                    return false;
                }
                if (lowerRowCell != null && lowerRowCell.status.label == 'O') {
                    return false;
                }
                if (curRowCell != null && curRowCell.status.label == 'O') {
                    return false;
                }
            }
            Cell leftColCell = board.getCell(start.row, start.col - 1);
            Cell rightColCell = board.getCell(start.row, end.col + 1);
            if (leftColCell != null && leftColCell.status.label == 'O') {
                return false;
            }
            return rightColCell == null || rightColCell.status.label != 'O';
        } else {
            for (int i = start.row; i <= end.row; i++) {
                Cell leftColCell = board.getCell(i, start.col - 1);
                Cell rightColCell = board.getCell(i, start.col + 1);
                Cell curColCell = board.getCell(i, start.col);
                if (leftColCell != null && leftColCell.status.label == 'O') {
                    return false;
                }
                if (rightColCell != null && rightColCell.status.label == 'O') {
                    return false;
                }
                if (curColCell != null && curColCell.status.label == 'O') {
                    return false;
                }
            }
            Cell upperRowCell = board.getCell(start.row - 1, start.col);
            Cell lowerRowCell = board.getCell(end.row + 1, start.col);
            if (upperRowCell != null && upperRowCell.status.label == 'O') {
                return false;
            }
            return lowerRowCell == null || lowerRowCell.status.label != 'O';
        }
    }

    public boolean setShip(Cell start, Cell end, GameStatus status) {
        if (start.row == end.row) {
            for (int i = start.col; i <= end.col; i++) {
                board.setCell(CellStatus.SHIP, start.row, i, status);
            }
        } else {
            for (int i = start.row; i <= end.row; i++) {
                board.setCell(CellStatus.SHIP, i, start.col, status);
            }
        }

        return true;
    }

    public boolean setBoard(GameStatus status, String startCell, String endCell) {
        int size = getSizeOfShip(status);
        Cell start = board.getCell(startCell);
        Cell end = board.getCell(endCell);

        if (!start.isSmallerThan(end)) {
            Cell tmp = start;
            start = end;
            end = tmp;
        }

        if (start == null || end == null) {
            System.out.println("Error! Invalid Cell");
            return false;
        }

        if (!checkLocation(start, end)) {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        }

        if (!checkShipLength(size, start, end)) {
            System.out.println("Error! Wrong length of the " + status + "! Try again:");
            return false;
        }

        if (!checkNear(start, end)) {
            System.out.println("Error! You placed it too close to another one. Try again:");
            return false;
        }

        if(!setShip(start, end, status)) {
            System.out.println("Error! Try again:");
            return false;
        }

        return true;
    }
}