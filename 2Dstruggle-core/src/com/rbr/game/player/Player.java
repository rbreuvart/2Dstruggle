package com.rbr.game.player;

import com.esotericsoftware.kryonet.Connection;
import com.rbr.game.entity.physics.GameObject;
import com.rbr.game.screen.game.ScreenGame;

public abstract class Player {

	
	private GameObject gameObject;
	
	private int id;
	private Connection connection;
	private float life;
	private float lifeMax;
	
	
/*
	
	public enum EtatPlayer{
		
	}*/
	
	public Player(GameObject gameObject) {
		this.gameObject = gameObject;
		
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


	public abstract void update(ScreenGame screenGame, float delta) ;



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

	

	
	
	
}
