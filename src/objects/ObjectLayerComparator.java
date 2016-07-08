package objects;

import java.util.Comparator;
import java.util.List;

public class ObjectLayerComparator implements Comparator<GameObject>{

	@Override
	public int compare(GameObject arg0, GameObject arg1) {
		return arg0.compareTo(arg1);
	}

}
