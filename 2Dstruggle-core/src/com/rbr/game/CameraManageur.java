package com.rbr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigPref;


public class CameraManageur {
	
	MainGame mainGame;
	OrthographicCamera orthographicCamera;

	
	
	public CameraManageur(MainGame mainGame) {
		this.mainGame = mainGame;
		orthographicCamera = new OrthographicCamera(960/ConfigPref.pixelMeter,540/ConfigPref.pixelMeter);
	
	}

	public OrthographicCamera getOrthographicCamera() {		
		return orthographicCamera;
	}
	
	
	boolean flinging = false;
	float velX, velY;
	
	public void update(ScreenGame screenGame, float delta/*boolean flinging,float velX,float velY*/){
		if (flinging) {
			velX *= 0.80f;// vitesse de deceleation de la gesture
			velY *= 0.80f;
			getOrthographicCamera().position.add(
					(-velX * Gdx.graphics.getDeltaTime())/ getOrthographicCamera().zoom,
					( velY * Gdx.graphics.getDeltaTime())/ getOrthographicCamera().zoom, 0);
			if (Math.abs(velX) < 0.1f)
				velX = 0;
			if (Math.abs(velY) < 0.1f)
				velY = 0;
		}
		getOrthographicCamera().update();
		
	}
	
	public boolean fling(float velocityX, float velocityY, int button) {
		flinging = true;
		velX = getOrthographicCamera().zoom * velocityX * 0.5f;
		velY = getOrthographicCamera().zoom * velocityY * 0.5f;
		return false;
	}

	public void folowTarget(Vector2 position) {
		
		getOrthographicCamera().position.set(position, 0);
	}
	
}
