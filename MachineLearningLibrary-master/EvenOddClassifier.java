package cs362;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class EvenOddClassifier extends Predictor implements Serializable{
	
	//the calculated label, to be used for all the predictions
	private ClassificationLabel mOddEvenLabel;
	
	@Override
	public void train(List<Instance> instances) {
		// TODO Auto-generated method stub
		
		//do nothing
		
	}

	@Override
	public Label predict(Instance instance) {
		// TODO Auto-generated method stub
		
		//variable to keep track of sum of features at even positions in the feature vector
		double evenSum = 0;
		//variable to keep track of sum of features at odd positions in the feature vector
		double oddSum = 0;
				
		//get the feature map for the instance's feature vector
		HashMap<Integer, Double> featureMap = instance.getFeatureVector().getFeatureMap();
		
		//parse through all the features in the feature map
		for(Integer i : featureMap.keySet()) {
			//for even numbered features
			if(i%2 == 0)	evenSum += featureMap.get(i);
			//for odd numbered features
			else 			oddSum += featureMap.get(i);
		}
		
		//if sum of even numbered features is greater than or equal to the odd numbered features
		//then label as one otherwise label as zero
		if (evenSum >= oddSum)	this.mOddEvenLabel = new ClassificationLabel(1);
		else					this.mOddEvenLabel = new ClassificationLabel(0);
		
		//return the calculated odd even label
		return this.mOddEvenLabel;
	}

}
