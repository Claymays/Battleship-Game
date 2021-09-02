package com.badlogic.drop;

import battleship.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

import static battleship.GameStatus.*;


public class ShipGraphics extends ApplicationAdapter {

	private SpriteBatch batch;
	private OrthographicCamera camera;
	public TextureRegion cruiserImage;
	public TextureRegion submarine;
	public TextureRegion carrier;
	public TextureRegion destroyer;
	public TextureRegion battleShip;
	public TextureRegion cellCube;
	public TextureRegion hoCruiser;
	public TextureRegion hoSubmarine;
	public TextureRegion hoCarrier;
	public TextureRegion hoDestroyer;
	public TextureRegion hoBattleShip;

	public Rectangle carrierPosition;
	public Rectangle hoCarrierPosition;
	public Rectangle destroyerPosition;
	public Rectangle hoDestroyerPosition;
	public Rectangle battleshipPosition;
	public Rectangle hoBattleshipPosition;
	public Rectangle submarinePosition;
	public Rectangle hoSubmarinePosition;
	public Rectangle cruiserPosition;
	public Rectangle hoCruiserPosition;


	Vector3 mousePos = new Vector3();


	BattleShip playerBoard = new BattleShip();
	BattleShip AI_board;
	BattleShip fogBoard1 = new BattleShip();
	Random random = new Random();
	Cell lastHit = playerBoard.board.getCell(0, 0);

	int playerScore = 0;
	int oppScore = 0;
	boolean vert;

	@Override
	public void create() {
		// load the images for the droplet and the bucket, 64x64 pixels each
		cruiserImage = new TextureRegion(new Texture(Gdx.files.internal("ShipCruiserHull.png")));
		battleShip = new TextureRegion(new Texture(Gdx.files.internal("ShipBattleshipHull.png")));
		submarine = new TextureRegion(new Texture(Gdx.files.internal("ShipSubMarineHull.png")));
		carrier = new TextureRegion(new Texture(Gdx.files.internal("ShipCarrierHull.png")));
		destroyer = new TextureRegion(new Texture(Gdx.files.internal("ShipDestroyerHull.png")));
		cellCube = new TextureRegion(new Texture(Gdx.files.internal("CellCube.png")));
		hoCruiser = new TextureRegion(new Texture(Gdx.files.internal("ShipCruiserHullHorizontal.png")));
		hoBattleShip = new TextureRegion(new Texture(Gdx.files.internal("ShipBattleshipHullHorizontal.png")));
		hoSubmarine = new TextureRegion(new Texture(Gdx.files.internal("ShipSubMarineHullHorizontal.png")));
		hoCarrier = new TextureRegion(new Texture(Gdx.files.internal("ShipCarrierHullHorizontal.png")));
		hoDestroyer = new TextureRegion(new Texture(Gdx.files.internal("ShipDestroyerHullHorizontal.png")));
		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 400, 800);
		batch = new SpriteBatch();
		AI_board = new BattleShip();
		for (int i = 0; i < 100; i++) {
			int x = i / 10;
			int y = i % 10;
			Cell cell = AI_board.board.getCell(x, y);
			Cell cellClone = fogBoard1.board.getCell(x, y);
			cell.bounds.y += 400;
			cellClone.bounds.y += 400;
		}

		lastHit.bounds.y = 0;
		lastHit.bounds.x = 0;


	}

	public void AI_set() {
		boolean rowOrCol = random.nextBoolean();
		int xORy = random.nextInt(10);
		int secondCoordinate = random.nextInt(10);
		while (secondCoordinate + AI_board.getSizeOfShip(AI_board.getStatus()) - 1 > 9) {
			secondCoordinate--;
		}

		if (rowOrCol) {
			if (AI_board.setBoard(AI_board.getStatus(), AI_board.board.getCell(xORy, secondCoordinate), AI_board.board.getCell(xORy, secondCoordinate + AI_board.getSizeOfShip(AI_board.getStatus()) - 1))) {
				switch (AI_board.status) {
					case AIRCRAFT:
						AI_board.setStatus(BATTLESHIP);
						break;
					case BATTLESHIP:
						AI_board.setStatus(CRUISER);
						break;
					case CRUISER:
						AI_board.setStatus(SUBMARINE);
						break;
					case SUBMARINE:
						AI_board.setStatus(DESTROYER);
						break;
					case DESTROYER:
						AI_board.setStatus(SHOOT);
						playerBoard.setStatus(SHOOT);
						break;
				}
			}
		} else {
			if (AI_board.setBoard(AI_board.getStatus(), AI_board.board.getCell(secondCoordinate, xORy), AI_board.board.getCell(secondCoordinate + AI_board.getSizeOfShip(AI_board.getStatus()) - 1, xORy))) {
				switch (AI_board.status) {
					case AIRCRAFT:
						AI_board.setStatus(BATTLESHIP);
						break;
					case BATTLESHIP:
						AI_board.setStatus(CRUISER);
						break;
					case CRUISER:
						AI_board.setStatus(SUBMARINE);
						break;
					case SUBMARINE:
						AI_board.setStatus(DESTROYER);
						break;
					case DESTROYER:
						AI_board.setStatus(SHOOT);
						playerBoard.setStatus(SHOOT);
						break;
				}
			}
		}
	}

	public void AI_shoot() {
		if (lastHit.row == 0 && lastHit.col == 0) {
			int x = random.nextInt(10);
			int y = random.nextInt(10);
			if (playerBoard.board.getCell(x, y).status != CellStatus.MISS && playerBoard.board.getCell(x, y).status != CellStatus.HIT) {
				Cell cell = playerBoard.board.getCell(x, y);
				switch (cell.status) {
					case FOG:
						cell.status = CellStatus.MISS;
						playerBoard.setStatus(SHOOT);
						break;
					case MISS:
					case HIT:
						playerBoard.setStatus(SHOOT);
						break;
					case SHIP:
						cell.status = CellStatus.HIT;
						oppScore++;
						playerBoard.setStatus(SHOOT);
						lastHit.row = cell.row;
						lastHit.col = cell.col;
						break;
				}
			}
		} else {
			Cell cell;
			if (lastHit.col < 9 && playerBoard.board.getCell(lastHit.row, lastHit.col + 1).status != CellStatus.MISS && playerBoard.board.getCell(lastHit.row, lastHit.col + 1).status != CellStatus.HIT && !vert) {
				cell = playerBoard.board.getCell(lastHit.row, lastHit.col + 1);
				switch (cell.status) {
					case FOG:
						cell.status = CellStatus.MISS;
						playerBoard.setStatus(SHOOT);
						break;
					case SHIP:
						cell.status = CellStatus.HIT;
						oppScore++;
						playerBoard.setStatus(SHOOT);
						lastHit.row = cell.row;
						lastHit.col = cell.col;
						vert = false;
						break;
				}
			} else if (lastHit.row < 9 && playerBoard.board.getCell(lastHit.row + 1, lastHit.col).status != CellStatus.MISS && playerBoard.board.getCell(lastHit.row + 1, lastHit.col).status != CellStatus.HIT) {
				cell = playerBoard.board.getCell(lastHit.row + 1, lastHit.col);
				switch (cell.status) {
					case FOG:
						cell.status = CellStatus.MISS;
						playerBoard.setStatus(SHOOT);
						break;
					case SHIP:
						cell.status = CellStatus.HIT;
						oppScore++;
						playerBoard.setStatus(SHOOT);
						lastHit.row = cell.row;
						lastHit.col = cell.col;
						vert = true;
						break;
				}
			} else if (lastHit.row > 0 && playerBoard.board.getCell(lastHit.row - 1, lastHit.col).status != CellStatus.MISS && playerBoard.board.getCell(lastHit.row - 1, lastHit.col).status != CellStatus.HIT) {
				cell = playerBoard.board.getCell(lastHit.row - 1, lastHit.col);
				switch (cell.status) {
					case FOG:
						cell.status = CellStatus.MISS;
						playerBoard.setStatus(SHOOT);
						break;
					case SHIP:
						cell.status = CellStatus.HIT;
						oppScore++;
						playerBoard.setStatus(SHOOT);
						lastHit.row = cell.row;
						lastHit.col = cell.col;
						vert = false;
						break;
				}
			}
			else if (lastHit.col > 0 && playerBoard.board.getCell(lastHit.row, lastHit.col - 1).status != CellStatus.MISS && playerBoard.board.getCell(lastHit.row, lastHit.col - 1).status != CellStatus.HIT && !vert) {
				cell = playerBoard.board.getCell(lastHit.row, lastHit.col - 1);
				switch (cell.status) {
					case FOG:
						cell.status = CellStatus.MISS;
						playerBoard.setStatus(SHOOT);
						break;
					case SHIP:
						cell.status = CellStatus.HIT;
						oppScore++;
						playerBoard.setStatus(SHOOT);
						lastHit.row = cell.row;
						lastHit.col = cell.col;
						vert = true;
						break;
				}
			}
			else {
				lastHit.row = 0;
				lastHit.col = 0;
				vert = false;
			}

		}
	}

	public void playerAction() {
		if (playerBoard.getStatus() != SHOOT) {
			for (int x = 0; x < playerBoard.board.cells.length; x++) {
				for (int y = 0; y < playerBoard.board.cells.length; y++) {
					Cell cell = playerBoard.board.getCell(y, x);
					if (cell.bounds.contains(mousePos.x, mousePos.y)) {
						switch (playerBoard.getStatus()) {
							case STARTED:
								playerBoard.setStatus(AIRCRAFT);
								break;
							case AIRCRAFT:
								if (playerBoard.setBoard(AIRCRAFT, playerBoard.board.getCell(y - 2, x), playerBoard.board.getCell(y + 2, x))) {
									playerBoard.setStatus(BATTLESHIP);
									carrierPosition = cell.bounds;
								}
								break;
							case HO_AIRCRAFT:
								if (playerBoard.setBoard(HO_AIRCRAFT, playerBoard.board.getCell(y, x - 2), playerBoard.board.getCell(y, x + 2))) {
									playerBoard.setStatus(BATTLESHIP);
									hoCarrierPosition = cell.bounds;
								}
								break;
							case BATTLESHIP:
								if (playerBoard.setBoard(BATTLESHIP, playerBoard.board.getCell(y - 1, x), playerBoard.board.getCell(y + 2, x))) {
									playerBoard.setStatus(CRUISER);
									battleshipPosition = cell.bounds;
								}
								break;
							case HO_BATTLESHIP:
								if (playerBoard.setBoard(HO_BATTLESHIP, playerBoard.board.getCell(y, x - 1), playerBoard.board.getCell(y, x + 2))) {
									playerBoard.setStatus(CRUISER);
									hoBattleshipPosition = cell.bounds;
								}
								break;
							case CRUISER:
								if (playerBoard.setBoard(CRUISER, playerBoard.board.getCell(y - 1, x), playerBoard.board.getCell(y + 1, x))) {
									playerBoard.setStatus(SUBMARINE);
									cruiserPosition = cell.bounds;
								}
								break;
							case HO_CRUISER:
								if (playerBoard.setBoard(HO_CRUISER, playerBoard.board.getCell(y, x - 1), playerBoard.board.getCell(y, x + 1))) {
									playerBoard.setStatus(SUBMARINE);
									hoCruiserPosition = cell.bounds;
								}
								break;
							case SUBMARINE:
								if (playerBoard.setBoard(SUBMARINE, playerBoard.board.getCell(y - 1, x), playerBoard.board.getCell(y + 1, x))) {
									playerBoard.setStatus(DESTROYER);
									submarinePosition = cell.bounds;
								}
								break;
							case HO_SUBMARINE:
								if (playerBoard.setBoard(HO_SUBMARINE, playerBoard.board.getCell(y, x - 1), playerBoard.board.getCell(y, x + 1))) {
									playerBoard.setStatus(DESTROYER);
									hoSubmarinePosition = cell.bounds;
								}
								break;
							case DESTROYER:
								if (playerBoard.setBoard(DESTROYER, playerBoard.board.getCell(y, x), playerBoard.board.getCell(y + 1, x))) {
									playerBoard.setStatus(AI_SET);
									destroyerPosition = cell.bounds;
								}
								break;
							case HO_DESTROYER:
								if (playerBoard.setBoard(HO_DESTROYER, playerBoard.board.getCell(y, x), playerBoard.board.getCell(y, x + 1))) {
									playerBoard.setStatus(AI_SET);
									hoDestroyerPosition = cell.bounds;
								}
								break;
						}
					}
				}
			}
		} else {
			// playerBoard.status is SHOOT
			for (int x = 0; x < AI_board.board.cells.length; x++) {
				for (int y = 0; y < AI_board.board.cells.length; y++) {
					Cell cell = AI_board.board.getCell(y, x);
					if (cell.bounds.contains(mousePos.x, mousePos.y)) {
						Cell cellClone = fogBoard1.board.getCell(y, x);
						switch (cell.status) {
							case FOG:
								cell.status = CellStatus.MISS;
								cellClone.status = CellStatus.MISS;
								break;
							case SHIP:
								cell.status = CellStatus.HIT;
								cellClone.status = CellStatus.HIT;
								playerScore++;
								break;
						}
					}
				}
			}
			playerBoard.setStatus(AI_SHOOT);
		}
	}

	public void flipShip() {
		switch (playerBoard.getStatus()) {
			case HO_AIRCRAFT:
				playerBoard.setStatus(GameStatus.AIRCRAFT);
				break;
			case AIRCRAFT:
				playerBoard.setStatus(GameStatus.HO_AIRCRAFT);
				break;
			case HO_BATTLESHIP:
				playerBoard.setStatus(BATTLESHIP);
				break;
			case BATTLESHIP:
				playerBoard.setStatus(HO_BATTLESHIP);
				break;
			case HO_CRUISER:
				playerBoard.setStatus(CRUISER);
				break;
			case CRUISER:
				playerBoard.setStatus(HO_CRUISER);
				break;
			case HO_SUBMARINE:
				playerBoard.setStatus(SUBMARINE);
				break;
			case SUBMARINE:
				playerBoard.setStatus(HO_SUBMARINE);
				break;
			case DESTROYER:
				playerBoard.setStatus(HO_DESTROYER);
				break;
			case HO_DESTROYER:
				playerBoard.setStatus(DESTROYER);
				break;
		}
	}

	public void drawPlayer() {
		for (int x = 0; x < playerBoard.board.cells.length; x++) {
			for (int y = 0; y < playerBoard.board.cells[0].length; y++) {
				Cell cell = playerBoard.board.getCell(y, x);
				switch (cell.status) {
					case FOG:  batch.setColor(Color.GRAY); break;
					case SHIP: batch.setColor(Color.GOLD); break;
					case HIT: batch.setColor(Color.RED); break;
					case MISS: batch.setColor(Color.BROWN); break;
				}
				if (cell.bounds.contains(mousePos.x, mousePos.y)) {
					batch.setColor(0, 0, 1, 0.1f);
				}
				batch.draw(cellCube, cell.bounds.x, cell.bounds.y, cell.bounds.width, cell.bounds.height);
				batch.setColor(Color.WHITE);
			}
		}
	}

	public void drawFog() {
		for (int x = 0; x < fogBoard1.board.cells.length; x++) {
			for (int y = 0; y < fogBoard1.board.cells[0].length; y++) {
				Cell cell = fogBoard1.board.getCell(y, x);
				switch (cell.status) {
					case FOG:  batch.setColor(Color.GRAY); break;
					case SHIP: batch.setColor(Color.GOLD); break;
					case HIT: batch.setColor(Color.RED); break;
					case MISS: batch.setColor(Color.BROWN); break;
				}
				if (cell.bounds.contains(mousePos.x, mousePos.y)) {
					batch.setColor(0, 0, 1, 0.1f);
				}
				batch.draw(cellCube, cell.bounds.x, cell.bounds.y, cell.bounds.width, cell.bounds.height);
				batch.setColor(Color.WHITE);
			}
		}
	}

	public void update() {

		// process user input
		mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(mousePos);

		if (playerScore == 17) {
			Gdx.app.log("winning message", "You win!");
			System.exit(0);
		} else if (oppScore == 17) {
			Gdx.app.log("losing message", "You lost!");
			System.exit(0);
		}

		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			playerAction();
		}

		if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
			flipShip();
		}

		while (playerBoard.getStatus() == AI_SET) {
			AI_set();
		}
		while (playerBoard.getStatus() == AI_SHOOT) {
			AI_shoot();
			camera.update();
		}
	}
	@Override
	public void render() {
		update();
		// clear the screen with a dark blue color. The
		// arguments to clear are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		ScreenUtils.clear(0.1f, 0, 0, 1);


		// tell the camera to update its matrices.


		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		batch.setProjectionMatrix(camera.combined);

		// begin a new batch and draw the bucket and
		// all drops
		batch.begin();

		drawPlayer();
		drawFog();

		switch (playerBoard.getStatus()) {
			case AIRCRAFT: batch.draw(carrier, mousePos.x - 20, mousePos.y - 99, 36, 198 ); break;
			case HO_AIRCRAFT: batch.draw(hoCarrier, mousePos.x - 99, mousePos.y - 20, 198, 36); break;
			case SUBMARINE: batch.draw(submarine, mousePos.x - 20, mousePos.y - 79, 36, 118); break;
			case HO_SUBMARINE: batch.draw(hoSubmarine, mousePos.x - 59, mousePos.y - 20, 118, 36); break;
			case CRUISER: batch.draw(cruiserImage, mousePos.x - 20, mousePos.y - 59, 36, 118); break;
			case HO_CRUISER: batch.draw(hoCruiser, mousePos.x - 59, mousePos.y - 20, 118, 36); break;
			case BATTLESHIP: batch.draw(battleShip, mousePos.x - 20, mousePos.y -59, 38, 158); break;
			case HO_BATTLESHIP: batch.draw(hoBattleShip, mousePos.x - 59, mousePos.y - 20, 158, 38); break;
			case DESTROYER: batch.draw(destroyer, mousePos.x - 20, mousePos.y - 20, 38, 78); break;
			case HO_DESTROYER: batch.draw(hoDestroyer, mousePos.x - 20, mousePos.y - 20, 78, 38); break;
		}

		if (carrierPosition != null) {batch.draw(carrier, carrierPosition.x + 2, carrierPosition.y - 80, 36, 198); }
		if (hoCarrierPosition != null) {batch.draw(hoCarrier,hoCarrierPosition.x - 80, hoCarrierPosition.y + 2, 198, 36); }
		if (battleshipPosition != null) {batch.draw(battleShip, battleshipPosition.x + 2, battleshipPosition.y - 40, 36, 156); }
		if (hoBattleshipPosition != null) {batch.draw(hoBattleShip, hoBattleshipPosition.x - 40, hoBattleshipPosition.y + 2, 156, 36);}
		if (submarinePosition != null) {batch.draw(submarine, submarinePosition.x + 2, submarinePosition.y - 40, 36, 116);}
		if (hoSubmarinePosition != null) {batch.draw(hoSubmarine, hoSubmarinePosition.x - 40, hoSubmarinePosition.y + 2, 116, 36);}
		if (cruiserPosition != null) {batch.draw(cruiserImage, cruiserPosition.x + 2, cruiserPosition.y - 40, 36, 116);}
		if (hoCruiserPosition != null) {batch.draw(hoCruiser, hoCruiserPosition.x - 40, hoCruiserPosition.y + 2, 116, 36);}
		if (destroyerPosition != null) {batch.draw(destroyer, destroyerPosition.x + 2, destroyerPosition.y, 36, 76);}
		if (hoDestroyerPosition != null) {batch.draw(hoDestroyer, hoDestroyerPosition.x, hoDestroyerPosition.y + 2, 76, 36);}

		batch.end();
	}

	@Override
	public void dispose() {
		// dispose of all the native resources
		batch.dispose();
	}
}