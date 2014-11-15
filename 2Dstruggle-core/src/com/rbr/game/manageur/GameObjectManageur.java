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
		for (GameObject gameObject : getGameObjectArray()) {
			gameObject.render(screenGam,batch);
		}
	}
	public void update(ScreenGame screenGame,float delta){
		for (GameObject gameObject : getGameObjectArray()) {
			gameObject.update(screenGame,delta);
		}
	}
	
	public GameObjectManageur add(int idPlayer,GameObject gameObject){
		//gameObjectArray.add(gameObject);
		gameObject.setIdArray(idPlayer+"_"+getGameObjectArray().size);
		getGameObjectArray().add(gameObject);		
		return this;
	}
	
	public Array<GameObject> getGameObjectByPlayer(int idPlayer){
		Array<GameObject> listGameObject = new Array<GameObject>();
		for (GameObject entry : getGameObjectArray()) {
			if (entry.getIdArray().split("_")[0].equals(String.valueOf(idPlayer))) {  //  "1_350"   [0] = "1"    "1".equal(1); 	
				//System.out.println("entry.getKey("+entry.getIdArray()+")");
				listGameObject.add(entry);
			}
		}
		
		return listGameObject;
	}
	
	public  GameObject getGObyBody(Body b){
		for (GameObject go : getGameObjectArray()) {
			if (go.getBody().equals(b)) {
				return go;
			}
		}		
		return null;
	}
	public Array<GameObject> getArrayGObyName(String name){
		Array<GameObject> listeRetour = new Array<GameObject>();
		for (GameObject go : getGameObjectArray()) {
			if (go.getName().equals(name)) {
				listeRetour.add(go);
			}
		}		
		if (listeRetour.size == 0) {
			return null;
		}
		return listeRetour;
	}	
	
	public void removeByGameObject(GameObject gameObject) {
		getGameObjectArray().removeValue(gameObject,false);
		/*Iterator<GameObject> it = getGameObjectArray().iterator();

		while(it.hasNext()){
			GameObject entry =  it.next();
			if (entry.equals(gameObject)) {
				getGameObjectArray().removeValue(entry,false);
				break;
			}
		}*/
	}
	
	
}
