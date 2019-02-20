package cs362;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class LinearRegression extends Predictor implements Serializable {
	
	double[] weight;
	
	@Override
	public void train(List<Instance> instances) {
		// TODO Auto-generated method stub
		double[][] matrix = new double[instances.size()][instances.get(0).getFeatureVector().getFeatureMap().size()+1];
		double[] output = new double[instances.size()];
		int count = 0;
		
		for(Instance inst : instances) {
			output[count] = Double.parseDouble(inst.getLabel().toString());
			HashMap<Integer, Double> map = inst.getFeatureVector().getFeatureMap();
			matrix[count][0] = 1;
			int countFeature = 1;
			for(int key : map.keySet()) {
				matrix[count][countFeature] = map.get(key);
				countFeature += 1;
			}
			count += 1;
		}
		
		RealMatrix featureMatrix = new Array2DRowRealMatrix(matrix);
		RealMatrix featureMatrixTranspose = featureMatrix.transpose();
		RealMatrix temp1 = featureMatrixTranspose.multiply(featureMatrix);
		RealMatrix temp2 = new LUDecomposition(temp1).getSolver().getInverse();
		RealMatrix temp3 = temp2.multiply(featureMatrixTranspose);
		//RealVector outputVector = new ArrayRealVector(output);
		//RealMatrix outputVector = new Array2DRowRealMatrix(output);
		weight = temp3.operate(output);
		
	}

	@Override
	public Label predict(Instance instance) {
		// TODO Auto-generated method stub
		HashMap<Integer, Double> map = instance.getFeatureVector().getFeatureMap();
		double[] features =  new double[map.size()+1];
		features[0] = 1;
		int count = 1;
		double sum = 0;
		for(int key : map.keySet()) {
			features[count] = map.get(key);
			count = count+1;
		}
		for(int i = 0; i<count; i++) {
			System.out.println(i);
			System.out.println(features[i]);
			System.out.println(weight[i]);
			sum += features[i]*weight[i];
		}
		return new RegressionLabel(sum);
	}

}
