package com.rbr.game.screen.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.rbr.game.CameraManageur;
import com.rbr.game.MainGame;
import com.rbr.game.entity.map.Zone;
import com.rbr.game.entity.physics.ContactGameObjectListener;
import com.rbr.game.manageur.GameObjectManageur;
import com.rbr.game.manageur.HudManageur;
import com.rbr.game.manageur.IaManageur;
import com.rbr.game.manageur.LightManageur;
import com.rbr.game.manageur.MapManageur;
import com.rbr.game.manageur.PlayerManageur;
import com.rbr.game.manageur.WorldManageur;
import com.rbr.game.net.kryo.NetKryoManageur;
import com.rbr.game.net.kryo.NetKryoManageur.NetApplicationType;
import com.rbr.game.utils.ConfigPref;

public class ScreenGame implements Screen,InputProcessor,GestureListener{

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
	
	//private NetworkManageur networkManageur;
	private NetKryoManageur kryoManageur;
	
	private WorldManageur worldManageur ;
	
	private GameObjectManageur gameObjectManageur;
	
	private MapManageur mapManageur;
	
	private PlayerManageur playerManageur;
	
	private IaManageur iaManageur;	
	
	private LightManageur lightManageur;
	
	private HudManageur hudManageur;
	
	
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
	public IaManageur getIaManageur() {
		return iaManageur;
	}
	public void setIaManageur(IaManageur iaManageur) {
		this.iaManageur = iaManageur;
	}	
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

	private Touchpad touchpad;
	private TouchpadStyle touchpadStyle;
	private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;
    private Stage stage;
    private Touchpad touchpadAim;
    public Touchpad getTouchpad() {
		return touchpad;
	}
	public void setTouchpad(Touchpad touchpad) {
		this.touchpad = touchpad;
	}
	public Stage getStage() {
		return stage;
	}
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	ScreenGame screenGame;
	public ScreenGame(MainGame mainGame,NetApplicationType  netAppType,String mapFileAsset) {
		this.screenGame = this;
		//recuperation de l'intance de MainGame
		this.setMainGame(mainGame);
		
		//Object de Rendu
		backGroundColor = ConfigPref.CouleurGameBackGroundGL;
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
	
      
        stage = new Stage(new StretchViewport(ConfigPref.viewPortWidth/1f,ConfigPref.viewPortHeight/1f));
        //controle
        InputMultiplexer im = new InputMultiplexer();
        GestureDetector gd = new GestureDetector(this);
        im.addProcessor(gd);
        im.addProcessor(this);
    	im.addProcessor(stage);
    	Gdx.input.setInputProcessor(im);
    	
        if (Gdx.app.getType().equals(ApplicationType.Desktop)) {        	
    	
        }         
        if (Gdx.app.getType().equals(ApplicationType.Android)) {
        	//Create a touchpad skin    
            touchpadSkin = new Skin();
            //Set background image
            touchpadSkin.add("touchBackground",getMainGame().getManager().get(ConfigPref.File_TouchBackground, Texture.class));
            //Set knob image
            touchpadSkin.add("touchKnob", getMainGame().getManager().get(ConfigPref.File_TouchKnob, Texture.class));
            //Create TouchPad Style
            touchpadStyle = new TouchpadStyle();
            //Create Drawable's from TouchPad skin
            touchBackground = touchpadSkin.getDrawable("touchBackground");
            
            touchKnob = touchpadSkin.getDrawable("touchKnob");
            //Apply the Drawables to the TouchPad Style
            touchpadStyle.background = touchBackground;
            touchpadStyle.knob = touchKnob;
            //Create new TouchPad with the created style
            touchpad = new Touchpad(10, touchpadStyle);
            touchpad.setColor(0, 0, 0, .2f);
            //setBounds(x,y,width,height)
            touchpad.setBounds(30, 40, 250, 250);
            
            //Create a Stage and add TouchPad
           // stage = new Stage(new StretchViewport(ConfigPref.viewPortWidth/1.2f,ConfigPref.viewPortHeight/1.2f));
            stage.addActor(touchpad);      
            
            
            
            touchpadAim = new Touchpad(20, touchpadStyle);
            touchpadAim.setColor(0.5f, 0, 0, .2f);
            //setBounds(x,y,width,height)
            touchpadAim.setBounds(960-250-30, 40, 250, 250);
            stage.addActor(touchpadAim);   
         //   Gdx.input.setInputProcessor(stage);
		}
       // Gdx.input.setInputProcessor(stage);
		//Cammera
		camManageur  = new CameraManageur(mainGame);
		camManageur.getOrthographicCamera().zoom = 100/ConfigPref.pixelMeter;
		
		//Stoquage des GameObjects
		gameObjectManageur = new GameObjectManageur();
		
		//Moteur physique
		worldManageur = new WorldManageur(ConfigPref.pixelMeter);
		worldManageur.getWorld().setContactListener(new ContactGameObjectListener(gameObjectManageur));
		
		//light
		lightManageur = new LightManageur(this);
		
		//map
		mapManageur = new MapManageur(this,mapFileAsset);
		//mapManageur.create(screenGame);
		
		//Multiplayer and player
		playerManageur = new PlayerManageur();
	

		//IA
		iaManageur = new IaManageur();
	
		
		
		
		hudManageur =  new HudManageur();
	
		kryoManageur = new NetKryoManageur(this, netAppType);
		
		
	}	
	
	
	@Override
	public void render(float delta) {		
		Gdx.gl.glClearColor(getBackGroundColor().r, getBackGroundColor().g, getBackGroundColor().b, getBackGroundColor().a);
		Gdx.gl20.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
					
		worldManageur.update(delta,camManageur);		
		gameObjectManageur.update(this, delta);		
		iaManageur.update(this,delta);
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
		
		screenGame.getShapeRenderer().setProjectionMatrix(screenGame.getCamManageur().getOrthographicCamera().combined);
		
		screenGame.getShapeRenderer().begin(ShapeType.Line);
		for (Zone zone : getMapManageur().getListZone()) {
			screenGame.getShapeRenderer().polygon(zone.getPolygon().getTransformedVertices());
		}
		screenGame.getShapeRenderer().end();
		
		lightManageur.render(this);
		
		hudManageur.update(this, delta);
		hudManageur.render(this, batch);
		
		
	}
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub		
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
	@Override
	public boolean keyDown(int keycode) {		
		return true;
	}
	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {		
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		System.out.println("ScreenGame.scrolled()");
		getCamManageur().getOrthographicCamera().zoom = getCamManageur().getOrthographicCamera().zoom +(amount*1);
		return false;
	}
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}	
	@Override
	public boolean fling(float velocityX, float velocityY, int button) {		
		getCamManageur().fling(velocityX, velocityY, button);
	return false;
	}
	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean zoom(float initialDistance, float distance) {		
		return false;
	}
	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}
	public Touchpad getTouchpadAim() {
		return touchpadAim;
	}
	public void setTouchpadAim(Touchpad touchpadAim) {
		this.touchpadAim = touchpadAim;
	}	
	
}
