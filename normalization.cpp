#include<iostream>
#include<fstream>
#include<cfloat>
#include<cmath>
#include<iomanip>
#include<string>
#include<cstring>
using namespace std;
bool isDigit(char x)
{
	if(x-'0'>=0 && x-'0'<10)
		return true;
	return false;
}

int main(int argc,char *argv[])
{
	if(argc!=3)
	{
		cout<<"Incorrect usage.. Please provide two argument, the svm file and the nsvm file\n";
		return 0;
	}
	ifstream svm(argv[1]);
	if(!svm.is_open())
	{
		cout<<"Could not open file\n";
		return 0;
	}
	char *fPath=new char [strlen(argv[1])+10];
	int i=0;
	for(i=0;argv[1][i]!='.';i++)
		fPath[i]=argv[1][i];
	fPath[i]=argv[1][i];
	fPath[++i]='n';fPath[++i]='s';fPath[++i]='v';fPath[++i]='m';fPath[++i]='\0';
	double max[10];
	double min[10];
	for(i=0;i<10;i++)
	{
		max[i]=0;
		min[i]=DBL_MAX;
	}
	string tok;
	while(svm>>tok)
	{
		//cout<<"done1\n";
		if(isDigit(tok[0]) && tok[1]==':')
		{
			long intPart=0;
			for(i=2;tok[i]!='.';i++)
				intPart=intPart*10+(tok[i]-'0');
			i++;
			double floatPart=0.0;
			int ten=1;
			for(;i<tok.size();i++)
				floatPart=floatPart+(tok[i]-'0')/pow(10,ten++);
			double val=intPart+floatPart;
			if(val>max[(tok[0]-'0')-1])
				max[(tok[0]-'0')-1]=val;
			if(val<min[(tok[0]-'0')-1])
				min[(tok[0]-'0')-1]=val;
		}
	}
	svm.close();
	svm.open(argv[1]);
	
	ofstream out(argv[2]);
	//string tok;
	out<<setprecision(12);
	int tot=0;
	while(svm>>tok)
	{
		if(isDigit(tok[0]) && tok[1]==':')
		{
			//cout<<"done2\n";
			long intPart=0;
			for(i=2;tok[i]!='.';i++)
				intPart=intPart*10+(tok[i]-'0');
			i++;
			double floatPart=0.0;
			int ten=1;
			for(;i<tok.size();i++)
				floatPart=floatPart+(tok[i]-'0')/pow(10,ten++);
			double val=intPart+floatPart;
			double fVal=(val-min[(tok[0]-'0')-1])/(max[(tok[0]-'0')-1]-min[(tok[0]-'0')-1]);
			out<<tok[0]<<":"<<fVal<<" ";
			
		}
		else 
		{
			out<<tok<<" ";
		}
		if(tot%10==9)
			out<<"\n";
		tot++;
	}
	svm.close();
	return 0;
}