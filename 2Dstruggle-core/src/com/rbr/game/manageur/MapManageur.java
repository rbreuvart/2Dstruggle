package com.rbr.game.manageur;


import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.rbr.game.entity.map.Zone;
import com.rbr.game.entity.map.ZoneTeleport;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigPref;
import com.rbr.game.utils.MapPropertyLoader;

public class MapManageur {
	
	
	private OrthogonalTiledMapRenderer renderer;
	private TiledMap tileMap;
	
	private Array<Vector2> listVectorSpawn ;
	
	private Array<Zone> listZone;
	
	
	public Array<Vector2> getListVectorSpawn() {
		return listVectorSpawn;
	}
	public void setListVectorSpawn(Array<Vector2> listVectorSpawn) {
		this.listVectorSpawn = listVectorSpawn;
	}
	public TiledMap getTileMap() {
		return tileMap;
	}
	public void setTileMap(TiledMap tileMap) {
		this.tileMap = tileMap;
	}
	public Array<Zone> getListZone() {
		return listZone;
	}
	public void setListZone(Array<Zone> listZone) {
		this.listZone = listZone;
	}
	
	
	
	public MapManageur(ScreenGame screenGame,String fileMapAsset) {
		this.tileMap = screenGame.getMainGame().getManager().get(fileMapAsset);
		float unitScale = (float)1/ConfigPref.pixelMeter;
		renderer = new OrthogonalTiledMapRenderer(tileMap, unitScale);
		
		listVectorSpawn = new Array<Vector2>();
		listZone = new Array<Zone>();
		
		
		TiledMapTileLayer layer = (TiledMapTileLayer) tileMap.getLayers().get(ConfigPref.MapLayerBlockage);
		//charge la map de tout les blocage
		MapPropertyLoader.loadBlocage(screenGame, layer);
		

		MapLayer layerElement = tileMap.getLayers().get(ConfigPref.MapLayerElement);
		//charge les element de tout les Elements
		MapPropertyLoader.loadElement(screenGame,layerElement,listVectorSpawn,listZone);	
	}
	
	
	public Vector2 getRandomSpawn(){
		int lower = 0;
		int higher = listVectorSpawn.size;
		int random = (int) (Math.random() * (higher - lower)) + lower;		
		return listVectorSpawn.get(random).cpy();
	}
	
	
	public void update(ScreenGame screenGame){
		
		if (screenGame.getPlayerManageur().getPlayerLocal()!=null) {
			Vector2 positionjoueurLocal =  screenGame.getPlayerManageur().getPlayerLocal().getGameObject().getBody().getPosition();
			
			for (Zone zone : listZone) {				
				//si joueux local entre dans une zone
				if (zone.getPolygon().contains(positionjoueurLocal.x, positionjoueurLocal.y)) {
					System.out.println("MapManageur.update() contains");
					if (zone instanceof ZoneTeleport) {
						ZoneTeleport zoneTeleport = (ZoneTeleport) zone;
						screenGame.getPlayerManageur().getPlayerLocal().getGameObject().getBody().setTransform(zoneTeleport.getTarget(), 0);
					}
					
				}
			}
		}
		
	}
	
	public void render(ScreenGame screenGame){	
		renderer.setView(screenGame.getCamManageur().getOrthographicCamera());
		renderer.render();
	}

	


	
	
}
