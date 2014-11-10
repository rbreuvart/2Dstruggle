package com.rbr.game.utils;

public class ConfigPhysics {

	
	//Physics 
	
	//Category	
	public static final short CATEGORY_GHOST 				= 0x0000; 
	public static final short CATEGORY_SCENERY 				= 0x0001; 		
	public static final short CATEGORY_ALLIER				= 0x0002;
	public static final short CATEGORY_ENEMY				= 0x0004;	
	public static final short CATEGORY_PROJECTILE 			= 0x0008;
	public static final short CATEGORY_LIGHT				= 0x0010;

	//Scene Light
	public static final short SceneLight_Category = CATEGORY_LIGHT;
	public static final short SceneLight_Group = 0;
	public static final short SceneLight_Mask = CATEGORY_SCENERY;
	
	//Scene TileTranslucide
	public static final short SceneTileTranslucide_Category = CATEGORY_SCENERY|CATEGORY_LIGHT;
	public static final short SceneTileTranslucide_Group = CATEGORY_SCENERY;
	public static final short SceneTileTranslucide_Mask =  CATEGORY_SCENERY|CATEGORY_ALLIER|CATEGORY_ENEMY;
	
	//Scene TileOpaque
	public static final short SceneTileOpaque_Category = CATEGORY_SCENERY;
	public static final short SceneTileOpaque_Group = CATEGORY_SCENERY;
	public static final short SceneTileOpaque_Mask = CATEGORY_LIGHT|CATEGORY_PROJECTILE|CATEGORY_ALLIER|CATEGORY_ENEMY;
		
	
	//Player Local (Allier)
	public static final short PlayerLocal_Category = CATEGORY_ALLIER;
	public static final short PlayerLocal_Group = 0;
	public static final short PlayerLocal_Mask = CATEGORY_ENEMY|CATEGORY_SCENERY;
			
	//Player Multi (Enemy)	
	public static final short PlayerMulti_Category = CATEGORY_ENEMY;
	public static final short PlayerMulti_Group = 0;
	public static final short PlayerMulti_Mask = CATEGORY_ALLIER|CATEGORY_SCENERY;
	
	
	//ProjectileAllier
	public static final short ProjectileAllier_Category = CATEGORY_PROJECTILE|CATEGORY_ALLIER;
	public static final short ProjectileAllier_Group = CATEGORY_ALLIER;
	public static final short ProjectileAllier_Mask = 0;
		
	//GameObject Circle
//	public static final short GameObjectCircle_Category = CATEGORY_SCENERY|CATEGORY_LIGHT;
	
	//GameObject Wall
//	public static final short GameObjectWall_Category = CATEGORY_SCENERY;
	
	//GameObject ChaineShape
//	public static final short GameObjectChaineShape_Category = CATEGORY_SCENERY;
	
	//GameObject Square
//	public static final short GameObjectSquare_Category = CATEGORY_SCENERY;
	
}
