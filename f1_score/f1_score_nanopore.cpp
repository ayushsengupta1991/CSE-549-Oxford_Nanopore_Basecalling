//calculates and stores precision, recall and f1 values for a matrix [actual classes][predicted classes]
//uses psuedocount to handle 0s since expected  number of values in a cell = (4m/5)/M = 347 for svm
#include <string.h>
#include <stdlib.h>
#include <iostream>
#include <fstream>
#include <dirent.h>
#define M 5 //max number of classes in svm = 2304

using namespace std;

int mat[M][M];
float precision[M];
float recall[M];
float f[M];
long check=0;

long find_sum()
{
	long sum=0;
	for(int i=0;i<M;i++)
		for(int j=0;j<M;j++)
			sum+=mat[i][j]; //psuedocount
	return sum;
}
int getEnum(char c)
{
	if(c=='A')return 0;
	if(c=='C')return 1;
	if(c=='G')return 2;
	if(c=='T')return 3;
	return 4;
}

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
			total_pos+= mat[i][j];
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
			total_prediction+= mat[j][i];
		}
		recall[i] = (float)mat[i][i]/(float)total_prediction;
	}
}


void calculate_f1()
{
	for(int i=0;i<M;i++)
	{
		float sum = precision[i]+ recall[i];
		//cout<<"\n Sum = "<<sum;
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

void read_file_and_fill_matrix(char *fname,char *dirname)
{
	bool isalfasta = false;
	const int file_name_len = strlen(fname);
	if(fname[file_name_len-7]=='e'&&fname[file_name_len-6]=='d' &&fname[file_name_len-5]=='f' && fname[file_name_len-4]=='a'&& fname[file_name_len-3]=='s'&& fname[file_name_len-2]=='t'&& fname[file_name_len-1]=='a')
		isalfasta=true;
	if(!isalfasta) //ignoring the swapped files
	{
		cout<<"\n Hey, this is not aligned fasta file!";
		return;
	}
	ifstream myfile;
	string line;
	
	char *fname1 = new char[file_name_len+100];
	strcpy(fname1,dirname);
	strcat(fname1,fname);
	myfile.open(fname1);
	//cout<<fname1;
	int count=0;
	if(!myfile.is_open())
	{
		cout<<"\nCANNOT OPEN FILE!!";
		return;
	}
	char ch;char val[10];int index=0;
	while(1)
	{
		myfile.get(ch);
		if(ch=='\n')
			break;
		val[index]=ch;
		//cout<<"\nIndex:"<<index;
		index++;
	}
	
	val[index] = '\0';
	int begin = atoi(val);
	//cout<<"\nbegin: "<<begin;
	//fseek(myfile,index+2,SEEK_SET);
	//myfile.seekg(index+2,myfile.beg);
	char val1[10];
	index=0;
	while(1)
	{
		myfile.get(ch);
		if(ch=='\n')
			break;
		val1[index]=ch;
		//cout<<"\nIndex:"<<index;
		index++;
	}
	
	val1[index] = '\0';
	int end = atoi(val1);
	int len=end-begin;
	//cout<<"\nbegin: "<<begin<<"\tend: "<<end<<"\t diff: "<<len;
	
	char *predicted = new char[len+1];
	index=0;
	while(1)
	{
		myfile.get(ch);
		if(ch=='\n')
			break;
		predicted[index]=ch;
		//cout<<"\nIndex:"<<index;
		index++;
	}
	predicted[index] = '\0';
	
	char *origional = new char[len+1];
	index=0;
	while(1)
	{
		myfile.get(ch);
		if(ch=='\n')
			break;
		origional[index]=ch;
		//cout<<"\nIndex:"<<index;
		index++;
	}
	origional[index] = '\0';
	
	//cout<<"\nPredicted string: "<<predicted<<"\nOrigional string: "<<origional;
	myfile.close();
	
	check+=len;
	for(int i=0;i<len;i++)
	{
		mat[getEnum(origional[i])][getEnum(predicted[i])]++;
	}
}
void fill_matrix(char *dpath)
{
	
	DIR *pDIR;
	struct dirent *entry;
	if( pDIR=opendir(dpath) ){
		while(entry = readdir(pDIR)){
			if( entry->d_name[0]!= '.' && strcmp(entry->d_name, "..") != 0)
				read_file_and_fill_matrix(entry->d_name,dpath);
		}
		closedir(pDIR);
	}
	else
	{
		cout<<"\nCant open directory!!";
	}
	
	/*
	for(int i=0;i<M;i++)
	{
		for(int j=0;j<M;j++)
		{
			cin>>mat[i][j];
			mat[i][j]++;
		}
	}*/
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

void read_integer_file_line_by_line()
{
	ifstream myfile;
	string line;
	
	myfile.open("/home/rishoo/Desktop/basecalling/alignedfasta_files/LomanLabz_PC_Ecoli_K12_R7.3_2549_1_ch51_file55_strand.alignedfasta");
	int count=0;
	if(myfile.is_open())
	{
		char ch;char val[10];int index=0;
		while(1)
		{
			myfile.get(ch);
			if(ch=='\n')
				break;
			val[index]=ch;
			//cout<<"\nIndex:"<<index;
			index++;
		}
		
		val[index] = '\0';
		int begin = atoi(val);
		cout<<"\nbegin: "<<begin;
		//fseek(myfile,index+2,SEEK_SET);
		//myfile.seekg(index+2,myfile.beg);
		char val1[10];
		index=0;
		while(1)
		{
			myfile.get(ch);
			if(ch=='\n')
				break;
			val1[index]=ch;
			//cout<<"\nIndex:"<<index;
			index++;
		}
		
		val1[index] = '\0';
		int end = atoi(val1);
		int len=end-begin;
 		cout<<"\nbegin: "<<begin<<"\tend: "<<end<<"\t diff: "<<len;
		
		char *predicted = new char[len+1];
		index=0;
		while(1)
		{
			myfile.get(ch);
			if(ch=='\n')
				break;
			predicted[index]=ch;
			//cout<<"\nIndex:"<<index;
			index++;
		}
		predicted[index] = '\0';
		
		char *origional = new char[len+1];
		index=0;
		while(1)
		{
			myfile.get(ch);
			if(ch=='\n')
				break;
			origional[index]=ch;
			//cout<<"\nIndex:"<<index;
			index++;
		}
		origional[index] = '\0';
		
		cout<<"\nPredicted string: "<<predicted<<"\nOrigional string: "<<origional;
		myfile.close();
	}
	else
	{
		cout<<"\nCannot read file!";
	}
	
}

int main(int argc, char *argv[])
{
	//read_integer_file_line_by_line();
	
	init_matrix();
	fill_matrix(argv[1]);
	display_matrix();
	calculate_precision();
	calculate_recall();
	calculate_f1();
	display_precision();
	display_recall();
	display_f1();
	if(check==find_sum()-M*M);
	cout<<"\n\nEverthing fine!!\n\n";
	
}