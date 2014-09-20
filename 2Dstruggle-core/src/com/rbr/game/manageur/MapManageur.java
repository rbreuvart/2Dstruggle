package com.rbr.game.manageur;


import box2dLight.Light;
import box2dLight.PointLight;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.rbr.game.ConfigPref;
import com.rbr.game.screen.game.ScreenGame;

public class MapManageur {
	
	
	private OrthogonalTiledMapRenderer renderer;
	private TiledMap tileMap;
	private float tileSize;
	
	private Array<Vector2> listVectorSpawn ; 
	
	public MapManageur(ScreenGame screenGame) {
		this.tileMap = screenGame.getMainGame().getManager().get(ConfigPref.file_MapTest);
	//	TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(0); // assuming the layer at index on contains tiles
		float unitScale = (float)1/ConfigPref.pixelMeter;
		renderer = new OrthogonalTiledMapRenderer(tileMap, unitScale);
		
		
		setListVectorSpawn(new Array<Vector2>());
		
		
		
		TiledMapTileLayer layer =(TiledMapTileLayer) tileMap.getLayers().get("blockage");
		tileSize = layer.getTileWidth();
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		for(int row = 0; row < layer.getHeight(); row++) {
			for(int col = 0; col < layer.getWidth(); col++) {
				
				// get cell
				Cell cell = layer.getCell(col, row);
				
				// check if cell exists
				if(cell == null) continue;
				if(cell.getTile() == null) continue;
				
				// create a body + fixture from cell
				bdef.type = BodyType.StaticBody;
				bdef.position.set(
					(col + 0.5f) * tileSize / ConfigPref.pixelMeter,
					(row + 0.5f) * tileSize / ConfigPref.pixelMeter
				);
				
				ChainShape cs = new ChainShape();
				Vector2[] v = new Vector2[5];
				v[0] = new Vector2(  tileSize / 2 / ConfigPref.pixelMeter,  -tileSize / 2 / ConfigPref.pixelMeter);
				v[1] = new Vector2(	 tileSize / 2 / ConfigPref.pixelMeter,  tileSize / 2 / ConfigPref.pixelMeter);
				v[2] = new Vector2(  -tileSize / 2 / ConfigPref.pixelMeter,  tileSize / 2 / ConfigPref.pixelMeter);
				v[3] = new Vector2(	-tileSize / 2 / ConfigPref.pixelMeter, -tileSize / 2 / ConfigPref.pixelMeter);
				v[4] = new Vector2(  tileSize / 2 / ConfigPref.pixelMeter,  -tileSize / 2 / ConfigPref.pixelMeter);
				/*
				*/
				
				
				
				cs.createChain(v);
				fdef.friction = 0;
				fdef.shape = cs;
				fdef.filter.categoryBits = ConfigPref.CATEGORY_SCENERY;
				fdef.filter.groupIndex = ConfigPref.CATEGORY_SCENERY;
				
				if (ConfigPref.TilePropLightBlocage.equals(cell.getTile().getProperties().get(ConfigPref.TilePropLightBlocage))) {
					fdef.filter.maskBits = ConfigPref.CATEGORY_LIGHT;
				//	System.out.println("trnasp");
				}else{
					fdef.filter.maskBits = ConfigPref.CATEGORY_SCENERY;
				//	System.err.println("opaque");
				}
				
				fdef.isSensor = false;
				screenGame.getWorldManageur().getWorld().createBody(bdef).createFixture(fdef);
				
				
			}
		}
		Light.setContactFilter(ConfigPref.CATEGORY_SCENERY, ConfigPref.CATEGORY_LIGHT, ConfigPref.CATEGORY_SCENERY);
		
		/*
		Light sun = new PointLight(screenGame.getLightManageur().getRayHandler(), 800);
		sun.setDistance(1000);
		sun.setPosition(new Vector2(-0,50));
		
		sun.setColor(255,249,200, 1f);
			//sun.setXray(true);	
		
		*/
		
		MapLayer layerElement = tileMap.getLayers().get("element");
		for (int j = 0; j <layerElement.getObjects().getCount(); j++) {
		
			MapObject mapObject = layerElement.getObjects().get(j);
			//String name = mapObject.getName();
		//	System.out.println(name);
		
			if (ConfigPref.MapPropTypeSpawn.equals(mapObject.getProperties().get("type"))) {
				
				listVectorSpawn.add(new Vector2(Float.valueOf(mapObject.getProperties().get("x").toString()), Float.valueOf(mapObject.getProperties().get("y").toString())));
			
			}else if(ConfigPref.MapPropTypePointLight.equals(mapObject.getProperties().get("type"))){
				
				Light light = new PointLight(screenGame.getLightManageur().getRayHandler(), 100);
				light.setDistance(10);
				light.setPosition(new Vector2(Float.valueOf(mapObject.getProperties().get("x").toString())/ConfigPref.pixelMeter
						,Float.valueOf(mapObject.getProperties().get("y").toString())/ConfigPref.pixelMeter));
				
				light.setColor(MathUtils.random(), MathUtils.random(),	MathUtils.random(), 1f);
			//	System.out.println(light.getPosition());
			}
			
			else{
				
			}
		}
		
		
	}
	
	
	public void render(ScreenGame screenGame){	
		renderer.setView(screenGame.getCamManageur().getOrthographicCamera());
		renderer.render();
	}


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
	
}
