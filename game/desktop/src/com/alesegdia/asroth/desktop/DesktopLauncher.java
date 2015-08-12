package com.alesegdia.asroth.desktop;

import com.alesegdia.asroth.game.GameConfig;
import com.alesegdia.asroth.game.GdxGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GameConfig.WINDOW_WIDTH;
		config.height = GameConfig.WINDOW_HEIGHT;
		new LwjglApplication(new GdxGame(), config);
	}
}
