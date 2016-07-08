package com.spud2D;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import objects.ClientPlayerObject;
import objects.GameObject;
import objects.ParticleObjectBase;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class GameFrame extends BasicGame {

	public GameFrame(String s) {
		super("Maapusitorree");
	}
	
	static String[] titles = {"Now in pre-super-omega!"};
	static int windowWidth=800, windowHeight=600;

	public static void main(String[] args) {
		String s = titles[(new Random()).nextInt(titles.length)];
		
		AppGameContainer app;
		try {
			app = new AppGameContainer(new GameFrame(s));
			app.setDisplayMode(windowWidth, windowHeight, false);
		     app.start();
		     
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	float scrollX, scrollY;
	World world;
	
	GameObject player;
	public static Camera camera;
	
	public static final float SCROLL_X_MIN = 300, SCROLL_X_MAX = 500;
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		GL11.glPushMatrix();
		GL11.glTranslatef(windowWidth/2,windowHeight/2,0);
		GL11.glScalef(camera.cameraZoom, camera.cameraZoom, 1);
		GL11.glTranslatef(-camera.pos.x, -camera.pos.y, 0);
		
		//TODO Add a "layer" variable to objects for what order they should be rendered,
		//and then create a comparator to sort objectsList so that entity entries are organized by
		//layer. I.E entity 23 has layer 0, and entity 72 has layer 0. 23 would become first entry in list, 72 second, etc.
		for(GameObject obj : world.objectsList){
			obj.render(g);
		}
		for(ParticleObjectBase p : world.particles){
			p.render(g);
		}
		GL11.glPopMatrix();
		g.drawString("X:"+player.pos.x+" Y:"+player.pos.y, 2, 24);
		g.drawString("MX:"+player.motionX+" MY:"+player.motionY, 2, 36);
		g.drawString("SCRX:"+scrollX+" SCRY:"+scrollY, 2, 48);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		gc.setTargetFrameRate(60);
		gc.setAlwaysRender(true);
		
		AnimationLibrary.init();
		
		Controls.setInput(gc.getInput());
		world = new World();
		player= createClientPlayer(world);
		
		world.addObject(player);
		camera = new Camera(world, player);
		world.addObject(camera);
		
		int maxX = 5, maxY = 5;
		/*
		for(int x = 0; x < maxX; x ++){
			for(int y = 0; y < maxY; y ++){
				TerrainObject obj = new TerrainObject(world);
				obj.setPosition(x*48, y*48);
				world.addObjectToSpawnQueue(obj);
			}
		}
		*/
		//world.addObject(new LevelObject(world));
			world.createWorldLevel(LevelLibrary.DEBUG_LEVEL,player);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
		world.update(delta);
		
		//Scrolling logic
		if(player.pos.x<SCROLL_X_MIN-scrollX){
			//scrollX ++;
			//player.pos.x = SCROLL_X_MIN-scrollX;
		}
		if(player.pos.x>SCROLL_X_MAX-scrollX){
			//scrollX --;
			//player.pos.x = SCROLL_X_MIN-scrollX;
		}
		
		if(player.pos.x > 0){
			scrollX = -(player.pos.x);//+(windowWidth/2);
		}
		if(player.pos.x < 0){
			scrollX = Math.abs((player.pos.x));//+(windowWidth/2);
		}
		
		if(player.pos.y > 0){
			scrollY = -(player.pos.y);//+(windowWidth/2);
		}
		if(player.pos.y < 0){
			scrollY = Math.abs((player.pos.y));//+(windowWidth/2);
		}
	}
	
	
	public GameObject createClientPlayer(World w){
		return new ClientPlayerObject(w);
	}

}
