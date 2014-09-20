package com.rbr.game.entity.projectile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rbr.game.entity.physics.GameObject;
import com.rbr.game.screen.game.ScreenGame;

public abstract class Projectile extends  GameObject{

	public Projectile(GameObject go) {
		super(go.getName(), go.getBodyDef(), go.getBody(), go.getFixtureDef());
		 go.getBody().setBullet(true);
		 go.getBody().setGravityScale(0);
		
	}
	
	
	
	
	@Override
	public void render(ScreenGame screenGame, SpriteBatch batch) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update(ScreenGame screenGame, float delta) {
		// TODO Auto-generated method stub
		
	}
}
