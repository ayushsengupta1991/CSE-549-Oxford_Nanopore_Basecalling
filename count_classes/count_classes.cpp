#include <string.h>
#include <stdlib.h>
#include <iostream>
#include <fstream>
#include <algorithm>
#define M 2304 //max number of classes in svm = 2304
#define GAP '-'
#define N 801
using namespace std;
int class_mat[M];
int proj_mat[N];
void init_matrix()
{
	for(int i=0;i<M;i++)
		class_mat[i]=0; //psuedocount
	for(int i=0;i<N;i++)
		proj_mat[i]=0; //psuedocount
			
}

float average_matrix(int count)
{
	long sum=0;
	for(int i=0;i<M;i++)
		sum+=class_mat[i]; //psuedocount
	return sum/M;
	
}


void get_projection()
{
	for(int i=0;i<M;i++)
		proj_mat[class_mat[i]]++;
}


void display_matrix()
{
	for(int i=0;i<M;i++)
		cout<<class_mat[i]<<"\n"; //psuedocount
}


void display_proj_matrix()
{
	for(int i=0;i<N;i++)
		cout<<proj_mat[i]<<"\n"; //psuedocount
}

int max_matrix()
{
	int maxi=0;
	for(int i=0;i<M;i++)
		maxi = max(maxi,class_mat[i]);
	return maxi;
}
int main()
{
	
	fstream myfile;
	string line;
	init_matrix();
	myfile.open("classes_large.txt");
	int count = 0;
	while(getline(myfile,line))
	{
		char *arr = new char[5];
		int i;
		for(i=0;i<5;i++)
		{
			if(line[i]=='\n')
				break;
			arr[i] = line[i];
		}
		arr[i]='\0';
		int class_no=atoi(arr);
		class_mat[class_no]++;
		count++;
	}
	
	myfile.close();
	int sum = 0;
	for(int i=1024;i<M;i++)
	{
		sum +=class_mat[i];
	}
	cout<<"\nSum: "<<sum-(2304-1024);
	
	sum = 0;
	for(int i=0;i<1024;i++)
	{
		sum +=class_mat[i];
	}
	cout<<"\nSum no gaps: "<<sum-(1024);
	//get_projection();
	//display_proj_matrix();
	//cout<<max_matrix();
	
	//display_matrix();
	//display_proj_matrix();
	
}