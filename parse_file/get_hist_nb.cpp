
#include <string.h>
#include <stdlib.h>
#include <iostream>
#include <fstream>
#include <algorithm>
using namespace std;
#define N 5

int arr[5];
char get_enum_char(int val)
{
	if(val==0) return 'A';
	if(val==1) return 'C';
	if(val==2) return 'G';
	if(val==3) return 'T';
}
long get_val(char c)
{
	if(c=='A')return 0;
	if(c=='C')return 1;
	if(c=='G')return 2;
	if(c=='T')return 3;
	return 4;
}
int get_gap_position(int value)
{
	if((value - 1024)<0) return -1;
	else return ((value - 1024)/256);
}



char conversion_to_base_4(int value,char gap)
{
	int gap_pos = get_gap_position(value);
	if(gap_pos>=0) value = value-1024;
	char base4[N+1],i=0;
	do
	{
		if((N-1-i)==gap_pos)
		{i++;}
		else
		{
		base4[N-1-i] = get_enum_char(value%4);
		value = value/4;i++;
		}
		
	}while(value!=0);
	for(int j = N-i-1;j>=0;j--)
	{
		
		if(j==gap_pos)
			continue;
		else
		base4[j] = get_enum_char(0);
	}
	base4[gap_pos]  = gap;
	return base4[2];
	//cout<<"\n";
}


int main()
{
	
	fstream myfile;
	string line;
	//init_matrix();
	myfile.open("/media/rishoo/DC7682B576828FC6/actual.txt");
	if(!myfile.is_open())
		cout<<"\nFile not open!";
	
	int count = 0;
	
	for(int i=0;i<N;i++)
	{
		arr[i]=0;
	}
	
	while(getline(myfile,line))
	{
			char arr[5];
		int i;
		for(i=0;i<line.length();i++)
		{
			arr[i] = line[i];
		}
		arr[i]='\0';
		int class_no=atoi(arr);
		char ch = conversion_to_base_4(class_no,'_');
		int vall = get_val(ch);
		//cout<<ch<<"\t"<<vall<<"\n";
		arr[vall]++;
		
		count++;
	}
	//cout<<"\n\n"<<count;
	for(int i=0;i<N;i++)
	{
		cout<<arr[i]<<" ";
	}
	
	myfile.close();
	
}

