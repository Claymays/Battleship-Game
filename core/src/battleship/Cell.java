package battleship;

import com.badlogic.gdx.math.Rectangle;

public class Cell {
    public int row;
    public int col;
    CellStatus status;
    GameStatus shipName;
    public Rectangle bounds;
    public final static float size = 40;



    public Cell(int row, int col, CellStatus status, GameStatus ship) {
        this.row = row;
        this.col = col;
        this.status = status;
        this.shipName = ship;
        this.bounds = new Rectangle(col * size, row * size, size, size);
    }

    boolean isSmallerThan(Cell cell) {
        if (this.row == cell.row) {
            return this.col < cell.col;
        } else {
            return this.row < cell.row;
        }
    }
}
