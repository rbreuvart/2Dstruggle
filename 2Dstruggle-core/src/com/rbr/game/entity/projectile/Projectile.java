package com.rbr.game.entity.projectile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rbr.game.entity.physics.GameObject;
import com.rbr.game.entity.physics.GameObjectSprite;
import com.rbr.game.screen.game.ScreenGame;

public class Projectile extends  GameObjectSprite{

	public Projectile(GameObject go,Sprite sprite) {
		super(go, sprite);
		//super(go.getName(), go.getBodyDef(), go.getBody(), go.getFixtureDef());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		go.getBody().setBullet(true);
		go.getBody().setGravityScale(0);
		setAutoDeceleration(false);
	}
	
	
	
	@Override
	public void colisionBegin(GameObject contact, final ScreenGame screenGame) {
		super.colisionBegin(contact,screenGame);
		this.setRemove(true);		
	}
	
}
