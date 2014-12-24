package com.cs.classifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/*
 * @author Chaitanya Saxena
 */
public class NBClassifier {
	DataManager dm;	

	int totalCount;
	File[] testFiles;
	
	FileOutputStream fos;
	PrintWriter pw;
	public NBClassifier(String trainingPath,String testFolderPath){

		dm = new DataManager(trainingPath,testFolderPath);
		dm.processTrainingData();
		
		

	}

	public int sum(int[] arr){
		int sum=0;
		for(int i=0;i<arr.length;i++)
		sum=sum+arr[i];
		return sum;
	}
	public void classify() {

		HashMap<String,Double> resultMap;
		File[] testFiles = dm.getTestFiles();

		float minMean= dm.getMinMean();
		float maxMean=dm.getMaxMean();
		float minStdDev= dm.getMinSD();
		float maxStdDev=dm.getMaxSD();
		float minLength =dm.getMinLength();
		float maxLength =dm.getMaxLength();
		
		

		ClassA a= (ClassA)dm.getClassA();
		ClassT t= (ClassT)dm.getClassT();
		ClassG g= (ClassG)dm.getClassG();
		ClassC c= (ClassC)dm.getClassC();

		//A
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


		//T
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

		//G

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

		//C 
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

		int[] m2lC=c.getM2BucketSD();
		int[] m1lC=c.getM1BucketSD();
		int[] lC= c.getBucketSD();
		int[] a1lC = c.getA1BucketSD();
		int[] a2lC = c.getA2BucketSD();

		
		float meanDivisor=dm.getMeanDivisor();
		float stdDevDivisor=dm.getStdDevDivisor();
		float lengthDivisor=dm.getLengthDivisor();
		int Acount=dm.getLabelCountMap().get("A");
		int Tcount=dm.getLabelCountMap().get("T");
		int Gcount=dm.getLabelCountMap().get("G");
		int Ccount=dm.getLabelCountMap().get("C");

		float PrA=(float)dm.getLabelCountMap().get("A")/dm.getTotalLabelCount();
		float PrT=(float)dm.getLabelCountMap().get("T")/dm.getTotalLabelCount();
		float PrG=(float)dm.getLabelCountMap().get("G")/dm.getTotalLabelCount();
		float PrC=(float)dm.getLabelCountMap().get("C")/dm.getTotalLabelCount();

				int power3A=dm.getLabelCountMap().get("A")*dm.getLabelCountMap().get("A")*dm.getLabelCountMap().get("A");
				int power6A=power3A*power3A;
				long power9A=power3A*power3A*power3A;
				long power12A=power9A*power3A;
				long power15A=power9A*power3A*power3A;
				int power3T=dm.getLabelCountMap().get("T")*dm.getLabelCountMap().get("T")*dm.getLabelCountMap().get("T");
				int power6T=power3T*power3T;
				long power9T=power3T*power3T*power3T;
				long power12T=power9T*power3T;
				long power15T=power9T*power3T*power3T;
				int power3G=dm.getLabelCountMap().get("G")*dm.getLabelCountMap().get("G")*dm.getLabelCountMap().get("G");
				int power6G=power3G*power3G;
				int power9G=power3G*power3G*power3G;
				long power12G=power9G*power3G;
				long power15G=power9G*power3G*power3G;
				int power3C=dm.getLabelCountMap().get("C")*dm.getLabelCountMap().get("C")*dm.getLabelCountMap().get("C");
				int power6C=power3C*power3C;
				long power9C=power3C*power3C*power3C;
				long power12C=power9C*power3C;
				long power15C=power9C*power3C*power3C;
				BufferedReader reader = null;
				for(File f: testFiles){
					if(f.getName().contains("nb")){
						try {
							fos=new FileOutputStream(new File(dm.getResultFolder()+"/"+f.getName()+".predictedfasta"));
							pw = new PrintWriter(fos,true);
						
						reader = null;
						


							reader = new BufferedReader(new FileReader(f));
							String text = null;

							ArrayList<String> arr = new ArrayList<String>();
							while ((text = reader.readLine()) != null) {
								arr.add(text);
							}
							//entire code goes here
							for(int i=0;i<arr.size();i++){
								resultMap = new HashMap<String,Double>();
								String result="";

								double PrAgivenD=PrA*((float)mA[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mA))*((float)sA[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sA))*((float)lA[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lA));
								//PrAgivenD=(PrAgivenD/power3A);
								double PrTgivenD=PrT*((float)mT[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mT))*((float)sT[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sT))*((float)lT[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lT));
								//PrTgivenD=(PrTgivenD/power3T);
								double PrGgivenD=PrG*((float)mG[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mG))*((float)sG[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sG))*((float)lG[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lG));
								//PrGgivenD=((PrGgivenD)/(power3G));
								double PrCgivenD=PrC*((float)mC[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mC))*((float)sC[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sC))*((float)lC[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lC));
								//PrCgivenD=((PrGgivenD)/(power3C));
								if(i==0){

									double PrAgivenDaD1=PrA*((float)mA[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mA))*((float)sA[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sA))*((float)lA[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lA))*((float)a1mA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mA))*((float)a1sA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sA))*((float)a1lA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lA));
									//PrAgivenDaD1=PrAgivenDaD1/power6A;
									double PrAgivenDaD1aD2=PrA*((float)mA[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mA))*((float)sA[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sA))*((float)lA[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lA))*((float)a1mA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mA))*((float)a1sA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sA))*((float)a1lA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lA))*((float)a2mA[(int)((Float.parseFloat(arr.get(i+2).split(" ")[0])-minMean)/meanDivisor)]/sum(a2mA))*((float)a2sA[(int)((Float.parseFloat(arr.get(i+2).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a2sA))*((float)a2lA[(int)((Float.parseFloat(arr.get(i+2).split(" ")[2])-minLength)/lengthDivisor)]/sum(a2lA));
									//PrAgivenDaD1aD2=PrAgivenDaD1aD2/power3A;
									//PrAgivenDaD1aD2=PrAgivenDaD1aD2/power6A;

									double PrTgivenDaD1=PrT*((float)mT[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mT))*((float)sT[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sT))*((float)lT[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lT))*((float)a1mT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mT))*((float)a1sT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sT))*((float)a1lT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lT));
									//PrTgivenDaD1=PrTgivenDaD1/power6T;
									double PrTgivenDaD1aD2=PrT*((float)mT[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mT))*((float)sT[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sT))*((float)lT[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lT))*((float)a1mT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mT))*((float)a1sT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sT))*((float)a1lT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lT))*((float)a2mT[(int)((Float.parseFloat(arr.get(i+2).split(" ")[0])-minMean)/meanDivisor)]/sum(a2mT))*((float)a2sT[(int)((Float.parseFloat(arr.get(i+2).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a2sT))*((float)a2lT[(int)((Float.parseFloat(arr.get(i+2).split(" ")[2])-minLength)/lengthDivisor)]/sum(a2lT));
									//PrTgivenDaD1aD2=PrTgivenDaD1aD2/power3T;
									//PrTgivenDaD1aD2=PrTgivenDaD1aD2/power6T;
									double PrGgivenDaD1=PrG*((float)mG[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mG))*((float)sG[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sG))*((float)lG[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lG))*((float)a1mG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mG))*((float)a1sG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sG))*((float)a1lG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lG));
									//PrGgivenDaD1=PrGgivenDaD1/power6G;
									double PrGgivenDaD1aD2=PrG*((float)mG[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mG))*((float)sG[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sG))*((float)lG[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lG))*((float)a1mG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mG))*((float)a1sG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sG))*((float)a1lG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lG))*((float)a2mG[(int)((Float.parseFloat(arr.get(i+2).split(" ")[0])-minMean)/meanDivisor)]/sum(a2mG))*((float)a2sG[(int)((Float.parseFloat(arr.get(i+2).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a2sG))*((float)a2lG[(int)((Float.parseFloat(arr.get(i+2).split(" ")[2])-minLength)/lengthDivisor)]/sum(a2lG));
									//PrGgivenDaD1aD2=PrGgivenDaD1aD2/(power3G);
									//PrGgivenDaD1aD2=PrGgivenDaD1aD2/(power6G);
									double PrCgivenDaD1=PrC*((float)mC[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mC))*((float)sC[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sC))*((float)lC[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lC))*((float)a1mC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mC))*((float)a1sC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sC))*((float)a1lC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lC));
									//PrCgivenDaD1=PrCgivenDaD1/power6C;
									double PrCgivenDaD1aD2=PrC*((float)mC[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mC))*((float)sC[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sC))*((float)lC[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lC))*((float)a1mC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mC))*((float)a1sC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sC))*((float)a1lC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lC))*((float)a2mC[(int)((Float.parseFloat(arr.get(i+2).split(" ")[0])-minMean)/meanDivisor)]/sum(a2mC))*((float)a2sC[(int)((Float.parseFloat(arr.get(i+2).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a2sC))*((float)a2lC[(int)((Float.parseFloat(arr.get(i+2).split(" ")[2])-minLength)/lengthDivisor)]/sum(a2lC));
									//PrCgivenDaD1aD2=PrCgivenDaD1aD2/power6C;
									//PrCgivenDaD1aD2=PrCgivenDaD1aD2/power3C;
									
									resultMap.put("A",(PrAgivenD+PrAgivenDaD1+PrAgivenDaD1aD2)/3);
									resultMap.put("T", (PrTgivenD+PrTgivenDaD1+PrTgivenDaD1aD2)/3);
									resultMap.put("G", (PrGgivenD+PrGgivenDaD1+PrGgivenDaD1aD2)/3);
									resultMap.put("C", (PrCgivenD+PrCgivenDaD1+PrCgivenDaD1aD2)/3);
								}
								else if(i==1){

									double PrAgivenMD1DaD1=PrA*((float)m1mA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mA))*((float)m1sA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sA))*((float)m1lA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lA))*((float)mA[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mA))*((float)sA[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sA))*((float)lA[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lA))*((float)a1mA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mA))*((float)a1sA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sA))*((float)a1lA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lA));
									//PrAgivenMD1DaD1=PrAgivenMD1DaD1/power6A;
									//PrAgivenMD1DaD1=PrAgivenMD1DaD1/power3A;
									double PrAgivenMD1DaD1aD2=PrA*((float)m1mA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mA))*((float)m1sA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sA))*((float)m1lA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lA))*((float)mA[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mA))*((float)sA[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sA))*((float)lA[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lA))*((float)a1mA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mA))*((float)a1sA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sA))*((float)a1lA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lA))*((float)a2mA[(int)((Float.parseFloat(arr.get(i+2).split(" ")[0])-minMean)/meanDivisor)]/sum(a2mA))*((float)a2sA[(int)((Float.parseFloat(arr.get(i+2).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a2sA))*((float)a2lA[(int)((Float.parseFloat(arr.get(i+2).split(" ")[2])-minLength)/lengthDivisor)]/sum(a2lA));
									//PrAgivenMD1DaD1aD2=PrAgivenMD1DaD1aD2/power6A;
									//PrAgivenMD1DaD1aD2=PrAgivenMD1DaD1aD2/power3A;
									//PrAgivenMD1DaD1aD2=PrAgivenMD1DaD1aD2/power3A;

									double PrTgivenMD1DaD1=PrT*((float)m1mT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mT))*((float)m1sT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sT))*((float)m1lT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lT))*((float)mT[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mT))*((float)sT[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sT))*((float)lT[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lT))*((float)a1mT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mT))*((float)a1sT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sT))*((float)a1lT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lT));
									//PrTgivenMD1DaD1=PrTgivenMD1DaD1/power6T;
									//PrTgivenMD1DaD1=PrTgivenMD1DaD1/power3T;
									double PrTgivenMD1DaD1aD2=PrT*((float)m1mT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mT))*((float)m1sT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sT))*((float)m1lT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lT))*((float)mT[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mT))*((float)sT[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sT))*((float)lT[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lT))*((float)a1mT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mT))*((float)a1sT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sT))*((float)a1lT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lT))*((float)a2mT[(int)((Float.parseFloat(arr.get(i+2).split(" ")[0])-minMean)/meanDivisor)]/sum(a2mT))*((float)a2sT[(int)((Float.parseFloat(arr.get(i+2).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a2sT))*((float)a2lT[(int)((Float.parseFloat(arr.get(i+2).split(" ")[2])-minLength)/lengthDivisor)]/sum(a2lT));
									//PrTgivenMD1DaD1aD2=PrTgivenMD1DaD1aD2/power6T;
									//PrTgivenMD1DaD1aD2=PrTgivenMD1DaD1aD2/power3T;
									//PrTgivenMD1DaD1aD2=PrTgivenMD1DaD1aD2/power3T;
									
									double PrGgivenMD1DaD1=PrG*((float)m1mG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mG))*((float)m1sG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sG))*((float)m1lG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lG))*((float)mG[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mG))*((float)sG[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sG))*((float)lG[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lG))*((float)a1mG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mG))*((float)a1sG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sG))*((float)a1lG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lG));
									//PrGgivenMD1DaD1=PrGgivenMD1DaD1/power6G;
									//PrGgivenMD1DaD1=PrGgivenMD1DaD1/power3G;
									double PrGgivenMD1DaD1aD2=PrG*((float)m1mG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mG))*((float)m1sG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sG))*((float)m1lG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lG))*((float)mG[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mG))*((float)sG[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sG))*((float)lG[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lG))*((float)a1mG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mG))*((float)a1sG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sG))*((float)a1lG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lG))*((float)a2mG[(int)((Float.parseFloat(arr.get(i+2).split(" ")[0])-minMean)/meanDivisor)]/sum(a2mG))*((float)a2sG[(int)((Float.parseFloat(arr.get(i+2).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a2sG))*((float)a2lG[(int)((Float.parseFloat(arr.get(i+2).split(" ")[2])-minLength)/lengthDivisor)]/sum(a2lG));
									//PrGgivenMD1DaD1aD2=PrGgivenMD1DaD1aD2/power6G;
									//PrGgivenMD1DaD1aD2=PrGgivenMD1DaD1aD2/power3G;
									//PrGgivenMD1DaD1aD2=PrGgivenMD1DaD1aD2/power3G;
									
									double PrCgivenMD1DaD1=PrC*((float)m1mC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mC))*((float)m1sC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sC))*((float)m1lC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lC))*((float)mC[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mC))*((float)sC[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sC))*((float)lC[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lC))*((float)a1mC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mC))*((float)a1sC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sC))*((float)a1lC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lC));
									//PrCgivenMD1DaD1=PrCgivenMD1DaD1/power6C;
									//PrCgivenMD1DaD1=PrCgivenMD1DaD1/power3C;
									
									double PrCgivenMD1DaD1aD2=PrC*((float)m1mC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mC))*((float)m1sC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sC))*((float)m1lC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lC))*((float)mC[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mC))*((float)sC[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sC))*((float)lC[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lC))*((float)a1mC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mC))*((float)a1sC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sC))*((float)a1lC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lC))*((float)a2mC[(int)((Float.parseFloat(arr.get(i+2).split(" ")[0])-minMean)/meanDivisor)]/sum(a2mC))*((float)a2sC[(int)((Float.parseFloat(arr.get(i+2).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a2sC))*((float)a2lC[(int)((Float.parseFloat(arr.get(i+2).split(" ")[2])-minLength)/lengthDivisor)]/sum(a2lC));
									//PrCgivenMD1DaD1aD2=PrCgivenMD1DaD1aD2/power6C;
									//PrCgivenMD1DaD1aD2=PrCgivenMD1DaD1aD2/power3C;
									//PrCgivenMD1DaD1aD2=PrCgivenMD1DaD1aD2/power3C;
									
									resultMap.put("A", (PrAgivenD+PrAgivenMD1DaD1+PrAgivenMD1DaD1aD2)/3);
									resultMap.put("T", (PrTgivenD+PrTgivenMD1DaD1+PrTgivenMD1DaD1aD2)/3);
									resultMap.put("G", (PrGgivenD+PrGgivenMD1DaD1+PrGgivenMD1DaD1aD2)/3);
									resultMap.put("C", (PrCgivenD+PrCgivenMD1DaD1+PrCgivenMD1DaD1aD2)/3);
								}
								else if(i==arr.size()-1){

									double PrAgivenMD1D=PrA*((float)m1mA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mA))*((float)m1sA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sA))*((float)m1lA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lA))*((float)mA[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mA))*((float)sA[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sA))*((float)lA[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lA));
									//PrAgivenMD1D=PrAgivenMD1D/power6A;
									double PrAgivenMD2mD1D=PrA*((float)m2mA[(int)((Float.parseFloat(arr.get(i-2).split(" ")[0])-minMean)/meanDivisor)]/sum(m2mA))*((float)m2sA[(int)((Float.parseFloat(arr.get(i-2).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m2sA))*((float)m2lA[(int)((Float.parseFloat(arr.get(i-2).split(" ")[2])-minLength)/lengthDivisor)]/sum(m2lA))*((float)m1mA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mA))*((float)m1sA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sA))*((float)m1lA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lA))*((float)mA[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mA))*((float)sA[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sA))*((float)lA[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lA));
									//PrAgivenMD2mD1D=PrAgivenMD2mD1D/power6A;
									//PrAgivenMD2mD1D=PrAgivenMD2mD1D/power3A;

									double PrTgivenMD1D=PrT*((float)m1mT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mT))*((float)m1sT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sT))*((float)m1lT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lT))*((float)mT[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mT))*((float)sT[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(mT))*((float)lT[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lT));
									//PrTgivenMD1D=PrTgivenMD1D/power6T;
									double PrTgivenMD2mD1D=PrT*((float)m2mT[(int)((Float.parseFloat(arr.get(i-2).split(" ")[0])-minMean)/meanDivisor)]/sum(m2mT))*((float)m2sT[(int)((Float.parseFloat(arr.get(i-2).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m2sT))*((float)m2lT[(int)((Float.parseFloat(arr.get(i-2).split(" ")[2])-minLength)/lengthDivisor)]/sum(m2lT))*((float)m1mT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mT))*((float)m1sT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sT))*((float)m1lT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lT))*((float)mT[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mT))*((float)sT[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sT))*((float)lT[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lT));
									//PrTgivenMD2mD1D=PrTgivenMD2mD1D/power6T;
									//PrTgivenMD2mD1D=PrTgivenMD2mD1D/power3T;
									
									double PrGgivenMD1D=PrG*((float)m1mG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mG))*((float)m1sG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sG))*((float)m1lG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lG))*((float)mG[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mG))*((float)sG[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sG))*((float)lG[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lG));
									//PrGgivenMD1D=PrGgivenMD1D/power6G;
									double PrGgivenMD2mD1D=PrG*((float)m2mG[(int)((Float.parseFloat(arr.get(i-2).split(" ")[0])-minMean)/meanDivisor)]/sum(m2mG))*((float)m2sG[(int)((Float.parseFloat(arr.get(i-2).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m2sG))*((float)m2lG[(int)((Float.parseFloat(arr.get(i-2).split(" ")[2])-minLength)/lengthDivisor)]/sum(m2lG))*((float)m1mG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mG))*((float)m1sG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sG))*((float)m1lG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lG))*((float)mG[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mG))*((float)sG[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sG))*((float)lG[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lG));
									//PrGgivenMD2mD1D=PrGgivenMD2mD1D/power6G;
									//PrGgivenMD2mD1D=PrGgivenMD2mD1D/power3G;
									
									double PrCgivenMD1D=PrC*((float)m1mC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mC))*((float)m1sC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sC))*((float)m1lC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lC))*((float)mC[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mC))*((float)sC[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sC))*((float)lC[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lC));
									//PrCgivenMD1D=PrCgivenMD1D/power6C;
									double PrCgivenMD2mD1D=PrC*((float)m2mC[(int)((Float.parseFloat(arr.get(i-2).split(" ")[0])-minMean)/meanDivisor)]/sum(m2mC))*((float)m2sC[(int)((Float.parseFloat(arr.get(i-2).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m2sC))*((float)m2lC[(int)((Float.parseFloat(arr.get(i-2).split(" ")[2])-minLength)/lengthDivisor)]/sum(m2lC))*((float)m1mC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mC))*((float)m1sC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sC))*((float)m1lC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lC))*((float)mC[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mC))*((float)sC[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sC))*((float)lC[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lC));
									//PrCgivenMD2mD1D=PrCgivenMD2mD1D/power6C;
									//PrCgivenMD2mD1D=PrCgivenMD2mD1D/power3C;
									
									resultMap.put("A", (PrAgivenD+PrAgivenMD1D+PrAgivenMD2mD1D)/3);
									resultMap.put("T", (PrTgivenD+PrTgivenMD1D+PrTgivenMD2mD1D)/3);
									resultMap.put("G", (PrGgivenD+PrGgivenMD1D+PrGgivenMD2mD1D)/3);
									resultMap.put("C", (PrCgivenD+PrCgivenMD1D+PrCgivenMD2mD1D)/3);
								}
								else if(i==arr.size()-2){

									double	PrAgivenMD1DaD1=PrA*((float)m1mA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mA))*((float)m1sA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sA))*((float)m1lA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lA))*((float)mA[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mA))*((float)sA[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sA))*((float)lA[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lA))*((float)a1mA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mA))*((float)a1sA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sA))*((float)a1lA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lA));
									//PrAgivenMD1DaD1=PrAgivenMD1DaD1/power6A;
									//PrAgivenMD1DaD1=PrAgivenMD1DaD1/power3A;
									
									double	PrAgivenMD2mD1DaD1=PrA*((float)m2mA[(int)((Float.parseFloat(arr.get(i-2).split(" ")[0])-minMean)/meanDivisor)]/sum(m2mA))*((float)m2sA[(int)((Float.parseFloat(arr.get(i-2).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m2sA))*((float)m2lA[(int)((Float.parseFloat(arr.get(i-2).split(" ")[2])-minLength)/lengthDivisor)]/sum(m2lA))*((float)m1mA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mA))*((float)m1sA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sA))*((float)m1lA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lA))*((float)mA[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mA))*((float)sA[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sA))*((float)lA[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lA))*((float)a1mA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mA))*((float)a1sA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sA))*((float)a1lA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lA));
									//PrAgivenMD2mD1DaD1=PrAgivenMD2mD1DaD1/power6A;
									//PrAgivenMD2mD1DaD1=PrAgivenMD2mD1DaD1/power3A;
									//PrAgivenMD2mD1DaD1=PrAgivenMD2mD1DaD1/power3A;

									double	PrTgivenMD1DaD1=PrT*((float)m1mT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mT))*((float)m1sT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sT))*((float)m1lT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lT))*((float)mT[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mT))*((float)sT[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sT))*((float)lT[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lT))*((float)a1mT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mT))*((float)a1sT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sT))*((float)a1lT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lT));
									//PrTgivenMD1DaD1=PrTgivenMD1DaD1/power6T;
									//PrTgivenMD1DaD1=PrTgivenMD1DaD1/power3T;
									double PrTgivenMD2mD1DaD1=PrT*((float)m2mT[(int)((Float.parseFloat(arr.get(i-2).split(" ")[0])-minMean)/meanDivisor)]/sum(m2mT))*((float)m2sT[(int)((Float.parseFloat(arr.get(i-2).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m2sT))*((float)m2lT[(int)((Float.parseFloat(arr.get(i-2).split(" ")[2])-minLength)/lengthDivisor)]/sum(m2lT))*((float)m1mT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mT))*((float)m1sT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sT))*((float)m1lT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lT))*((float)mT[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mT))*((float)sT[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sT))*((float)lT[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lT))*((float)a1mT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mT))*((float)a1sT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sT))*((float)a1lT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lT));
									//PrTgivenMD2mD1DaD1=PrTgivenMD2mD1DaD1/power6T;
									//PrTgivenMD2mD1DaD1=PrTgivenMD2mD1DaD1/power3T;
									//PrTgivenMD2mD1DaD1=PrTgivenMD2mD1DaD1/power3T;
									
									double PrGgivenMD1DaD1=PrG*((float)m1mG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mG))*((float)m1sG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sG))*((float)m1lG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lG))*((float)mG[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mG))*((float)sG[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sG))*((float)lG[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lG))*((float)a1mG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mG))*((float)a1sG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sG))*((float)a1lG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lG));
									//PrGgivenMD1DaD1=PrGgivenMD1DaD1/power6G;
									//PrGgivenMD1DaD1=PrGgivenMD1DaD1/power3G;
									double PrGgivenMD2mD1DaD1=PrG*((float)m2mG[(int)((Float.parseFloat(arr.get(i-2).split(" ")[0])-minMean)/meanDivisor)]/sum(m2mG))*((float)m2sG[(int)((Float.parseFloat(arr.get(i-2).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m2sG))*((float)m2lG[(int)((Float.parseFloat(arr.get(i-2).split(" ")[2])-minLength)/lengthDivisor)]/sum(m2lG))*((float)m1mG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mG))*((float)m1sG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sG))*((float)m1lG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lG))*((float)mG[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mG))*((float)sG[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sG))*((float)lG[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lG))*((float)a1mG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mG))*((float)a1sG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sG))*((float)a1lG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lG));
									//PrGgivenMD2mD1DaD1=PrGgivenMD2mD1DaD1/power6G;
									//PrGgivenMD2mD1DaD1=PrGgivenMD2mD1DaD1/power3G;
									//PrGgivenMD2mD1DaD1=PrGgivenMD2mD1DaD1/power3G;

									double PrCgivenMD1DaD1=PrC*((float)m1mC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mC))*((float)m1sC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sC))*((float)m1lC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lC))*((float)mC[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mC))*((float)sC[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sC))*((float)lC[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lC))*((float)a1mC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mC))*((float)a1sC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sC))*((float)a1lC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lC));
									//PrCgivenMD1DaD1=PrCgivenMD1DaD1/power6C;
									//PrCgivenMD1DaD1=PrCgivenMD1DaD1/power3C;
									double PrCgivenMD2mD1DaD1=PrC*((float)m2mC[(int)((Float.parseFloat(arr.get(i-2).split(" ")[0])-minMean)/meanDivisor)]/sum(m2mC))*((float)m2sC[(int)((Float.parseFloat(arr.get(i-2).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m2sC))*((float)m2lC[(int)((Float.parseFloat(arr.get(i-2).split(" ")[2])-minLength)/lengthDivisor)]/sum(m2lC))*((float)m1mC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mC))*((float)m1sC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sC))*((float)m1lC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lC))*((float)mC[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mC))*((float)sC[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sC))*((float)lC[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lC))*((float)a1mC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mC))*((float)a1sC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sC))*((float)a1lC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lC));
									//PrCgivenMD2mD1DaD1=PrCgivenMD2mD1DaD1/power6C;
									//PrCgivenMD2mD1DaD1=PrCgivenMD2mD1DaD1/power3C;
									//PrCgivenMD2mD1DaD1=PrCgivenMD2mD1DaD1/power3C;
									
									resultMap.put("A", (PrAgivenD+PrAgivenMD1DaD1+PrAgivenMD2mD1DaD1)/3);
									resultMap.put("T", (PrTgivenD+PrTgivenMD1DaD1+PrTgivenMD2mD1DaD1)/3);
									resultMap.put("G", (PrGgivenD+PrGgivenMD1DaD1+PrGgivenMD2mD1DaD1)/3);
									resultMap.put("C", (PrCgivenD+PrCgivenMD1DaD1+PrCgivenMD2mD1DaD1)/3);
								}
								else{
									double PrAgivenMD1DaD1=PrA*((float)m1mA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mA))*((float)m1sA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sA))*((float)m1lA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lA))*((float)mA[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mA))*((float)sA[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sA))*((float)lA[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lA))*((float)a1mA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mA))*((float)a1sA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sA))*((float)a1lA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lA));
									//PrAgivenMD1DaD1=PrAgivenMD1DaD1/power6A;
									//PrAgivenMD1DaD1=PrAgivenMD1DaD1/power3A;
									
									double PrAgivenMD2mD1DaD1aD2=PrA*((float)m2mA[(int)((Float.parseFloat(arr.get(i-2).split(" ")[0])-minMean)/meanDivisor)]/sum(m2mA))*((float)m2sA[(int)((Float.parseFloat(arr.get(i-2).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m2sA))*((float)m2lA[(int)((Float.parseFloat(arr.get(i-2).split(" ")[2])-minLength)/lengthDivisor)]/sum(m2lA))*((float)m1mA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mA))*((float)m1sA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sA))*((float)m1lA[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lA))*((float)mA[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mA))*((float)sA[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sA))*((float)lA[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lA))*((float)a1mA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mA))*((float)a1sA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sA))*((float)a1lA[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lA))*((float)a2mA[(int)((Float.parseFloat(arr.get(i+2).split(" ")[0])-minMean)/meanDivisor)]/sum(a2mA))*((float)a2sA[(int)((Float.parseFloat(arr.get(i+2).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a2sA))*((float)a2lA[(int)((Float.parseFloat(arr.get(i+2).split(" ")[2])-minLength)/lengthDivisor)]/sum(a2lA));
									//PrAgivenMD2mD1DaD1aD2=PrAgivenMD2mD1DaD1aD2/power6A;
									//PrAgivenMD2mD1DaD1aD2=PrAgivenMD2mD1DaD1aD2/power6A;
									//PrAgivenMD2mD1DaD1aD2=PrAgivenMD2mD1DaD1aD2/power3A;
									

									double PrTgivenMD1DaD1=PrT*((float)m1mT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mT))*((float)m1sT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sT))*((float)m1lT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lT))*((float)mT[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mT))*((float)sT[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sT))*((float)lT[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lT))*((float)a1mT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mT))*((float)a1sT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sT))*((float)a1lT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lT));
									//PrTgivenMD1DaD1=PrTgivenMD1DaD1/power6T;
									//PrTgivenMD1DaD1=PrTgivenMD1DaD1/power3T;
									double PrTgivenMD2mD1DaD1aD2=PrT*((float)m2mT[(int)((Float.parseFloat(arr.get(i-2).split(" ")[0])-minMean)/meanDivisor)]/sum(m2mT))*((float)m2sT[(int)((Float.parseFloat(arr.get(i-2).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m2sT))*((float)m2lT[(int)((Float.parseFloat(arr.get(i-2).split(" ")[2])-minLength)/lengthDivisor)]/sum(m2lT))*((float)m1mT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mT))*((float)m1sT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sT))*((float)m1lT[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lT))*((float)mT[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mT))*((float)sT[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sT))*((float)lT[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lT))*((float)a1mT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mT))*((float)a1sT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sT))*((float)a1lT[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lT))*((float)a2mT[(int)((Float.parseFloat(arr.get(i+2).split(" ")[0])-minMean)/meanDivisor)]/sum(a2mT))*((float)a2sT[(int)((Float.parseFloat(arr.get(i+2).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a2sT))*((float)a2lT[(int)((Float.parseFloat(arr.get(i+2).split(" ")[2])-minLength)/lengthDivisor)]/sum(a2lT));
									//PrTgivenMD2mD1DaD1aD2=PrTgivenMD2mD1DaD1aD2/power6T;
									//PrTgivenMD2mD1DaD1aD2=PrTgivenMD2mD1DaD1aD2/power6T;
									//PrTgivenMD2mD1DaD1aD2=PrTgivenMD2mD1DaD1aD2/power3T;
									
									double PrGgivenMD1DaD1=PrG*((float)m1mG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mG))*((float)m1sG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sG))*((float)m1lG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lG))*((float)mG[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mG))*((float)sG[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sG))*((float)lG[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lG))*((float)a1mG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mG))*((float)a1sG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sG))*((float)a1lG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lG));
									//PrGgivenMD1DaD1=PrGgivenMD1DaD1/power6G;
									//PrGgivenMD1DaD1=PrGgivenMD1DaD1/power3G;
									double PrGgivenMD2mD1DaD1aD2=PrG*((float)m2mG[(int)((Float.parseFloat(arr.get(i-2).split(" ")[0])-minMean)/meanDivisor)]/sum(m2mG))*((float)m2sG[(int)((Float.parseFloat(arr.get(i-2).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m2sG))*((float)m2lG[(int)((Float.parseFloat(arr.get(i-2).split(" ")[2])-minLength)/lengthDivisor)]/sum(m2lG))*((float)m1mG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mG))*((float)m1sG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sG))*((float)m1lG[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lG))*((float)mG[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mG))*((float)sG[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sG))*((float)lG[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lG))*((float)a1mG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mG))*((float)a1sG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sG))*((float)a1lG[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lG))*((float)a2mG[(int)((Float.parseFloat(arr.get(i+2).split(" ")[0])-minMean)/meanDivisor)]/sum(a2mG))*((float)a2sG[(int)((Float.parseFloat(arr.get(i+2).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a2sG))*((float)a2lG[(int)((Float.parseFloat(arr.get(i+2).split(" ")[2])-minLength)/lengthDivisor)]/sum(a2lG));
									//PrGgivenMD2mD1DaD1aD2=PrGgivenMD2mD1DaD1aD2/power6G;
									//PrGgivenMD2mD1DaD1aD2=PrGgivenMD2mD1DaD1aD2/power6G;
									//PrGgivenMD2mD1DaD1aD2=PrGgivenMD2mD1DaD1aD2/power3G;

									double PrCgivenMD1DaD1=PrC*((float)m1mC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mC))*((float)m1sC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sC))*((float)m1lC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lC))*((float)mC[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mC))*((float)sC[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sC))*((float)lC[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lC))*((float)a1mC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mC))*((float)a1sC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sC))*((float)a1lC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lC));
									//PrCgivenMD1DaD1=PrCgivenMD1DaD1/power6C;
									//PrCgivenMD1DaD1=PrCgivenMD1DaD1/power3C;
									double PrCgivenMD2mD1DaD1aD2=PrC*((float)m2mC[(int)((Float.parseFloat(arr.get(i-2).split(" ")[0])-minMean)/meanDivisor)]/sum(m2mC))*((float)m2sC[(int)((Float.parseFloat(arr.get(i-2).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m2sC))*((float)m2lC[(int)((Float.parseFloat(arr.get(i-2).split(" ")[2])-minLength)/lengthDivisor)]/sum(m2lC))*((float)m1mC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[0])-minMean)/meanDivisor)]/sum(m1mC))*((float)m1sC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(m1sC))*((float)m1lC[(int)((Float.parseFloat(arr.get(i-1).split(" ")[2])-minLength)/lengthDivisor)]/sum(m1lC))*((float)mC[(int)((Float.parseFloat(arr.get(i).split(" ")[0])-minMean)/meanDivisor)]/sum(mC))*((float)sC[(int)((Float.parseFloat(arr.get(i).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(sC))*((float)lC[(int)((Float.parseFloat(arr.get(i).split(" ")[2])-minLength)/lengthDivisor)]/sum(lC))*((float)a1mC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[0])-minMean)/meanDivisor)]/sum(a1mC))*((float)a1sC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a1sC))*((float)a1lC[(int)((Float.parseFloat(arr.get(i+1).split(" ")[2])-minLength)/lengthDivisor)]/sum(a1lC))*((float)a2mC[(int)((Float.parseFloat(arr.get(i+2).split(" ")[0])-minMean)/meanDivisor)]/sum(a2mC))*((float)a2sC[(int)((Float.parseFloat(arr.get(i+2).split(" ")[1])-minStdDev)/stdDevDivisor)]/sum(a2sC))*((float)a2lC[(int)((Float.parseFloat(arr.get(i+2).split(" ")[2])-minLength)/lengthDivisor)]/sum(a2lC));
									//PrCgivenMD2mD1DaD1aD2=PrCgivenMD2mD1DaD1aD2/power6C;
									//PrCgivenMD2mD1DaD1aD2=PrCgivenMD2mD1DaD1aD2/power6C;
									//PrCgivenMD2mD1DaD1aD2=PrCgivenMD2mD1DaD1aD2/power3C;
									
									resultMap.put("A", (PrAgivenD+PrAgivenMD1DaD1+PrAgivenMD2mD1DaD1aD2)/3);
									resultMap.put("T", (PrTgivenD+PrTgivenMD1DaD1+PrTgivenMD2mD1DaD1aD2)/3);
									resultMap.put("G", (PrGgivenD+PrGgivenMD1DaD1+PrGgivenMD2mD1DaD1aD2)/3);
									resultMap.put("C", (PrCgivenD+PrCgivenMD1DaD1+PrCgivenMD2mD1DaD1aD2)/3);
								}
								//write result to the result file here
								Double maxProb=0.0;
								for(Entry<String,Double> e: resultMap.entrySet()){
									if(e.getValue()>maxProb){
										maxProb=e.getValue();
										result=e.getKey();
									}
								}
								pw.append(result);
								pw.flush();
							}

							// code ends here
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
								pw.close();
								fos.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}	

					}
				}
	}


}
