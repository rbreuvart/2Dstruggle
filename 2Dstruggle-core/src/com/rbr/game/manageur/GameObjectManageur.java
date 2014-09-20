package com.rbr.game.manageur;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.rbr.game.entity.physics.GameObject;
import com.rbr.game.screen.game.ScreenGame;

public class GameObjectManageur {

	private Array<GameObject> gameObjectArray;
	
	public GameObjectManageur() {
		gameObjectArray = new Array<GameObject>();
	}

	public Array<GameObject> getGameObjectArray() {
		return gameObjectArray;
	}

	public void setGameObjectArray(Array<GameObject> gameObjectArray) {
		this.gameObjectArray = gameObjectArray;
	}
	
	public void render(ScreenGame screenGam,SpriteBatch batch ){
		for (GameObject gameObject : gameObjectArray) {
			gameObject.render(screenGam,batch);
		}
	}
	public void update(ScreenGame screenGame,float delta){
		for (GameObject gameObject : gameObjectArray) {
			gameObject.update(screenGame,delta);
		}
	}
	public GameObjectManageur add(GameObject gameObject){
		gameObjectArray.add(gameObject);
		return this;
	}
	public  GameObject getGObyBody(Body b){
		for (GameObject go : gameObjectArray) {
			if (go.getBody().equals(b)) {
				return go;
			}
		}		
		return null;
	}
	public Array<GameObject> getArrayGObyName(String name){
		Array<GameObject> listeRetour = new Array<GameObject>();
		for (GameObject go : gameObjectArray) {
			if (go.getName().equals(name)) {
				listeRetour.add(go);
			}
		}		
		if (listeRetour.size == 0) {
			return null;
		}
		return listeRetour;
	}
}
