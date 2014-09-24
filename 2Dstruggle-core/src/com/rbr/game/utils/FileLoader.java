package com.rbr.game.utils;

import java.lang.reflect.Field;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.rbr.game.MainGame;

public class FileLoader {

	public void addAllFile(MainGame mainGame){
		AssetManager manager = mainGame.getManager();
		Class<ConfigPref> c = ConfigPref.class;
		Field[] fields = c.getDeclaredFields();
 
		for(Field f : fields){
			if (f.getName().contains("File_")) {
				
				try {
					System.out.println(f.getName()+ " "+(String)f.get(ConfigPref.class));
					String pathFile = (String)f.get(ConfigPref.class);
					//	Skin Ui
					if (f.getName().contains("Skin")) {
						manager.load( pathFile, Skin.class);
					}
					//	Tiled MAP
					else if(f.getName().contains("Map")){
						manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
						manager.load(pathFile, TiledMap.class);
					}
					//	IMAGE
					else if (pathFile.contains(".png")||pathFile.contains(".PNG")||pathFile.contains(".jpg")||pathFile.contains(".JPG")) {
						manager.load( pathFile, Texture.class);
					}
					//	Autre
					else{
						
					}
					
				} catch (IllegalArgumentException e) {e.printStackTrace();} catch (IllegalAccessException e) {e.printStackTrace();}
				
			}
			
		}
	}
	
	
}
