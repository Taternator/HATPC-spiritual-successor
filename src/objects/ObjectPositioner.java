package objects;

import com.spud2D.World;

public class ObjectPositioner extends GameObject {
	GameObject toMove;
	
	public ObjectPositioner(){
		super();
	}
	
	public ObjectPositioner(World w, GameObject obj){
		super(w);
		this.canMove=false;
		this.isSolid=false;
		this.shouldRender=false;
		this.weight=0;
		toMove = obj;
		System.out.println("Init!");
	}
	
	public void update(int delta){
		if(liveTicks>=2){
			toMove.setPosition(pos.x, pos.y);
			System.out.println("Sproink!");
			this.isDead = true;
			world.removeObject(this);
		}
		liveTicks ++;
		
		
		
	}
	
	public GameObject copy(){
		ObjectPositioner obj=null;
		obj = new ObjectPositioner(this.world,this.toMove);
		obj.world=this.world;
		obj.isSolid=this.isSolid;
		obj.canMove=this.canMove;
		obj.pos.x=this.pos.x;obj.pos.y=this.pos.y;
		obj.motionX=this.motionX;obj.motionY=this.motionY;
		obj.moveSpeed=this.moveSpeed;obj.jumpHeight=this.jumpHeight;
		obj.health=this.health;obj.maxHealth=this.maxHealth;
		obj.idleAnimation=this.idleAnimation;
		obj.currentAnimation=this.idleAnimation;
		
		
		return obj;
	}
}
