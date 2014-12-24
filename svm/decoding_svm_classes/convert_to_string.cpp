#include<iostream>
using namespace std;
#define N 5


char get_enum_char(int val)
{
	if(val==0) return 'A';
	if(val==1) return 'C';
	if(val==2) return 'G';
	if(val==3) return 'T';
}

int get_gap_position(int value)
{
	if((value - 1024)<0) return -1;
	else return ((value - 1024)/256);
}



void conversion_to_base_4(int value,char gap)
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
	for(int j = 0;j<N;j++)
	{
		cout<<base4[j];
	}
	cout<<"\n";
}

int main()
{
	//conversion_to_base_4(,'_');
	conversion_to_base_4(30,'_');
	conversion_to_base_4(1023,'_');
	conversion_to_base_4(1024,'_');
	conversion_to_base_4(1014,'_');
	conversion_to_base_4(2303,'_');
	conversion_to_base_4(1279,'_');
	conversion_to_base_4(2500,'_');
}