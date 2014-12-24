//calculates and stores precision, recall and f1 values for a matrix [actual classes][predicted classes]
//uses psuedocount to handle 0s since expected  number of values in a cell = (4m/5)/M = 347 for svm

#include <iostream>

#define M 3 //max number of classes in svm = 2304

using namespace std;

int mat[M][M];
float precision[M];
float recall[M];
float f[M];
void init_matrix()
{
	for(int i=0;i<M;i++)
		for(int j=0;j<M;j++)
			mat[i][j]=1; //psuedocount
}

void calculate_precision()
{
	for(int i=0;i<M;i++)
	{
		int total_pos = 0;
		for(int j=0;j<M;j++)
		{
			total_pos+= mat[j][i];
		}
		precision[i] = (float)mat[i][i]/(float)total_pos;
	}
}

void calculate_recall()
{
	for(int i=0;i<M;i++)
	{
		int total_prediction = 0;
		for(int j=0;j<M;j++)
		{
			total_prediction+= mat[i][j];
		}
		recall[i] = (float)mat[i][i]/(float)total_prediction;
	}
}


void calculate_f1()
{
	for(int i=0;i<M;i++)
	{
		float sum = precision[i]+ recall[i];
		cout<<"\n Sum = "<<sum;
		f[i] = 2*precision[i]*recall[i]/sum;
	}
}

void display_precision()
{
	cout<<"\nPrecision\t";
	for(int i=0;i<M;i++)
	{
		cout<<precision[i]<<"\t";
	}
	cout<<"\n";
}


void display_recall()
{
	cout<<"\nRecall   \t";
	for(int i=0;i<M;i++)
	{
		cout<<recall[i]<<"\t";
	}
	cout<<"\n";
}

void fill_matrix()
{
	for(int i=0;i<M;i++)
	{
		for(int j=0;j<M;j++)
		{
			cin>>mat[i][j];
			mat[i][j]++;
		}
	}
}


void display_matrix()
{
	for(int i=0;i<M;i++)
	{
		cout<<"\n";
		for(int j=0;j<M;j++)
		{
			cout<<mat[i][j]<<"\t";
		}
	}
	cout<<"\n";
}

void display_f1()
{
	
	cout<<"\nF1     \t\t";
	for(int i=0;i<M;i++)
	{
		cout<<f[i]<<"\t";
	}
	cout<<"\n";
}

int main()
{
	init_matrix();
	fill_matrix();
	display_matrix();
	calculate_precision();
	calculate_recall();
	calculate_f1();
	display_precision();
	display_recall();
	display_f1();
	
}