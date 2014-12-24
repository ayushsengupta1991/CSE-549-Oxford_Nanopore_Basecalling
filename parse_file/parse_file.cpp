#include <string.h>
#include <stdlib.h>
#include <iostream>
#include <fstream>
#include <algorithm>
#define M 2304 //max number of classes in svm = 2304
#define GAP '-'
#define N 219
//#define WC 156086784
#define WC 156086784
using namespace std;
int main()
{
	
	fstream myfile;
	string line;
	//init_matrix();
	myfile.open("/media/rishoo/DC7682B576828FC6/prediction2");
	if(!myfile.is_open())
		cout<<"\nFile not open!";
	/*char ch;
	long count;
	while(1)
	{
		myfile.get(ch);
		if(ch==EOF)
			break;
		//myfile.get(ch);
		if(ch=='\n' || ch==' ')
			count++;
		cout<<"\nhere";
	}
	cout<<"\nhere2";
	cout<<count;
	*/
	/*
	float val;
	long count=0;
	long tc=0;
	for(long i=0;i<(WC-1);i++)
	{
		cin>>val;
		tc++;
	//	cout<<val;
		
		long int_val= (long)(val);
		float check = val - (long)val;
		if
		if(int_val==val)
		{
			cout<<val<<"\n";
			count++;
		}
	}
	cout<<"\n\n"<<count;*/
	/*cout<<"\nCounts: ";
	cout<<count<<" ";
	cout<<tc;*/
	int count = 0;
	
	while(getline(myfile,line))
	{
		char *arr = new char[5];
		int i;
		for(i=0;i<5;i++)
		{
			if(line[i]==' ')
				break;
			arr[i] = line[i];
		}
		arr[i]='\0';
		int class_no=atoi(arr);
		//class_mat[class_no]++;
		cout<<class_no<<"\n";
		count++;
	}
	cout<<"\n\n"<<count;
	myfile.close();
	//cout<<average_matrix(count)<<" "<<count;
	//display_matrix();
	
	//myfile.close();
	//cout<<max_matrix();
	//get_projection();
	//display_proj_matrix();
	
}