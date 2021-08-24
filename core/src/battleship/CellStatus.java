package battleship;

public enum CellStatus {
    FOG('~')
    ,SHIP('O')
    ,HIT('X')
    ,MISS('M');

    public final char label;

    CellStatus(char label) {
        this.label = label;
    }
}
