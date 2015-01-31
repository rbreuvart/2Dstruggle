package com.rbr.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.rbr.game.MainGame;
import com.rbr.game.screen.AbsctactScreen;
import com.rbr.game.utils.ConfigPref;

public class ScreenAccueil extends AbsctactScreen {
	Texture backgroundTexture;
	public ScreenAccueil(MainGame mainGame) {
		super(mainGame);
		setBackGroundColor(new Color(Color.MAROON));
	}
	
	@Override
	public void create() {		
		super.create();
		backgroundTexture = new Texture(Gdx.files.internal("data/menu/intro.png"));
		Skin skin = getMainGame().getManager().get(ConfigPref.File_UiSkin);
	    Table table = new Table(skin);
	    table.setFillParent(true);
	    table.setHeight(Gdx.graphics.getHeight());
	    table.setWidth(Gdx.graphics.getWidth());
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
		
		
	/*	Table infoTable = new Table(skin);
		infoTable.setFillParent(true);
		infoTable.add(buttonChoose).width(Gdx.graphics.getWidth()).height(Gdx.graphics.getHeight());	
		getStage().addActor(infoTable);
		*/
		Label lblAuteur = new Label("fait par Remi Breuvart \n rbreuvart@gmail.com", skin);
		lblAuteur.setColor(Color.RED);
		lblAuteur.setPosition(Gdx.graphics.getWidth()*0.8f, Gdx.graphics.getHeight()*0.05f);
	    getStage().addActor(lblAuteur);
		
		
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
		 getBatch().begin();
		 getBatch().draw(backgroundTexture, 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		 getBatch().end();
		 super.renderStage(delta);
		
	}
	
}
