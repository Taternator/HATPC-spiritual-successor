package com.spud2D;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import objects.GameObject;
import objects.ObjectLayerComparator;
import objects.ObjectPositioner;
import objects.tiles.CrateObjectBase;
import objects.tiles.TerrainObject;

public class World {
	public ArrayList<GameObject> objectsList = new ArrayList<GameObject>();
	public ArrayList<GameObject> objectSpawnQueue = new ArrayList<GameObject>();
	
	public float gravity = 1.0F;
	public Random rand;
	
	public World(){
		rand = new Random();
	}
	
	public void update(float scrollX, float scrollY){
		if(!objectSpawnQueue.isEmpty()){
			for(int i = 0; i < objectSpawnQueue.size(); i ++){
				GameObject obj = objectSpawnQueue.get(i);
				if(!objectsList.contains(obj)){
					objectsList.add(obj);
					objectSpawnQueue.remove(obj);
				}
			}
			Collections.sort(objectsList,new ObjectLayerComparator());
		}
		
		
		int slanters = 0;
		
		for(int i = 0; i < objectsList.size(); i ++){
			GameObject obj = objectsList.get(i);
			if(obj.isControllable){
				Controls.onBoundObjectUpdate(obj);
			}
			obj.update(scrollX, scrollY);
			
			
			
			if(obj instanceof TerrainObject && ( (TerrainObject)obj ).hitbox.isSlantedTerrain ){
				slanters++;
			}
			
			boolean inside = false;
			//collision checking
			if(obj.isSolid&&obj.canMove){
				for(GameObject coll : objectsList){
					if(coll.isSolid&& obj.getDistanceTo(coll) <= obj.width+obj.height){
						if(coll.collideWith(obj)){
							if(obj.insideOfObject(coll)){
								inside=true;
								if(obj.insideTicks<20){
									obj.insideOfObject=true;
								}
								else{
									System.out.println("Inside!");
									obj.pushOutOfObject(coll);
								}
							}
						}
					}
				}
			}
			if(!inside){
				obj.insideOfObject = false;
			}
			
		}
		
		//System.out.println("SLANTED TERRAIN #="+slanters);
		
	}
	
	public void addObject(GameObject o){
		this.objectSpawnQueue.add(o);
	}
	
	public void removeObject(GameObject o){
		this.objectsList.remove(o);
	}

	public void addObjectToSpawnQueue(GameObject o) {
		this.objectSpawnQueue.add(o);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	//TODO Use LevelObjects for maps instead of procedural generation.
	//Make a copy of map image, and use color coding to determine collisions
	//within LevelObject.
	
	
	
	
	
	
	public void createWorldLevel(String fileLoc, GameObject player){
		try {
			FileReader levelStream = new FileReader(new File(fileLoc+".lvl"));
			FileReader dataStream = new FileReader(new File(fileLoc+".lvldata"));
			
			BufferedReader levelBuffer = new BufferedReader(levelStream);
			BufferedReader dataBuffer = new BufferedReader(dataStream);
			
			String line;
			
			HashMap<Character, GameObject> levelDefinitions = new HashMap<Character, GameObject>();
			
			float spawnX,spawnY;
			
			while((line = dataBuffer.readLine()) != null){
				//x=terr.ROCK;
				
				GameObject object = null;
				
				String[] split = line.split("=");
				
				if(split[1].startsWith("terr.")){
					
					object = new TerrainObject(this);
					System.out.println("Slant!");
					boolean isSlanted = false;
					boolean slantLeft = false;
					if(split[1].contains(" ")){
						
						String[] argSplit = split[1].split(" ");
						System.out.println("argsplit[1]:"+argSplit[1]);
						for(String arg : argSplit){
							if(arg.toLowerCase().startsWith("slant_")){
								String slantdir="";
								isSlanted=true;
								((TerrainObject)object).getHitbox().isSlantedTerrain=true;
								slantdir = arg.toLowerCase().split("slant_")[1];
								System.out.println("slantdir:"+slantdir);
								if(slantdir.equalsIgnoreCase("right")){
									((TerrainObject)object).getHitbox().isSlantedLeft=false;
									slantLeft=false;
								}
								else{
									((TerrainObject)object).getHitbox().isSlantedLeft=true;
									slantLeft=true;
								}
							}
						}
						
						//split = argSplit;
						
					}
					
					System.out.println(split[1].split("terr.")[0]);
					
					String[] objdef = split[1].split("terr.");
					
					System.out.println(objdef[1].split(" ")[0]);
					
						((TerrainObject)object).setTexture(objdef[1].split(" ")[0]);

					
					System.out.println(objdef[1]+" "+line.charAt(0));
				}
	
	if(split[1].startsWith("obs.")){
		
		object = new CrateObjectBase(this);
		boolean isSlanted = false;
		boolean slantLeft = false;
		((TerrainObject)object).setTexture("TERRAIN_EMPTYCRATE");
		if(split[1].contains(" ")){//This all handles metadata... Shouldn't need it
			
			String[] argSplit = split[1].split(" ");
			System.out.println("argsplit[1]:"+argSplit[1]);
			for(String arg : argSplit){
				if(arg.toLowerCase().startsWith("arrow")){//Populate the crate with an arrow
					CrateObjectBase cr = new CrateObjectBase(this);
					cr.setPosition(200, 200);
					( (CrateObjectBase)object ).setObjectInside(cr);
					
					String arrowBearing = arg.toLowerCase().split("arrow_")[1];
					System.out.println(arrowBearing);
					if(arrowBearing.equals("down")){
						((TerrainObject)object).setTexture("TERRAIN_WOODCRATEARROWDOWN");
					}
					
				}
			}
			
			//split = argSplit;
			
		}
		
		System.out.println(split[1].split("obs.")[0]);
		
		String[] objdef = split[1].split("obs..");
		
		System.out.println(objdef[1].split(" ")[0]);
		
		((CrateObjectBase)object).setTexture(objdef[1].split(" ")[0]);
	
		
		System.out.println(objdef[1]+" "+line.charAt(0));
		System.out.println("::"+object.getClass().getName());
	}
				
				if(split[1].startsWith("PPOS")){
					object = new ObjectPositioner(this, player);
					object = ((ObjectPositioner)object);
					System.out.println("PPOS " + split[0]);
				}
				levelDefinitions.put(line.charAt(0), object);
			}
			
			for(int i = 0; i < levelDefinitions.size(); i ++){
				//System.out.println(levelDefinitions.)
			}
			
			ArrayList<String> levelLayers = new ArrayList<String>();
			int maxline=0;
			while((line = levelBuffer.readLine()) != null){
				levelLayers.add(line);
				if(line.length()>maxline)maxline=line.length();
			}
			
			System.out.println("Level size:[Width:"+maxline+" Height:"+levelLayers.size()+"]");
			
			
			float x = 0, y = 0, maxY = 0;
			for(int j =0; j < levelLayers.size(); j ++){
				String lvln = (levelLayers.get(j));
				System.out.println(lvln);
				x=0;
				maxY=0;
				for(int i =0; i < lvln.length(); i ++){
					char c = lvln.charAt(i);
					if(levelDefinitions.get(c)!=null){
						GameObject res = levelDefinitions.get(c).copy();
						if(res != null){
							if(levelDefinitions.get(c) instanceof TerrainObject){
								res.idleAnimation = ((TerrainObject)res).idleAnimation;
							}
							res.setPosition(x, y);
							x+= res.idleAnimation.getCurrentFrame().getWidth();
							if(maxY<res.idleAnimation.getCurrentFrame().getHeight()){
								maxY = res.idleAnimation.getCurrentFrame().getHeight();
							}
							addObject(res);
						}
					}
					else{
						x+= 48;
					}
				}
				y+=maxY;
			}
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String flipString(String s){
		String res = "";
		
		for(int i = s.length()-1; i > -1; i --){
			res += s.charAt(i);
		}
		
		System.out.println(res);
		
		return res;
	}
	
}
