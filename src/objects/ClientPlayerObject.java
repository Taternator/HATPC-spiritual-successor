package objects;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.spud2D.AnimationLibrary;
import com.spud2D.Controls;
import com.spud2D.World;

public class ClientPlayerObject extends GameObject {

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
			img.draw(pos.x, pos.y);
			if(Controls.DEBUG){
				renderHitbox(g);
			}
			g.popTransform();
		}
	}
}
