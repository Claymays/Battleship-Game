package com.badlogic.drop;

import battleship.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Scanner;

import static battleship.GameStatus.AIRCRAFT;



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



	Vector3 mousePos = new Vector3();


	BattleShip playerBoard = new BattleShip();
	BattleShip fogBoard1 = new BattleShip();






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



	}
	public void update() {

		// process user input
		mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(mousePos);

		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			for (int x = 0; x < playerBoard.board.board.length; x++) {
				for (int y = 0; y < playerBoard.board.board.length; y++) {
					Cell cell = playerBoard.board.getCell(y, x);
					if (cell.bounds.contains(mousePos.x, mousePos.y)) {
						if (cell.status == CellStatus.FOG) {
							cell.status = CellStatus.HIT;
						} else if (cell.status == CellStatus.HIT) {
							cell.status = CellStatus.SHIP;
						} else if (cell.status == CellStatus.SHIP) {
							cell.status = CellStatus.MISS;
						} else if (cell.status == CellStatus.MISS) {
							cell.status = CellStatus.FOG;
						}
					}
				}
			}
		}

		if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
			if (playerBoard.getStatus() == GameStatus.HO_AIRCRAFT) {
				playerBoard.setStatus(GameStatus.AIRCRAFT);
			} else {
				playerBoard.setStatus(GameStatus.HO_AIRCRAFT);
			}
		}
		camera.update();
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

		for (int x = 0; x < playerBoard.board.board.length; x++) {
			for (int y = 0; y < playerBoard.board.board[0].length; y++) {
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
		if (playerBoard.getStatus() == GameStatus.HO_AIRCRAFT) {
			batch.draw(hoCarrier, mousePos.x - 99, mousePos.y - 20, 198, 36 );
		} else if (playerBoard.getStatus() == AIRCRAFT) {
			batch.draw(carrier, mousePos.x - 20, mousePos.y - 99, 36, 198);
		}
//		float size = cellCube.getRegionWidth();
//		for (int y = 0; y < 10; y++) {
//			for (int x = 0; x < 10; x++) {
//				batch.draw(destroyer, 100, 100, 0, 0, 20, 100, 1, 1, -90);
//				batch.draw(submarine, 2, 42, 17.5f, 71, 35, 142, 1, 1, -90);
//				batch.draw(cellCube, x * size, y * size);
//			}
//		}
		batch.end();




	}

	@Override
	public void dispose() {
		// dispose of all the native resources
		batch.dispose();
	}
}