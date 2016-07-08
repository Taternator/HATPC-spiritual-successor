package objects.tiles;

import objects.GameObject;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

import com.spud2D.AnimationLibrary;
import com.spud2D.World;

public class TerrainObject extends GameObject {
	
	public boolean doesStopOnCollision=true;
	
	public TerrainObject(){
		super();
	}
	
	public TerrainObject(World w) {
		super(w);
		setTexture("TERRAIN_ROCK");
		this.canMove=false;
		//this.currentAnimation.setAutoUpdate(false);
		this.currentAnimation.setCurrentFrame(0);
	}
	
	public void update(int delta){
		super.update(delta);
		/*
		if(this.getHitbox().isSlantedTerrain){
			if(this.getHitbox().isSlantedLeft){
				//this.currentAnimation.setAutoUpdate(false);
				this.currentAnimation.setCurrentFrame(1);
			}
			else{
				//this.currentAnimation.setAutoUpdate(false);
				this.currentAnimation.setCurrentFrame(2);
			}
		}
		else{
			this.currentAnimation.setCurrentFrame(0);
		}
		this.currentAnimation=this.idleAnimation;
		
		if(this.currentAnimation.getFrame()!=0){
			System.out.println("asd"+currentAnimation.getFrame());
			this.currentAnimation.update(1000);
		}
		*/
	}
	
	public Image getCurrentFrame(){
		Image res = null;
		boolean slant = hitbox.isSlantedTerrain;
		if(slant){
			res = hitbox.isSlantedLeft ? currentAnimation.getImage(1).getFlippedCopy(true, false) : currentAnimation.getImage(1);
		}
		else{
			res=currentAnimation.getImage(0);
		}
		return res;
	}
	
	
	public boolean collideWith(GameObject other){
		//Ground collision check
		boolean collisionTop =hitbox.doesTopTouchBottomOf(other.getHitbox());			
			if(hitbox.isSlantedTerrain && hitbox.pathHitbox.intersects(other.getHitbox().footHitbox)){
				
				if(other.jumpTicks==0){
					//other.motionY += Math.abs(other.motionY);
					other.motionY=0;
					other.onGround=true;
					if(hitbox.isSlantedLeft){
						float slant = 0;
						System.out.println(slant);
						slant -= (getHitbox().pathHitbox.getMaxX()-other.getHitbox().footHitbox.getMinX());
						
						
						slant+=34;
						
						if(slant>0)slant=0;
						
						if(slant<-other.height)slant=-other.height;
						
						
						
						System.out.println("left slant:"+slant);
						
						other.pos.y=( this.pos.y )+Math.abs( Math.round(slant) ) - other.height;
						other.getHitbox().update();
					}
					else{
						float slant = -width;
						System.out.println(slant);
						slant -= (other.getHitbox().footHitbox.getMinX()-getHitbox().pathHitbox.getMaxX());
						
						if(slant>0)slant=0;
						
						if(slant<-other.height)slant=-other.height;
						
						System.out.println("right slant:"+slant);
						
						slant -= 6;
						
						other.pos.y=( ( this.pos.y )-Math.round(slant) ) - other.height;
						other.getHitbox().update();
					}
				}
			}
			else if(collisionTop) {
				if(doesStopOnCollision){
					other.motionY=0;
					other.onGround=true;
					other.pos.y=this.pos.y-other.height;
				}
			}
		
		boolean collisionBottom = hitbox.doesBottomTouchTopOf(other.getHitbox());
		if(collisionBottom&&doesStopOnCollision) {
				other.pos.y += Math.abs(other.motionY);
				other.motionY=0;
		}
		
		boolean collisionLeft = hitbox.doesRightTouchLeftOf(other.getHitbox());
		
		if(collisionLeft&&doesStopOnCollision){
			if(hitbox.isSlantedTerrain){
				float slant = hitbox.pathHitbox.getWidth();
				
				slant += (hitbox.pathHitbox.getMaxX()-other.getHitbox().hitbox.getMaxX());
				
				float diff = hitbox.pathHitbox.getWidth()-slant;
				System.out.println(slant);
			}
			else{
				other.pos.x -= 0.1F;
				other.pos.x -=other.motionX * 1.1F;
				other.motionX = 0;
				System.out.println("LEFT!");
			}
		}
		
		boolean collisionRight = hitbox.doesLeftTouchRightOf(other.getHitbox());
		
		if(collisionRight&&doesStopOnCollision){
			if(hitbox.isSlantedTerrain){
				float slant = hitbox.pathHitbox.getWidth();
				
				slant += (hitbox.pathHitbox.getMaxX()-other.getHitbox().hitbox.getMaxX());
				
				float diff = hitbox.pathHitbox.getWidth()-slant;
				System.out.println(slant);
			}
			else{
				other.pos.x += 0.1F;
				other.pos.x +=Math.abs(other.motionX * 1.1F);
				other.motionX = 0;
			}
		}
		
		//Returns true if any of collisionTop/Left/Right/Bottom are true
		
		return  collisionTop ? collisionTop : collisionLeft ?
				collisionLeft : collisionRight ? collisionRight :
				collisionBottom;
	}
	
	public void setTexture(String s){
		try {
			idleAnimation = (Animation)AnimationLibrary.class.getField(s).get(null);
			currentAnimation=idleAnimation;
		} catch (IllegalArgumentException | IllegalAccessException
				| NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public GameObject copy(){
		TerrainObject obj=null;
		obj = new TerrainObject(this.world);
		obj.world=this.world;
		obj.isSolid=this.isSolid;
		obj.canMove=this.canMove;
		obj.pos.x=this.pos.x;obj.pos.y=this.pos.y;
		obj.motionX=this.motionX;obj.motionY=this.motionY;
		obj.moveSpeed=this.moveSpeed;obj.jumpHeight=this.jumpHeight;
		obj.health=this.health;obj.maxHealth=this.maxHealth;
		obj.idleAnimation=this.idleAnimation;
		obj.currentAnimation=this.idleAnimation;
		obj.hitbox.isSlantedTerrain=hitbox.isSlantedTerrain;
		obj.hitbox.isSlantedLeft=hitbox.isSlantedLeft;
		
		return obj;
	}
	
}
