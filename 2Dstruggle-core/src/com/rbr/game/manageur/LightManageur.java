package com.rbr.game.manageur;

import box2dLight.ChainLight;
import box2dLight.RayHandler;

import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigPref;

public class LightManageur {

	
	
	protected RayHandler rayHandler;
	public RayHandler getRayHandler() {
		return rayHandler;
	}
	public void setRayHandler(RayHandler rayHandler) {
		this.rayHandler = rayHandler;
	}
	
	ChainLight chainLight, chainLight2, chainLight3;
	
	public LightManageur(ScreenGame screenGame) {
		//light
		RayHandler.setGammaCorrection(true);
		RayHandler.useDiffuseLight(true);
		this.rayHandler = new RayHandler(screenGame.getWorldManageur().getWorld());
		rayHandler.setAmbientLight(ConfigPref.CouleurAmbientLight.r	, ConfigPref.CouleurAmbientLight.g	, ConfigPref.CouleurAmbientLight.b, ConfigPref.CouleurAmbientLight.a);
		rayHandler.setCulling(true);	
		rayHandler.setBlurNum(1);
		
	}
	
	
	public void render(ScreenGame screenGame) {
		rayHandler.setCombinedMatrix(screenGame.getCamManageur().getOrthographicCamera().combined);
		rayHandler.updateAndRender();
	}
	
	
}
