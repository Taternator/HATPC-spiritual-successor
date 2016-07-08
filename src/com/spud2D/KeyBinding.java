package com.spud2D;

import org.newdawn.slick.Input;

public class KeyBinding {
	private int KEY_ID;
	
	private Input input;
	
	public KeyBinding(int key){
		KEY_ID = key;
		input=Controls.getInput();
	}
	
	public void setKey(int k){
		this.KEY_ID = k;
	}
	
	public boolean isPressed(){
		if(input==null){
			input=Controls.getInput();
		}
		return input.isKeyPressed(KEY_ID);
	}
	
	public boolean isDown(){
		if(input==null){
			input=Controls.getInput();
		}
		return input.isKeyDown(KEY_ID);
	}
	
	public boolean isUp(){
		if(input==null){
			input=Controls.getInput();
		}
		return !input.isKeyDown(KEY_ID) && !input.isKeyPressed(KEY_ID);
	}
}
