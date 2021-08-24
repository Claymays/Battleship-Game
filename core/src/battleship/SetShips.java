package battleship;

import java.util.Scanner;

public class SetShips {
    Scanner s = new Scanner(System.in);

    boolean statusIn = true;
    String[] command = null;

     public void placeShips(BattleShip battleShip) {
        while (!battleShip.getStatus().equals(GameStatus.SHOOT)) {
            switch (battleShip.getStatus()) {
                case STARTED:
                    battleShip.setStatus(GameStatus.AIRCRAFT);
                    break;
                case AIRCRAFT:
                    if (statusIn) {
                        battleShip.showMessage(GameStatus.AIRCRAFT);
                        statusIn = false;
                    }
                    command = s.nextLine().trim().split(" ");
                    if (battleShip.setBoard(GameStatus.AIRCRAFT, command[0], command[1])) {
                        statusIn = true;
                        battleShip.setStatus(GameStatus.BATTLESHIP);

                    }
                    break;
                case BATTLESHIP:
                    if (statusIn) {
                        battleShip.showMessage(GameStatus.BATTLESHIP);
                        statusIn = false;
                    }
                    command = s.nextLine().trim().split(" ");
                    if (battleShip.setBoard(GameStatus.BATTLESHIP, command[0], command[1])) {
                        statusIn = true;
                        battleShip.setStatus(GameStatus.SUBMARINE);

                    }
                    break;
                case SUBMARINE:
                    if (statusIn) {
                        battleShip.showMessage(GameStatus.SUBMARINE);
                        statusIn = false;
                    }
                    command = s.nextLine().trim().split(" ");
                    if (battleShip.setBoard(GameStatus.SUBMARINE, command[0], command[1])) {
                        statusIn = true;
                        battleShip.setStatus(GameStatus.CRUISER);

                    }
                    break;
                case CRUISER:
                    if (statusIn) {
                        battleShip.showMessage(GameStatus.CRUISER);
                        statusIn = false;
                    }
                    command = s.nextLine().trim().split(" ");
                    if (battleShip.setBoard(GameStatus.CRUISER, command[0], command[1])) {
                        statusIn = true;
                        battleShip.setStatus(GameStatus.DESTROYER);

                    }
                    break;
                case DESTROYER:
                    if (statusIn) {
                        battleShip.showMessage(GameStatus.DESTROYER);
                        statusIn = false;
                    }
                    command = s.nextLine().trim().split(" ");
                    if (battleShip.setBoard(GameStatus.DESTROYER, command[0], command[1])) {
                        statusIn = true;
                        battleShip.setStatus(GameStatus.SHOOT);
                    }
                    break;
                default:
            }
        }
    }
}
