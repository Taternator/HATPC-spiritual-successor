package objects.tiles;

import objects.ClientPlayerObject;
import objects.GameObject;

import com.spud2D.AnimationLibrary;
import com.spud2D.World;

public class TreasureCrate extends CrateObjectBase {

	public TreasureCrate() {
		this.setTexture("TERRAIN_TREASURECHEST");
		this.doesStopOnCollision=false;
	}

	public TreasureCrate(World w) {
		super(w);
		this.setTexture("TERRAIN_TREASURECHEST");
		this.doesStopOnCollision=false;
	}
	
	
	protected void openCrate(ClientPlayerObject obj){
		spawnBloodSpurts();
		if(objectInside!=null){
			world.addObjectToSpawnQueue(objectInside);
		}
		
		obj.gainPoints(1);
	}
	
	public GameObject copy(){
		TreasureCrate obj=null;
		obj = new TreasureCrate(this.world);
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
		
		return obj;
	}
}
