package com.rbr.game.entity.physics;


import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.rbr.game.manageur.WorldManageur;
import com.rbr.game.utils.BodyEditorLoader;
import com.rbr.game.utils.ConfigPref;

public class FabriqueAll {

	
	public static GameObject2dCircle creationGameObjectCircle(WorldManageur worldManageur, Sprite s,Vector2 position,String name, float radius,int pixelmeter) {
		BodyDef Def;
		Body Body;
		CircleShape DynamicShape;
		FixtureDef fixtureDef;
		Sprite sprite = new Sprite(s);
		sprite.scale(pixelmeter);
		Def = new BodyDef();	
		Def.type = BodyType.DynamicBody;
		Def.position.set(position.x/pixelmeter, position.y/pixelmeter);
		
		Body = worldManageur.getWorld().createBody(Def);
		
		DynamicShape = new CircleShape();	
		DynamicShape.setRadius(radius);
	
		fixtureDef = new FixtureDef();
		fixtureDef.shape = DynamicShape;
		fixtureDef.density = 1f;
		fixtureDef.friction = 0.95f;
		fixtureDef.restitution = 0.01f;
		fixtureDef.filter.categoryBits = ConfigPref.CATEGORY_SCENERY+ConfigPref.CATEGORY_LIGHT;
		//fixtureDef.filter.maskBits = ;
		Body.createFixture(fixtureDef);
		DynamicShape.dispose();
		
		return new GameObject2dCircle(new GameObject(name,Def, Body, fixtureDef), sprite,radius/pixelmeter);
	}
	
	public static GameObjectWall creationStaticWall(World w, Sprite s, float x, float y,float width, float height,String name,int pixelMeter) {
		
		
		BodyDef wallDef;
		Body wallBody;
		PolygonShape wallShape;
		Sprite sprite = new Sprite(s);
		sprite.setScale(width/4100, height/200);
	//	sprite.setAlpha(0.5F);
		wallDef = new BodyDef();		
		wallDef.position.set((x+width/2)/pixelMeter,(y+height/2)/pixelMeter);
		
		wallBody = w.createBody(wallDef);
		
		
		wallShape = new PolygonShape();		
		wallShape.setAsBox((width/2)/pixelMeter, (height/2)/pixelMeter);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = wallShape;
		fixtureDef.friction = 1;
		fixtureDef.restitution = 0;
		fixtureDef.filter.categoryBits = ConfigPref.CATEGORY_SCENERY;
		wallBody.createFixture(fixtureDef);
		
		//GameObjectWall wa = new GameObjectWall("wall",new Sprite(sprite),wallDef, wallBody,fixtureDef, (width/2)/pixelMeter,(height/2)/pixelMeter);
		GameObjectWall wa = new GameObjectWall(new GameObject(name, wallDef, wallBody, fixtureDef),sprite);
		wallShape.dispose();
		return wa;
	}
	
	public static GameObjectChaineShape creationStaticChaineShape(World w,	float x, float y,String name, Vector2[] veclist,int pixelMeter) {
		
		BodyDef def;
		Body body;
		ChainShape shape;
		
		def = new BodyDef();
		def.position.set(x/pixelMeter, y/pixelMeter);
		
		body = w.createBody(def);
		
		shape = new ChainShape();
		shape.createChain(veclist);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = ConfigPref.CATEGORY_SCENERY;
		
		body.createFixture(fixtureDef);
		shape.dispose();
		return new GameObjectChaineShape(name,def, body,fixtureDef);
	}

	public static GameObject2dSquare creationGameObjectSquare(World w,String name,Sprite s,float x,float y,float forcedwidth,float forcedheight,int pixelMeter,BodyType bodyType) {
		
		BodyDef squareBodyDef;
		Body squareBody ;
		PolygonShape squareDynamicShape;
		FixtureDef fixtureDef;
		
		Sprite sprite = new Sprite(s);
		
		
		squareBodyDef = new BodyDef();
		squareBodyDef.type = bodyType;
		squareBodyDef.position.set(x/pixelMeter, y/pixelMeter);
		
		squareDynamicShape = new PolygonShape();
		
		if (forcedwidth!=0 & forcedheight!=0) {
			squareDynamicShape.setAsBox((forcedwidth/2)/pixelMeter,(forcedheight/2)/pixelMeter);
		}else{
			squareDynamicShape.setAsBox((sprite.getWidth()/2)/pixelMeter, (sprite.getHeight()/2)/pixelMeter);
		}
		
		fixtureDef = new FixtureDef();
		fixtureDef.shape = squareDynamicShape;
		fixtureDef.density = 2.5f;
		fixtureDef.friction = 0.25f;
		fixtureDef.restitution = 0.1f;
		fixtureDef.filter.categoryBits = ConfigPref.CATEGORY_SCENERY;
		
		squareBody = w.createBody(squareBodyDef);
		squareBody.createFixture(fixtureDef);
		
		squareDynamicShape.dispose();
		return new GameObject2dSquare(new GameObject(name,squareBodyDef, squareBody, fixtureDef),sprite);
	}
	
	//FIXME le jSon reader ne marche pas
	public static GameObjectSprite creationBody(String name,World world,FileHandle fileHandle ,Vector2 position,Sprite sprite,short CATEGORY,short GROUP,short MASK){		
			
		BodyEditorLoader loader = new BodyEditorLoader(ConfigPref.File_BodyJson);	
		
	    BodyDef bd = new BodyDef();
	    bd.position.set(0, 0);
	    bd.type = BodyType.DynamicBody;
	    
	    // 2. Create a FixtureDef, as usual.
	    FixtureDef fd = new FixtureDef();
	    fd.density = 1;
	    fd.friction = 0.5f;
	    fd.restitution = 0.3f;
	 
	    // 3. Create a Body, as usual.
	    Body body = world.createBody(bd);
	    
	    loader.attachFixture(body, "test01", fd, 1);
	 
	    GameObjectSprite gameObjSp = new GameObjectSprite(new GameObject(name, bd, body, fd), sprite) ;	 
		return gameObjSp ;		
	}
	
	
/*
	public Ship creationPersonnage(Vector2 position,short CATEGORIE_JOUEUR){		
		int scale = 4;			
		BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("data/v1f.json"));	
		 // 1. Create a BodyDef, as usual.
	    BodyDef bd = new BodyDef();
	   // bd.position.set(position.x/GameConfigPref.pixelMeter,position.y/GameConfigPref.pixelMeter);
	    bd.position.set(position.cpy());
	    bd.type = BodyType.DynamicBody;	 
	    // 2. Create a FixtureDef, as usual.
	    FixtureDef fd = new FixtureDef();
	    fd.density = 1;
	    fd.friction = 1f;
	    fd.filter.categoryBits = CATEGORY_JOUEUR_1;//defini sont groupe
	    fd.filter.maskBits = (short) (CATEGORY_SCENERY|CATEGORY_PROJECTILE);//colisionavecles groupe :

	    
		// 3. Create a Body, as usual.
	    Body body = world.createBody(bd);	    
	    loader.attachFixture(body, "vaisseau1", fd, scale);
	 
	   /* Ship s = new Ship("personnage",  		bd,
							    		body,
							    		fd,
							    		new Sprite(Ressources.getListSprite().get("PERSO_smoking")),
							    		new Life(5, 5));*/

	
	    
//	    Ship ship = new Ship("vaisseau1", bd, body, fd,new Sprite((Texture) mainGame.getManager().get(GameConfigPref.file_Vaisseau1)));
//	    Ship ship = new Ship("vaisseau1", bd, body, fd,new Sprite());
	/*    
		return ship ;		
	}
	*/




/*
	public Joueur creationJoueur(Personnage perso,KeymapJoueur keyj,Score score){
		Joueur j = new Joueur(score, keyj, perso);
		
		return j;		
	}
	
	public Projectile creationProjectile(Vector2 position,Personnage personnage,LinkedList<EffectPotion> listeEffects){
		//
		
		int scale = 1;		
		BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("data/potion/potion.json"));	
		 // 1. Create a BodyDef, as usual.
	    BodyDef bd = new BodyDef();
	    bd.position.set(position.x/Level.pixelpermeter,position.y/Level.pixelpermeter);
	    bd.type = BodyType.DynamicBody;	 
	    // 2. Create a FixtureDef, as usual.
	    FixtureDef fd = new FixtureDef();
	    fd.density = 0.1f;
	    fd.friction = 0.01f;	   
	    fd.restitution = 0f;	
	    fd.filter.categoryBits = CATEGORY_PROJECTILE;
	  //  fd.isSensor = true;
	    if (personnage.getFixtureDef().filter.categoryBits == CATEGORY_JOUEUR_1) {
	    	 fd.filter.maskBits =  CATEGORY_SCENERY|CATEGORY_JOUEUR_2|CATEGORY_PROJECTILE;
		}else{
			 fd.filter.maskBits =  CATEGORY_SCENERY|CATEGORY_JOUEUR_1|CATEGORY_PROJECTILE;
		}
	  
		// 3. Create a Body, as usual.
	    Body body = world.createBody(bd);	    
	    loader.attachFixture(body, "potion", fd, scale);
		
		Projectile proj = new Projectile("projetile",
									bd,
									body,
									fd,
									new Sprite(Ressources.getListSprite().get("POTION_degat"))
									,
									listeEffects);
		
		return proj;
		
	}
	
	public GameObject2dSquare creationGameObject2dSquare(World w,Sprite s,float x,float y,float forcedwidth,float forcedheight) {
		
		BodyDef squareBodyDef;
		Body squareBody ;
		PolygonShape squareDynamicShape;
		FixtureDef fixtureDef;
		
		Sprite sprite = new Sprite(s);
		
		
		squareBodyDef = new BodyDef();
		squareBodyDef.type = BodyType.DynamicBody;
		squareBodyDef.position.set(x/Level.pixelpermeter, y/Level.pixelpermeter);
		
		squareDynamicShape = new PolygonShape();
		
		if (forcedwidth!=0 & forcedheight!=0) {
			squareDynamicShape.setAsBox((forcedwidth/2)/Level.pixelpermeter,(forcedheight/2)/Level.pixelpermeter);
		}else{
			squareDynamicShape.setAsBox((sprite.getWidth()/2)/Level.pixelpermeter, (sprite.getHeight()/2)/Level.pixelpermeter);
		}
		
		fixtureDef = new FixtureDef();
		fixtureDef.shape = squareDynamicShape;
		fixtureDef.density = 2.5f;
		fixtureDef.friction = 0.25f;
		fixtureDef.restitution = 0.1f;
		fixtureDef.filter.categoryBits = CATEGORY_SCENERY;
		
		squareBody = w.createBody(squareBodyDef);
		squareBody.createFixture(fixtureDef);
		
		squareDynamicShape.dispose();
		return new GameObject2dSquare("square",squareBodyDef, squareBody, fixtureDef, sprite);
	}

	public GameObject2dPolygon creationGameObject2dPolygon(World w,Sprite s,float x,float y,Vector2[] veclist, float width,float height) {
		
		BodyDef polyDef;
		Body polyBody;
		PolygonShape polyDynamicShape;
		FixtureDef fixtureDef;
		
		Sprite sprite = new Sprite(s);
		
		polyDef = new BodyDef();	
		polyDef.type = BodyType.DynamicBody;
		polyDef.position.set(x/Level.pixelpermeter, y/Level.pixelpermeter);
		
		polyBody = w.createBody(polyDef);
		
		polyDynamicShape = new PolygonShape();		
		
		polyDynamicShape.set(veclist);
		
		
		
		fixtureDef = new FixtureDef();
		fixtureDef.shape = polyDynamicShape;
		
		fixtureDef.density = 1f;
		fixtureDef.filter.categoryBits = CATEGORY_SCENERY;
		
		polyBody.createFixture(fixtureDef);
		polyDynamicShape.dispose();
		GameObject2dPolygon gop = new GameObject2dPolygon("polygon",polyDef, polyBody, fixtureDef, sprite);
		gop.initialiseSprite(width,height);
		return gop;
		
	}

	public GameObject2dCircle creationGameObject2dCircle(World w, Sprite s,	float x, float y, float radius) {
		BodyDef Def;
		Body Body;
		CircleShape DynamicShape;
		FixtureDef fixtureDef;
		
		Sprite sprite = new Sprite(s);
		
		Def = new BodyDef();	
		Def.type = BodyType.DynamicBody;
		Def.position.set(x/Level.pixelpermeter, y/Level.pixelpermeter);
		
		Body = w.createBody(Def);
		
		DynamicShape = new CircleShape();	
		DynamicShape.setRadius(radius/Level.pixelpermeter);
	
		fixtureDef = new FixtureDef();
		fixtureDef.shape = DynamicShape;
		fixtureDef.density = 1f*(radius/Level.pixelpermeter);
		fixtureDef.friction = 0.1f;
		fixtureDef.restitution = 0.7f;
		fixtureDef.filter.categoryBits = CATEGORY_SCENERY;
		Body.createFixture(fixtureDef);
		DynamicShape.dispose();
		return new GameObject2dCircle("circle",Def, Body, fixtureDef, sprite,radius/Level.pixelpermeter);
	}

	public Wall creationStaticWall(World w, Sprite s, float x, float y,float width, float height) {
		
		
		BodyDef wallDef;
		Body wallBody;
		PolygonShape wallShape;
		Sprite sprite = new Sprite(s);
		
		wallDef = new BodyDef();		
		wallDef.position.set((x+width/2)/Level.pixelpermeter,(y+height/2)/Level.pixelpermeter);
		
		wallBody = w.createBody(wallDef);
		
		
		wallShape = new PolygonShape();		
		wallShape.setAsBox((width/2)/Level.pixelpermeter, (height/2)/Level.pixelpermeter);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = wallShape;
		fixtureDef.filter.categoryBits = CATEGORY_SCENERY;
		wallBody.createFixture(fixtureDef);
		
		Wall wa = new Wall("wall",new Sprite(sprite),wallDef, wallBody,fixtureDef, (width/2)/Level.pixelpermeter,(height/2)/Level.pixelpermeter);
		wallShape.dispose();
		return wa;
	}

	public StaticChaineShape creationStaticChaineShape(World w, Sprite s,
			float x, float y, Vector2[] veclist) {
		
		BodyDef def;
		Body body;
		ChainShape shape;
		Sprite sprite = new Sprite(s);
		
		def = new BodyDef();
		def.position.set(x/Level.pixelpermeter, y/Level.pixelpermeter);
		
		body = w.createBody(def);
		
		shape = new ChainShape();
		shape.createChain(veclist);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = CATEGORY_SCENERY;
		
		body.createFixture(fixtureDef);
		shape.dispose();
		return new StaticChaineShape("chaine",def, body,fixtureDef, sprite);
	}*/
}
