package objects;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class ParticleBloodDrops extends ParticleObjectBase {

	public ParticleBloodDrops(int p, float x, float y) {
		super(p,x,y);
		Random r = new Random();
		this.particleLifespan = 100;
		this.color = new Color(r.nextFloat(), r.nextFloat()/10F, r.nextFloat()/10F, 0.4F);
		this.particleMotionDecayMultiplier = 0.96F;//THICK_LIQUID
		fallSpeed=0.4F;
		//this.weight = 0.6F;//THICK_LIQUID
	}
	
	public void setColor(Color c){
		this.color=c;
	}
	
	public Color color;
	
	public void update(int delta){
		for(int i = 0; i < particleClusterSize; i ++){
			motionX[i] *= particleMotionDecayMultiplier;
			motionY[i] *= particleMotionDecayMultiplier;
		}
		super.update(delta);
	}
	
	public void render(Graphics g){
		//super.render(g);
		g.setColor(color);
		for(int i = 0; i < particleClusterSize; i ++){
			g.fillRoundRect(pos[i].x, pos[i].y, 3, 3, 3);
		}
		g.setColor(Color.white);
	}

}
