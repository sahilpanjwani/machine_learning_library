package cs362;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class NaiveBayesClassifier extends Predictor {
	
	double smoothingConstant;
	int[] featureTrueOutputTrue;
	int[] featureTrueOutputFalse;
	int[] featureFalseOutputTrue;
	int[] featureFalseOutputFalse;
	int outputTrue = 0;
	int outputFalse = 0;
	int featureCount = 0;
		
	@Override
	public void train(List<Instance> instances) {
		// TODO Auto-generated method stub
		for (Instance inst : instances) {
			int highestFeature = Collections.max(inst.getFeatureVector().getFeatureMap().keySet());
			if(highestFeature > featureCount)	featureCount = highestFeature;
		}
		featureTrueOutputTrue = new int[featureCount];
		featureTrueOutputFalse = new int[featureCount];
		featureFalseOutputTrue = new int[featureCount];
		featureFalseOutputFalse = new int[featureCount];
		
		for(int i = 0; i<featureCount; i++) {
			featureTrueOutputTrue[i] = 0;
			featureTrueOutputFalse[i] = 0;
			featureFalseOutputTrue[i] = 0;
			featureFalseOutputFalse[i] = 0;
		}
		
		for(Instance inst: instances) {
			int output = Integer.parseInt(inst.getLabel().toString());
			if(output == 1)	outputTrue += 1;
			else outputFalse += 1;
			HashMap<Integer, Double> featureMap = inst.getFeatureVector().getFeatureMap();
			
			for(int i = 0; i< featureCount; i++) {
				if(featureMap.containsKey(i)) {
					if(featureMap.get(i) < 0.5) {
						if(output == 1) featureFalseOutputTrue[i] += 1;
						else featureFalseOutputFalse[i] += 1;
					} else {
						if(output == 1) featureTrueOutputTrue[i] += 1;
						else featureTrueOutputFalse[i] += 1;
					}
				} else {
					if(output == 1) featureFalseOutputTrue[i] += 1;
					else featureFalseOutputFalse[i] += 1;
				}
			}
		}

	}

	@Override
	public Label predict(Instance instance) {
		// TODO Auto-generated method stub
		smoothingConstant = Learn.lambda;
		HashMap<Integer, Double> featureMap = instance.getFeatureVector().getFeatureMap();
		int[] features = new int[featureCount];
		for(int i = 0; i<featureCount; i ++) {
			if(featureMap.containsKey(i)) {
				if(featureMap.get(i) < 0.5)	features[i] = 0;
				else features[i] = 1;
			} else {
				features[i] = 0;
			}
		}
		double probTrue = 0;
		double probFalse = 0;
		for(int i =0; i<featureCount; i++) {
			if(i==281) {
				System.out.println("here");
			} else {
				
			}
			if(features[i] == 0){
				probTrue += Math.log(((double) featureFalseOutputTrue[i] + smoothingConstant)/((double) outputTrue + 2*smoothingConstant));
				System.out.println("probTrue" + probTrue);
				probFalse += Math.log(((double) featureFalseOutputFalse[i] + smoothingConstant)/((double) outputFalse + 2*smoothingConstant));
				System.out.println("probFalse" + probFalse);
			}
			else {
				double val = Math.log(((double) featureTrueOutputTrue[i] + smoothingConstant)/((double) outputTrue + 2*smoothingConstant));
				probTrue += val;
				System.out.println("probTrue" + probTrue);
				probFalse += Math.log(((double) featureTrueOutputFalse[i] + smoothingConstant)/((double) outputFalse + 2*smoothingConstant));
				System.out.println("probFalse" + probFalse);
			}
		}
		if(probTrue > probFalse)	return new ClassificationLabel(1);
		else	return new ClassificationLabel(0);
	}

}
