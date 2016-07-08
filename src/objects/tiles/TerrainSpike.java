package objects.tiles;

import objects.GameObject;

import com.spud2D.World;

public class TerrainSpike extends TerrainObject {
	
	float spikeDamage=1;
	
	public TerrainSpike() {
		
	}

	public TerrainSpike(World w) {
		super(w);
	}
	
	public boolean collideWith(GameObject other){
		boolean b = super.collideWith(other);
		
		if(b){
			other.takeDamage(spikeDamage);
		}
		
		return b;
	}
	
	public GameObject copy(){
		TerrainSpike obj=null;
		obj = new TerrainSpike(this.world);
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
