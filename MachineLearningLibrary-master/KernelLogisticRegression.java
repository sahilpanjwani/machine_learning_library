package cs362;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class KernelLogisticRegression extends Predictor {

	int featureCount;
	double[] alpha;
	double learningRate;
	double[][] gramMatrix;
	int iterations;
		
	@Override
	public void train(List<Instance> instances) {
		// TODO Auto-generated method stub
		learningRate = Learn.gradient_ascent_learning_rate;
		iterations = Learn.gradient_ascent_training_iterations;
						
		int numInstances = instances.size();
		alpha = new double[numInstances];
		//double[] alphaCopy = new double[numInstances];
		for(int n=0; n<iterations; n++) {
			//System.out.println("iteration- " + n);
			for(int k=0; k<numInstances; k++) {
				//System.out.println("k= " + k);
				double diff = 0;
				for(int i=0; i<numInstances; i++) {
					double K_ik = gramMatrix[i][k];
					int yi = Integer.parseInt(instances.get(i).getLabel().toString());
					double posZ = 0;
					for(int j=0; j<numInstances; j++) {
						double K_ji = gramMatrix[j][i];
						double a_j = alpha[j];
						posZ += a_j*K_ji;
					}
					double gPosZ = 1/(1+Math.pow(Math.E, -posZ));
					double gNegZ = 1/(1+Math.pow(Math.E, posZ));
					
					diff += yi*gNegZ*K_ik + (1-yi)*gPosZ*(-K_ik);
					
				}
				
				alpha[k] += diff*learningRate;
				
			}
			//alpha = alphaCopy;
		}
		
		
		
	}

	@Override
	public Label predict(Instance instance) {
		
		return null;
	}
	
	public void setVariables(double[][] gramMatrix, int featureCount) {
		this.featureCount = featureCount;
		this.gramMatrix = gramMatrix;
	}

}
