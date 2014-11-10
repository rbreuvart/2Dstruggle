package com.rbr.game.utils;

import box2dLight.Light;
import box2dLight.PointLight;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.rbr.game.entity.map.Zone;
import com.rbr.game.entity.map.ZoneTeleport;
import com.rbr.game.screen.game.ScreenGame;

public class MapPropertyLoader {

	public static void loadBlocage(ScreenGame screenGame,TiledMapTileLayer layer){
		
		float tileSize = layer.getTileWidth();
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
				
				cs.createChain(v);
				fdef.friction = 0;
				fdef.shape = cs;
				
				
				//si la tuile laise passer la lumiére ou non
				if ("true".equals(cell.getTile().getProperties().get(ConfigPref.TilePropLightBlocage))) {
					fdef.filter.categoryBits = ConfigPhysics.SceneTileTranslucide_Category;
					fdef.filter.groupIndex = ConfigPhysics.SceneTileTranslucide_Group;
					fdef.filter.maskBits = ConfigPhysics.SceneTileTranslucide_Mask;
				}else{
					
					fdef.filter.categoryBits = ConfigPhysics.SceneTileOpaque_Category;
					fdef.filter.groupIndex = ConfigPhysics.SceneTileOpaque_Group;
					fdef.filter.maskBits = ConfigPhysics.SceneTileOpaque_Mask;
				}
				
				fdef.isSensor = false;
				screenGame.getWorldManageur().getWorld().createBody(bdef).createFixture(fdef);
			}
		}
//		Light.setContactFilter(ConfigPref.CATEGORY_SCENERY, ConfigPref.CATEGORY_LIGHT, ConfigPref.CATEGORY_SCENERY);
		Light.setContactFilter(	(short)(ConfigPhysics.SceneLight_Category),
								(short)(ConfigPhysics.SceneLight_Group),
								(short)(ConfigPhysics.SceneLight_Mask));
		
	}
	
	
	public static void loadElement(ScreenGame screenGame, MapLayer layerElement, Array<Vector2> listVectorSpawn, Array<Zone> listZone){
		
		for (int j = 0; j <layerElement.getObjects().getCount(); j++) {
			
			MapObject mapObject = layerElement.getObjects().get(j);
		
		
			if (ConfigPref.MapTypeSpawn.equals(mapObject.getProperties().get("type"))) {
				loadSpawn(screenGame,mapObject,listVectorSpawn);
			}else if(ConfigPref.MapTypePointLight.equals(mapObject.getProperties().get("type"))){
				loadPointLight(screenGame,mapObject);
			}else if(mapObject instanceof RectangleMapObject){
				RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject;
			
				//Teleporteur
				if (ConfigPref.MapTeleporteurType.equals(rectangleMapObject.getProperties().get("type"))) {
					loadTeleport(screenGame,rectangleMapObject,layerElement,listZone);
				}
			}
		}
		
	}
	/**
	 * ajoute un spawn dans la collection
	 * @param screenGame
	 * @param listVectorSpawn
	 * @param mapObject
	 * @param listVectorSpawn 
	 */
	private static void loadSpawn(ScreenGame screenGame, MapObject mapObject, Array<Vector2> listVectorSpawn){
		Vector2 spawn = new Vector2(Float.valueOf(mapObject.getProperties().get("x").toString())+ConfigPref.MapTypeSpawnOffsetPosition.x,
									Float.valueOf(mapObject.getProperties().get("y").toString())+ConfigPref.MapTypeSpawnOffsetPosition.y);
		listVectorSpawn.add(spawn);
	}
	
	/**
	 * ajoute une lumiere au rendu
	 * @param screenGame
	 * @param mapObject
	 */
	private static void loadPointLight(ScreenGame screenGame, MapObject mapObject){		
		Light light = new PointLight(screenGame.getLightManageur().getRayHandler(), (int) Float.parseFloat(mapObject.getProperties().get(ConfigPref.MapPointLightQuality, String.class)));
		light.setDistance(		Float.parseFloat(mapObject.getProperties().get(ConfigPref.MapPointLightDistance, String.class)));
		light.setSoftnessLength(Float.parseFloat(mapObject.getProperties().get(ConfigPref.MapPointLightSoftnesslen, String.class)));
		
		light.setPosition(new Vector2(	Float.valueOf(mapObject.getProperties().get("x").toString())/ConfigPref.pixelMeter,
										Float.valueOf(mapObject.getProperties().get("y").toString())/ConfigPref.pixelMeter));
		
		light.setPosition(light.getPosition().add(ConfigPref.MapPointLightOffsetPosition));
		
		float r = Float.parseFloat(mapObject.getProperties().get(ConfigPref.MapPointLightColorRed, String.class));
		float g = Float.parseFloat(mapObject.getProperties().get(ConfigPref.MapPointLightColorGreen, String.class));
		float b = Float.parseFloat(mapObject.getProperties().get(ConfigPref.MapPointLightColorBlue, String.class));
		float a = Float.parseFloat(mapObject.getProperties().get(ConfigPref.MapPointLightColorAlpha, String.class));
		light.setColor(r,g,b,a);
		if (Boolean.parseBoolean(mapObject.getProperties().get(ConfigPref.MapPointLightStatic, String.class))) {
			light.setStaticLight(true);
		}
		if (Boolean.parseBoolean(mapObject.getProperties().get(ConfigPref.MapPointLightXray, String.class))) {
			light.setXray(true);
		}
		if (Boolean.parseBoolean(mapObject.getProperties().get(ConfigPref.MapPointLightSoft, String.class))) {
			light.setSoft(true);
		}else{
			light.setSoft(false);
		}
	}
	
	/**
	 * ajoute une zone du teleport
	 * @param screenGame
	 * @param rectangleMapObject
	 * @param layerElement
	 * @param listZone 
	 */
	private static void loadTeleport(ScreenGame screenGame, RectangleMapObject rectangleMapObject, MapLayer layerElement, Array<Zone> listZone){
		String tpTargetName = (String) rectangleMapObject.getProperties().get(ConfigPref.MapTeleporteurTarget);
		
		MapObject mapObjectTPTarget =  layerElement.getObjects().get(tpTargetName);
		
		float targetx = (Float) mapObjectTPTarget.getProperties().get("x")/ConfigPref.pixelMeter+ConfigPref.MapPointLightOffsetPosition.x;
		float targety = (Float) mapObjectTPTarget.getProperties().get("y")/ConfigPref.pixelMeter+ConfigPref.MapPointLightOffsetPosition.y;
		
		float unitScale = (float)1/ConfigPref.pixelMeter;
		Polygon polygon = new Polygon(new float[]{	0,0,
													rectangleMapObject.getRectangle().width*unitScale,0,
													rectangleMapObject.getRectangle().width*unitScale,rectangleMapObject.getRectangle().height*unitScale,
													0,rectangleMapObject.getRectangle().height*unitScale});
		if (rectangleMapObject.getProperties().get("rotation",float.class)!=null) {			
			polygon.setRotation(-(float)rectangleMapObject.getProperties().get("rotation",float.class));
		}
	    polygon.setPosition(rectangleMapObject.getRectangle().x/ConfigPref.pixelMeter, rectangleMapObject.getRectangle().y/ConfigPref.pixelMeter);
	 
	    ZoneTeleport zoneTeleport = new ZoneTeleport(polygon,rectangleMapObject.getName(), new Vector2(targetx, targety));
	  
	    listZone.add(zoneTeleport);
	}
}
