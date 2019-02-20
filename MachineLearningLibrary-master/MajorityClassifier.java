package cs362;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MajorityClassifier extends Predictor implements Serializable{
	
	//the calculated majority label, to be used for all the predictions
	private ClassificationLabel mMajorityLabel;
	
	@Override
	public void train(List<Instance> instances) {
		// TODO Auto-generated method stub
		
		//hashmap to keep track of all the labels and their count
		HashMap<String, Integer> mLabelCountMap = new HashMap<String, Integer>();
		
		//parse through all the instances
		for(Instance inst : instances) {
			//get the label for this instance
			String label = inst.getLabel().toString();
			
			//check if the label exists in the hashmap, if it does,
			//increment the count by one, otherwise insert a new key-value pair
			if(mLabelCountMap.containsKey(label)) {
				int count = mLabelCountMap.get(label);
				mLabelCountMap.put(label, count+1);
			} else 
				mLabelCountMap.put(label, 1);
						
		}
		
		//list to keep track of the most frequent labels
		ArrayList<String> maxLabelList = new ArrayList<String>();
		//variable to keep track of the count for the most frequent label, initially 0
		int maxLabelCount = 0;
		
		//parse through the hashmap
		for(String label : mLabelCountMap.keySet()) {
			
			//if the current label has a count greater than current max label count
			if(mLabelCountMap.get(label) > maxLabelCount) {
				maxLabelList.clear();
				maxLabelList.add(label);
				maxLabelCount = mLabelCountMap.get(label);
			} 
			//if the current label has a count equal to the current max label count
			else if (mLabelCountMap.get(label) == maxLabelCount) {
				maxLabelList.add(label);
			}
		}
		
		//assign a random label from the list of labels with highest count to the majority label
		Random randomGenerator = new Random();
		int index = randomGenerator.nextInt(maxLabelList.size());
		this.mMajorityLabel = new ClassificationLabel(Integer.parseInt(maxLabelList.get(index)));
				
	}

	@Override
	public Label predict(Instance instance) {
		// TODO Auto-generated method stub
		
		// return the stored majority label
		return this.mMajorityLabel;
	}

}
