package com.rbr.game.manageur;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;
import com.rbr.game.entity.physics.FabriqueAll;
import com.rbr.game.entity.physics.GameObject;
import com.rbr.game.player.Player;
import com.rbr.game.player.PlayerLocal;
import com.rbr.game.player.PlayerMulti;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigPhysics;
import com.rbr.game.utils.ConfigPref;

public class PlayerManageur {

	private PlayerLocal playerLocal;	
	private HashMap<Integer, Player>  hashMapPlayer ;
	
	public PlayerManageur(){
		hashMapPlayer = new HashMap<Integer, Player>();
	}
	
	
	public void update(float delta,ScreenGame screenGame){		
		for (Entry<Integer, Player> player : hashMapPlayer.entrySet()) {
		
			if (player.getValue().isSpawn()) {
				player.getValue().update(screenGame,delta);	
			}
		}
		
	}
	
	
	public void render(ScreenGame screenGame, SpriteBatch spriteBatch, ShapeRenderer shapeRenderer){
		for (Entry<Integer, Player> player : hashMapPlayer.entrySet()) {
			
			if (player.getValue().isSpawn()) {
				player.getValue().render(screenGame,spriteBatch,shapeRenderer);		
			}
		}
	}
	
	
	/**
	 * retourne le player Cité
	 */
	public Player getPlayerById(int id){		
		return hashMapPlayer.get(id);		
	}
	public Player getPlayerByGameObject(GameObject contact) {
		for (Entry<Integer, Player> player : hashMapPlayer.entrySet()) {
			if (player.getValue().getGameObject().equals(contact)) {
				return player.getValue();
			}
		}
		return null;
	}
	
	public void addPlayerInMap(int id,Player player){
		hashMapPlayer.put(id, player);
		player.setId(id);
		if (player instanceof PlayerLocal) {
			this.playerLocal = (PlayerLocal) player;
		}
	}
	
	public void spawnPlayer(ScreenGame screenGame,int id,Vector2 position){		
		Player player = getPlayerById(id);
		player.getGameObject().getBody().setTransform(position, 0);
		player.setSpawn(true);
		player.setLife(player.getLifeMax());
		
		player.getGameObject().getBody().setLinearVelocity(new Vector2());
		player.getGameObject().getBody().setLinearDamping(0);
		player.getGameObject().getBody().setAngularVelocity(0);
		player.getGameObject().getBody().setTransform(screenGame.getMapManageur().getRandomSpawn(), 0);

	}
	
	
	public PlayerLocal createLocalPlayer(ScreenGame screenGame,int id,String name, String color){
		Sprite spritePlayer = new Sprite(  screenGame.getMainGame().getManager().get(ConfigPref.File_BodyPerso,Texture.class));
	
		PlayerLocal playerLocal= new PlayerLocal(FabriqueAll.creationGameObjectCircle(screenGame.getWorldManageur(), 
				spritePlayer,getDefaultPlayerPosition() ,"player:"+id,
				0.45f,ConfigPref.pixelMeter,1,0.95f,0f,
				ConfigPhysics.PlayerLocal_Category,
				ConfigPhysics.PlayerLocal_Group,
				ConfigPhysics.PlayerLocal_Mask),name);		
	
		playerLocal.setColor(Color.valueOf(color));
		
		//screenGame.getGameObjectManageur().getGameObjectArray().add(playerControle.getGameObject());
	//	System.out.println("Ajout du joueur local dans la map id:"+id);
		screenGame.getGameObjectManageur().add(id,playerLocal.getGameObject());
		screenGame.getPlayerManageur().addPlayerInMap(id,playerLocal);
		
		return playerLocal;
	}
	
	public PlayerMulti createMultiPlayer(ScreenGame screenGame,Connection connection, int id,String name, String color){
		Sprite spritePlayerMulti = new Sprite( screenGame.getMainGame().getManager().get(ConfigPref.File_BodyPerso,Texture.class));
		spritePlayerMulti.setColor(Color.valueOf(color));
		
		PlayerMulti playerMulti =  new PlayerMulti(FabriqueAll.creationGameObjectCircle(screenGame.getWorldManageur(), 
					spritePlayerMulti,getDefaultPlayerPosition(),"playerMulti:"+id,
					0.45f,ConfigPref.pixelMeter,1,0.95f,0f,
					ConfigPhysics.PlayerMulti_Category,
					ConfigPhysics.PlayerMulti_Group,
					ConfigPhysics.PlayerMulti_Mask),name);
		playerMulti.setColor(Color.valueOf(color));
		
			//screenGame.getGameObjectManageur().getGameObjectArray().add(playerMulti.getGameObject());
		playerMulti.setConnection(connection);
		//System.out.println("Ajout du joueur Multi dans la map id:"+connection.getID());
		screenGame.getGameObjectManageur().add(id,playerMulti.getGameObject());
		screenGame.getPlayerManageur().addPlayerInMap(id,playerMulti);
		return playerMulti;
	}
	
	public Vector2 getDefaultPlayerPosition(){
		return new Vector2(0, 0);
	}
	public void removePlayerById(int id){
		hashMapPlayer.remove(id);
	}
	public HashMap<Integer, Player> getHashMapPlayer() {
		return hashMapPlayer;
	}
	public PlayerLocal getPlayerLocal() {
		return playerLocal;
	}
	public void setPlayerLocal(PlayerLocal playerLocal) {
		this.playerLocal = playerLocal;
	}


	
}
