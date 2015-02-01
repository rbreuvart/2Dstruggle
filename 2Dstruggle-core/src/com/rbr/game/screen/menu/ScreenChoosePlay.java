package com.rbr.game.screen.menu;

import java.lang.reflect.Field;
import java.net.InetAddress;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.rbr.game.MainGame;
import com.rbr.game.net.client.NetKryoNewClientManageur;
import com.rbr.game.net.kryo.NetApplicationContainer;
import com.rbr.game.net.kryo.NetKryoManageur.NetApplicationType;
import com.rbr.game.screen.AbsctactScreen;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigPref;
import com.rbr.game.utils.ConfigPref.TypeMsg;

public class ScreenChoosePlay extends AbsctactScreen{
	
	private String mapFileAssetChoisie;
	private String ip ;
	Texture backgroundTexture;
	
	Window windowMap;
	Table tableWindow;
	SplitPane splitPane;
		
	TextButton buttonStartClient;	
	TextButton buttonStartServer;
	TextButton buttonRefrechServer;
	
	
	Window windowServeur;
	@SuppressWarnings("rawtypes")
	List listServeur ;
	//Label IpLabel ;
	TextField textfieldIp;
	
	public ScreenChoosePlay(MainGame mainGame) {
		super(mainGame);
		setBackGroundColor(Color.NAVY);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void create() {		
		super.create();
		backgroundTexture = new Texture(Gdx.files.internal("data/menu/choixmap.png"));
		Image imgbackground = new Image(backgroundTexture);
		imgbackground.setFillParent(true);
		getStage().addActor(imgbackground);

		
		
		Skin skin = getMainGame().getManager().get(ConfigPref.File_UiSkin);
		/*
		 * Bouton de retour
		 */
		Table tableCornerRight = new Table(skin);
	    tableCornerRight.setFillParent(false);
	    tableCornerRight.setPosition(885, 490);
	    getStage().addActor(tableCornerRight);
	  
	    TextButton buttonRetour = new TextButton("Retour ", skin);
	    buttonRetour.addCaptureListener(new InputListener(){
	    		@Override
	    		public boolean touchDown(InputEvent event, float x, float y,int pointer, int button) {	  
	    			getMainGame().setScreen(new ScreenAccueil(getMainGame()));
	    						return super.touchDown(event, x, y, pointer, button);	    			
	    		}
	    });
	    tableCornerRight.add(buttonRetour).width(120).height(40);
	    tableCornerRight.row();
		
	    
	    
		
		/*
		 * Bouton de Start
		 */
		
		Table tableCornerbotRight = new Table(skin);
		tableCornerbotRight.setFillParent(false);	 
		tableCornerbotRight.align(Align.bottom+Align.right);
		tableCornerbotRight.setPosition(960, 20);
	    getStage().addActor(tableCornerbotRight);
	   
	    
	    this.buttonRefrechServer =  new TextButton("Refrech Serveur", skin);
		buttonRefrechServer.addCaptureListener(new InputListener(){
			@Override
    		public boolean touchDown(InputEvent event, float x, float y,int pointer, int button) {	
				Array<String> items = new Array<String>();
				items.add(" Recherche Serveur ... ");
				listServeur.setItems(items);
				rechercheServeurs();
				return super.touchDown(event, x, y, pointer, button);	
			}
		});
		tableCornerbotRight.add(buttonRefrechServer).width(130).height(50);	
		
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
							ScreenGame screenGame = new ScreenGame(getMainGame(),new NetApplicationContainer(ip, NetApplicationType.Client),"");
							screenGame.getKryoManageur().getKryoClientManageur().update(screenGame,0);
							getMainGame().setScreen(screenGame);
						}
					}));
	    			getStage().addAction(sequenceAction);
	    			
	    			return super.touchDown(event, x, y, pointer, button);	    			  
	    		}
	    });	    
	    tableCornerbotRight.add(buttonStartClient).width(130).height(50);	
	    
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
								getMainGame().setScreen(new ScreenGame(getMainGame(),new NetApplicationContainer("127.0.0.1", NetApplicationType.Serveur),mapFileAssetChoisie));
							}
						}));
		    			getStage().addAction(sequenceAction);
		    			return super.touchDown(event, x, y, pointer, button);	    			
		    		}
		});
		tableCornerbotRight.add(buttonStartServer).width(130).height(50);	
		
		
		
		
	
		/*
		 * defini la fenetre avec la liste des maps et le visuel de la map
		 */
		
		Table tableMap = new Table(skin);
		tableMap.setFillParent(false);
		tableMap.setPosition(550/2+10, 540/2+120);
		
		windowMap = new Window("Liste des MAPs", skin);
		windowMap.setVisible(true);
		windowMap.setColor(1, 1, 1, 0.75f);
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
	
		
		final List listMap = new List(skin);					
		listMap.setItems(listEntries);
		listMap.getSelection().setMultiple(false);
		listMap.getSelection().setRequired(true);	
		
		ScrollPane scrollPane2 = new ScrollPane(listMap, skin);
		scrollPane2.setFlickScroll(false);
		
		splitPane = new SplitPane(scrollPane, scrollPane2, false, skin, "default-horizontal");
	
		listMap.addCaptureListener(new InputListener(){
			/*
			 * 
			 */
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.postRunnable(new Runnable() {						
					@Override
					public void run() {							
						int index = listMap.getSelectedIndex();
						if (index>=0) {
							//recup la chaine de l'item selectionné
							String nameObjectlist = (String) listMap.getItems().get(index);					
							defineTextureMap(nameObjectlist);
										
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
		Table tableOption = new Table(skin);
		tableOption.setFillParent(false);
		tableOption.setPosition(680, 490);
		getStage().addActor(tableOption);
		
		textfieldIp = new TextField("", skin);
		textfieldIp.addCaptureListener(new InputListener(){
			@Override
			public boolean keyTyped(InputEvent event, char character) {
				setIp(textfieldIp.getText());
				return super.keyTyped(event, character);
			}
		});
		tableOption.add(new Label("IP ", skin));
		tableOption.add(textfieldIp);
		tableOption.row();
		
		
		
		
		
		
		
		
		/*
		 * Liste des Serveurs
		 */		
		Table tableServeur = new Table(skin);
		tableServeur.align(Align.bottom+Align.left);
		tableServeur.setFillParent(false);
		tableServeur.moveBy(10, 10);
		getStage().addActor(tableServeur);
				
		windowServeur = new Window("Liste des Serveurs", skin);
		//windowServeur.setVisible(true);
		windowServeur.setColor(1, 1, 1, 0.75f);
		windowServeur.align(Align.center);
		windowServeur.setMovable(false);
		windowServeur.defaults().space(0);
		tableServeur.add(windowServeur).width(550).height(240);
		
		
		Array<String> listEntriesServeur = new Array<String>();
		listEntriesServeur.add(" Recherche Serveur ... ");
	
		listServeur = new List(skin);					
		listServeur.setItems(listEntriesServeur);
		listServeur.getSelection().setMultiple(false);
		listServeur.getSelection().setRequired(true);	
		listServeur.addCaptureListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				int index = listServeur.getSelectedIndex() ;
				if (index>=0) {					
					setIp((String)listServeur.getItems().get(index));
				//	System.out.println(getIp());
				}else{
					setIp("");
				}
				return super.touchDown(event, x, y, pointer, button);			
			}
		});
		ScrollPane scrollPaneServeur = new ScrollPane(listServeur, skin);
		//scrollPaneServeur.setFlickScroll(false);
		windowServeur.add(scrollPaneServeur).expand().fill();
		
		//recherche des serveur
		rechercheServeurs();
		
		
		
		//map par default
		String nameObjectlist = (String) listMap.getItems().first();		
		defineTextureMap(nameObjectlist);
		actualiseBTN();
		//FIXME
		//getStage().setDebugAll(true);
	}

	
	
	private void rechercheServeurs() {
		new Thread(new Runnable() {			
			@Override
			public void run() {
				listServeur.setItems(listLanServeur());
			}
		}).start();;
	}

	private Array<String> listLanServeur(){		
		java.util.List<InetAddress> addressList = NetKryoNewClientManageur.getLanDiscovery();
		//System.out.println(""+addressList.size()+" Serveur Trouvé");
		Array<String> listEntriesServeur = new Array<String>();
		
		for (InetAddress address : addressList) {
		//	System.out.println(address);
			
			listEntriesServeur.add(address.getHostAddress());
		}
		
		
		return  listEntriesServeur;		
	}
	
	
	
	/**
	 * defini l'image a coté de la liste des map grace au nom dans  listMap
	 * @param nameObjectlist
	 */
	private void defineTextureMap(String nameObjectlist){
		/*
		 * defini l'image a coté de  
		 */
		Class<ConfigPref> cd = ConfigPref.class;
		Field[] fieldsd = cd.getDeclaredFields();
	
		String minmap = ConfigPref.File_MapMiniature;
		for (int i = 0; i < fieldsd.length; i++) {
			if (fieldsd[i].getName().contains(nameObjectlist)) {
				try {
					minmap = (String) fieldsd[i].get(ConfigPref.class);
				} catch (IllegalArgumentException e) {e.printStackTrace();} catch (IllegalAccessException e) {e.printStackTrace();}
			}
		}
		Texture textureMiniature;
		try {
			textureMiniature = getMainGame().getManager().get(minmap, Texture.class);
		} catch (Exception e) {
			textureMiniature = getMainGame().getManager().get(ConfigPref.File_MapMiniature,Texture.class);
		}
		
	
		TextureRegion imageMin = new TextureRegion(textureMiniature);
		final Image imageActorMin = new Image(imageMin);
		
		ScrollPane spane = (ScrollPane) splitPane.getChildren().get(0);
		spane.setWidget(new ScrollPane(imageActorMin));
		
		setMapFileAssetChoisie(getPathfileFormItemName(nameObjectlist));
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
	public String getMapFileAssetChoisie() {return mapFileAssetChoisie;}
	public void setMapFileAssetChoisie(String mapFileAssetChoisie) {
		this.mapFileAssetChoisie = mapFileAssetChoisie;
		actualiseBTN();
	}
	public String getIp() {	return ip;	}
	public void setIp(String ip) {
		this.ip = ip;
		textfieldIp.setText(getIp());
		actualiseBTN();
	}
	
	
	private void actualiseBTN(){
		
		if (!"".equals(getMapFileAssetChoisie())) {
			if (!"".equals(getIp())) {
				buttonStartClient.setVisible(true);
			}else{
				buttonStartClient.setVisible(false);
			}
			
			if (serveurLocalActif()) {
				buttonStartServer.setVisible(false);
			}else{
				buttonStartServer.setVisible(true);
			}
			
			
		}else {
			buttonStartClient.setVisible(false);
			buttonStartServer.setVisible(false);
		}
	}

	private boolean serveurLocalActif(){
		boolean serveurLocal = false;
		for (int i = 0; i < listServeur.getItems().size; i++) {
			if (listServeur.getItems().get(i).equals("127.0.0.1")) {
				serveurLocal = true;
				return serveurLocal;
			}
		}
		return serveurLocal;
	}
	
	@Override
	public void render(float delta) {
		
		 super.render(delta);
		/* getBatch().begin();
		 getBatch().draw(backgroundTexture, 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		 getBatch().end();*/
		 super.renderStage(delta);
		
		
	}
	
	
}
