package com.lpoo.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lpoo.game.Spheral;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Spheral - The Game";
        config.height = 720;
        config.width = 1280;
		new LwjglApplication(new Spheral(), config);
	}
}
