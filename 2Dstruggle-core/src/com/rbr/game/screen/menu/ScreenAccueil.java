package com.rbr.game.screen.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.rbr.game.MainGame;
import com.rbr.game.screen.AbsctactScreen;
import com.rbr.game.utils.ConfigPref;

public class ScreenAccueil extends AbsctactScreen {

	public ScreenAccueil(MainGame mainGame) {
		super(mainGame);
		setBackGroundColor(new Color(Color.MAROON));
	}
	
	@Override
	public void create() {		
		super.create();
		Skin skin = getMainGame().getManager().get(ConfigPref.File_UiSkin);
	    Table table = new Table(skin);
	    table.setFillParent(true);	  
	    getStage().addActor(table);
			
		table.row();
		
		final TextButton buttonChoose = new TextButton("Jouer", skin,"default");
		buttonChoose.addCaptureListener(new InputListener(){
			 @Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
					getMainGame().setScreen(new ScreenChoosePlay(getMainGame()));
					
				return super.touchDown(event, x, y, pointer, button);
			}
			
			 
		 });
		table.add(buttonChoose).width(100).height(50);	
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
		
	}
	
}
