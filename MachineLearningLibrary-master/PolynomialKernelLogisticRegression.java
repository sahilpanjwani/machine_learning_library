package cs362;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

public class PolynomialKernelLogisticRegression extends KernelLogisticRegression {
	double[][] gramMatrix;
	int featureCount = 0;
	double exponent;
	KernelLogisticRegression klr;
	List<Instance> instances;
	
	@Override
	public void train(List<Instance> instances) {
		// TODO Auto-generated method stub
		this.instances = instances;
		this.exponent = Learn.polynomial_kernel_exponent;
		
		for (Instance inst : instances) {
			int highestFeature = Collections.max(inst.getFeatureVector().getFeatureMap().keySet());
			if(highestFeature > featureCount)	featureCount = highestFeature;
		}
		
		gramMatrix = new double[instances.size()][instances.size()];
		
		for(int i=0; i<instances.size(); i++) {
			HashMap<Integer, Double> xi_map = instances.get(i).getFeatureVector().getFeatureMap();
			double[] xi_array = new double[featureCount];
			
			for(int f=0; f<featureCount; f++) {
				if(xi_map.containsKey(f+1))	xi_array[f] = xi_map.get(f+1);
				else xi_array[f] = 0;
			}
			RealVector xi = new ArrayRealVector(xi_array);
			
			for(int j=0; j<instances.size(); j++) {
				HashMap<Integer, Double> xj_map = instances.get(j).getFeatureVector().getFeatureMap();
				double[] xj_array = new double[featureCount];
				
				for(int f=0; f<featureCount; f++) {
					if(xj_map.containsKey(f+1))	xj_array[f] = xj_map.get(f+1);
					else xj_array[f] = 0;
				}
				RealVector xj = new ArrayRealVector(xj_array);
				gramMatrix[i][j] = Math.pow(1+xi.dotProduct(xj), exponent);
			}
		}
		
		klr = new KernelLogisticRegression();
		klr.setVariables(gramMatrix, featureCount);
		klr.train(instances);

	}

	@Override
	public Label predict(Instance instance) {
		// TODO Auto-generated method stub
		
		HashMap<Integer, Double> xi_map = instance.getFeatureVector().getFeatureMap();
		double[] xi_array = new double[featureCount];
		
		for(int f=0; f<featureCount; f++) {
			if(xi_map.containsKey(f+1))	xi_array[f] = xi_map.get(f+1);
			else xi_array[f] = 0;
		}
		RealVector xi = new ArrayRealVector(xi_array);
		
		double[] alpha = klr.alpha;
		double z = 0;
		
		for(int j=0; j<instances.size(); j++) {
			double a_j = alpha[j];
			HashMap<Integer, Double> xj_map = instances.get(j).getFeatureVector().getFeatureMap();
			double[] xj_array = new double[featureCount];
			
			for(int f=0; f<featureCount; f++) {
				if(xj_map.containsKey(f+1))	xj_array[f] = xj_map.get(f+1);
				else xj_array[f] = 0;
			}
			RealVector xj = new ArrayRealVector(xj_array);
			
			z += a_j*(Math.pow(1+xi.dotProduct(xj), exponent));
		}
		double g_z = 1/(1+Math.pow(Math.E, -z));
		if(g_z >=0.5)	return new ClassificationLabel(1);
		else return new ClassificationLabel(0);
	}
}
