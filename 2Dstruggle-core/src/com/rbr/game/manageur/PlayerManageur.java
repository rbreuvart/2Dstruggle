package com.rbr.game.manageur;

import java.util.HashMap;
import java.util.Map.Entry;

import com.rbr.game.player.Player;
import com.rbr.game.player.PlayerControle;
import com.rbr.game.screen.game.ScreenGame;

public class PlayerManageur {

	private PlayerControle playerLocal;
	
	private HashMap<Integer, Player>  hashMapPlayer ;
	
	public PlayerManageur(){
		hashMapPlayer = new HashMap<Integer, Player>();
	}
	
	
	public void update(float delta,ScreenGame screenGame){		
		for (Entry<Integer, Player> player : hashMapPlayer.entrySet()) {
			player.getValue().update(screenGame,delta);
		}
	}
	
	
	/**
	 * retourne le player Cité
	 */
	public Player getPlayerById(int id){		
		return hashMapPlayer.get(id);		
	}
	public void addPlayerInMap(int id,Player player){
		hashMapPlayer.put(id, player);
		player.setId(id);
		if (playerLocal instanceof PlayerControle) {
			this.playerLocal = (PlayerControle) player;
		}
	}
	
	public void removePlayerById(int id){
		hashMapPlayer.remove(id);
	}
	
	
	public HashMap<Integer, Player> getHashMapPlayer() {
		return hashMapPlayer;
	}
	/*
	public void setHashMapPlayer(HashMap<Integer, Player> hashMapPlayer) {
		this.hashMapPlayer = hashMapPlayer;
	}*/

	public PlayerControle getPlayerLocal() {
		return playerLocal;
	}

	public void setPlayerLocal(PlayerControle playerLocal) {
		this.playerLocal = playerLocal;
	}




}
