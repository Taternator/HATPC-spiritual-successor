package objects;

import objects.particles.ParticleSprite;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.spud2D.AnimationLibrary;
import com.spud2D.Controls;
import com.spud2D.World;

public class ClientPlayerObject extends GameObject {
	public int points = 0;
	
	public ClientPlayerObject(World w) {
		super(w);
		canMove = true;
		isControllable = true;
		moveSpeed = 0.4F;
		jumpHeight=1.9F;
		layer = 99;
		this.idleAnimation = AnimationLibrary.DEBUG_PLAYER_STAND;
		this.walkAnimation = AnimationLibrary.DEBUG_PLAYER_WALK;
	}
	
	public void render(Graphics g){
		if(currentAnimation!=null){
			g.pushTransform();
			currentAnimation.update((long) currentAnimation.getSpeed());
			Image img = currentAnimation.getCurrentFrame();
			if(direction==DIRECTION_RIGHT){
				img = img.getFlippedCopy(true, false);
			}
			if(invulnTicks>0&&invulnTicks%15>8){
				img.setAlpha(0.4F);
			}
			else{
				img.setAlpha(1.0F);
			}
			img.draw(pos.x, pos.y);
			if(Controls.DEBUG){
				renderHitbox(g);
			}
			g.popTransform();
		}
	}

	public void gainPoints(int i) {
		points+= i;
		world.spawnParticle(new ParticleSprite(1,pos.x+(width/2),pos.y+(height/2), AnimationLibrary.PARTICLE_GOLDCOIN));
	}
}
