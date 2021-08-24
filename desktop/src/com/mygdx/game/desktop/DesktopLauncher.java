package com.mygdx.game.desktop;

import battleship.GameLoop;
import com.badlogic.drop.ShipGraphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Battleship";
		config.width = 400;
		config.height = 800;
		new LwjglApplication(new ShipGraphics(), config);
	}
}