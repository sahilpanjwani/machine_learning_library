package cs362;

import java.io.Serializable;

public class RegressionLabel extends Label implements Serializable {
	
	//variable to store regression label value
	private double mRegLabel;
	
	public RegressionLabel(double label) {
		// TODO Auto-generated constructor stub
		
		//store label at the regression label variable
		this.mRegLabel = label;
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		//return the value of the regression label as a string
		return Double.toString(this.mRegLabel);
	}

}
