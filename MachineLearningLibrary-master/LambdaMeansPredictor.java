package cs362;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

public class LambdaMeansPredictor extends Predictor {
	
	double default_lambda;
	int featureCount = 0;
	double[] meanX;
	HashMap<Integer, double[]> mu = new HashMap<Integer, double[]>();
	HashMap<Instance, Integer> clusterLabels = new HashMap<Instance, Integer>();
	int k;
	int iterations;
	
	@Override
	public void train(List<Instance> instances) {
		// TODO Auto-generated method stub
		iterations = Learn.clustering_training_iterations;
		
		for (Instance inst : instances) {
			int highestFeature = Collections.max(inst.getFeatureVector().getFeatureMap().keySet());
			if(highestFeature > featureCount)	featureCount = highestFeature;
		}
		featureCount += 1;
		meanX = new double[featureCount];
		for(int i=0; i<featureCount; i++)	meanX[i] = 0;
		for(Instance inst : instances) {
			clusterLabels.put(inst, 1);
			HashMap<Integer, Double> featureMap = inst.getFeatureVector().getFeatureMap();
			for(int key : featureMap.keySet()) {
				meanX[key] += featureMap.get(key);
			}
		}
		double numInstances = instances.size();
		for(int i=0; i<featureCount; i++)	meanX[i] = meanX[i]/numInstances;
		
		if(Learn.cluster_lambda != 0)	default_lambda = Learn.cluster_lambda;
		else {
			double sumSquaredNorms = 0;
			RealVector meanXVector = new ArrayRealVector(meanX);
			for(Instance inst: instances) {
				HashMap<Integer, Double> featureMap = inst.getFeatureVector().getFeatureMap();
				double[] xi = new double[featureCount];
				for(int i=0; i<featureCount; i++) {
					if(featureMap.containsKey(i))	xi[i] = featureMap.get(i);
					else xi[i] = 0;
				}
				RealVector xiVector = new ArrayRealVector(xi);
				RealVector diff = xiVector.subtract(meanXVector);
				double norm = diff.getNorm();
				sumSquaredNorms += Math.pow(norm,2);
			}
			default_lambda = sumSquaredNorms/numInstances;
			
		}
		
		mu.put(1, meanX);
		k=1;
		
		for(int iter=0; iter<iterations; iter++) {
			for(Instance inst:instances) {
				HashMap<Integer, Double> featureMap = inst.getFeatureVector().getFeatureMap();
				double[] xi = new double[featureCount];
				for(int i=0; i<featureCount; i++) {
					if(featureMap.containsKey(i))	xi[i] = featureMap.get(i);
					else xi[i] = 0;
				}
				RealVector xiVector = new ArrayRealVector(xi);
				
				double minSquaredNorm = 2*default_lambda;
				int bestCluster=0;
				for(int key : mu.keySet()) {
					RealVector muJVector = new ArrayRealVector(mu.get(key));
					RealVector diff = xiVector.subtract(muJVector);
					double norm = diff.getNorm();
					double squaredNorm = Math.pow(norm, 2);
					if(squaredNorm < minSquaredNorm) {
						minSquaredNorm = squaredNorm;
						bestCluster = key;
					}
				}
				if(minSquaredNorm > default_lambda) {
					k += 1;
					mu.put(k, xi);
					clusterLabels.put(inst, k);
					
				} else {
					clusterLabels.put(inst, bestCluster);
				}
				
			}
			
			for(int key : mu.keySet()) {
				double[] sum = new double[featureCount];
				double[] avg = new double[featureCount];
				double count = 0;
				for(Instance inst : clusterLabels.keySet()) {
					if(clusterLabels.get(inst) == key) {
						count += 1;
						HashMap<Integer, Double> featureMap = inst.getFeatureVector().getFeatureMap();
						double[] xi = new double[featureCount];
						for(int i=0; i<featureCount; i++) {
							if(featureMap.containsKey(i))	xi[i] = featureMap.get(i);
							else xi[i] = 0;
							sum[i] += xi[i];
						}
						
					}
				}
				if (count != 0) {
					for(int i=0; i<featureCount; i++) {
						avg[i] = sum[i]/count;
					}
					mu.put(key, avg);
				} else {
					for(int i=0; i<featureCount; i++) {
						avg[i] = 0;
					}
					mu.put(key, avg);
				}
				
			}
		}
		
		

	}

	@Override
	public Label predict(Instance instance) {
		// TODO Auto-generated method stub
		HashMap<Integer, Double> featureMap = instance.getFeatureVector().getFeatureMap();
		double[] xi = new double[featureCount];
		for(int i=0; i<featureCount; i++) {
			if(featureMap.containsKey(i))	xi[i] = featureMap.get(i);
			else xi[i] = 0;
		}
		RealVector xiVector = new ArrayRealVector(xi);
		double minSquaredNorm = Math.pow(10, 10);
		int bestCluster=0;
		for(int key : mu.keySet()) {
			RealVector muJVector = new ArrayRealVector(mu.get(key));
			RealVector diff = xiVector.subtract(muJVector);
			double norm = diff.getNorm();
			double squaredNorm = Math.pow(norm, 2);
			if(squaredNorm < minSquaredNorm) {
				minSquaredNorm = squaredNorm;
				bestCluster = key;
			}
		}
		return new ClassificationLabel(bestCluster);
	}

}
