package com.rbr.game.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.rbr.game.entity.arme.Arme;
import com.rbr.game.entity.physics.GameObject;
import com.rbr.game.entity.physics.GameObjectCollisionListener;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigPhysics;

public class PlayerIa extends Player implements GameObjectCollisionListener{

	private Arme arme;
	
	public PlayerIa(GameObject gameObject) {
		super(gameObject);	
		
		getGameObject().setAutoDeceleration(false);
				
		arme = new Arme(100, 100, 5, 300, 0.99f);
		
	}
	
	

	@Override
	public void update(ScreenGame screenGame,float delta){
		//code de l'Ia
	//	arme.shootUpdate(screenGame,getGameObject().getBody().getPosition(), new Vector2(0,1),this);
	}
	
	@Override
	public void render(ScreenGame screenGame, SpriteBatch spriteBatch,	ShapeRenderer shapeRenderer) {
		getLifeBarRender().render(screenGame, spriteBatch, shapeRenderer,getGameObject().getBody().getPosition().cpy().add(0, -0.45f), getLife(), getLifeMax());

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
