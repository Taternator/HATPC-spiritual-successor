package objects.tiles;

import objects.ClientPlayerObject;
import objects.GameObject;

import com.spud2D.World;

public class CrateObjectBase extends TerrainObject {
	
	private GameObject objectInside;
	
	public CrateObjectBase() {
		//this.setTexture("TERRAIN_EMPTYCRATE");
	}

	public CrateObjectBase(World w) {
		super(w);
		//this.setTexture("TERRAIN_EMPTYCRATE");
	}
	
	public void update(float sx, float sy){
		super.update(sx, sy);
	}
	
	@Override
	public boolean collideWith(GameObject other){
		System.out.println("REEEEE");
		boolean topCollision = this.hitbox.doesTopTouchBottomOf(other.getHitbox());
		
		float my = other.motionY;

		boolean b = super.collideWith(other);
		
		//Apply 'bounce' effect if needed
		if(topCollision&&health>0){
			other.motionY = -Math.abs(my-0.1F);
			other.baseY+=other.motionY;
			other.hitbox.update();
		}
		
		
		//Called if the object passed collided with this, and is a player
		if(b && other instanceof ClientPlayerObject){
			openCrate();
			other.onGround=false;
			isDead=true;
		}
		
		return b;
	}
	
	public void setObjectInside(GameObject o){
		objectInside=o;
	}
	
	
	//Called when a player collides with this
	protected void openCrate(){
		spawnBloodSpurts();
		if(objectInside!=null){
			world.addObjectToSpawnQueue(objectInside);
		}
	}
	
	
	public GameObject copy(){
		CrateObjectBase obj=null;
		obj = new CrateObjectBase(this.world);
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
		if(objectInside!=null)
		obj.setObjectInside(objectInside.copy());
		
		return obj;
	}
}
