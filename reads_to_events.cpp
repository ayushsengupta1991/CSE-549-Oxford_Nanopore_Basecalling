//Assuming that start and end are including and the index is zero based. Change the values of start and end accordingly.
//considers normal as well as reverse reads
//takes overlapping into consideration.

/*
	Takes the following input:

	argv[1]: <file_name>.alignedfasta
	argv[2]: <file_name>.events
	argv[3]: <svm_op_dir>
	argv[4]: <nb_op_dir>
*/

#include<iostream>
#include<fstream>
#include<vector>
#include<cstdlib>
#include<string>
#include<cstring>
#define gv(mod) ((3)+(mod))%(3)
using namespace std;
char gap='-';

int overlap(string x,string y)
{
	bool hyp;
	int k,i,j;
	for(k=0;k<=3;k++)
	{
		hyp=true;
		for(i=k,j=0;i<x.size();i++)
			hyp=hyp && (x[i]==y[j++]);
		if(hyp) return (5-k);
	}
}
int getEnum(char c)
{
	if(c=='A')return 0;
	if(c=='C')return 1;
	if(c=='G')return 2;
	if(c=='T')return 3;
}
long long int getLabel(string s,char gap)
{
	int i,cg=0;
	for(i=0;i<5;i++)
		if(s[i]==gap)
			cg++;
	if(cg>1)
		return -1;
	if(cg==0)
	{
		long long int sum=0;
		for(i=0;i<5;i++)
			sum=sum*4+getEnum(s[i]);
		return sum;
	}
	else
	{
		long long int sum=1024;
		for(i=0;i<5;i++)
			if(s[i]==gap)
			{
				sum=sum+(i*256);
				break;
			}
		long long int offset=0;
		for(i=0;i<5;i++)
			if(s[i]!=gap)
			{
				offset=offset*4+getEnum(s[i]);
			}
		return (sum+offset);
	}
}
int main(int argc, char *argv[])
{
	if(argc!=5)
		cout<<"Incorrect usage... Please provide two arguments... Usage ./<executable> <alignedfasta> <events> <svm_output_dir> <nb_output_dir>\n";
	else
	{
		ifstream fast_five(argv[2]);
		if(!fast_five.is_open())
		{
			cout<<"Could not open events file\n";
			return 0;
		}
		ifstream fasta(argv[1]);
		if(!fasta.is_open())
		{
			cout<<"Could not open fasta file\n";
			return 0;
		}
		char *x1=new char [1000];
		char *x2=new char [1000];
		int i;
		int lastslash1=-1,lastslash2=-1,dot1=-1,dot2=-1,j1=0,j2=0,j;
		cout<<"done1\n";
		for(i=0;argv[1][i]!='\0';i++)
		{
			if(argv[1][i]=='/')
				lastslash1=i;
			if(argv[1][i]=='.')
				dot1=i;
		}
		cout<<"done2\n";
		for(i=lastslash1+1;i<dot1;i++)
			x1[j1++]=argv[1][i];
		cout<<"done3\n";
		// part 1 done
		for(i=0;argv[2][i]!='\0';i++)
		{
			if(argv[2][i]=='/')
				lastslash2=i;
			if(argv[2][i]=='.')
				dot2=i;
		}
		cout<<"done4\n";
		for(i=lastslash2+1;i<dot2;i++)
			x2[j2++]=argv[2][i];
		cout<<"done5\n";
		
		if(j1!=j2)
		{
			cout<<"Filenames are not same\n";
			cout<<"done11\n";
			return 0;
		}
		cout<<"done6\n";
		j=j1;
		x1[j1]='\0';
		x2[j2]='\0';
		cout<<x1<<"\n"<<x2<<"\n";
		for(i=0;i<j;i++)
			if(x1[i]!=x2[i])
				{
					cout<<"Filenames are not same\n";
					cout<<"done12\n";
					cout<<i<<"\n";
					cout<<x1[i]<<" "<<x2[i]<<"\n";
					return 0;
				}
		cout<<"Filenames are same!!\n";
		x1[j]='.';x1[j+1]='s';x1[j+2]='v';x1[j+3]='m';
		x1[j+4]=='\0';
		char *fPath1=new char [strlen(x1)+strlen(argv[3])+5];
		strcat(fPath1,argv[3]);
		strcat(fPath1,x1);
		cout<<fPath1<<"\n";
		ofstream outfilesvm(fPath1);
		x2[j]='.';x2[j+1]='n';x2[j+2]='b';
		x2[j+3]=='\0';
		char *fPath2=new char [strlen(x2)+strlen(argv[4])+5];
		strcat(fPath2,argv[4]);
		strcat(fPath2,x2);
		cout<<fPath2<<"\n";
		ofstream outfilenb(fPath2);
		string x,aligned_read;
		long long int start,end;
		fasta>>start;
		fasta>>end;
		fasta>>x;
		fasta>>aligned_read;
		cout<<start<<"\n"<<end<<"\n"<<x<<"\n"<<aligned_read;
		if(start>end)
		{
			int swap=start;
			start=end;
			end=swap;
			int i_,j_;
			for(i_=0,j_=aligned_read.size()-1;i_<=j_;i_++,j_--)
			{
				char s=aligned_read[i_];
				aligned_read[i_]=aligned_read[j_];
				aligned_read[j_]=s;
			}
		}
		string tok;
		int counter=-1,fasti=-1,top=-1,ctr=0;
		i=-1;
		string event[3][5];// 0-> mean,1->start, 2->std, 3->length, 4->model state 
		vector<string> state;
		string curr,prev;
		cout<<"howdy\n";
		cout<<"aligned read size="<<aligned_read.size()<<"\n";
		while(fast_five>>tok)
		{
			if(tok.compare("raw_index")!=0)
			{
				counter++;
				int attr=counter%16;
				if(counter>=16)
				{
					if(attr>=2 && attr<=6)
						state.push_back(tok);
					if(attr==15)
					{
						top=(top+1)%3;
						if(top==0 && ctr==0)
						{
							for(int ii=0;ii<3;ii++)
								for(int jj=0;jj<5;jj++)
								{
									event[ii][jj]=state[jj];
									//cout<<event[ii][jj]<<"\n";
								}
							
							ctr++;	
						}
						else
						{
							for(int ii=0;ii<5;ii++)
								event[top][ii]=state[ii];
						}
						state.clear();
						if(i==-1) // change settings here if the index is not zero based but rather 1 based.
							i=0;
						else
							i=i+(5-overlap(event[gv(top-1)][4],event[gv(top)][4]));
						if((start<=end) && i>=start && i<=end-4)
						{
							if(fasti==-1)
								fasti=0;
							else
								fasti=fasti+(5-overlap(event[gv(top-1)][4],event[gv(top)][4]));
							cout<<aligned_read.substr(fasti,5)<<"\n";
							long long int label=getLabel(aligned_read.substr(fasti,5),gap);
							if(label>0)
							{
								outfilesvm<<label<<" ";
								outfilesvm<<"1:"<<event[gv(top)][0]<<" ";
								outfilesvm<<"2:"<<event[gv(top)][2]<<" ";
								outfilesvm<<"3:"<<event[gv(top)][3]<<" ";
								outfilesvm<<"4:"<<event[gv(top-1)][0]<<" ";
								outfilesvm<<"5:"<<event[gv(top-1)][2]<<" ";
								outfilesvm<<"6:"<<event[gv(top-1)][3]<<" ";
								outfilesvm<<"7:"<<event[gv(top-2)][0]<<" ";
								outfilesvm<<"8:"<<event[gv(top-2)][2]<<" ";
								outfilesvm<<"9:"<<event[gv(top-2)][3]<<" ";
								outfilesvm<<"\n";
								
								outfilenb<<event[gv(top)][0]<<" "<<event[gv(top)][2]<<" "<<event[gv(top)][3]<<" "<<aligned_read.substr(fasti,5)<<"\n";
							}
						}
					}
				}
			}
		}
		
		/*if(start>end)//implies reverse strand
		{
			int j=start-4,i_,j_;
			int fasti=0,top_;
			while((top_=indirection[j])==0)
				j--;
			while(j>=end)
			{
				//char gap='_'
				string temp=aligned_read.substr(fasti,5);
				for(i_=0,j_=4;i_<=j_;i_++,j_--)
				{
					char swap=temp[i_];
					temp[i_]=temp[j_];
					temp[j_]=swap;
				}
				long long int label=getLabel(temp,gap);
				if(label>0)
				{
					outfilesvm<<label<<" ";
					outfilesvm<<"1:"<<event[0][top_]<<" ";
					outfilesvm<<"2:"<<event[2][top_]<<" ";
					outfilesvm<<"3:"<<event[3][top_]<<" ";
					if(top-1>=0)
					{
						outfilesvm<<"4:"<<event[0][top_-1]<<" ";
						outfilesvm<<"5:"<<event[2][top_-1]<<" ";
						outfilesvm<<"6:"<<event[3][top_-1]<<" ";
					}
					if(top-2>=0)
					{
						outfilesvm<<"7:"<<event[0][top_-2]<<" ";
						outfilesvm<<"8:"<<event[2][top_-2]<<" ";
						outfilesvm<<"9:"<<event[3][top_-2]<<" ";
					}
					outfilesvm<<"\n";
					outfilenb<<event[0][top_]<<" "<<event[2][top_]<<" "<<event[3][top_]<<" "<<label<<"\n";
				}
				int shift=5-overlap(event[4][top_-1],event[4][top_]);
				j=j-shift;
				fasti=fasti+shift;
				top_--;
				
			}
		}
		*/
				
		outfilesvm.close();
		outfilenb.close();
		fast_five.close();
		fasta.close();
	}
	
	
	return 0;
}

