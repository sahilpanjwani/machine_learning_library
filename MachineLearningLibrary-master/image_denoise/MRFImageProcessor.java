package cs362.image_denoise;

public class MRFImageProcessor {
	
	double eta;
	double beta;
	int num_iterations;
	
	public MRFImageProcessor(double eta, double beta, int num_iterations) {
		// TODO Auto-generated constructor stub
		this.eta = eta;
		this.beta = beta;
		this.num_iterations = num_iterations;
	}

	public int[][] denoisifyImage(int[][] encoded_image_array, int[][] encoded_image_array2) {
		// TODO Auto-generated method stub
		
		int [][] hidden_nodes = new int[encoded_image_array.length][encoded_image_array[0].length];
		for (int i=0; i<hidden_nodes.length; i++) {
			for (int j=0; j<hidden_nodes[i].length; j++) {
				hidden_nodes[i][j] = encoded_image_array[i][j];
			}
		}
		
		if(ImageUtils.countColors(encoded_image_array, false) == 2) {
									
			for(int iteration=0; iteration<num_iterations; iteration++) {
				int[][] new_hidden_nodes = new int[encoded_image_array.length][encoded_image_array[0].length];
				for (int i=0; i<hidden_nodes.length; i++) {
					for (int j=0; j<hidden_nodes[i].length; j++) {
						double energyZero = 0;
						double energyOne = 0;
						try {
							if(hidden_nodes[i][j-1] == 0)	energyZero -= this.beta;
							else	energyOne -= this.beta;
						} catch(Exception e) {
							
						}
						try {	
							if(hidden_nodes[i][j+1] == 0)	energyZero -= this.beta;
							else	energyOne -= this.beta;
						} catch(Exception e) {
							
						}
						try {	
							if(hidden_nodes[i-1][j] == 0)	energyZero -= this.beta;
							else	energyOne -= this.beta;
						} catch(Exception e) {
							
						}
						try {	
							if(hidden_nodes[i+1][j] == 0)	energyZero -= this.beta;
							else	energyOne -= this.beta;
						} catch(Exception e) {
							
						}
						try {	
							if(encoded_image_array[i][j] == 0)	energyZero -= this.eta;
							else energyOne -= this.eta;
						} catch(Exception e) {
							
						}
												
						if(energyZero <= energyOne)	new_hidden_nodes[i][j] = 0;
						else new_hidden_nodes[i][j] = 1;
					}
				}
				
				for (int i=0; i<hidden_nodes.length; i++) {
					for (int j=0; j<hidden_nodes[i].length; j++) {
						hidden_nodes[i][j] = new_hidden_nodes[i][j];
					}
				}
				
			}
			
			
		} else {
			int numColors = ImageUtils.countColors(encoded_image_array, false);
			for(int iteration=0; iteration<num_iterations; iteration++) {
				System.out.println("iteration-" + iteration);
				int[][] new_hidden_nodes = new int[encoded_image_array.length][encoded_image_array[0].length];
				for (int i=0; i<hidden_nodes.length; i++) {
					for (int j=0; j<hidden_nodes[i].length; j++) {
						double[] energy = new double[numColors];
						int yi = encoded_image_array[i][j];
						int xj;
						int absVal;
						double finalVal;
						for(int k=0; k<numColors; k++) {
							try {
								xj = hidden_nodes[i][j-1];
								absVal = Math.abs(xj-k);
								finalVal = Math.log(absVal+1) - 1;
								energy[k] += this.beta*finalVal;
							} catch (Exception e) {
								
							}
							try {
								xj = hidden_nodes[i][j+1];
								absVal = Math.abs(xj-k);
								finalVal = Math.log(absVal+1) - 1;
								energy[k] += this.beta*finalVal;
							} catch(Exception e) {
								
							}
							try {
								xj = hidden_nodes[i-1][j];
								absVal = Math.abs(xj-k);
								finalVal = Math.log(absVal+1) - 1;
								energy[k] += this.beta*finalVal;
							} catch(Exception e) {
								
							}
							try {
								xj = hidden_nodes[i+1][j];
								absVal = Math.abs(xj-k);
								finalVal = Math.log(absVal+1) - 1;
								energy[k] += this.beta*finalVal;
							} catch (Exception e) {
								
							}
							try {
								absVal = Math.abs(k-yi);
								finalVal = Math.log(absVal+1) - 1;
								energy[k] += this.eta*finalVal;
							} catch(Exception e) {
								
							}
							
						}
						int minColor = 0;
						double minEnergy = 100000;
						for(int k=0; k<numColors; k++) {
							if(energy[k] < minEnergy) {
								minColor = k;
								minEnergy = energy[k];
							}
						}
						new_hidden_nodes[i][j] = minColor;
					}
				}
				
				for (int i=0; i<hidden_nodes.length; i++) {
					for (int j=0; j<hidden_nodes[i].length; j++) {
						hidden_nodes[i][j] = new_hidden_nodes[i][j];
					}
				}
			
			}
		}
		return hidden_nodes;
		
	}

}
