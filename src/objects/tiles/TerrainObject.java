package objects.tiles;

import objects.GameObject;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

import com.spud2D.AnimationLibrary;
import com.spud2D.World;

public class TerrainObject extends GameObject {
	
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
	
	public void update(float sx, float sy){
		super.update(sx, sy);
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
						
						other.baseY=( this.baseY )+Math.abs( Math.round(slant) ) - other.height;
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
						
						other.baseY=( ( this.baseY )-Math.round(slant) ) - other.height;
						other.getHitbox().update();
					}
				}
			}
			else if(collisionTop) {
				other.motionY=0;
				other.onGround=true;
				other.baseY=this.baseY-other.height;
			}
		
		boolean collisionBottom = hitbox.doesBottomTouchTopOf(other.getHitbox());
		if(collisionBottom) {
				other.baseY += Math.abs(other.motionY);
				other.motionY=0;
		}
		
		boolean collisionLeft = hitbox.doesRightTouchLeftOf(other.getHitbox());
		
		if(collisionLeft){
			if(hitbox.isSlantedTerrain){
				float slant = hitbox.pathHitbox.getWidth();
				
				slant += (hitbox.pathHitbox.getMaxX()-other.getHitbox().hitbox.getMaxX());
				
				float diff = hitbox.pathHitbox.getWidth()-slant;
				System.out.println(slant);
			}
			else{
				other.baseX -= 0.1F;
				other.baseX -=other.motionX * 1.1F;
				other.motionX = 0;
				System.out.println("LEFT!");
			}
		}
		
		boolean collisionRight = hitbox.doesLeftTouchRightOf(other.getHitbox());
		
		if(collisionRight){
			if(hitbox.isSlantedTerrain){
				float slant = hitbox.pathHitbox.getWidth();
				
				slant += (hitbox.pathHitbox.getMaxX()-other.getHitbox().hitbox.getMaxX());
				
				float diff = hitbox.pathHitbox.getWidth()-slant;
				System.out.println(slant);
			}
			else{
				other.baseX += 0.1F;
				other.baseX +=Math.abs(other.motionX * 1.1F);
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
		obj.baseX=this.baseX;obj.baseY=this.baseY;
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
