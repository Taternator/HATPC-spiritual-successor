package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Path;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.ShapeRenderer;

public class ObjectHitbox {
	public Rectangle hitbox, footHitbox, headHitbox, leftHitbox, rightHitbox;
	public boolean isSlantedTerrain=false;
	public boolean isSlantedLeft=false;
	public Path pathHitbox;
	protected final int HITBOX_WHOLE=0, HITBOX_FOOT=1, HITBOX_HEAD=2, HITBOX_LEFT=3, HITBOX_RIGHT=4;
	
	
	GameObject owner;
	
	public ObjectHitbox(GameObject o){
		owner=o;
		hitbox = new Rectangle(0,0,0,0);
		
		footHitbox = new Rectangle(0,0,0,0);
		headHitbox = new Rectangle(0,0,0,0);
		
		leftHitbox = new Rectangle(0,0,0,0);
		rightHitbox = new Rectangle(0,0,0,0);
		
		pathHitbox = new Path(0, 0);
	}
	
	public void update(){
		hitbox.setLocation(owner.baseX, owner.baseY);
		
		footHitbox.setLocation(owner.baseX+4,owner.baseY+owner.height-8);
		headHitbox.setLocation(owner.baseX+4,owner.baseY);
		
		rightHitbox.setLocation(owner.baseX+owner.width-4,owner.baseY+8);
		leftHitbox.setLocation(owner.baseX,owner.baseY+8);
		
		
		if(!isSlantedLeft){
			pathHitbox = new Path(owner.baseX+owner.width,owner.baseY+owner.height);
			pathHitbox.lineTo(owner.baseX, owner.baseY);
			pathHitbox.lineTo(owner.baseX, owner.baseY+owner.height);
		}
		else{
			pathHitbox = new Path(owner.baseX,owner.baseY+owner.height);
			pathHitbox.lineTo(owner.baseX+owner.width, owner.baseY);
			pathHitbox.lineTo(owner.baseX+owner.width, owner.baseY+owner.height);
		}
		
		
		
		
		
	}
	
	public void setSize(float w, float h){
		hitbox.setSize(w, h);
		footHitbox.setSize(w-8, 8);
		headHitbox.setSize(w-8, 8);
		leftHitbox.setSize(4, h-16);
		rightHitbox.setSize(4, h-16);
	}
	
	public boolean fullyInsideOf(ObjectHitbox other){
		if(isSlantedTerrain){
			return other.hitbox.contains(this.pathHitbox);
		}
		return other.hitbox.contains(this.hitbox);
	}
	
	public boolean doesBottomTouchTopOf(ObjectHitbox other){
		if(isSlantedTerrain){
			return other.headHitbox.intersects(this.pathHitbox);
		}
		return other.headHitbox.intersects(this.footHitbox);
	}
	
	public boolean doesTopTouchBottomOf(ObjectHitbox other){
		if(isSlantedTerrain){
			return other.footHitbox.intersects(this.pathHitbox);
		}
		return other.footHitbox.intersects(this.headHitbox);
	}
	
	public boolean doesLeftTouchRightOf(ObjectHitbox other){
		if(isSlantedTerrain){
			return other.leftHitbox.intersects(this.pathHitbox);
		}
		return other.leftHitbox.intersects(this.rightHitbox);
	}
	
	public boolean doesRightTouchLeftOf(ObjectHitbox other){
		if(isSlantedTerrain){
			return other.rightHitbox.intersects(this.pathHitbox);
		}
		return other.rightHitbox.intersects(this.leftHitbox);
	}
	
	
	public void renderAllHitboxes(Graphics g){
		if(!isSlantedTerrain){
			renderHitbox(g, HITBOX_FOOT);
			renderHitbox(g, HITBOX_HEAD);
			renderHitbox(g, HITBOX_LEFT);
			renderHitbox(g, HITBOX_RIGHT);
		}
		else{
			g.setColor(Color.pink);
			ShapeRenderer.draw(pathHitbox);
		}
		g.setColor(Color.white);
	}
	
	
	public void renderHitbox(Graphics g, int hb){
		Image img = owner.getCurrentFrame();
		switch(hb){
			case HITBOX_WHOLE:
				g.setColor(Color.pink);
				g.drawRect(hitbox.getMinX(), hitbox.getMinY(), img.getWidth(), img.getHeight());
			break;
			case HITBOX_FOOT:
				g.setColor(Color.blue);
				g.drawRect(footHitbox.getMinX(), footHitbox.getMinY(), footHitbox.getWidth(), footHitbox.getHeight());
			break;
			case HITBOX_HEAD:
				g.setColor(Color.red);
				g.drawRect(headHitbox.getMinX(), headHitbox.getMinY(), headHitbox.getWidth(), headHitbox.getHeight());
			break;
			case HITBOX_LEFT:
				g.setColor(Color.green);
				g.drawRect(leftHitbox.getMinX(), leftHitbox.getMinY(), leftHitbox.getWidth(), leftHitbox.getHeight());
			break;
			case HITBOX_RIGHT:
				g.setColor(Color.yellow);
				g.drawRect(rightHitbox.getMinX(), rightHitbox.getMinY(), rightHitbox.getWidth(), rightHitbox.getHeight());
			break;
		}
		g.setColor(Color.white);
		
		/*
		g.drawRect(footHitbox.getMinX(), footHitbox.getMinY(), footHitbox.getWidth(), footHitbox.getHeight());
		
		g.drawRect(headHitbox.getMinX(), headHitbox.getMinY(), headHitbox.getWidth(), headHitbox.getHeight());
		
		g.drawRect(leftHitbox.getMinX(), leftHitbox.getMinY(), leftHitbox.getWidth(), leftHitbox.getHeight());
		
		g.drawRect(rightHitbox.getMinX(), rightHitbox.getMinY(), rightHitbox.getWidth(), rightHitbox.getHeight());
		*/
	}
	
}
