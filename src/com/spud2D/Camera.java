package com.spud2D;

import objects.GameObject;

public class Camera extends GameObject{
	public GameObject focus;
	
	public float posX, posY;
	public float cameraZoom=1.0F;
	
	public Camera(World w,GameObject o){
		super(w);
		focus=o;
		this.setSize(0, 0);
		this.canMove=false;
	}
	
	public void update(float sx, float sy){
		baseX = focus.baseX;
		baseY = focus.baseY;
	}
}
