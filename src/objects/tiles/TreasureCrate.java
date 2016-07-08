package objects.tiles;

import objects.ClientPlayerObject;

import com.spud2D.World;

public class TreasureCrate extends CrateObjectBase {

	public TreasureCrate() {
		
	}

	public TreasureCrate(World w) {
		super(w);
	}
	
	
	protected void openCrate(ClientPlayerObject obj){
		spawnBloodSpurts();
		if(objectInside!=null){
			world.addObjectToSpawnQueue(objectInside);
		}
		
		obj.points++;
	}
}
