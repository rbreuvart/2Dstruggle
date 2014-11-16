package com.rbr.game.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.rbr.game.entity.physics.GameObject;
import com.rbr.game.entity.physics.GameObjectCollisionListener;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigPhysics;

public class PlayerMulti extends Player implements GameObjectCollisionListener{

	public PlayerMulti(GameObject gameObject,String name) {
		super(gameObject,name);		
	}

	@Override
	public void update(ScreenGame screenGame, float delta) {
	
	}
	@Override
	public void render(ScreenGame screenGame, SpriteBatch spriteBatch,	ShapeRenderer shapeRenderer) {	
		getLifeBarRender().render(screenGame, spriteBatch, shapeRenderer,getGameObject().getBody().getPosition().cpy().add(0, -0.45f), this);
		
	}
	
	@Override
	public void colisionBegin(GameObject called, GameObject contact2,ScreenGame screenGame) {	
		
	}
	@Override
	public void colisionEnd(GameObject called, GameObject contact2,	ScreenGame screenGame) {
		
	}
	
	@Override
	public short getProjectileFilterCategory() {		
		return ConfigPhysics.ProjectileEnemy_Category;
	}
	@Override
	public short getProjectileFilterGroup() {	
		return ConfigPhysics.ProjectileEnemy_Group;
	}
	@Override
	public short getProjectileFilterMask() {	
		return ConfigPhysics.ProjectileEnemy_Mask;
	}
}
