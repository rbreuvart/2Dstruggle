package com.rbr.game;

import com.badlogic.gdx.graphics.Color;


public class ConfigPref {
	//Vertion
	public static final String Version = "0.0.0.2";
	public static final int VertionNum = 2;
	
	//Camera
	public static final Float viewPortWidth = 960f;
	public static final Float viewPortHeight = 540f;
	
	//Dev debug
	public static final boolean debug = true;
	public static final boolean debugShapeRender = true;
	public static final boolean debugBatch = true;
	public static final boolean debugControlleur = true;
	public static final boolean debugMsg = true;
	public static final boolean debugMsgErr = true;
	public static final boolean debugStage = true;	
	public enum TypeMsg {
		Debug,ShapeRender,Batch,Controlleur,Stage
	}

	//Physics
	public static final int	pixelMeter = 64;
	public static final short CATEGORY_GHOST 				= 0x0000; 
	public static final short CATEGORY_SCENERY 				= 0x0001; 		
	public static final short CATEGORY_JOUEUR				= 0x0002;
	public static final short CATEGORY_MONSTER				= 0x0004;	
	public static final short CATEGORY_PROJECTILE 			= 0x0008;
	public static final short CATEGORY_LIGHT				= 0x0010;
	
	public static final Color CouleurAmbientLight = new Color(0.2f, 0.2f, 0.2f, 0.2f);

	//Couleur Scene
	public static final Color CouleurGameBackGroundGL = new Color(0.f, 0f, 0.0f, 0f);
	
	//Map
	//Layer
	public static final String MapLayerBlockage = "blockage";
	public static final String MapLayerDecor = "decor";
	public static final String MapLayerElement = "element";
	//Object
	public static final String MapPropTypeSpawn = "SPAWN";
	public static final String MapPropTypePointLight = "POINTLIGHT";
	//Tile
	public static final String TilePropLightBlocage = "TRANSPARENT";
	
	
	//network
	public static final int Net_CommunicationPort = 9090;
	public static final int Net_TimeOut = 4000;//socket close aprés 4sec 
	public static final String Net_PatternPosition = "@P@"; 
	public static final String Net_PatternMsgRecu = "<--";
	public static final String Net_PatternMsgEmit = "-->";
	public static final Color Net_ChatColorRecu = Color.CYAN;
	public static final Color Net_ChatColorEmit = Color.WHITE;
	public static final int Net_ChatMaxaffMsg = 15;
	
	//fichier
	public static final String file_UiSkin = "data/skin/uiskin.json";
	public static final String file_Vaisseau1 = "data/v1f.png";
	public static final String file_RedBox = "data/redbox.png";
	public static final String file_RedCircle = "data/redcircle.png";
	public static final String file_MapTest = "data/map/texturepack.tmx";
	public static final String file_touchBackground = "data/touchBackground.png";
	public static final String file_touchKnob = "data/touchKnob.png";
	
	
	
	
	
}
