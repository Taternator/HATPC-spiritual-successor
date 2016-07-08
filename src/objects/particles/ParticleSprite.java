package objects.particles;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class ParticleSprite extends ParticleObjectBase {
	
	private Animation anim;
	
	public ParticleSprite(int p, float x, float y, Animation a) {
		super(p, x, y);
		anim=a;
		fallSpeed=-0.2F;
		this.particleLifespan = 100;
		this.particleMotionDecayMultiplier = 1.01F;//THICK_LIQUID
	}
	
	public void update(int delta){
		for(int i = 0; i < particleClusterSize; i ++){
			motionX[i] *= particleMotionDecayMultiplier;
			motionY[i] *= particleMotionDecayMultiplier;
		}
		super.update(delta);
	}
	
	public void render(Graphics g){
		//super.render(g);
		g.setColor(Color.white);
		for(int i = 0; i < particleClusterSize; i ++){
			//g.fillRoundRect(pos[i].x, pos[i].y, 3, 3, 3);
			anim.draw(pos[i].x, pos[i].y);
		}
		g.setColor(Color.white);
	}

}
