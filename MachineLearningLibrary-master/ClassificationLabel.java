package cs362;

import java.io.Serializable;

public class ClassificationLabel extends Label implements Serializable {
	
	//variable to store class label
	private int mClassLabel;
	
	public ClassificationLabel(int label) {
		// TODO Auto-generated constructor stub
		
		//store label into the class label variable
		this.mClassLabel = label;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		//return the value stored at the class label variable as a string
		return Integer.toString(this.mClassLabel);
	}

}
