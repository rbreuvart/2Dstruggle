package com.rbr.game.manageur;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.rbr.game.MainGame;
import com.rbr.game.entity.map.Zone;
import com.rbr.game.entity.map.ZoneTeleport;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.ConfigPref;
import com.rbr.game.utils.ConfigPref.TypeMsg;
import com.rbr.game.utils.MapPropertyLoader;

public class MapManageur {
	
	
	private OrthogonalTiledMapRenderer renderer;
	private TiledMap tileMap;
	
	private Array<Vector2> listVectorSpawn ;
	
	private Array<Zone> listZone;
	
	private String fileMapAsset;
	
	private boolean renderOverLayer;
	
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
	public String getVersionMap(){
		return tileMap.getProperties().get("VERSIONMAP", String.class);
	}
	public String getNameMap(){
		return tileMap.getProperties().get("NAMEMAP", String.class);
	}
	public String getTypeMap(){
		return tileMap.getProperties().get("TYPEMAP", String.class);
	}
	public String getAnbientLight(){
		if (tileMap.getProperties().get("AMBIENTLIGHT", String.class)==null) {
			return ConfigPref.CouleurAmbientLight;
		}else{
			return tileMap.getProperties().get("AMBIENTLIGHT", String.class);
		}
		
	}
	public String getFileMapAsset(){
		return fileMapAsset;
	}
	
	ScreenGame screenGame;
	float unitScale;
	
	public MapManageur(ScreenGame screenGame) {
		this.screenGame = screenGame;
		this.unitScale = (float)1/ConfigPref.pixelMeter;
		renderOverLayer = false;
	}
	
	
	public void loadMap(ScreenGame screenGame,String fileMapAsset){
		this.fileMapAsset = fileMapAsset;

		this.tileMap = screenGame.getMainGame().getManager().get(fileMapAsset);
		
		renderer = new OrthogonalTiledMapRenderer(tileMap, unitScale);
		
		listVectorSpawn = new Array<Vector2>();
		listZone = new Array<Zone>();
		
		
		TiledMapTileLayer layer = (TiledMapTileLayer) tileMap.getLayers().get(ConfigPref.MapLayerBlockage);
		//charge la map de tout les blocage
		MapPropertyLoader.loadBlocage(screenGame, layer);
		

		MapLayer layerElement = tileMap.getLayers().get(ConfigPref.MapLayerElement);
		//charge les element de tout les Elements
		MapPropertyLoader.loadElement(screenGame,layerElement,listVectorSpawn,listZone);	
	
		//lightAnbient
		screenGame.getLightManageur().getRayHandler().setAmbientLight(Color.valueOf(getAnbientLight()));
		
		TiledMapTileLayer overLayer = (TiledMapTileLayer) tileMap.getLayers().get(ConfigPref.MapLayerOverLayer);
		if (overLayer==null) {
			renderOverLayer = false;
		}else{
			renderOverLayer = true;
		}
		
	}
	
	public Vector2 getRandomSpawn(){
		int lower = 0;
		int higher = listVectorSpawn.size;
		int random = (int) (Math.random() * (higher - lower)) + lower;		
		return listVectorSpawn.get(random).cpy().scl((float)(unitScale));
	}
	
	
	public void update(ScreenGame screenGame){
		
		if (screenGame.getPlayerManageur().getPlayerLocal()!=null) {
			Vector2 positionjoueurLocal =  screenGame.getPlayerManageur().getPlayerLocal().getGameObject().getBody().getPosition();
			
			for (Zone zone : listZone) {				
				//si joueux local entre dans une zone
				if (zone.getPolygon().contains(positionjoueurLocal.x, positionjoueurLocal.y)) {
				
					if (zone instanceof ZoneTeleport) {
						ZoneTeleport zoneTeleport = (ZoneTeleport) zone;
						screenGame.getPlayerManageur().getPlayerLocal().getGameObject().getBody().setTransform(zoneTeleport.getTarget(), 0);
						System.out.println("Teleport :"+zoneTeleport.getName()+" to "+zoneTeleport.getTarget());
					}
					
				}
			}
		}
		
	}
	
	public void render(ScreenGame screenGame){
		
		if (renderer!=null) {
			renderer.setView(screenGame.getCamManageur().getOrthographicCamera());
		
			renderer.getSpriteBatch().begin();
			renderer.renderTileLayer((TiledMapTileLayer) tileMap.getLayers().get(ConfigPref.MapLayerDecor));
			renderer.renderTileLayer((TiledMapTileLayer) tileMap.getLayers().get(ConfigPref.MapLayerBlockage));
			if (tileMap.getLayers().get(ConfigPref.MapLayerForeGround)!=null) {
				renderer.renderTileLayer((TiledMapTileLayer)tileMap.getLayers().get(ConfigPref.MapLayerForeGround) );
			}
			
			renderer.getSpriteBatch().end();
			
			//renderer.render();
		}else{
			MainGame.printErr("map non Chargé", TypeMsg.ShapeRender);
		}
	}
	public void renderOverLayer(ScreenGame screenGame2){
		if (renderOverLayer) {
			renderer.getSpriteBatch().begin();
			renderer.renderTileLayer((TiledMapTileLayer) getTileMap().getLayers().get(ConfigPref.MapLayerOverLayer));
			renderer.getSpriteBatch().end();
		}
		
	}
	
	public OrthogonalTiledMapRenderer getRenderer() {
		return renderer;
	}
	public void setRenderer(OrthogonalTiledMapRenderer renderer) {
		this.renderer = renderer;
	}

	
	
	
}
