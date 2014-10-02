package com.rbr.game.screen.menu;

import java.lang.reflect.Field;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.rbr.game.MainGame;
import com.rbr.game.net.kryo.NetKryoManageur.NetApplicationType;
import com.rbr.game.screen.AbsctactScreen;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigPref;
import com.rbr.game.utils.ConfigPref.TypeMsg;

public class ScreenChoosePlay extends AbsctactScreen{
	
	
	
	Window windowMap;
	Table tableWindow;
	
	private String mapFileAssetChoisie;
	
	TextButton buttonStartClient;	
	TextButton buttonStartServer;
	
	public ScreenChoosePlay(MainGame mainGame) {
		super(mainGame);
		setBackGroundColor(Color.NAVY);
	}
	
	@Override
	public void create() {		
		super.create();
		Skin skin = getMainGame().getManager().get(ConfigPref.File_UiSkin);
		
		/*
		 * Bouton de retour
		 */
		Table tableCornerRight = new Table(skin);
	    tableCornerRight.setFillParent(false);
	    tableCornerRight.setPosition(885, 520);
	    getStage().addActor(tableCornerRight);
	    
	    TextButton buttonRetour = new TextButton("Retour ", skin);
	    buttonRetour.addCaptureListener(new InputListener(){
	    		@Override
	    		public boolean touchDown(InputEvent event, float x, float y,int pointer, int button) {	  
	    			getMainGame().setScreen(new ScreenAccueil(getMainGame()));
	    						return super.touchDown(event, x, y, pointer, button);	    			
	    		}
	    });
	    tableCornerRight.add(buttonRetour).width(150).height(40);
	    tableCornerRight.row();
		
		
		/*
		 * Bouton de Start
		 */
		
		Table tableCornerbotRight = new Table(skin);
		tableCornerbotRight.setFillParent(false);	 
		tableCornerbotRight.align(Align.bottom+Align.right);
		tableCornerbotRight.setPosition(960, 0);
	    getStage().addActor(tableCornerbotRight);
		
	    this.buttonStartClient = new TextButton("Start Client", skin,"default");
	    buttonStartClient.addCaptureListener(new InputListener(){
	    		@Override
	    		public boolean touchDown(InputEvent event, float x, float y,int pointer, int button) {
	    			MainGame.printMsg("BtnStart : touchDown", TypeMsg.Stage);
	    			final SequenceAction sequenceAction  = new SequenceAction();	   
	    			sequenceAction.addAction(Actions.fadeOut(0.0f));
	    			sequenceAction.addAction(Actions.run(new Runnable() {
						@Override
						public void run() {
							getMainGame().setScreen(new ScreenGame(getMainGame(),NetApplicationType.Client,mapFileAssetChoisie));
						}
					}));
	    			getStage().addAction(sequenceAction);
	    			
	    			return super.touchDown(event, x, y, pointer, button);	    			
	    		}
	    });	    
	    tableCornerbotRight.add(buttonStartClient).width(100).height(50);	
		
	    
	    
	    
	    this.buttonStartServer = new TextButton("Start Server", skin,"default");
	    buttonStartServer.addCaptureListener(new InputListener(){
		    		@Override
		    		public boolean touchDown(InputEvent event, float x, float y,int pointer, int button) {
		    			MainGame.printMsg("BtnStart : touchDown", TypeMsg.Stage);
		    			final SequenceAction sequenceAction  = new SequenceAction();	   
		    			sequenceAction.addAction(Actions.fadeOut(0.0f));
		    			sequenceAction.addAction(Actions.run(new Runnable() {
							@Override
							public void run() {
								getMainGame().setScreen(new ScreenGame(getMainGame(),NetApplicationType.Serveur,mapFileAssetChoisie));
							}
						}));
		    			getStage().addAction(sequenceAction);
		    			return super.touchDown(event, x, y, pointer, button);	    			
		    		}
		});
		tableCornerbotRight.add(buttonStartServer).width(100).height(50);	
		
		buttonStartClient.setVisible(false);
		buttonStartServer.setVisible(false);
	
		/*
		 * 
		 */
		
		Table tableMap = new Table(skin);
		tableMap.setFillParent(false);
		tableMap.setPosition(550/2+10, 540/2+120);
		
		windowMap = new Window("Liste des MAPs", skin);
		windowMap.setVisible(true);
		windowMap.align(Align.center);
		windowMap.setMovable(false);
		windowMap.defaults().space(0);
				
		tableWindow = new Table(skin);
	
		getStage().addActor(tableMap);
		tableMap.add(windowMap).width(550).height(275);
		windowMap.add(tableWindow).width(550).height(255);
		
		//stage -> tableMap -> windowMap ->tablewindow
		
		/*
		 * defini la liste des Map
		 */
		Array<String> listEntries = new Array<String>();
		
		Class<ConfigPref> c = ConfigPref.class;
		Field[] fields = c.getDeclaredFields();
		for(Field f : fields){
			if (f.getName().contains(ConfigPref.PatternField_File)) {
				try {	
					String pathFile = (String)f.get(ConfigPref.class);					
					if(pathFile.contains(ConfigPref.Pattern_Map)){
						String nomMap =f.getName().substring(f.getName().indexOf("_")+1);
						listEntries.add(nomMap);
					}
				} catch (IllegalArgumentException e) {e.printStackTrace();} catch (IllegalAccessException e) {e.printStackTrace();}
			}
		}
		
		/*
		 * defini l'Acter et image de la map
		 */
		Texture texture2 = getMainGame().getManager().get(ConfigPref.File_MapMiniature, Texture.class);		
		TextureRegion image2 = new TextureRegion(texture2);
		Image imageActor = new Image(image2);
		final ScrollPane scrollPane = new ScrollPane(imageActor);
		
		@SuppressWarnings("rawtypes")
		final List list = new List(skin);					
		list.setItems(listEntries);
		list.getSelection().setMultiple(false);
		list.getSelection().setRequired(false);	
		
		ScrollPane scrollPane2 = new ScrollPane(list, skin);
		scrollPane2.setFlickScroll(false);
		
		final SplitPane splitPane = new SplitPane(scrollPane, scrollPane2, false, skin, "default-horizontal");
		
		list.addCaptureListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.postRunnable(new Runnable() {						
					@Override
					public void run() {							
						int index = list.getSelectedIndex();
						if (index>=0) {
							
							//recup la chaine de l'item selectionné
							String nameObjectlist = (String) list.getItems().get(index);
					
							/*
							 * defini l'image a coté de  
							 */
							Class<ConfigPref> c = ConfigPref.class;
							Field[] fields = c.getDeclaredFields();
						
							String minmap = ConfigPref.File_MapMiniature;
							for (int i = 0; i < fields.length; i++) {
								if (fields[i].getName().contains(nameObjectlist)) {
									try {
										minmap = (String) fields[i].get(ConfigPref.class);
									} catch (IllegalArgumentException e) {e.printStackTrace();} catch (IllegalAccessException e) {e.printStackTrace();}
								}
							}
							
							Texture textureMiniature = getMainGame().getManager().get(minmap, Texture.class);
						
							TextureRegion imageMin = new TextureRegion(textureMiniature);
							final Image imageActorMin = new Image(imageMin);
							
							ScrollPane spane = (ScrollPane) splitPane.getChildren().get(0);
							spane.setWidget(new ScrollPane(imageActorMin));
							
								
							/*
							 * definie la carte a chargé
							 */
						
							setMapFileAssetChoisie(getPathfileFormItemName(nameObjectlist));
										
						}else {
							setMapFileAssetChoisie("");
						}
						
						
					}
				});
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		tableWindow.add(splitPane).fill().expand().colspan(4).maxHeight(470);
		
		
		/*
		 * Option interface
		 */
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//map par default
		setMapFileAssetChoisie(getPathfileFormItemName((String)list.getItems().first()));
	}
	private String getPathfileFormItemName(String nameObjectlist){
		/*
		 * definie la carte a chargé
		 */
		Class<ConfigPref> ct = ConfigPref.class;
		Field[] fieldsClass = ct.getDeclaredFields();
		for(Field f : fieldsClass){
			if (f.getName().contains(ConfigPref.PatternField_File)) {
				try {	
					String pathFile = (String)f.get(ConfigPref.class);					
					if (f.getName().contains(nameObjectlist)) {											
						 if(pathFile.contains(ConfigPref.Pattern_Map)){
							setMapFileAssetChoisie(pathFile);
						//	System.out.println("--->"+getMapFileAssetChoisie());					
							return pathFile;
						}											
					}
				} catch (IllegalArgumentException e) {e.printStackTrace();} catch (IllegalAccessException e) {e.printStackTrace();}
			}
		}
		return nameObjectlist;
		
	}
	public String getMapFileAssetChoisie() {
		return mapFileAssetChoisie;
	}

	public void setMapFileAssetChoisie(String mapFileAssetChoisie) {
		this.mapFileAssetChoisie = mapFileAssetChoisie;
		if (!"".equals(mapFileAssetChoisie)) {
		//	System.out.println("il y a du text");
			buttonStartClient.setVisible(true);
			buttonStartServer.setVisible(true);
		}else {
		//	System.out.println("il y a pas de text");
			buttonStartClient.setVisible(false);
			buttonStartServer.setVisible(false);
		}
	}
	
	
	
}
