package com.cs.classifier;

import java.util.ArrayList;
import java.util.HashMap;


/*
 * @author Chaitanya Saxena
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		NBClassifier nbc = new NBClassifier(args[0],args[1]);
	//	NBClassifier nbc = new NBClassifier("/Users/chaitanya/Public/CompBioFinal/basecalling/training_bayes","/Users/chaitanya/Public/CompBioFinal/basecalling/testing_bayes");
		nbc.classify();
		
		//HashMap<String,ArrayList<Node>> hm = dm.getTrainingData();
		//ArrayList<Node> arr = hm.get("G");
		//for(Node n : arr){
		//	System.out.println(n.getAllValues());
	//	}
		
	}

}
