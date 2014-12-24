import java.io.*;
import java.util.*;

public class ResultAnalyzer2 {
	public static void main(String[] args) throws IOException {
		//Folder in which the files are kept
		String folder = args[0];

		//Output file saved in same folder with name 'output.txt'
		PrintWriter writer = new PrintWriter(folder+"/"+"output.txt");
		
		File dir = new File(folder);
	
		File[] alignedFiles = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".alignedfasta");
			}
		});

		BufferedReader br = null;
		StringTokenizer st = null;
		long sumED = 0L;
		long sumSqED = 0L;
		int fileCount = alignedFiles.length;
		
		for (int i = 0; i < fileCount; ++i) {
			File alignedFile = alignedFiles[i];
			String fileName = alignedFile.getName();
			br = new BufferedReader(new FileReader(alignedFile));

			int tokenCount = 0;
			String alignedFasta = null;
			String predictedFasta = null;
			String temp = null;
			while((temp = br.readLine()) != null) {
				if (tokenCount == 2) {
					predictedFasta = temp;
				}
				if (tokenCount == 3) {
					alignedFasta = temp;
					break;
				}
				tokenCount++;
			}
			br.close();

			if (tokenCount == 3)
			{
				long ED = getED(predictedFasta, alignedFasta);
				sumED += ED;
				sumSqED += ED*ED;

				//File name - space - ED
				writer.println(fileName + " " + ED);
			}
		}

		//Mean of ED - space - RMS ED
		writer.println(((sumED*1.0)/fileCount) + " " + Math.sqrt((sumSqED*1.0)/fileCount));

		writer.flush();
		writer.close();

		System.exit(0);
	}

	private static long getED(String str1, String str2) {
		int length1 = str1.length();
		int length2 = str2.length();
		int gapCost = 1;
		int subCost = 1;

		long[] currentEDs = new long[length1+1];
		long[] lastEDs = null;
		for (int i = 0; i < length1+1; ++i) {
			currentEDs[i] = i;
		}

		for (int j = 1; j < length2+1; ++j) {
			lastEDs = currentEDs;
			currentEDs = new long[length1+1];
			currentEDs[0] = j;
			for (int i = 1; i < length1+1; ++i) {
				char ch1 = str1.charAt(i-1);
				char ch2 = str2.charAt(j-1);
				
				if (ch1 == ch2) {
					currentEDs[i] = lastEDs[i-1];
				}	
				else {
					long ins = lastEDs[i]+gapCost;
					long del = currentEDs[i-1] + gapCost;
					long sub = lastEDs[i-1] + subCost;
					long min = ((ins > del) ? del : ins);
					min = ((min > sub) ? sub : min);
					currentEDs[i] = min;
				}
			}
		}

		return currentEDs[length1];
	}					
}
		
		
		
