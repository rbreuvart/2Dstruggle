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
			//	move = false;
			// apply left impulse, but only if max velocity is not reached yet
			if (Gdx.input.isKeyPressed(Keys.LEFT) && vel.x > -ConfigPref.Player_MAX_VELOCITY) {          
				getGameObject().getBody().applyLinearImpulse(-spesificImpulse, 0, pos.x, pos.y, true);
				//		move = true;
				//gameObject.getBody().applyForceToCenter(-spesificImpulse, 0, true);
			}
	
			// apply right impulse, but only if max velocity is not reached yet
			if (Gdx.input.isKeyPressed(Keys.RIGHT) && vel.x < ConfigPref.Player_MAX_VELOCITY) {
				getGameObject().getBody().applyLinearImpulse(spesificImpulse, 0, pos.x, pos.y, true);
				//		move = true;
				//gameObject.getBody().applyForceToCenter(spesificImpulse, 0, true);
			}
			if (Gdx.input.isKeyPressed(Keys.DOWN) && vel.y > -ConfigPref.Player_MAX_VELOCITY) {          
				getGameObject().getBody().applyLinearImpulse(0, -spesificImpulse, pos.x, pos.y, true);
				//		move = true;
				//gameObject.getBody().applyForceToCenter(0,-spesificImpulse, true);
			}
	
			// apply right impulse, but only if max velocity is not reached yet
			if (Gdx.input.isKeyPressed(Keys.UP) && vel.y < ConfigPref.Player_MAX_VELOCITY) {
				getGameObject().getBody().applyLinearImpulse(0,spesificImpulse, pos.x, pos.y, true);
				//		move = true;
				//gameObject.getBody().applyForceToCenter(0,spesificImpulse, true);
			}
			//	System.out.println(gameObject.getBody().getLinearVelocity());
			
			
			//	if (!move) {
			//	System.out.println("Player.update() deceleration vel:"+vel);
			getGameObject().deceleration(getGameObject().getRatioDeceleration());
			//}
		}
		if (Gdx.app.getType().equals(ApplicationType.Android)) {
		//	move = false;
			Vector2 touchpadVec = new Vector2(screenGame.getTouchpad().getKnobPercentX(),screenGame.getTouchpad().getKnobPercentY());
			touchpadVec = touchpadVec.nor().scl(spesificImpulse);
			
			
		//	System.out.println(touchpadVec);
			if (vel.y < ConfigPref.Player_MAX_VELOCITY  && vel.y > -ConfigPref.Player_MAX_VELOCITY) {
				getGameObject().getBody().applyLinearImpulse(0,touchpadVec.y, pos.x, pos.y, true);
			//	move = true;
			}
			/*if (vel.y > -MAX_VELOCITY ) {
				gameObject.getBody().applyLinearImpulse(0,touchpadVec.y, pos.x, pos.y, true);
				move = true;
		
			}*/
			if (vel.x < ConfigPref.Player_MAX_VELOCITY && vel.x > -ConfigPref.Player_MAX_VELOCITY ) {
				getGameObject().getBody().applyLinearImpulse(touchpadVec.x,0, pos.x, pos.y, true);
				//move = true;
			}
			
		//	if (move == false) {
				//System.out.println("Player.update() deceleration vel:"+vel);
				getGameObject().deceleration(getGameObject().getRatioDeceleration());
		//	}
			
			/*if (vel.x > -MAX_VELOCITY ) {
				gameObject.getBody().applyLinearImpulse(touchpadVec.x,0, pos.x, pos.y, true);
				move = true;		
			}*/
			//System.out.println(gameObject.getBody().getLinearVelocity());
		}
		
		
		
		//getGameObject().getBody().setAngularVelocity(0);
		getGameObject().getBody().setTransform(pos, 0);
		//if (Gdx.input.isKeyPressed(Keys.SPACE)) {
		//}
		/*
		// apply right impulse, but only if max velocity is not reached yet
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			int lower = 12;
			int higher = 29;
			int random = (int)(Math.random() * (higher-lower)) + lower;
			GameObjectWall gameObjectWall = screenGame.getFabriqueAll().creationStaticWall(screenGame.getWorldManageur().getWorld(),
					new Sprite((Texture) screenGame.getMainGame().getManager().get(GameConfigPref.file_redBox)),
					-random*100, -1000, 1000, 1,
					"groundWall", GameConfigPref.pixelMeter);
			screenGame.getGameObjectManageur().add(gameObjectWall);
		}
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				if (Gdx.input.isKeyPressed(Keys.ENTER)) {
					GameObjectWall gameObjectWall  = (GameObjectWall) screenGame.getGameObjectManageur().getArrayGObyName("groundWall").first();
					screenGame.getWorldManageur().getWorld().destroyBody(gameObjectWall.getBody());
					screenGame.getGameObjectManageur().getGameObjectArray().removeValue(gameObjectWall, false);
				}
			}
		});
		*/
		//gameObject.lookAt(screenGame.getIaManageur().getListIaPlayer().first().getGameObject().getBody().getPosition());
		if (screenGame.getKryoManageur().getNetApplicationType().equals(NetApplicationType.Client)) {
			PacketUpdateGameObjectPlayer packetUpdateGameObjectPlayer = new PacketUpdateGameObjectPlayer();
			packetUpdateGameObjectPlayer.id = this.getId();
			packetUpdateGameObjectPlayer.gameObject = this.getGameObject();
			
		//	screenGame.getKryoManageur().getKryoClientManageur().getClient().sendUDP(packetUpdateGameObjectPlayer);
		
		
		}		
	}
	
	
	
}
