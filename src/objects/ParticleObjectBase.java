package objects;

import com.spud2D.World;

public class ParticleObjectBase extends GameObject {
	public float particleLifespan, particleMotionDecayMultiplier=1;
	
	public ParticleObjectBase(World w) {
		super(w);
		this.canMove = true;
		this.isSolid = false;
		this.setSize(0, 0);
		
	}
	
	public void update(float sx, float sy){
		super.update(sx, sy);
		if(liveTicks>=particleLifespan){
			isDead=true;
		}
		motionX *= particleMotionDecayMultiplier;
		motionY *= particleMotionDecayMultiplier;
	}

}