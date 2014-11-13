package com.rbr.game.manageur;

import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.rbr.game.entity.physics.GameObject;
import com.rbr.game.net.kryo.NetKryoManageur.NetApplicationType;
import com.rbr.game.player.Player;
import com.rbr.game.screen.game.ScreenGame;

public class HudManageur {
	Matrix4 normalProjection;
	BitmapFont bitmapFont;
	BitmapFontCache  bfc ;
	public HudManageur(ScreenGame screenGame) {
		normalProjection = new Matrix4();
		normalProjection.setToOrtho2D(0, 0, Gdx.graphics.getWidth()/1.5f,Gdx.graphics.getHeight()/1.5f);	
		bitmapFont = new BitmapFont();
		
		bfc = new BitmapFontCache(bitmapFont);
		
	}
	
	public void render(ScreenGame screenGame,SpriteBatch batch){
		batch.setProjectionMatrix(normalProjection);
		batch.begin();
		bitmapFont.draw(batch, "cam("+screenGame.getCamManageur().getOrthographicCamera().position.x+","+screenGame.getCamManageur().getOrthographicCamera().position.y+")", 10, 12);
		bitmapFont.draw(batch, "FPS:"+Gdx.graphics.getFramesPerSecond(), 10, 24);
		if (screenGame.getKryoManageur()!=null) {		
			if (screenGame.getKryoManageur().getNetApplicationType().equals(NetApplicationType.Client)) {
				bitmapFont.draw(batch, "Type : "+"Client",60, 24);
			}else {
				bitmapFont.draw(batch, "Type : "+"Serveur",60, 24);
			}
		}
		int i = 0;
		for (Entry<Integer, Player> entry : screenGame.getPlayerManageur().getHashMapPlayer().entrySet()) {
			bitmapFont.draw(batch, "PlayerManageur idMap:"+entry.getKey()+" name:"+entry.getValue().getGameObject().getName()+" position"+entry.getValue().getGameObject().getBody().getPosition(), 10,36+(i*12));
			
			//bitmapFont.draw(batch, "PlayerManageur id"+entry.getValue().getId()+" name"+entry.getValue().getGameObject().getName()+" key"+entry.getKey()+" position"+entry.getValue().getGameObject().getBody().getPosition(), 10,36+(i*12));
			i++;
			
		}
		
			
		bitmapFont.draw(batch, "nbZone : "+screenGame.getMapManageur().getListZone().size,160, 24);
			
		int y = 70;
		for (GameObject go : screenGame.getGameObjectManageur().getGameObjectArray()) {
		/*if (go instanceof Projectile) {
			
		}*/
			bitmapFont.draw(batch, 	go.getName()+" "+
									go.isRemove()+" "+
									go.getBody().getPosition(), 10, y);
			y = y+10;
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
		
		
		
		batch.end();
		
		screenGame.getStage().act(Gdx.graphics.getDeltaTime());        
		screenGame.getStage().draw();
	}
	
	public void update(ScreenGame screenGame,float delta){
	}
}
