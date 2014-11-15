package com.rbr.game.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.rbr.game.CameraManageur;
import com.rbr.game.MainGame;
import com.rbr.game.entity.physics.ContactGameObjectListener;
import com.rbr.game.manageur.GameObjectManageur;
import com.rbr.game.manageur.GarbageManageur;
import com.rbr.game.manageur.HudManageur;
import com.rbr.game.manageur.LightManageur;
import com.rbr.game.manageur.MapManageur;
import com.rbr.game.manageur.PlayerManageur;
import com.rbr.game.manageur.WorldManageur;
import com.rbr.game.net.kryo.NetApplicationContainer;
import com.rbr.game.net.kryo.NetKryoManageur;
import com.rbr.game.utils.ConfigPref;

public class ScreenGame implements Screen{

	private MainGame mainGame;
	private Color backGroundColor;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	
	public Color getBackGroundColor() {
		return backGroundColor;
	}
	public void setBackGroundColor(Color backGroundColor) {
		this.backGroundColor = backGroundColor;
	}
	public SpriteBatch getBatch() {
		return batch;
	}
	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}
	public ShapeRenderer getShapeRenderer() {
		return shapeRenderer;
	}
	public void setShapeRenderer(ShapeRenderer shapeRenderer) {
		this.shapeRenderer = shapeRenderer;
	}
	public MainGame getMainGame() {
		return mainGame;
	}
	public void setMainGame(MainGame mainGame) {
		this.mainGame = mainGame;
	}
	
	
	private CameraManageur camManageur;
	
	private NetKryoManageur kryoManageur;
	
	private WorldManageur worldManageur ;
	
	private GameObjectManageur gameObjectManageur;
	
	private MapManageur mapManageur;
	
	private PlayerManageur playerManageur;
	
	//private IaManageur iaManageur;	
	
	private LightManageur lightManageur;
	
	private HudManageur hudManageur;
	
	private GarbageManageur garbageManageur;
	
	public CameraManageur getCamManageur() {
		return camManageur;
	}
	public void setCamManageur(CameraManageur camManageur) {
		this.camManageur = camManageur;
	}
	public WorldManageur getWorldManageur() {
		return worldManageur;
	}
	public void setWorldManageur(WorldManageur worldManageur) {
		this.worldManageur = worldManageur;
	}
	public GameObjectManageur getGameObjectManageur() {
		return gameObjectManageur;
	}
	public void setGameObjectManageur(GameObjectManageur gameObjectManageur) {
		this.gameObjectManageur = gameObjectManageur;
	}
	public PlayerManageur getPlayerManageur() {
		return playerManageur;
	}
	public void setPlayerManageur(PlayerManageur playerManageur) {
		this.playerManageur = playerManageur;
	}
	/*public IaManageur getIaManageur() {
		return iaManageur;
	}
	public void setIaManageur(IaManageur iaManageur) {
		this.iaManageur = iaManageur;
	}	*/
	public MapManageur getMapManageur() {
		return mapManageur;
	}
	public void setMapManageur(MapManageur mapManageur) {
		this.mapManageur = mapManageur;
	}	
	public HudManageur getHudManageur() {
		return hudManageur;
	}
	public void setHudManageur(HudManageur hudManageur) {
		this.hudManageur = hudManageur;
	}
	public LightManageur getLightManageur() {
		return lightManageur;
	}
	public void setLightManageur(LightManageur lightManageur) {
		this.lightManageur = lightManageur;
	}
	public NetKryoManageur getKryoManageur() {
		return kryoManageur;
	}
	public void setKryoManageur(NetKryoManageur kryoManageur) {
		this.kryoManageur = kryoManageur;
	}
	public GarbageManageur getGarbageManageur() {
		return garbageManageur;
	}
	public void setGarbageManageur(GarbageManageur garbageManageur) {
		this.garbageManageur = garbageManageur;
	}

	
	ScreenGame screenGame;
	public ScreenGame(MainGame mainGame,NetApplicationContainer  netApplicationContainer,String mapFileAsset) {
		this.screenGame = this;
		//recuperation de l'intance de MainGame
		this.setMainGame(mainGame);
		
		//Object de Rendu
		backGroundColor = ConfigPref.CouleurGameBackGroundGL;
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
	
        
		//Camera
		camManageur  = new CameraManageur(mainGame);
		camManageur.getOrthographicCamera().zoom = 100/ConfigPref.pixelMeter;
		
		//Stoquage des GameObjects
		gameObjectManageur = new GameObjectManageur();
		
		//Moteur physique
		worldManageur = new WorldManageur(ConfigPref.pixelMeter);
		worldManageur.getWorld().setContactListener(new ContactGameObjectListener(this));
		
		//light
		lightManageur = new LightManageur(this);
		
		//map
		mapManageur = new MapManageur(this);
		if (!"".equals(mapFileAsset)) {
			mapManageur.loadMap(mapFileAsset);
		}
				
		//Multiplayer and player
		playerManageur = new PlayerManageur();
		
		//IA
		//iaManageur = new IaManageur();
		
		kryoManageur = new NetKryoManageur(this, netApplicationContainer);		
		
		hudManageur =  new HudManageur(this);
		
		garbageManageur = new GarbageManageur(this);
	
	
	}	
	
	
	@Override
	public void render(float delta) {		
		Gdx.gl.glClearColor(getBackGroundColor().r, getBackGroundColor().g, getBackGroundColor().b, getBackGroundColor().a);
		Gdx.gl20.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
					
		worldManageur.update(delta,camManageur);		
		gameObjectManageur.update(this, delta);		
		//iaManageur.update(this,delta);
		playerManageur.update(delta, this);
		
		camManageur.update(this,delta);
		
		mapManageur.update(this);
		
		if (kryoManageur!=null) {
			kryoManageur.update(this, delta);
		}
		
		mapManageur.render(this);
		worldManageur.render(delta,camManageur);
		
		getBatch().setProjectionMatrix(camManageur.getOrthographicCamera().combined);
		getBatch().begin();
			gameObjectManageur.render(this, getBatch());
		getBatch().end();
	
		
		lightManageur.render(this);
		
		
		//debug pour les zones de la map
		shapeRenderer.setProjectionMatrix(screenGame.getCamManageur().getOrthographicCamera().combined);
		shapeRenderer.begin(ShapeType.Filled);
		playerManageur.render(this, getBatch(),shapeRenderer);	
/*
		if (getMapManageur().getListZone()!=null) {
			for (Zone zone : getMapManageur().getListZone()) {
				shapeRenderer.polygon(zone.getPolygon().getTransformedVertices());
			}
		}*/
		shapeRenderer.end();
		
		
		hudManageur.update(this, delta);
		hudManageur.render(this, batch);
		
		Gdx.app.postRunnable(new Runnable() {			
			@Override
			public void run() {
				garbageManageur.update(screenGame);
			}
		});
		
	}
	@Override
	public void resize(int width, int height) {
	
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub		
	}
	@Override
	public void hide() {
		// TODO Auto-generated method stub		
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub		
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub		
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub		
	}
	
}
