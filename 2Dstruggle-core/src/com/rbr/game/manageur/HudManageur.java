package com.rbr.game.manageur;

import java.util.Map.Entry;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.rbr.game.net.kryo.NetKryoManageur.NetApplicationType;
import com.rbr.game.player.Player;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigPref;

public class HudManageur {
	Matrix4 normalProjection;
	BitmapFont bitmapFont;
	BitmapFontCache  bfc ;
	
	
	private Touchpad touchpad;
	private TouchpadStyle touchpadStyle;
	private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;
    private Stage stage;
    private Touchpad touchpadAim;
    
    public Touchpad getTouchpad() {	return touchpad;	}
	public void setTouchpad(Touchpad touchpad) {		this.touchpad = touchpad;	}
	public Touchpad getTouchpadAim() {		return touchpadAim;	}
	public void setTouchpadAim(Touchpad touchpadAim) {		this.touchpadAim = touchpadAim;}
	public Stage getStage() {	return stage;	}
	public void setStage(Stage stage) {		this.stage = stage;	}
	public Table getTableStartGame() {return tableStartGame;	}
	public void setTableStartGame(Table tableStartGame) {this.tableStartGame = tableStartGame;}
	public Window getWindowListPlayer() {		return windowListPlayer;	}
	public void setWindowListPlayer(Window windowListPlayer) {	this.windowListPlayer = windowListPlayer;}
	
	private Table tableStartGame;
	private Window windowListPlayer; 
	@SuppressWarnings("rawtypes")
	List listPlayer;
	ScreenGame screenGame;
	
	//Conponent pour serveur
	TextButton textButtonStarGame;
	
	@SuppressWarnings("rawtypes")
	public HudManageur(final ScreenGame screenGame) {
		this.screenGame = screenGame;
		normalProjection = new Matrix4();
		normalProjection.setToOrtho2D(0, 0, Gdx.graphics.getWidth()/1.5f,Gdx.graphics.getHeight()/1.5f);	
		bitmapFont = new BitmapFont();
		bitmapFont.scale(-0.1f);
		bfc = new BitmapFontCache(bitmapFont);
		
		Skin skin = screenGame.getMainGame().getManager().get(ConfigPref.File_UiSkin);
	    stage = new Stage(new StretchViewport(ConfigPref.viewPortWidth/1f,ConfigPref.viewPortHeight/1f));
      
	    //controle
      //  InputMultiplexer im = new InputMultiplexer();
       // GestureDetector gd = new GestureDetector(this);
        //im.addProcessor(gd);
       // im.addProcessor(screenGame);
    	//im.addProcessor(stage);
    	Gdx.input.setInputProcessor(stage);
    	//Gdx.input.setInputProcessor(im);
		
		if (Gdx.app.getType().equals(ApplicationType.Desktop)) {        	
		        //	Gdx.input.setCursorCatched(true);
		        //	Gdx.input.setCursorImage(pixmap, xHotspot, yHotspot);
        }         
        if (Gdx.app.getType().equals(ApplicationType.Android)) {
        	//Create a touchpad skin    
            touchpadSkin = new Skin();
            //Set background image
            touchpadSkin.add("touchBackground",screenGame.getMainGame().getManager().get(ConfigPref.File_TouchBackground, Texture.class));
            //Set knob image
            touchpadSkin.add("touchKnob", screenGame.getMainGame().getManager().get(ConfigPref.File_TouchKnob, Texture.class));
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
            touchpad.setBounds(30, 40, 250, 250); 
            stage.addActor(touchpad);      
            
            touchpadAim = new Touchpad(20, touchpadStyle);
            touchpadAim.setColor(0.5f, 0, 0, .2f);
            touchpadAim.setBounds(960-250-30, 40, 250, 250);
            stage.addActor(touchpadAim);   
		}
		
		
		
		windowListPlayer = new Window("Liste Joueurs", skin);
		windowListPlayer.setHeight(400);
		windowListPlayer.setWidth(200);
		windowListPlayer.setColor(1, 1, 1, 0.5f);
		windowListPlayer.setModal(false);
		stage.addActor(windowListPlayer);
		
		
		Array<String> listEntriesPlayer = new Array<String>();
		listPlayer = new List(skin);					
		listPlayer.setItems(listEntriesPlayer);
		listPlayer.getSelection().setMultiple(false);
		listPlayer.getSelection().setRequired(true);
		
		
		ScrollPane scrollPaneServeur = new ScrollPane(listPlayer, skin);
		scrollPaneServeur.setFlickScroll(false);
		windowListPlayer.add(scrollPaneServeur).expand().fill();
		
		
		tableStartGame = new Table(skin);
		tableStartGame.moveBy(0, 0);
		tableStartGame.setFillParent(true);
		stage.addActor(tableStartGame);
		
		final TextButton textButtonReady = new TextButton("Ready", skin);
		textButtonReady.addCaptureListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			System.out.println("btn ready");
				if (screenGame.getPlayerManageur().getPlayerLocal()!=null) {
					textButtonReady.setVisible(false);
					screenGame.getPlayerManageur().getPlayerLocal().setReadyToPlay(true);		
					if (screenGame.getKryoManageur().getNetApplicationType().equals(NetApplicationType.Client)) {
						screenGame.getKryoManageur().getKryoClientManageur().getLobbyClient().eventPlayerLocalReadyToPlay();
					}else{
						screenGame.getKryoManageur().getKryoServerManageur().getLobbyServer().eventPlayerLocalReadyToPlay();
					}
				}
				
				return super.touchDown(event, x, y, pointer, button);			
			}
		});
		tableStartGame.add(textButtonReady).align(Align.top);
		
		
		if (screenGame.getKryoManageur().getNetApplicationType().equals(NetApplicationType.Serveur)) {
		
			textButtonStarGame = new TextButton("Start Game", skin);
			textButtonStarGame.addCaptureListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					System.out.println("btnstarGame");
					screenGame.getKryoManageur().getKryoServerManageur().startGame();
					return super.touchDown(event, x, y, pointer, button);			
				}
			});
			tableStartGame.add(textButtonStarGame).align(Align.top);
		}
		
		
		stage.addCaptureListener( new InputListener(){
		@Override
		public boolean scrolled(InputEvent event, float x, float y,
				int amount) {
		/*	float zoom = screenGame.getCamManageur().getOrthographicCamera().zoom +(amount*0.5f);
			if (zoom>0) {
				screenGame.getCamManageur().getOrthographicCamera().zoom =zoom;
			}*/
			return false;
			//return super.scrolled(event, x, y, amount);
		}
			
		});
		
	}
	
	public void render(ScreenGame screenGame,SpriteBatch batch){
		batch.setProjectionMatrix(normalProjection);
		batch.begin();
	//	bitmapFont.draw(batch, "cam("+screenGame.getCamManageur().getOrthographicCamera().position.x+"\n"+screenGame.getCamManageur().getOrthographicCamera().position.y+")\n"+screenGame.getCamManageur().getOrthographicCamera().zoom, 10, 350);
	//	bitmapFont.draw(batch, "FPS:"+Gdx.graphics.getFramesPerSecond(), 10, 24);
		if (screenGame.getKryoManageur()!=null) {		
			if (screenGame.getKryoManageur().getNetApplicationType().equals(NetApplicationType.Client)) {
				bitmapFont.draw(batch, "Type : "+"Client",60, 24);
			}else {
				bitmapFont.draw(batch, "Type : "+"Serveur",60, 24);
			}
		}
		/*
		int i = 0;
		for (Entry<Integer, Player> entry : screenGame.getPlayerManageur().getHashMapPlayer().entrySet()) {
			bitmapFont.draw(batch, "PlayerManageur idMap:"+entry.getKey()+" name:"+entry.getValue().getGameObject().getName()+" position"+entry.getValue().getGameObject().getBody().getPosition(), 10,36+(i*12));
			
			//bitmapFont.draw(batch, "PlayerManageur id"+entry.getValue().getId()+" name"+entry.getValue().getGameObject().getName()+" key"+entry.getKey()+" position"+entry.getValue().getGameObject().getBody().getPosition(), 10,36+(i*12));
			i++;
			
		}*/
			/*
		int y = 70;
		for (GameObject go : screenGame.getGameObjectManageur().getGameObjectArray()) {
			bitmapFont.draw(batch, 	go.getName()+" "+
									go.isRemove()+" "+
									go.getBody().getPosition(), 10, y);
			y = y+10;
		}
		*//*
		for (Entry<Integer, Player> entry : screenGame.getPlayerManageur().getHashMapPlayer().entrySet()) {
		
			
			Vector2 vName = entry.getValue().getGameObject().getBody().getPosition().cpy();//.scl(((float)0.1f/64)); 
			Vector2 vCam = new Vector2(screenGame.getCamManageur().getOrthographicCamera().position.x, screenGame.getCamManageur().getOrthographicCamera().position.y);
			Vector2 vDif =  vName.cpy().sub(vCam);
		
			Vector2 vPosStatic = 	 vDif.cpy().add(screenGame.getCamManageur().getOrthographicCamera().viewportWidth/2,
					screenGame.getCamManageur().getOrthographicCamera().viewportHeight/2).scl(32);
			Vector2 vResult = vPosStatic.cpy().add(vDif.cpy().scl(2));
			
			System.out.println(entry.getValue().getName()+" : \n vCam"+vCam+
					"\n vName"+vName+
					"\n vDif"+vDif+
					"\n vPosStatic"+vPosStatic+
					"\n vResult"+vResult);
			bitmapFont.draw(batch, entry.getValue().getName(),vResult.x,	vResult.y);
			
		}		*/
		batch.end();
		
		stage.draw();
	}
	
	
	float timeState=0f; 
	public void update(ScreenGame screenGame,float delta){
		stage.act(delta); 
		timeState+=delta;
		if(timeState>=0.5f){
		   timeState=0f; 
		   updateListPlayer();
		   updateBtnAllReady();
		  //System.out.println(Gdx.input.getInputProcessor());
		}
	}
	
	private void updateBtnAllReady(){
		 if (screenGame.getKryoManageur().getNetApplicationType().equals(NetApplicationType.Serveur)) {
			   boolean allvalide = true;
			   for (Entry<Integer, Player> entryPlayer : screenGame.getPlayerManageur().getHashMapPlayer().entrySet()) {		
					//System.out.println(entryPlayer.getKey()+" "+entryPlayer.getValue().getGameObject().getName());
					if (!entryPlayer.getValue().isReadyToPlay()) {
						allvalide = false;
						break;
					}
			   }
			   if (allvalide) {
				   textButtonStarGame.setVisible(true);
			   }else{
				   textButtonStarGame.setVisible(false);
			   }
		   }
	}
	
	public void updateListPlayer(){
		Array<String> listEntriesPlayer = new Array<String>();
		for (Entry<Integer, Player> entryPlayer : screenGame.getPlayerManageur().getHashMapPlayer().entrySet()) {		
			//System.out.println(entryPlayer.getKey()+" "+entryPlayer.getValue().getGameObject().getName());
			listEntriesPlayer.add(entryPlayer.getValue().getGameObject().getName()+" Ready:"+entryPlayer.getValue().isReadyToPlay());
		}
		listPlayer.setItems(listEntriesPlayer);
	}

	
	
	/*
			int compt = 0;
			for (int i = screenGame.getNetworkManageur().getListMSG().size-1; i >= 0; i--) {
				String text =screenGame.getNetworkManageur().getListMSG().get(i);
			//	System.out.println(text+" "+i);
				bfc.setText(text, 350, 540-(15*(screenGame.getNetworkManageur().getListMSG().size-i)));	
				//bitmapFont.draw(batch,text, 600, 540-(15*i));
				if (text.substring(0, 2).contains(ConfigPref.Net_PatternMsgRecu)) {						
					bfc.setColors(ConfigPref.Net_ChatColorRecu, 3, text.length());
					bfc.draw(batch);
				}else if(text.substring(0, 2).contains(ConfigPref.Net_PatternMsgEmit)){						
					bfc.setColors(ConfigPref.Net_ChatColorEmit, 3, text.length());
					bfc.draw(batch);
				}
				
				if (compt>=ConfigPref.Net_ChatMaxaffMsg) {
					break;
				}
			}
	*/
		
}
