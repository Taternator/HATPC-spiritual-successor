package objects.tiles;

import objects.ClientPlayerObject;
import objects.GameObject;
import objects.particles.ParticleColored;

import org.newdawn.slick.Color;

import com.spud2D.World;

public class CrateObjectBase extends TerrainObject {
	
	protected GameObject objectInside;
	protected boolean shouldPlayerBounceOffTop=true;
	
	public CrateObjectBase() {
		//this.setTexture("TERRAIN_EMPTYCRATE");
	}

	public CrateObjectBase(World w) {
		super(w);
		//this.setTexture("TERRAIN_EMPTYCRATE");
	}
	
	public void update(int delta){
		super.update(delta);
	}
	
	@Override
	public boolean collideWith(GameObject other){
		if(isDead)return false;
		boolean topCollision = this.hitbox.doesTopTouchBottomOf(other.getHitbox());
		
		float my = other.motionY;

		boolean b = super.collideWith(other);
		
		//Apply 'bounce' effect if needed
		if(shouldPlayerBounceOffTop&&topCollision){
			other.motionY = -Math.abs(my-0.1F);
			other.pos.y+=other.motionY;
			other.hitbox.update();
		}
		
		
		//Called if the object passed collided with this, and is a player
		if(b && other instanceof ClientPlayerObject){
			openCrate((ClientPlayerObject)other);
			other.onGround=false;
			isDead=true;
		}
		
		return b;
	}
	
	public void setObjectInside(GameObject o){
		objectInside=o;
	}
	
	
	//Called when a player collides with this
	protected void openCrate(ClientPlayerObject c){
		spawnBloodSpurts();
		if(objectInside!=null){
			world.addObjectToSpawnQueue(objectInside);
		}
	}
	
	
	public void spawnBloodSpurts(){
		Color c = new Color(1.7F,0.5F,0.2F,1);
		for(int i = 0; i < 1; i ++){
			ParticleColored particle = new ParticleColored(10, pos.x+(width/2), pos.y+(height/2));
			particle.color=c;
			
			float h,k;
			
			for(int b = 0; b < particle.pos.length; b ++){
				
				h=world.rand.nextBoolean() ? ((world.rand.nextInt(3)+1)*4) : -((world.rand.nextInt(3)+1)*4);
				k= -((world.rand.nextInt(3)+0.5F)*7);// : -((world.rand.nextInt(3)+2)*9);
				
				h *= world.rand.nextFloat();
				k *= world.rand.nextFloat()+0.2F;
				
				particle.motionX[b] = h;particle.motionY[b] = k;
				particle.motionX[b]+= motionX; particle.motionY[b] += motionY;
			}
			world.spawnParticle(particle);
		}
	}
	
	public GameObject copy(){
		CrateObjectBase obj=null;
		obj = new CrateObjectBase(this.world);
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
		if(objectInside!=null)
		obj.setObjectInside(objectInside.copy());
		obj.shouldPlayerBounceOffTop=this.shouldPlayerBounceOffTop;
		
		return obj;
	}
}
