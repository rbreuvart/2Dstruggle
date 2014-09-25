package com.rbr.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.rbr.game.entity.physics.GameObject;
import com.rbr.game.net.kryo.NetKryoManageur.NetApplicationType;
import com.rbr.game.net.kryo.packet.PacketUpdateGameObjectPlayer;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigPref;

public class PlayerControle extends Player{

	public PlayerControle(GameObject gameObject) {
		super(gameObject);		
	}

	
	float spesificImpulse;
	public void update(final ScreenGame screenGame ,float delta){
		
		spesificImpulse = 0.5f;
		
		Vector2 vel = getGameObject().getBody().getLinearVelocity();
		Vector2 pos = getGameObject().getBody().getPosition();		
		if (Gdx.app.getType().equals(ApplicationType.Desktop)) {
			
			if (Gdx.input.isKeyPressed(Keys.LEFT) && vel.x > -ConfigPref.Player_MAX_VELOCITY) {          
				getGameObject().getBody().applyLinearImpulse(-spesificImpulse, 0, pos.x, pos.y, true);
			}
	
			if (Gdx.input.isKeyPressed(Keys.RIGHT) && vel.x < ConfigPref.Player_MAX_VELOCITY) {
				getGameObject().getBody().applyLinearImpulse(spesificImpulse, 0, pos.x, pos.y, true);
			}
			
			if (Gdx.input.isKeyPressed(Keys.DOWN) && vel.y > -ConfigPref.Player_MAX_VELOCITY) {          
				getGameObject().getBody().applyLinearImpulse(0, -spesificImpulse, pos.x, pos.y, true);
			}
	
			if (Gdx.input.isKeyPressed(Keys.UP) && vel.y < ConfigPref.Player_MAX_VELOCITY) {
				getGameObject().getBody().applyLinearImpulse(0,spesificImpulse, pos.x, pos.y, true);
			}
		
			getGameObject().deceleration(getGameObject().getRatioDeceleration());
		
		}
		if (Gdx.app.getType().equals(ApplicationType.Android)) {
	
			Vector2 touchpadVec = new Vector2(screenGame.getTouchpad().getKnobPercentX(),screenGame.getTouchpad().getKnobPercentY());
			touchpadVec = touchpadVec.nor().scl(spesificImpulse);
			
			if (vel.y < ConfigPref.Player_MAX_VELOCITY  && vel.y > -ConfigPref.Player_MAX_VELOCITY) {
				getGameObject().getBody().applyLinearImpulse(0,touchpadVec.y, pos.x, pos.y, true);
			}
			
			if (vel.x < ConfigPref.Player_MAX_VELOCITY && vel.x > -ConfigPref.Player_MAX_VELOCITY ) {
				getGameObject().getBody().applyLinearImpulse(touchpadVec.x,0, pos.x, pos.y, true);
			}
			
			getGameObject().deceleration(getGameObject().getRatioDeceleration());
	
		}
	
		getGameObject().getBody().setTransform(pos, 0);
		
		
		//camera follow cette position
		screenGame.getCamManageur().folowTarget(getGameObject().getBody().getPosition());
	}
	
	
	
}
