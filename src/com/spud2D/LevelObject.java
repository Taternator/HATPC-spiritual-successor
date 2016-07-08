package com.spud2D;

import objects.tiles.TerrainObject;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public class LevelObject extends TerrainObject {

	public LevelObject(World w) {
		super(w);
		this.setTexture("DEBUG_LEVEL");
	}
	
	Graphics graphics;
	
	/*
	public boolean collideWith(GameObject other){
		boolean collision = this.hitbox.intersects(other.getHitbox());
		boolean notAir = true;
		
		float sx = other.getHitbox().getMinX(), sy = other.getHitbox().getMaxY(), ex = other.getHitbox().getMaxX(), ey = other.getHitbox().getMinY();
		
		//System.out.println(sx+"/"+ex+"/"+sy+"/"+ey);
		
		int imageWidth = getCurrentFrame().getWidth();
		int imageHeight = getCurrentFrame().getHeight();
		
		Image level = getCurrentFrame();
		
		Image area = level.getSubImage((int)other.baseX, (int)other.baseY, (int)other.width, (int)other.height);
		
		
		System.out.println(area.getWidth()+"/"+area.getHeight());
		
		for(int x = 0; x < area.getWidth(); x ++){
			for(int y = 0; y < area.getHeight(); y ++){
				if(area.getColor(x, y).a!=0){
					notAir=false;
				}
			}
		}
		
		
		if(notAir){
			return false;
		}
			
		if(collision){
			other.onGround=true;
			if(other.jumpTicks==0){
				other.baseY -= Math.abs(other.motionY);
				other.motionY=0;
			}
		}
			
			
		return collision;
	}
	
	
	
	public void pushObjectOut(GameObject other){
		Rectangle r1 = new Rectangle(disX,disY,width,height);
		Rectangle r2 = new Rectangle(other.disX,other.disY,other.width,other.height);
		//r1.grow(-2, 2);
		//r2.grow(-2, 2);
		if(r1.getCenterX()>r2.getCenterX()){
			baseX ++;
			if(other instanceof TerrainObject){
				//motionX = 0;
			}
		}
		else if(r1.getCenterX()<r2.getCenterX()){
			other.baseX --;
			if(other instanceof TerrainObject){
				//motionX = 0;
			}
		}
		if(r1.getMaxY()>r2.getMinY()){
			System.out.println("y--~");
			other.baseY --;
			if(other instanceof TerrainObject){
				//motionY = 0;
			}
		}
		else if(r1.getMaxY()<r2.getMinY()){
			System.out.println("y++~");
			other.baseY ++;
			if(other instanceof TerrainObject){
				//motionY = 0;
			}
		}
	}
	
	
	*/
}
