package com.cs.classifier;

import java.util.HashMap;


/*
 * @author Chaitanya Saxena
 */
public class ClassA {

int[] m2BucketMean;
int[] m1BucketMean;
int[] bucketMean;
int[] a1BucketMean;
int[] a2BucketMean;
int[] m2BucketSD;
int[] m1BucketSD;
int[] bucketSD;
int[] a1BucketSD;
int[] a2BucketSD;
int[] m2BucketLN;
int[] m1BucketLN;
int[] bucketLN;
int[] a1BucketLN;
int[] a2BucketLN;


public ClassA(int numBucket){
	int numBuckets=numBucket+1;
	 m2BucketMean=new int[numBuckets];
	 m1BucketMean=new int[numBuckets];
	 bucketMean=new int[numBuckets];
	 a1BucketMean=new int[numBuckets];
	 a2BucketMean=new int[numBuckets];
	 m2BucketSD=new int[numBuckets];
	 m1BucketSD=new int[numBuckets];
	 bucketSD=new int[numBuckets];
	 a1BucketSD=new int[numBuckets];
	 a2BucketSD=new int[numBuckets];
	 m2BucketLN=new int[numBuckets];
	 m1BucketLN=new int[numBuckets];
	 bucketLN=new int[numBuckets];
	 a1BucketLN=new int[numBuckets];
	 a2BucketLN=new int[numBuckets];
	
	}


public int[] getM2BucketMean() {
	return m2BucketMean;
}


public void setM2BucketMean(int[] m2BucketMean) {
	this.m2BucketMean = m2BucketMean;
}


public int[] getM1BucketMean() {
	return m1BucketMean;
}


public void setM1BucketMean(int[] m1BucketMean) {
	this.m1BucketMean = m1BucketMean;
}


public int[] getBucketMean() {
	return bucketMean;
}


public void setBucketMean(int[] bucketMean) {
	this.bucketMean = bucketMean;
}


public int[] getA1BucketMean() {
	return a1BucketMean;
}


public void setA1BucketMean(int[] a1BucketMean) {
	this.a1BucketMean = a1BucketMean;
}


public int[] getA2BucketMean() {
	return a2BucketMean;
}


public void setA2BucketMean(int[] a2BucketMean) {
	this.a2BucketMean = a2BucketMean;
}


public int[] getM2BucketSD() {
	return m2BucketSD;
}


public void setM2BucketSD(int[] m2BucketSD) {
	this.m2BucketSD = m2BucketSD;
}


public int[] getM1BucketSD() {
	return m1BucketSD;
}


public void setM1BucketSD(int[] m1BucketSD) {
	this.m1BucketSD = m1BucketSD;
}


public int[] getBucketSD() {
	return bucketSD;
}


public void setBucketSD(int[] bucketSD) {
	this.bucketSD = bucketSD;
}


public int[] getA1BucketSD() {
	return a1BucketSD;
}


public void setA1BucketSD(int[] a1BucketSD) {
	this.a1BucketSD = a1BucketSD;
}


public int[] getA2BucketSD() {
	return a2BucketSD;
}


public void setA2BucketSD(int[] a2BucketSD) {
	this.a2BucketSD = a2BucketSD;
}


public int[] getM2BucketLN() {
	return m2BucketLN;
}


public void setM2BucketLN(int[] m2BucketLN) {
	this.m2BucketLN = m2BucketLN;
}


public int[] getM1BucketLN() {
	return m1BucketLN;
}


public void setM1BucketLN(int[] m1BucketLN) {
	this.m1BucketLN = m1BucketLN;
}


public int[] getBucketLN() {
	return bucketLN;
}


public void setBucketLN(int[] bucketLN) {
	this.bucketLN = bucketLN;
}


public int[] getA1BucketLN() {
	return a1BucketLN;
}


public void setA1BucketLN(int[] a1BucketLN) {
	this.a1BucketLN = a1BucketLN;
}


public int[] getA2BucketLN() {
	return a2BucketLN;
}


public void setA2BucketLN(int[] a2BucketLN) {
	this.a2BucketLN = a2BucketLN;
}



}