package com.rbr.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.rbr.game.Game;

public class DesktopStarter {
	public static void main(String[] args) 
	{
		new LwjglApplication(new Game(), "KYRO EXAMPLE", 800, 480);
	}
}
