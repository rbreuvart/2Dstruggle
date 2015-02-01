package com.rbr.game.screen.layer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.rbr.game.screen.game.ScreenGame;
import com.rbr.game.utils.PerfLogger;
import com.rbr.game.utils.PerfLogger.AnalyseBloc;

public class PerformanceAnnalyseLayer {
	private PerfLogger perfLogger;
	
	private float widthRatio;
	private float heightRatio;
	
	private Vector2 position;
	private float widthLine;
	private float heightByMilisec;
	
	Matrix4 normalProjection;
	
	float mini ;
	float ymax ;
	
	public PerformanceAnnalyseLayer(ScreenGame screenGame) {
		perfLogger = new PerfLogger();
		widthRatio  = 1;
		heightRatio = 0.2f;
		position =  new Vector2(0, 0);
		widthLine = (Gdx.graphics.getWidth()*widthRatio)/perfLogger.getMaxListBloc();
		heightByMilisec = 5000;
		normalProjection = screenGame.getCamManageur().getNormalProjection();
		ymax= (float)(Gdx.graphics.getHeight() * heightRatio);
	}
	
	//int modCompteur = 20;
	
	public void update(float delta){
		/*int compteur = 0;
		if (compteur % modCompteur == 0) {
			compteur = 0;
			mini = 1;
			for (AnalyseBloc analyseBloc : perfLogger.getListAnalyseBloc()) {
				
			}
			//System.out.println("mini = "+mini);
		}else{
			compteur++;
		}*/
	}
	
	public void render(float delta,SpriteBatch batch, ShapeRenderer shapeRenderer ){
		int compteurX = 0;
		mini = 1;
		shapeRenderer.setProjectionMatrix(normalProjection);
		shapeRenderer.begin(ShapeType.Line);
		
		for (AnalyseBloc analyseBloc : perfLogger.getListAnalyseBloc()) {
			if(analyseBloc.getTime()<=mini){				
				mini = analyseBloc.getTime();
			}
			
			float x = 0*widthRatio+(compteurX);
			float ratioY = (float)((mini/analyseBloc.getTime()))*-1+1;
			float y = 0*heightRatio+((float)ymax*ratioY);
			//System.out.println(analyseBloc.getTime()+"ms mini"+mini+" ratio"+ratioY+" y"+y+" ymax"+ymax);
			//System.out.println("X"+ position.x+x+" Y"+position.y+y);
			
			shapeRenderer.line( position.x+x, position.y,
								position.x+x, position.y+y,
								Color.GREEN,Color.RED);
			compteurX++;
		}
		shapeRenderer.end();
		//System.out.println("------------");
	}

	public PerfLogger getPerfLogger() {
		return perfLogger;
	}

	public void setPerfLogger(PerfLogger perfLogger) {
		this.perfLogger = perfLogger;
	}
	
	
	
}
