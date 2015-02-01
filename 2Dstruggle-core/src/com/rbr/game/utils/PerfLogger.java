package com.rbr.game.utils;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.rbr.game.screen.game.ScreenGame;

public class PerfLogger {
/*
	public class AnalyseItem{
		private float time;
		private String name;		
		public AnalyseItem(float time, String name) {
			super();
			this.time = time;
			this.name = name;
		}
		public float getTime() {
			return time;
		}
		public void setTime(float time) {
			this.time = time;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}		
	}*/
	
	
	
	
	public class AnalyseBloc {
		/*Array<AnalyseItem> listAnalyseItem;
		
		public AnalyseBloc() {
			listAnalyseItem =  new Array<PerfLogger.AnalyseItem>();
		}
		public void add(AnalyseItem analyseItem){
			listAnalyseItem.add(analyseItem);
		}*/
		private float time; 
		public float getTime() {
			return time;
		}
		public void setTime(float time) {
			this.time = time;
		}
		public AnalyseBloc() {
			time = 0;
		}
	}
	
	private int maxListBlock;
    private LinkedList<AnalyseBloc> listAnalyseBloc;
    
    public PerfLogger getPerfLogger( ) {	
		return this;
	}
	public PerfLogger( ) {
		listAnalyseBloc = new LinkedList<PerfLogger.AnalyseBloc>();
		maxListBlock = 500;
	}
	
	public void notifyTime(String name,float millisSec){
		if (ConfigPref.Performance_AnalyseStatistiqueLogger) {					
			listAnalyseBloc.getLast().setTime(millisSec);
		}
	}
	public void notifyBloc(float millisSec){
		if (ConfigPref.Performance_AnalyseStatistiqueLogger) {					
			if (!listAnalyseBloc.isEmpty()) {
				listAnalyseBloc.getLast().setTime(millisSec);
			}
			
			AnalyseBloc ab = new AnalyseBloc();
			ab.setTime(millisSec);
			listAnalyseBloc.add(ab);
			
			if (listAnalyseBloc.size()>getMaxListBloc()) {
				listAnalyseBloc.remove(0);
			}
		}
	}
	
	public int getMaxListBloc() {
		return maxListBlock;
	}
	public void setMaxListBloc(int maxList) {
		this.maxListBlock = maxList;
	}
	
	public LinkedList<AnalyseBloc> getListAnalyseBloc() {
		return listAnalyseBloc;
	}
	public void setListAnalyseBloc(LinkedList<AnalyseBloc> listAnalyseBloc) {
		this.listAnalyseBloc = listAnalyseBloc;
	}
	
	
	
	
	
	

}
