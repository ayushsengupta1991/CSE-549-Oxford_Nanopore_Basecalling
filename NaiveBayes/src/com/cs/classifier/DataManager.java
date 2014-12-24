package com.cs.classifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;



/*
 * @author Chaitanya Saxena
 */
public class DataManager {
	int numBuckets=15;
	File[] trainingFiles;	
	File[] testingFiles;
	File resultFolder;
	int totalLabelCount;
	HashMap<String,Integer> labelCountMap;
	ClassA a;
	ClassT t;
	ClassG g;
	ClassC c;
	float minMean,maxMean,minStdDev,maxStdDev,minLength,maxLength;
	
	float meanDivisor = 0,stdDevDivisor = 0,lengthDivisor = 0;
	
	public DataManager(String trainingDataFolderPath,String testDataFolderPath){
		trainingFiles=new File(trainingDataFolderPath).listFiles();
		testingFiles =new File(testDataFolderPath).listFiles();
		
		resultFolder=new File(new File(testDataFolderPath).getParent()+"/"+"NaiveBayesResults");
		resultFolder.mkdir();
		labelCountMap= new HashMap<String,Integer>();
		labelCountMap.put("A", 0);
		labelCountMap.put("T", 0);
		labelCountMap.put("G", 0);
		labelCountMap.put("C", 0);
		totalLabelCount=0;
		a=new ClassA(numBuckets);
		t=new ClassT(numBuckets);
		g=new ClassG(numBuckets);
		c=new ClassC(numBuckets);	
		meanDivisor = 0;
		stdDevDivisor = 0;
		lengthDivisor = 0;

		minMean=10000;
		maxMean=-1;
		minStdDev=10000;
		maxStdDev=-1;
		minLength=10000;
		maxLength=-1;
		ComputeMaxMin(trainingFiles);
	}
	public File getResultFolder(){
		
		return resultFolder;
	}
	public float getMinMean(){
		return minMean;	
	}
	public float getMaxMean(){
		return maxMean;	
	}
	public float  getMinSD(){
		return minStdDev;	
	}
	public float getMaxSD(){

		return maxStdDev;
	}
	public float getMinLength(){
		return minLength;	
	}
	public float getMaxLength(){
		return maxLength;	
	}
	public float getMeanDivisor(){
		return meanDivisor;	
	}
	public float getStdDevDivisor(){
		return stdDevDivisor;	
	}
	public float getLengthDivisor(){
		return lengthDivisor;	
	}
	
	
	
	public Object getClassA(){
		return a;
	}
	public Object getClassT(){
		return t;
	}
	public Object getClassG(){
		return g;
	}
	public Object getClassC(){
		return c;
	}
	public int getTotalLabelCount(){
	return totalLabelCount;	
	}
	public HashMap<String,Integer> getLabelCountMap(){
	return labelCountMap;	
	}
	public void processTrainingData(){

		//float minMean = 0,maxMean,minStdDev = 0,maxStdDev,minLength = 0,maxLength;

		


		//ComputeMaxMin(trainingFiles);


		meanDivisor=((float)(maxMean-minMean)/numBuckets);



		stdDevDivisor=((float)(maxStdDev-minStdDev)/numBuckets);


		lengthDivisor=((float)(maxLength-minLength)/numBuckets);





		//HashMap<String,ArrayList<Node>> resultMap = new HashMap<String,ArrayList<Node>>();


		for(File f: trainingFiles){
			if(f.getName().contains("nb")){
			BufferedReader reader = null;
			try {


				reader = new BufferedReader(new FileReader(f));
				String text = null;

				ArrayList<String> arr = new ArrayList<String>();
				while ((text = reader.readLine()) != null) {
					arr.add(text);
				}
				for(int i=0;i<arr.size();i++){

					String[] strArr= arr.get(i).split(" ");


					switch(strArr[3].substring(2,3)){
					case "A":
						//if(a.getiBucketMean()[(int)((mean-minMean)/meanDivisor)]==null){

						int[] m2mA=a.getM2BucketMean();
						int[] m1mA=a.getM1BucketMean();
						int[] mA= a.getBucketMean();
						int[] a1mA = a.getA1BucketMean();
						int[] a2mA = a.getA2BucketMean();

						int[] m2sA=a.getM2BucketSD();
						int[] m1sA=a.getM1BucketSD();
						int[] sA= a.getBucketSD();
						int[] a1sA = a.getA1BucketSD();
						int[] a2sA= a.getA2BucketSD();

						int[] m2lA=a.getM2BucketLN();
						int[] m1lA=a.getM1BucketLN();
						int[] lA= a.getBucketLN();
						int[] a1lA = a.getA1BucketLN();
						int[] a2lA = a.getA2BucketLN();

						if(i==0){
							m2mA[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]+=1;
							m1mA[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]+=1;

							m2sA[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
							m1sA[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;

							m2lA[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]+=1;
							m1lA[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]+=1;


						}
						else if(i==1){
							m2mA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]+=1;
							m1mA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]+=1;

							m2sA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
							m1sA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;

							m2lA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]+=1;
							m1lA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]+=1;

						}
						else{
							m2mA[(int)((Float.parseFloat(arr.get(i-2).split(" ")[0])-minMean)/meanDivisor)]+=1;
							m1mA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]+=1;

							m2sA[(int)((Float.parseFloat(arr.get(i-2).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
							m1sA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;

							m2lA[(int)((Float.parseFloat(arr.get(i-2).split(" ")[2])-minLength)/lengthDivisor)]+=1;
							m1lA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]+=1;

						}
						if(i==arr.size()-1){
							a1mA[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]+=1;
							a2mA[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]+=1;

							a1sA[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
							a2sA[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;

							a1lA[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]+=1;
							a2lA[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]+=1;

						}
						else if(i==arr.size()-2){
							a1mA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]+=1;
							a2mA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]+=1;

							a1sA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
							a2sA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;

							a1lA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]+=1;
							a2lA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]+=1;

						}
						else{
							a1mA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]+=1;
							a2mA[(int)((Float.parseFloat(arr.get(i+2).split(" ")[0])-minMean)/meanDivisor)]+=1;

							a1sA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
							a2sA[(int)((Float.parseFloat(arr.get(i+2).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;

							a1lA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]+=1;
							a2lA[(int)((Float.parseFloat(arr.get(i+2).split(" ")[2])-minLength)/lengthDivisor)]+=1;


						}
						mA[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]+=1;
						sA[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
						lA[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]+=1;


						a.setM2BucketMean(m2mA);
						a.setM1BucketMean(m1mA);
						a.setBucketMean(mA);
						a.setA1BucketMean(a1mA);
						a.setA2BucketMean(a2mA);

						a.setM2BucketSD(m2sA);
						a.setM1BucketSD(m1sA);
						a.setBucketSD(sA);
						a.setA1BucketSD(a1sA);
						a.setA2BucketSD(a2sA);

						a.setM2BucketLN(m2lA);
						a.setM1BucketLN(m1lA);
						a.setBucketLN(lA);
						a.setA1BucketLN(a1lA);
						a.setA2BucketLN(a2lA);


						break;	
					case "T":


						int[] m2mT=t.getM2BucketMean();
						int[] m1mT=t.getM1BucketMean();
						int[] mT= t.getBucketMean();
						int[] a1mT = t.getA1BucketMean();
						int[] a2mT = t.getA2BucketMean();

						int[] m2sT=t.getM2BucketSD();
						int[] m1sT=t.getM1BucketSD();
						int[] sT= t.getBucketSD();
						int[] a1sT = t.getA1BucketSD();
						int[] a2sT= t.getA2BucketSD();

						int[] m2lT=t.getM2BucketLN();
						int[] m1lT=t.getM1BucketLN();
						int[] lT= t.getBucketLN();
						int[] a1lT = t.getA1BucketLN();
						int[] a2lT = t.getA2BucketLN();

						if(i==0){
							m2mT[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]+=1;
							m1mT[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]+=1;

							m2sT[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
							m1sT[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;

							m2lT[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]+=1;
							m1lT[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]+=1;


						}
						else if(i==1){
							m2mT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]+=1;
							m1mT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]+=1;

							m2sT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
							m1sT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;

							m2lT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]+=1;
							m1lT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]+=1;

						}
						else{
							m2mT[(int)((Float.parseFloat(arr.get(i-2).split(" ")[0])-minMean)/meanDivisor)]+=1;
							m1mT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]+=1;

							m2sT[(int)((Float.parseFloat(arr.get(i-2).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
							m1sT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;

							m2lT[(int)((Float.parseFloat(arr.get(i-2).split(" ")[2])-minLength)/lengthDivisor)]+=1;
							m1lT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]+=1;

						}
						if(i==arr.size()-1){
							a1mT[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]+=1;
							a2mT[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]+=1;

							a1sT[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
							a2sT[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;

							a1lT[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]+=1;
							a2lT[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]+=1;

						}
						else if(i==arr.size()-2){
							a1mT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]+=1;
							a2mT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]+=1;

							a1sT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
							a2sT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;

							a1lT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]+=1;
							a2lT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]+=1;

						}
						else{
							a1mT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]+=1;
							a2mT[(int)((Float.parseFloat(arr.get(i+2).split(" ")[0])-minMean)/meanDivisor)]+=1;

							a1sT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
							a2sT[(int)((Float.parseFloat(arr.get(i+2).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;

							a1lT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]+=1;
							a2lT[(int)((Float.parseFloat(arr.get(i+2).split(" ")[2])-minLength)/lengthDivisor)]+=1;


						}
						mT[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]+=1;
						sT[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
						lT[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]+=1;

						t.setM2BucketMean(m2mT);
						t.setM1BucketMean(m1mT);
						t.setBucketMean(mT);
						t.setA1BucketMean(a1mT);
						t.setA2BucketMean(a2mT);

						t.setM2BucketSD(m2sT);
						t.setM1BucketSD(m1sT);
						t.setBucketSD(sT);
						t.setA1BucketSD(a1sT);
						t.setA2BucketSD(a2sT);

						t.setM2BucketLN(m2lT);
						t.setM1BucketLN(m1lT);
						t.setBucketLN(lT);
						t.setA1BucketLN(a1lT);
						t.setA2BucketLN(a2lT);

						break;
					case "C":

						int[] m2mC=c.getM2BucketMean();
						int[] m1mC=c.getM1BucketMean();
						int[] mC= c.getBucketMean();
						int[] a1mC = c.getA1BucketMean();
						int[] a2mC = c.getA2BucketMean();

						int[] m2sC=c.getM2BucketSD();
						int[] m1sC=c.getM1BucketSD();
						int[] sC= c.getBucketSD();
						int[] a1sC = c.getA1BucketSD();
						int[] a2sC= c.getA2BucketSD();

						int[] m2lC=c.getM2BucketLN();
						int[] m1lC=c.getM1BucketLN();
						int[] lC= c.getBucketLN();
						int[] a1lC = c.getA1BucketLN();
						int[] a2lC = c.getA2BucketLN();


						if(i==0){
							m2mC[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]+=1;
							m1mC[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]+=1;

							m2sC[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
							m1sC[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;

							m2lC[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]+=1;
							m1lC[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]+=1;


						}
						else if(i==1){
							m2mC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]+=1;
							m1mC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]+=1;

							m2sC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
							m1sC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;

							m2lC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]+=1;
							m1lC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]+=1;

						}
						else{
							m2mC[(int)((Float.parseFloat(arr.get(i-2).split(" ")[0])-minMean)/meanDivisor)]+=1;
							m1mC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]+=1;

							m2sC[(int)((Float.parseFloat(arr.get(i-2).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
							m1sC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;

							m2lC[(int)((Float.parseFloat(arr.get(i-2).split(" ")[2])-minLength)/lengthDivisor)]+=1;
							m1lC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]+=1;

						}
						if(i==arr.size()-1){
							a1mC[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]+=1;
							a2mC[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]+=1;

							a1sC[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
							a2sC[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;

							a1lC[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]+=1;
							a2lC[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]+=1;

						}
						else if(i==arr.size()-2){
							a1mC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]+=1;
							a2mC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]+=1;

							a1sC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
							a2sC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;

							a1lC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]+=1;
							a2lC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]+=1;

						}
						else{
							a1mC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]+=1;
							a2mC[(int)((Float.parseFloat(arr.get(i+2).split(" ")[0])-minMean)/meanDivisor)]+=1;

							a1sC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
							a2sC[(int)((Float.parseFloat(arr.get(i+2).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;

							a1lC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]+=1;
							a2lC[(int)((Float.parseFloat(arr.get(i+2).split(" ")[2])-minLength)/lengthDivisor)]+=1;


						}
						mC[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]+=1;
						sC[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
						lC[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]+=1;

						c.setM2BucketMean(m2mC);
						c.setM1BucketMean(m1mC);
						c.setBucketMean(mC);
						c.setA1BucketMean(a1mC);
						c.setA2BucketMean(a2mC);

						c.setM2BucketSD(m2sC);
						c.setM1BucketSD(m1sC);
						c.setBucketSD(sC);
						c.setA1BucketSD(a1sC);
						c.setA2BucketSD(a2sC);

						c.setM2BucketLN(m2lC);
						c.setM1BucketLN(m1lC);
						c.setBucketLN(lC);
						c.setA1BucketLN(a1lC);
						c.setA2BucketLN(a2lC);


						break;
					case "G":

						int[] m2mG=g.getM2BucketMean();
						int[] m1mG=g.getM1BucketMean();
						int[] mG= g.getBucketMean();
						int[] a1mG = g.getA1BucketMean();
						int[] a2mG = g.getA2BucketMean();

						int[] m2sG=g.getM2BucketSD();
						int[] m1sG=g.getM1BucketSD();
						int[] sG= g.getBucketSD();
						int[] a1sG = g.getA1BucketSD();
						int[] a2sG= g.getA2BucketSD();

						int[] m2lG=g.getM2BucketLN();
						int[] m1lG=g.getM1BucketLN();
						int[] lG= g.getBucketLN();
						int[] a1lG = g.getA1BucketLN();
						int[] a2lG = g.getA2BucketLN();


						if(i==0){
							m2mG[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]+=1;
							m1mG[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]+=1;

							m2sG[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
							m1sG[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;

							m2lG[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]+=1;
							m1lG[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]+=1;


						}
						else if(i==1){
							m2mG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]+=1;
							m1mG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]+=1;

							m2sG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
							m1sG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;

							m2lG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]+=1;
							m1lG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]+=1;

						}
						else{
							m2mG[(int)((Float.parseFloat(arr.get(i-2).split(" ")[0])-minMean)/meanDivisor)]+=1;
							m1mG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]+=1;

							m2sG[(int)((Float.parseFloat(arr.get(i-2).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
							m1sG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;

							m2lG[(int)((Float.parseFloat(arr.get(i-2).split(" ")[2])-minLength)/lengthDivisor)]+=1;
							m1lG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]+=1;

						}
						if(i==arr.size()-1){
							a1mG[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]+=1;
							a2mG[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]+=1;

							a1sG[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
							a2sG[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;

							a1lG[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]+=1;
							a2lG[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]+=1;

						}
						else if(i==arr.size()-2){
							a1mG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]+=1;
							a2mG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]+=1;

							a1sG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
							a2sG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;

							a1lG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]+=1;
							a2lG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]+=1;

						}
						else{
							a1mG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]+=1;
							a2mG[(int)((Float.parseFloat(arr.get(i+2).split(" ")[0])-minMean)/meanDivisor)]+=1;

							a1sG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
							a2sG[(int)((Float.parseFloat(arr.get(i+2).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;

							a1lG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]+=1;
							a2lG[(int)((Float.parseFloat(arr.get(i+2).split(" ")[2])-minLength)/lengthDivisor)]+=1;


						}
						mG[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]+=1;
						sG[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]+=1;
						lG[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]+=1;


						g.setM2BucketMean(m2mG);
						g.setM1BucketMean(m1mG);
						g.setBucketMean(mG);
						g.setA1BucketMean(a1mG);
						g.setA2BucketMean(a2mG);

						g.setM2BucketSD(m2sG);
						g.setM1BucketSD(m1sG);
						g.setBucketSD(sG);
						g.setA1BucketSD(a1sG);
						g.setA2BucketSD(a2sG);


						g.setM2BucketLN(m2lG);
						g.setM1BucketLN(m1lG);
						g.setBucketLN(lG);
						g.setA1BucketLN(a1lG);
						g.setA2BucketLN(a2lG);


						break;
					}



				}

			}








			catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 

			finally {
				try {
					if (reader != null) {
						reader.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}	
			}
		}


	}
	private void ComputeMaxMin(File[] trainingFiles2) {

		//float minMean=1000,maxMean=0,minStdDev=1000,maxStdDev=0,minLength=1000,maxLength=0;
		for(File f: trainingFiles2){
			if(f.getName().contains("nb")){
			BufferedReader reader = null;
			try {


				reader = new BufferedReader(new FileReader(f));
				String text = null;
				while ((text = reader.readLine()) != null) {
					String[] lineStr= text.split(" ");
					    if(lineStr.length>3 && !lineStr[3].substring(2, 3).isEmpty() && !lineStr[3].substring(2, 3).equals("-")){
						float mean=Float.parseFloat(lineStr[0]);
						float stdDev=Float.parseFloat(lineStr[1]);
						float length=Float.parseFloat(lineStr[2]);
						labelCountMap.put(lineStr[3].substring(2, 3), labelCountMap.get(lineStr[3].substring(2, 3))+1);
						totalLabelCount++;
						minMean=Math.min(mean,minMean);
						maxMean=Math.max(mean, maxMean);
						minStdDev=Math.min(stdDev,minStdDev);
						maxStdDev=Math.max(stdDev, maxStdDev);
						minLength=Math.min(length,minLength);
						maxLength=Math.max(length, maxLength);

					}
				}
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			finally {
				try {
					if (reader != null) {
						reader.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			}

		}


	}
	public File[] getTestFiles() {

		return testingFiles;
	}





}
