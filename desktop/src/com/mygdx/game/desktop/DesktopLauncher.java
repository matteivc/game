
package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.title = "ASTEROIDS";
                config.width = 600;
                config.height = 400;
                config.useGL30 = false;
                config.resizable = false;
                new LwjglApplication(new MyGdxGame(), config);
	}
}
