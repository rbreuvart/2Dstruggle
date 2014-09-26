package com.rbr.game.screen.menu;

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
import com.rbr.game.screen.AbsctactScreen;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigPref;
import com.rbr.game.utils.ConfigPref.TypeMsg;

public class ScreenChoosePlay extends AbsctactScreen{
	

	
	
	
	
	Window windowMap;
	Table tableWindow;
	
	public ScreenChoosePlay(MainGame mainGame) {
		super(mainGame);
		setBackGroundColor(Color.TEAL);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void create() {		
		super.create();
		Skin skin = getMainGame().getManager().get(ConfigPref.File_UiSkin);
		
		
		
		
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
		
		
		
		
		Table tableCornerbotRight = new Table(skin);
		tableCornerbotRight.setFillParent(false);	 
		tableCornerbotRight.align(Align.bottom+Align.right);
		tableCornerbotRight.setPosition(900, 0);
	    getStage().addActor(tableCornerbotRight);
		
	    final TextButton buttonStartClient = new TextButton("Start Client", skin,"default");
	    buttonStartClient.addCaptureListener(new InputListener(){
	    		@Override
	    		public boolean touchDown(InputEvent event, float x, float y,int pointer, int button) {
	    			
	    			MainGame.printMsg("BtnStart : touchDown", TypeMsg.Stage);
	    			final SequenceAction sequenceAction  = new SequenceAction();	   
	    			sequenceAction.addAction(Actions.fadeOut(0.0f));
	    			sequenceAction.addAction(Actions.run(new Runnable() {
						
						@Override
						public void run() {
							getMainGame().setScreen(new ScreenGame(getMainGame()));
						}
					}));
	    			getStage().addAction(sequenceAction);
	    			
	    			return super.touchDown(event, x, y, pointer, button);	    			
	    		}

				
	    });
	    
	    tableCornerbotRight.add(buttonStartClient).width(100).height(50);	
		
		final TextButton buttonStartServer = new TextButton("Start Server", skin,"default");
		    buttonStartClient.addCaptureListener(new InputListener(){
		    		@Override
		    		public boolean touchDown(InputEvent event, float x, float y,int pointer, int button) {
		    			
		    			MainGame.printMsg("BtnStart : touchDown", TypeMsg.Stage);
		    			final SequenceAction sequenceAction  = new SequenceAction();	   
		    			sequenceAction.addAction(Actions.fadeOut(0.0f));
		    			sequenceAction.addAction(Actions.run(new Runnable() {
							
							@Override
							public void run() {
								getMainGame().setScreen(new ScreenGame(getMainGame()));
							}
						}));
		    			getStage().addAction(sequenceAction);
		    			
		    			return super.touchDown(event, x, y, pointer, button);	    			
		    		}

					
		    });
		    
		    tableCornerbotRight.add(buttonStartServer).width(100).height(50);	
		
		    
		    
		    
		    
		    
		    
			Table tableMap = new Table(skin);
			tableMap.setFillParent(false);
			tableMap.setPosition(600/2+10, 540/2+10);
			getStage().addActor(tableMap);		
			
			windowMap = new Window("Liste des MAPs", skin);
			windowMap.setVisible(true);
			windowMap.setPosition(20, 100);
			windowMap.setMovable(false);
			windowMap.defaults().space(0);
			tableMap.add(windowMap).width(600).height(500);
			
			tableWindow = new Table(skin);
			tableWindow.setFillParent(true);
			windowMap.add(tableWindow).width(600).height(500);
		    
			
			//stage -> tableMap -> windowMap 
			
		
			
		
			Object[] listEntries = {"This is a list entry1", "And another one1", "The meaning of life1", "Is hard to come by1",
					"This is a list entry2", "And another one2", "The meaning of life2", "Is hard to come by2", "This is a list entry3",
					"And another one3", "The meaning of life3", "Is hard to come by3", "This is a list entry4", "And another one4",
					"The meaning of life4", "Is hard to come by4", "This is a list entry5", "And another one5", "The meaning of life5",
					"Is hard to come by5"};
			Texture texture2 = new Texture(Gdx.files.internal("data/map/texturepack.png"));
			TextureRegion image2 = new TextureRegion(texture2);
			Image imageActor = new Image(image2);
			ScrollPane scrollPane = new ScrollPane(imageActor);
			
			@SuppressWarnings("rawtypes")
			List list = new List(skin);
						
			list.setItems(listEntries);
			list.getSelection().setMultiple(false);
			list.getSelection().setRequired(false);
			ScrollPane scrollPane2 = new ScrollPane(list, skin);
			scrollPane2.setFlickScroll(false);
			SplitPane splitPane = new SplitPane(scrollPane, scrollPane2, false, skin, "default-horizontal");
			
			list.addCaptureListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					System.out.println("List touchDown "+event);
					return super.touchDown(event, x, y, pointer, button);
				}
			});
			tableWindow.add(splitPane).fill().expand().colspan(4).maxHeight(470);
			
			//tableWindow.add(list).align(Align.top+Align.left).expand(1, 1);
			/*
			for (MapEntity mapEntity : array) {
				RadioB
			}*/
	}
	
	
	
}
