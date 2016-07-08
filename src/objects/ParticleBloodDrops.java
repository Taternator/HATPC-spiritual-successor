package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.spud2D.World;

public class ParticleBloodDrops extends ParticleObjectBase {

	public ParticleBloodDrops(World w) {
		super(w);
		this.particleLifespan = 100;
		this.color = new Color(w.rand.nextFloat(), w.rand.nextFloat()/10F, w.rand.nextFloat()/10F, 0.4F);
		this.particleMotionDecayMultiplier = 0.96F;//THICK_LIQUID
		this.weight = 0.6F;//THICK_LIQUID
	}
	
	public Color color;
	
	public void update(int delta){
		motionX *= particleMotionDecayMultiplier;
		motionY *= particleMotionDecayMultiplier;
		super.update(delta);
	}
	
	public void render(Graphics g){
		//super.render(g);
		g.setColor(color);
		g.fillRoundRect(pos.x, pos.y, 3, 3, 3);
		g.setColor(Color.white);
	}

}
