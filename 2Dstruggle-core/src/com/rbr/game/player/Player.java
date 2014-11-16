package com.rbr.game.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.esotericsoftware.kryonet.Connection;
import com.rbr.game.entity.physics.GameObject;
import com.rbr.game.net.kryo.NetKryoManageur.NetApplicationType;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigPref;

public abstract class Player {
	
	
	
	//GameLogic
	private GameObject gameObject;
	private float life;
	private float lifeMax;	
	

	private boolean spawn;
	
	//render
	private LifeBarRender lifeBarRender ; 

	
	//lié au reseaux
	private int id;
	private Connection connection;
	
	//Lobby
	private boolean readyToPlay;	
	private TeamPlayer teamPlayer;
	
	private String name;
	private Color color;
	
	public Player(GameObject gameObject,String name) {
		this.gameObject = gameObject;
		lifeBarRender = new LifeBarRender( (float)(50f/ConfigPref.pixelMeter), (float)(5f/ConfigPref.pixelMeter));
		life = 100;
		lifeMax = 100;
		setSpawn(false);
		setReadyToPlay(false);
		setTeamPlayer(TeamPlayer.FFA);
		setName(name);
		//color = Color.WHITE;
	}
	
	
	public void subitDegat(ScreenGame screenGame, float degat){
		if(screenGame.getKryoManageur().getNetApplicationType().equals(NetApplicationType.Serveur)){
			if (getLife()-degat<=0) {
				setLife(0);

			}else{
				setLife(getLife()-degat);
			}	
		}
		
	}
	
	public abstract void update(ScreenGame screenGame, float delta) ;
	public abstract void render(ScreenGame screenGame, SpriteBatch spriteBatch,	ShapeRenderer shapeRenderer) ;


	public abstract short getProjectileFilterCategory();
	public abstract short getProjectileFilterGroup();
	public abstract short getProjectileFilterMask();

	
	
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
	}/*
	public boolean isNeedRespawn() {
		return needRespawn;
	}
	public void setNeedRespawn(boolean needRespawn) {
		this.needRespawn = needRespawn;
	}*/
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
	public boolean isReadyToPlay() {
		return readyToPlay;
	}
	public void setReadyToPlay(boolean readyToPlay) {
		this.readyToPlay = readyToPlay;
	}
	public TeamPlayer getTeamPlayer() {
		return teamPlayer;
	}
	public void setTeamPlayer(TeamPlayer teamPlayer) {
		this.teamPlayer = teamPlayer;
	}
	public boolean isSpawn() {
		return spawn;
	}
	public void setSpawn(boolean spawn) {
		this.spawn = spawn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
}
