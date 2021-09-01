package battleship;

public class BattleShip {
    public Board2 board = new Board2();
    public GameStatus status = GameStatus.AIRCRAFT;

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }



    public int getSizeOfShip(GameStatus status) {
        int size = 0;
        switch (status) {
            case AIRCRAFT:
            case HO_AIRCRAFT:
                size = 5;
                break;
            case BATTLESHIP:
            case HO_BATTLESHIP:
                size = 4;
                break;
            case SUBMARINE:
            case CRUISER:
            case HO_CRUISER:
            case HO_SUBMARINE:
                size = 3;
                break;
            case DESTROYER:
            case HO_DESTROYER:
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

    public boolean checkNear(Cell start, Cell end) {
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

    public boolean setBoard(GameStatus status, Cell startCell, Cell endCell) {
        int size = getSizeOfShip(status);
        Cell start = startCell;
        Cell end = endCell;

        if (!start.isSmallerThan(end)) {
            Cell tmp = start;
            start = end;
            end = tmp;
        }

        if (start == null || end == null) {
            return false;
        }

        if (!checkLocation(start, end)) {
            return false;
        }

        if (!checkShipLength(size, start, end)) {
            return false;
        }

        if (!checkNear(start, end)) {
            return false;
        }

        if(!setShip(start, end, status)) {
            return false;
        }

        return true;
    }
}