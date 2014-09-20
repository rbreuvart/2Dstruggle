package com.rbr.game.screen.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
import com.rbr.game.ConfigPref;
import com.rbr.game.MainGame;
import com.rbr.game.entity.physics.ContactGameObjectListener;
import com.rbr.game.entity.physics.FabriqueAll;
import com.rbr.game.inteligence.IaPlayer;
import com.rbr.game.inteligence.Player;
import com.rbr.game.manageur.GameObjectManageur;
import com.rbr.game.manageur.HudManageur;
import com.rbr.game.manageur.IaManageur;
import com.rbr.game.manageur.LightManageur;
import com.rbr.game.manageur.MapManageur;
import com.rbr.game.manageur.PlayerManageur;
import com.rbr.game.manageur.WorldManageur;
import com.rbr.game.net.NetworkManageur;

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
	/*
	public NetworkManageur getNetworkManageur() {
		return networkManageur;
	}
	public void setNetworkManageur(NetworkManageur networkManageur) {
		this.networkManageur = networkManageur;
	}*/
	
	private Touchpad touchpad;
	private TouchpadStyle touchpadStyle;
	private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;
    private Stage stage;
    
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
	
	public ScreenGame(MainGame mainGame) {
		
		//recuperation de l'intance de MainGame
		this.setMainGame(mainGame);
		
		//Object de Rendu
		backGroundColor = ConfigPref.CouleurGameBackGroundGL;
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		//controle
		InputMultiplexer im = new InputMultiplexer();
        GestureDetector gd = new GestureDetector(this);
        im.addProcessor(gd);
        im.addProcessor(this);
      
        
        if (Gdx.app.getType().equals(ApplicationType.Desktop)) {
            stage = new Stage(new StretchViewport(ConfigPref.viewPortWidth/1.2f,ConfigPref.viewPortHeight/1.2f));
            im.addProcessor(stage);
        }
        
        Gdx.input.setInputProcessor(im);
        
        if (Gdx.app.getType().equals(ApplicationType.Android)) {
        	//Create a touchpad skin    
            touchpadSkin = new Skin();
            //Set background image
            touchpadSkin.add("touchBackground", new Texture("data/touchBackground.png"));
            //Set knob image
            touchpadSkin.add("touchKnob", new Texture("data/touchKnob.png"));
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
            touchpad.setBounds(30, 40, 200, 200);
            
            //Create a Stage and add TouchPad
            stage = new Stage(new StretchViewport(ConfigPref.viewPortWidth/1.2f,ConfigPref.viewPortHeight/1.2f));
            stage.addActor(touchpad);            
            Gdx.input.setInputProcessor(stage);
		}
        
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
		mapManageur = new MapManageur(this);
		
		//joueur		
		/*Player player = new Player(FabriqueAll.creationGameObjectCircle(worldManageur, 
				new Sprite( (Texture) getMainGame().getManager().get(ConfigPref.file_Vaisseau1)),
				getMapManageur().getListVectorSpawn().first().cpy(),"player", 0.5f,ConfigPref.pixelMeter));
		playerManageur = new PlayerManageur(player);
		gameObjectManageur.getGameObjectArray().add(player.getGameObject());*/
		
		Player player = new Player(FabriqueAll.creationGameObjectCircle(worldManageur, 
				new Sprite( (Texture) getMainGame().getManager().get(ConfigPref.file_Vaisseau1)),
				getMapManageur().getListVectorSpawn().first().cpy(),"player", 0.45f,ConfigPref.pixelMeter));
		playerManageur = new PlayerManageur(player);
		gameObjectManageur.getGameObjectArray().add(player.getGameObject());

		//IA
		iaManageur = new IaManageur();
		IaPlayer iaplayer = new IaPlayer(FabriqueAll.creationGameObjectCircle(getWorldManageur(), new Sprite((Texture) mainGame.getManager().get(ConfigPref.file_RedCircle)),
				getMapManageur().getListVectorSpawn().get(1).cpy(), "Ia", 0.45f, ConfigPref.pixelMeter));
		
		iaManageur.getListIaPlayer().add(iaplayer);
		gameObjectManageur.add(iaplayer.getGameObject());
				
		//centre la camera
		camManageur.folowTarget(playerManageur.player.getGameObject().getBody().getPosition());

		
		hudManageur =  new HudManageur();
		
	//	networkManageur = new NetworkManageur(this);
		
	}	
	
	
	
	@Override
	public void render(float delta) {		
		Gdx.gl.glClearColor(getBackGroundColor().r, getBackGroundColor().g, getBackGroundColor().b, getBackGroundColor().a);
		Gdx.gl20.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
					
		worldManageur.update(delta,camManageur);		
		gameObjectManageur.update(this, delta);		
		iaManageur.update(this,delta);
		playerManageur.update(delta, this);
		
	//	if (Gdx.input.isKeyPressed(Keys.SPACE)) {
		camManageur.folowTarget(playerManageur.player.getGameObject().getBody().getPosition());			
	//	}
		
		camManageur.update(this,delta);
		
		mapManageur.render(this);
		worldManageur.render(delta,camManageur);
		
		getBatch().setProjectionMatrix(camManageur.getOrthographicCamera().combined);
		getBatch().begin();
		gameObjectManageur.render(this, getBatch());
		getBatch().end();
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
	//object Terrain
	
	
			//test Wall
			/*GameObjectWall gameObjectWall = fabriqueAll.creationStaticWall(worldManageur.getWorld(),
											new Sprite((Texture) mainGame.getManager().get(GameConfigPref.file_redBox)),
											-1000, -1000, 1000, 1,
											"groundWall", GameConfigPref.pixelMeter);
			
			gameObjectManageur.add(gameObjectWall);*/
	/*
	
	//chaine
	Vector2[] listeVector2 = new Vector2[1000];
	
	for (int i = 0; i < 1000; i++) {
		int lower = 0;
		int higher = 3;

		int randomy = (int)(Math.random() * (higher-lower)) + lower;
		listeVector2[i] = new Vector2(i, randomy);
	}
	GameObjectChaineShape gameObjectChaineShape = FabriqueAll.creationStaticChaineShape(worldManageur.getWorld(), -2000, -2000,"chaine", listeVector2, ConfigPref.pixelMeter);
	gameObjectManageur.add(gameObjectChaineShape);
	
	*/
}
