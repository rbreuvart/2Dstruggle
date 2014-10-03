package com.rbr.game.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;


public class ConfigPref {
	//Vertion
	public static final String Version = "0.0.0.3";
	public static final int VertionNum = 3;
	
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

	//Player
	
	public static final float Player_MAX_VELOCITY = 3.5f;
	
	
	
	
	//Couleur Scene
	public static final Color CouleurGameBackGroundGL = new Color(0.f, 0f, 0.0f, 0f);
	
	//Map
	public static final String MapVersion = "VERSIONMAP";
	public static final Vector2 MapTileOffsetPosition = new Vector2((float)32/pixelMeter,(float)32/pixelMeter);

	//Layer
	public static final String MapLayerBlockage = "blockage";
	public static final String MapLayerDecor = "decor";
	public static final String MapLayerElement = "element";
	//Object
	
	//spawn
	public static final String MapTypeSpawn = "SPAWN";

	public static final Vector2 MapTypeSpawnOffsetPosition = MapTileOffsetPosition;
	
	//light
	//pointLight
	public static final String MapTypePointLight = "POINTLIGHT";
	public static final Vector2 MapPointLightOffsetPosition = MapTileOffsetPosition;
	public static final String MapPointLightColorRed 	= "ColorR";
	public static final String MapPointLightColorGreen	= "ColorG";
	public static final String MapPointLightColorBlue	= "ColorB";
	public static final String MapPointLightColorAlpha	= "ColorA";	
	public static final String MapPointLightDistance	= "Distance";
	public static final String MapPointLightSoftnesslen	= "Softness";
	public static final String MapPointLightStatic		= "Static";
	public static final String MapPointLightXray		= "Xray";
	public static final String MapPointLightSoft		= "Soft";
	public static final String MapPointLightQuality 	= "Quality";
	
	//Tile
	public static final String TilePropLightBlocage = "TRANSPARENT";
	
	//Teleport les teleport sont des zonnes
	public static final String MapTeleporteurType = "TELEPORT";
	public static final String MapTeleporteurTarget = "TargetName";
	
	
	//Target
	public static final String MapTargetType = "TARGET";
	
	
	//network
	public static final int Net_CommunicationPortTCP = 54555;
	public static final int Net_CommunicationPortUDP = 54777;
	
	public static final int Net_TimeOut = 4000;//socket close aprés 4sec 
	

	public static final Color Net_ChatColorRecu = Color.CYAN;
	public static final Color Net_ChatColorEmit = Color.WHITE;
	public static final int Net_ChatMaxaffMsg = 15;
	
	
	/* fichier
	 * 
	 */
	public static final String File_UiSkin 	= "data/skin/uiskin.json";
	
	public static final String File_BodyJson 	= "data/body/test.json";	

	public static final String File_BodyPerso 	= "data/body/bodyPerso.png";
	
	public static final String File_RedBox 		= 	"data/debug/redbox.png";
	public static final String File_RedCircle	= 	"data/debug/redcircle.png";
	
	public static final String File_TouchBackground = "data/ui/touchBackground.png";
	public static final String File_TouchKnob 		= "data/ui/touchKnob.png";
	
	
	public static final String File_MapTest 			= "data/map/texturepack.tmx";		
	public static final String File_MapTestMiniature 	= "data/map/minmap_texturepack.png";
	
	public static final String File_MapDeDust2 				= "data/map/ninjamap.tmx";		
	public static final String File_MapDeDust2Miniature 	= "data/map/minmap_ninjamap.png";
	
	public static final String File_MapMiniature 	= 	"data/map/minmap.png";
	
	
	//pattern pour fichier
	public static final String Pattern_Map 			=	".tmx";
	public static final String Pattern_TexturePng 	=	".png";
	public static final String Pattern_TextureJpg 	=	".jpg";
	
	public static final String PatternField_Skin =	"Skin";
	public static final String PatternField_File =	"File_";
	
	
}
