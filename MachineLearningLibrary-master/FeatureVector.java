package cs362;

import java.io.Serializable;
import java.util.HashMap;

public class FeatureVector implements Serializable {
	
	//hashmap containing the features as values and indexes as keys
	private HashMap<Integer, Double> mVectorMap = new HashMap<Integer, Double>();
	
	public void add(int index, double value) {
		// TODO Auto-generated method stub
		
		//add the value to the hashmap at the index position
		this.mVectorMap.put(index, value);
		
	}
	
	public double get(int index) {
		// TODO Auto-generated method stub
		
		//get the value at the index position from the hashmap
		return this.mVectorMap.get(index);
	}
	
	//method to get the feature vector hashmap
	public HashMap<Integer, Double> getFeatureMap() {
		
		//return the hashmap
		return this.mVectorMap;
	}

}
