package objects;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class ParticleObjectBase{
	public float particleLifespan, particleMotionDecayMultiplier=1;
	
	int liveTicks;
	float[] motionX,motionY;
	float fallSpeed;
	public boolean isDead;
	Vector2f[] pos;
	int particleClusterSize;
	
	public ParticleObjectBase(int p, float x, float y) {
		particleClusterSize = p;
		motionX = new float[particleClusterSize];
		motionY = new float[particleClusterSize];
		pos = new Vector2f[particleClusterSize];
		for(int i = 0; i < particleClusterSize; i ++){
			pos[i] = new Vector2f(x,y);
		}
	}
	
	public void update(int delta){
		liveTicks++;
		if(liveTicks>=particleLifespan){
			isDead=true;
		}
		for(int i = 0; i < particleClusterSize; i ++){
			motionX[i] *= particleMotionDecayMultiplier;
			motionY[i] *= particleMotionDecayMultiplier;
			motionY[i]+=fallSpeed;
			pos[i].x+=motionX[i];
			pos[i].y+=motionY[i];
		}
	}

	public void render(Graphics g) {
		
	}

}
