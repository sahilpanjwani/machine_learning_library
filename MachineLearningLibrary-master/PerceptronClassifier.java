package cs362;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PerceptronClassifier extends Predictor {
	
	double[] weights;
	double learningRate;
	int iterations = 1;
	int featureCount = 0;
	
	@Override
	public void train(List<Instance> instances) {
		// TODO Auto-generated method stub
		learningRate = Learn.online_learning_rate;
		for (Instance inst : instances) {
			int highestFeature = Collections.max(inst.getFeatureVector().getFeatureMap().keySet());
			if(highestFeature > featureCount)	featureCount = highestFeature;
		}
		
		weights = new double[featureCount];
		for(int i = 0; i<featureCount; i++)	weights[i] = 0;
		
		for(int i = 0; i < iterations; i++) {
			
			for(Instance inst : instances) {
				double actualLabel = Integer.parseInt(inst.getLabel().toString());
				if(actualLabel == 0)	actualLabel = -1;
				double dotProduct = 0;
				double predLabel;
				HashMap<Integer, Double> featureMap = inst.getFeatureVector().getFeatureMap();
				double[] features = new double[featureCount];
				for(int f=0; f<featureCount; f++) {
					if(featureMap.containsKey(f))	features[f] = featureMap.get(f);
					else	features[f] = 0;
				}
				for(int k = 0; k<featureCount; k++) {
					dotProduct += (weights[k])*(features[k]);
				}
				if(dotProduct >= 0) predLabel=1;
				else predLabel=-1;
				
				if(predLabel != actualLabel)  for(int k = 0; k<featureCount; k++)	weights[k] += actualLabel*learningRate*features[k];
								
			}
		}

	}

	@Override
	public Label predict(Instance instance) {
		// TODO Auto-generated method stub
		HashMap<Integer, Double> featureMap = instance.getFeatureVector().getFeatureMap();
		double[] features = new double[featureCount];
		for(int f=0; f<featureCount; f++) {
			if(featureMap.containsKey(f))	features[f] = featureMap.get(f);
			else	features[f] = 0;
		}
		double dotProduct = 0;
		for(int k = 0; k<featureCount; k++) {
			dotProduct += (weights[k])*(features[k]);
		}
		if(dotProduct >= 0) return new ClassificationLabel(1);
		else return new ClassificationLabel(0);
	}

}
