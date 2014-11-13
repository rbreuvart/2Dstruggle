package com.rbr.game.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.esotericsoftware.kryonet.Connection;
import com.rbr.game.entity.physics.GameObject;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigPref;

public abstract class Player {
	
	private GameObject gameObject;
	
	private int id;
	private Connection connection;
	private float life;
	private float lifeMax;
	
	private boolean needRespawn;
	
	private LifeBarRender lifeBarRender ; 
	
	
	private boolean spawn;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	public LifeBarRender getLifeBarRender() {
		return lifeBarRender;
	}
	public void setLifeBarRender(LifeBarRender lifeBarRender) {
		this.lifeBarRender = lifeBarRender;
	}
	public boolean isNeedRespawn() {
		return needRespawn;
	}
	public void setNeedRespawn(boolean needRespawn) {
		this.needRespawn = needRespawn;
	}
	public GameObject getGameObject() {
		return gameObject;
	}
	public void setGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}
	public float getLife() {
		return life;
	}
	public void setLife(float life) {
		this.life = life;
	}
	public float getLifeMax() {
		return lifeMax;
	}
	public void setLifeMax(float lifeMax) {
		this.lifeMax = lifeMax;
	}

	
	public Player(GameObject gameObject) {
		this.gameObject = gameObject;
		lifeBarRender = new LifeBarRender( (float)(50f/ConfigPref.pixelMeter), (float)(5f/ConfigPref.pixelMeter));
		life = 100;
		lifeMax = 100;
		needRespawn = false;
		spawn = false;
	}
	
	
	public void subitDegat(float degat){
		if (getLife()-degat<=0) {
			setLife(0);
			setNeedRespawn(true);
			//FIXME faire respawn Reseaux
		}else{
			setLife(getLife()-degat);
		}
	}
	
	public abstract void update(ScreenGame screenGame, float delta) ;
	public abstract void render(ScreenGame screenGame, SpriteBatch spriteBatch,	ShapeRenderer shapeRenderer) ;



	
	public abstract short getProjectileFilterCategory();
	public abstract short getProjectileFilterGroup();
	public abstract short getProjectileFilterMask();
	public boolean isSpawn() {
		return spawn;
	}
	public void setSpawn(boolean spawn) {
		this.spawn = spawn;
	}
	

	
	
	
}
