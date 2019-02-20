package cs362;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AccuracyEvaluator extends Evaluator implements Serializable{

	@Override
	public double evaluate(List<Instance> instances, Predictor predictor) {
		// TODO Auto-generated method stub
		
		//store for the given labels
		ArrayList<Integer> givenLabels = new ArrayList<Integer>();
		//store for the predicted labels
		ArrayList<Integer> predLabels = new ArrayList<Integer>();
		
		//parse through all the instances
		for(Instance inst : instances) {
			//add the given label to the list of given labels
			givenLabels.add(Integer.parseInt(inst.getLabel().toString()));
			//add the predicted label to the list of predicted labels
			predLabels.add(Integer.parseInt(predictor.predict(inst).toString()));
		}
		
		double allLabels = givenLabels.size();
		double corrLabels = 0;
		
		//parse through all the labels
		for(int i = 0; i < allLabels; i++) {
			//if given and predicted labels are equal then increase the corrLabels by one
			if(givenLabels.get(i) == predLabels.get(i))	corrLabels += 1;
		}
		
		
		return ((corrLabels/allLabels)*100);
	}

}
