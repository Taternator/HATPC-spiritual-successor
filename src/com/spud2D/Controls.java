package com.spud2D;

import objects.GameObject;

import org.newdawn.slick.Input;

public class Controls {
	
	public static Input input;
	
	public static final KeyBinding MOVE_LEFT = new KeyBinding(Input.KEY_A);
	public static final KeyBinding MOVE_RIGHT = new KeyBinding(Input.KEY_D);
	public static final KeyBinding JUMP = new KeyBinding(Input.KEY_SPACE);
	public static final KeyBinding CROUCH = new KeyBinding(Input.KEY_S);
	
	public static final KeyBinding DEBUGCAMERA_ZOOMIN = new KeyBinding(Input.KEY_L);
	public static final KeyBinding DEBUGCAMERA_ZOOMOUT = new KeyBinding(Input.KEY_O);
	
	public static final KeyBinding DEBUGTOGGLE = new KeyBinding(Input.KEY_GRAVE);
	public static final KeyBinding DEBUG_TOGGLE_FLY = new KeyBinding(Input.KEY_P);
	public static final KeyBinding DEBUG_NEW_WORLD = new KeyBinding(Input.KEY_N);
	
	public static boolean DEBUG = true;
	
	public static void onBoundObjectUpdate(GameObject o){
		if(!o.isControllable)return;
		
		if(MOVE_LEFT.isDown()){
			o.motionX -= o.moveSpeed;
			o.direction = o.DIRECTION_LEFT;
		}
		if(MOVE_RIGHT.isDown()){
			o.motionX += o.moveSpeed;
			o.direction = o.DIRECTION_RIGHT;
		}
		if(JUMP.isDown()){
			o.jump();
		}
		
		if(DEBUGTOGGLE.isPressed()){
			DEBUG = !DEBUG;
		}
		//Debug options
		if(DEBUG){
			if(DEBUG_TOGGLE_FLY.isPressed()){
				o.canFly = !o.canFly;
			}
			if(CROUCH.isDown()){
				o.spawnBloodSpurts();
			}
			
			
			if(JUMP.isDown()&&o.canFly){
				//o.onGround=true;
				o.motionY=-4;
				o.jump();
			}
			
			if(DEBUG_NEW_WORLD.isPressed()){
				o.setPosition(300, 400);
				//o.world.createWorldLevel(LevelLibrary.DEBUG_LEVEL,o);
			}
			
			if(DEBUGCAMERA_ZOOMIN.isDown()){
				GameFrame.camera.cameraZoom+=0.01F;
			}
			if(DEBUGCAMERA_ZOOMOUT.isDown()){
				//GameFrame.camera.cameraZoom=0;
				GameFrame.camera.cameraZoom-=0.01F;
			}
			
		}
	}
	
	public static void setInput(Input i){
		input = i;
	}
	
	public static Input getInput() {
		return input;
	}
}
