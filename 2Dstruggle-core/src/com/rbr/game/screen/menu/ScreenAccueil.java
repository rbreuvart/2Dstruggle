package com.rbr.game.screen.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.rbr.game.ConfigPref;
import com.rbr.game.ConfigPref.TypeMsg;
import com.rbr.game.MainGame;
import com.rbr.game.screen.AbsctactScreen;
import com.rbr.game.screen.game.ScreenGame;

public class ScreenAccueil extends AbsctactScreen {

	public ScreenAccueil(MainGame mainGame) {
		super(mainGame);
		getBackGroundColor().set(0, 0, 1, 1);
	}
	
	@Override
	public void create() {		
		super.create();
		Skin skin = getMainGame().getManager().get(ConfigPref.file_UiSkin);
	    Table table = new Table(skin);
	    table.setFillParent(true);	  
	    getStage().addActor(table);
		final TextButton buttonStart = new TextButton("Start", skin,"default");
	    buttonStart.addCaptureListener(new InputListener(){
	    		@Override
	    		public boolean touchDown(InputEvent event, float x, float y,int pointer, int button) {
	    			
	    			MainGame.printMsg("BtnStart : touchDown", TypeMsg.Stage);
	    			final SequenceAction sequenceAction  = new SequenceAction();	   
	    			sequenceAction.addAction(Actions.fadeOut(0.3f));
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
		table.add(buttonStart).width(100).height(50);		
		table.row();
		
		
	/*
		getStage().addCaptureListener(new InputListener(){
				@Override
				public boolean mouseMoved(InputEvent event, final float x, final float y) {
					 final SequenceAction sequenceAction  = new SequenceAction();	    		
		    		
		    			sequenceAction.addAction(Actions.delay(0.5f));
		    			sequenceAction.addAction(Actions.run(new Runnable() {
							
							@Override
							public void run() {
								sequenceAction.addAction(Actions.moveTo(x, y,3.5f, Interpolation.elasticOut));
					    		
							}
						}));
		    			buttonStart.addAction(sequenceAction);
					return super.mouseMoved(event, x, y);
				}
		});
		*/
	
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		setBackGroundColor(new Color(Color.MAROON));
	}
	
}
