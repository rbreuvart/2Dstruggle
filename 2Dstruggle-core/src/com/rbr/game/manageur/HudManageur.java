package com.rbr.game.manageur;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.rbr.game.screen.game.ScreenGame;

public class HudManageur {
	Matrix4 normalProjection;
	BitmapFont bitmapFont;
	BitmapFontCache  bfc ;
	public HudManageur() {
		normalProjection = new Matrix4();
		normalProjection.setToOrtho2D(0, 0, Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);	
		bitmapFont = new BitmapFont();
		
		bfc = new BitmapFontCache(bitmapFont);
		
	}
	
	public void render(ScreenGame screenGame,SpriteBatch batch){
		batch.setProjectionMatrix(normalProjection);
		batch.begin();
		bitmapFont.draw(batch, "cam("+screenGame.getCamManageur().getOrthographicCamera().position.x+","+screenGame.getCamManageur().getOrthographicCamera().position.y+")", 10, 12);
		bitmapFont.draw(batch, "FPS:"+Gdx.graphics.getFramesPerSecond(), 10, 24);
		
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
		
		
		
		batch.end();
		
		screenGame.getStage().act(Gdx.graphics.getDeltaTime());        
		screenGame.getStage().draw();
	}
	
	public void update(ScreenGame screenGame,float delta){
	}
}
