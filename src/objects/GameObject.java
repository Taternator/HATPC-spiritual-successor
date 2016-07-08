package objects;

import objects.tiles.TerrainObject;

import org.newdawn.slick.Color;

import java.io.File;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import com.spud2D.AnimationLibrary;
import com.spud2D.Controls;
import com.spud2D.LevelObject;
import com.spud2D.World;

public class GameObject implements Comparable<GameObject> {
	public float baseX, baseY;
	//public float disX, disY;
	public int layer=0;
	public float width, height;
	public float motionX, motionY;
	public float moveSpeed, jumpHeight;
	public final int DIRECTION_UP = 0, DIRECTION_DOWN = 1, DIRECTION_LEFT = 2, DIRECTION_RIGHT = 3;
	public int direction;
	public float overheal=0, health=0, maxHealth = 10, hitlag;
	public float weight = 0.9F;
	public int jumpTicks, maxJumpTicks=-25, liveTicks, deathTicks, insideTicks;
	public boolean onGround = false, isDead=false, shouldOverheal=false, canMove = false, canWallCling = false, canFly = false, isSolid = true, insideOfObject = false, isControllable = false, shouldRender = true;
	public World world;
	
	public Animation currentAnimation, idleAnimation, walkAnimation, jumpAnimation, fallAnimation;
	
	public ObjectHitbox hitbox;
	
	public String defaultImgPath = "res/textures/terrain/rock";
	
	public GameObject(){
		hitbox = new ObjectHitbox(this);
	}
	
	public GameObject(World w){
		hitbox = new ObjectHitbox(this);
		world = w;
		health = maxHealth;
		String[] split = defaultImgPath.split("/");
		
		String path = "";
		
		for(String s : split){
			if(s!=split[split.length-1]){
				path+=s;
				path+="/";
			}
		}
		this.idleAnimation=AnimationLibrary.DEBUG_TEST;
		
		this.setSize(1, 1);
	}
	
	
	public void update(float sx, float sy){
		if(health<= 0&&!isDead){
			this.isDead = true;
		}
		if(this.isDead){
			deathTicks ++;
			if(deathTicks >=40){
				onDeath();
				world.removeObject(this);
			}
		}
		else{
			liveTicks ++;
		}
		
		if(insideOfObject){
			insideTicks++;
		}
		else{
			insideTicks=0;
		}
		
		if(currentAnimation == null){
			currentAnimation=idleAnimation;
		}
		
		//Physics
		if(canMove){
			
			if(!onGround&&!canFly){
				motionY += world.gravity;
				if(this.hitlag==0){
					this.currentAnimation = this.fallAnimation;
				}
			}
			
			if(jumpTicks<0){
				motionY-=jumpHeight*( ((Math.abs(jumpTicks)))*0.05F );
				jumpTicks++;
			}
			
			
			baseX += motionX;
			baseY += motionY;
			
			motionX *= 0.9F;
			motionY *= 0.95F;
			
		}
		
		
		if(currentAnimation == null){
			currentAnimation=idleAnimation;
		}
		
		if(currentAnimation!=idleAnimation){
			if(currentAnimation.getFrame()==currentAnimation.getFrameCount()){
				currentAnimation=idleAnimation;
			}
		}
		
		if(currentAnimation.isStopped())
		currentAnimation.start();
		
		
		
		//disX = baseX+sx;
		//disY = baseY+sy;
		
		setSize(getCurrentFrame().getWidth(),getCurrentFrame().getHeight());
		
		getHitbox().update();
		
		onGround=false;
	}
	
	public boolean collideWith(GameObject other){
		return false;
	}
	
	public boolean insideOfObject(GameObject other){
		Rectangle r1 = new Rectangle(baseX,baseY,width,height);
		Rectangle r2 = new Rectangle(other.baseX,other.baseY,other.width,other.height);
		r1.grow(canWallCling ? -1 : 0, -1);
		r2.grow(canWallCling ? -1 : 0, -1);
		return r1.intersects(r2);
	}
	
	public void pushObjectOut(GameObject other){
		
	}
	
	public void pushOutOfObject(GameObject other){
		//this.onGround=false;
		if(other instanceof LevelObject){
			//other.pushObjectOut(this);
			//return;
		}
		/*
		if(other.headHitbox.getMinY()>=hitbox.getMinY()){
			System.out.println("yea");
			baseY--;
		}
		
		if(other.hitbox.intersects(leftHitbox)){
			baseX+=3;
		}
		if(other.hitbox.intersects(rightHitbox)){
			baseX-=3;
		}
		
		if(other.headHitbox.intersects(footHitbox)){
			baseY-=3;
		}
		*/
		if(1==1){
			return;
		}
		
		Rectangle r1 = new Rectangle(baseX,baseY,width,height);
		Rectangle r2 = new Rectangle(other.baseX,other.baseY,other.width,other.height);
		//r1.grow(-2, 2);
		//r2.grow(-2, 2);
		if(r1.getMaxY()>r2.getMaxY()){
			//System.out.println("y--~");
			baseY -=2;
			if(other instanceof TerrainObject){
				//motionY = 0;
			}
		}
		if(r1.getMinY()<r2.getMinY()){
			//System.out.println("y++~");
			baseY -=2;
			if(other instanceof TerrainObject){
				//motionY = 0;
			}
		}
		if(r1.getMaxX()>r2.getMinX()){
			baseX -=2;
			if(other instanceof TerrainObject){
				//motionX = 0;
			}
		}
		if(r1.getMinX()<r2.getMaxX()){
			//baseX +=2;
			if(other instanceof TerrainObject){
				//motionX = 0;
			}
		}
		
	}
	
	public void onDeath() {
		
	}
	
	public void spawnBloodSpurts(){
		for(int i = 0; i < (world.rand.nextInt(3)*50)+80; i ++){
			ParticleBloodDrops particle = new ParticleBloodDrops(world);
			float h,k;
			
			h=world.rand.nextBoolean() ? ((world.rand.nextInt(3)+1)*4) : -((world.rand.nextInt(3)+1)*4);
			k= -((world.rand.nextInt(3)+0.5F)*7);// : -((world.rand.nextInt(3)+2)*9);
			
			h *= world.rand.nextFloat();
			k *= world.rand.nextFloat()+0.2F;
			
			particle.setPosition(baseX+(width/2), baseY+(height/2));
			particle.motionX = h;particle.motionY = k;
			particle.motionX += motionX; particle.motionY += motionY;
			world.addObjectToSpawnQueue(particle);
		}
	}
	
	public void heal(float amt){
		health += amt;
		if(health>maxHealth){
			if(shouldOverheal) {
				if(health>(maxHealth+overheal)){
					health = (maxHealth+overheal);
				}
			}
			else {
				health=maxHealth;
			}
		}
	}

	public void setSize(float w, float h){
		width=w;height=h;
		getHitbox().setSize(w, h);
	}
	
	public void setPosition(float x, float y){
		baseX=x;baseY=y;
	}
	
	public float getDistanceTo(GameObject other){
		float res = 0;
		
		res+=(baseX-other.baseX);
		res+=(baseY-other.baseY);
		//if(res < 48)
		//System.out.println("DIST:"+res);
		return Math.abs(res);
	}
	
	public void jump() {
		if(onGround&&jumpTicks==0){
			onGround = false;
			motionY-= 2.2F;
			jumpTicks=maxJumpTicks;
			if(this.jumpAnimation!=null){
				this.currentAnimation=this.jumpAnimation;
			}
		}
	}
	
	
	
	
	public void render(Graphics g){
		if(shouldRender&&currentAnimation!=null){
			Image img = getCurrentFrame();
			g.drawImage(img,baseX,baseY);
			if(Controls.DEBUG){
				renderHitbox(g);
			}
		}
	}
	
	public void renderHitbox(Graphics g){
		g.setColor(Color.darkGray);
		Image img = getCurrentFrame();
		hitbox.renderAllHitboxes(g);
	}
	
	public Animation flipAnimation(){
		Animation res = currentAnimation;
		if(res!=null){
			Image[] images = new Image[res.getFrameCount()];
			for(int i = 0; i < images.length; i ++){
				images[i]=res.getImage(i).getFlippedCopy(true, false);
			}
			Animation tmp = new Animation(images, res.getDuration(0));
			res=tmp;
		}
		return res;
	}
	
	public Image getCurrentFrame(){
		return currentAnimation.getCurrentFrame();
	}
	
	public ObjectHitbox getHitbox() {
		return this.hitbox;
	}
	
	
	
	
	
	
	
	public GameObject copy(){
		GameObject obj=null;
		obj = new GameObject(this.world);
		obj.world=this.world;
		obj.isSolid=this.isSolid;
		obj.canMove=this.canMove;
		obj.baseX=this.baseX;obj.baseY=this.baseY;
		obj.motionX=this.motionX;obj.motionY=this.motionY;
		obj.moveSpeed=this.moveSpeed;obj.jumpHeight=this.jumpHeight;
		obj.health=this.health;obj.maxHealth=this.maxHealth;
		obj.idleAnimation=this.idleAnimation;
		obj.currentAnimation=this.idleAnimation;
		
		
		return obj;
	}


	@Override
	public int compareTo(GameObject other) {
		return layer - other.layer;
	}
	
}
