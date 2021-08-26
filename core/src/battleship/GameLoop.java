package battleship;

//import java.io.IOException;
//import java.util.Scanner;
//
//public class GameLoop {
//    public static void Run() {
//        // Write your code here
//        Scanner in = new Scanner(System.in);
//        BattleShip battleShip = new BattleShip();
//        BattleShip fogBoard1 = new BattleShip();
//        BattleShip battleShip2 = new BattleShip();
//        BattleShip fogBoard2 = new BattleShip();
//
//        int counter = 0;
//        int carrier = 0;
//        int cruiser = 0;
//        int bShip = 0;
//        int submarine = 0;
//        int destroyer = 0;
//
//        int counter2 = 0;
//        int carrier2 = 0;
//        int cruiser2 = 0;
//        int bShip2 = 0;
//        int submarine2 = 0;
//        int destroyer2 = 0;
//
//
//        Board2 board = battleShip.board;
//        Board2 board2 = battleShip2.board;

//                if (board2.getCell(target).status.equals(CellStatus.FOG)
//                    || board2.getCell(target).status.equals(CellStatus.MISS)
//                    || board2.getCell(target).status.equals(CellStatus.HIT)) {
//                    fogBoard2.board.setCell(CellStatus.MISS, row, col, GameStatus.STARTED);
//                    board2.setCell(CellStatus.MISS, row, col, GameStatus.STARTED);
//                    System.out.println("You missed!");
//                } else if (board2.getCell(target).status.equals(CellStatus.SHIP)) {
//                    fogBoard2.board.setCell(CellStatus.HIT, row, col, board2.getCell(target).shipName);
//                    board2.setCell(CellStatus.HIT, row, col, board2.getCell(target).shipName);
//                    counter++;
//                    switch (board2.getCell(target).shipName) {
//                        case CRUISER:
//                            cruiser++;
//                            break;
//                        case BATTLESHIP:
//                            bShip++;
//                            break;
//                        case AIRCRAFT:
//                            carrier++;
//                            break;
//                        case SUBMARINE:
//                            submarine++;
//                            break;
//                        case DESTROYER:
//                            destroyer++;
//                            break;
//                    }
//                    if (carrier == 5) {
//                        System.out.println("You sank a ship!");
//                        carrier = 0;
//                    } else if (bShip == 4) {
//                        System.out.println("You sank a ship!");
//                        bShip = 0;
//                    } else if (submarine == 3) {
//                        System.out.println("You sank a ship!");
//                        submarine = 0;
//                    } else if (cruiser == 3) {
//                        System.out.println("You sank a ship!");
//                        cruiser = 0;
//                    } else if (destroyer == 2) {
//                        System.out.println("You sank a ship!");
//                        destroyer = 0;
//                    } else {
//                        System.out.println("You hit a ship!");
//                    }
//                }
//                switchPlayer();
//            } else {
//                System.out.println("Player 2, it's your turn:");
//                String target = in.next();
//                int row = target.charAt(0) - 'A';
//                int col = Integer.parseInt(target.substring(1)) - 1;
//                if (row < 0 || row > 9 || col > 9 || col < 0) {
//                    System.out.println("Error! You entered the wrong coordinates! Try again:");
//                    continue;
//                }
//                if (board.getCell(target).status.equals(CellStatus.FOG)
//                    || board.getCell(target).status.equals(CellStatus.HIT)
//                    || board.getCell(target).status.equals(CellStatus.MISS)) {
//                    fogBoard1.board.setCell(CellStatus.MISS, row, col, GameStatus.STARTED);
//                    board.setCell(CellStatus.MISS, row, col, GameStatus.STARTED);
//                    System.out.println("You missed!");
//                } else if (board.getCell(target).status.equals(CellStatus.SHIP)) {
//                    fogBoard1.board.setCell(CellStatus.HIT, row, col, board.getCell(target).shipName);
//                    board.setCell(CellStatus.HIT, row, col, board.getCell(target).shipName);
//                    counter2++;
//                    switch (board.getCell(target).shipName) {
//                        case CRUISER:
//                            cruiser2++;
//                            break;
//                        case BATTLESHIP:
//                            bShip2++;
//                            break;
//                        case AIRCRAFT:
//                            carrier2++;
//                            break;
//                        case SUBMARINE:
//                            submarine2++;
//                            break;
//                        case DESTROYER:
//                            destroyer2++;
//                            break;
//                    }
//                    if (carrier2 == 5) {
//                        System.out.println("You sank a ship!");
//                        carrier2 = 0;
//                    } else if (bShip2 == 4) {
//                        System.out.println("You sank a ship!");
//                        bShip2 = 0;
//                    } else if (submarine2 == 3) {
//                        System.out.println("You sank a ship!");
//                        submarine2 = 0;
//                    } else if (cruiser2 == 3) {
//                        System.out.println("You sank a ship!");
//                        cruiser2 = 0;
//                    } else if (destroyer2 == 2) {
//                        System.out.println("You sank a ship!");
//                        destroyer2 = 0;
//                    } else {
//                        System.out.println("You hit a ship!");
//                    }
//                }
//                switchPlayer();
//            }
//
//
//            if (counter == 17) {
//                System.out.println("You sank the last ship, player 1. You won. Congratulations!");
//                battleShip.setStatus(GameStatus.END);
//            } else if (counter2 == 17) {
//                System.out.println("You sank the last ship, player 2. You won. Congratulations!");
//                battleShip.setStatus(GameStatus.END);
//                }
//
//            }
//        }
//}
//
