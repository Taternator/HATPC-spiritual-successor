package com.spud2D;

import java.io.File;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class AnimationLibrary {
	public static final Animation DEBUG_TEST = createAnimation(PathConstants.PATH_TEXTURE_DEBUG, "test");
	
	public static final Animation TERRAIN_ROCK = createAnimation(PathConstants.PATH_TEXTURE_TERRAIN, "rock");
	public static final Animation TERRAIN_TREASURECHEST = createAnimation(PathConstants.PATH_TEXTURE_TERRAIN, "chest");
	public static final Animation TERRAIN_EMPTYCRATE = createAnimation(PathConstants.PATH_TEXTURE_TERRAIN, "emptycrate");
	public static final Animation TERRAIN_WOODCRATEARROWDOWN = createAnimation(PathConstants.PATH_TEXTURE_TERRAIN, "woodcratearrowdown");
	
	public static final Animation DEBUG_PLAYER_STAND = createAnimation(PathConstants.PATH_TEXTURE_DEBUG_PLAYER, "stand",100);
	public static final Animation DEBUG_PLAYER_WALK = createAnimation(PathConstants.PATH_TEXTURE_DEBUG_PLAYER, "walk",100);
	public static final Animation DEBUG_PLAYER_ = createAnimation(PathConstants.PATH_TEXTURE_DEBUG_PLAYER, "stand");
	
	
	public static final Animation PARTICLE_GOLDCOIN = createAnimation(PathConstants.PATH_TEXTURE_PARTICLES, "goldcoin");
	
	
	public static final void init(){
		
	}
	
	//Levels
	public static final Animation DEBUG_LEVEL = createAnimation(PathConstants.PATH_TEXTURE_TERRAIN, "testlevel");
	
	
	public static Animation createAnimation(String dir, String baseAnm){
		return createAnimation(dir, baseAnm, 100);
	}
	
	
	public static Animation createAnimation(String dir, String baseAnm, int dur){
		Animation res = null;
		Image[] images;
		
		File[] files = new File(dir).listFiles();
		if(files == null){
			System.out.println("Directory is null!");
			return null;
		}
		int related_images = 0;
		for(File f : files){
			if(f.getName().startsWith(baseAnm)){
				related_images ++;
			}
		}
		images = new Image[related_images];
		for(int i = 0; i < related_images; i ++){
			try {
				System.out.println("Adding image to animation:"+dir+baseAnm+"_"+i+".png");
				images[i] = new Image(dir+baseAnm+"_"+i+".png");
			} catch (SlickException e) {
				System.out.println(e.getMessage());
			}
		}
		res = new Animation(images, dur);
		
		res.setLooping(true);
		res.setAutoUpdate(true);
		
		
		return res;
	}
	
}
