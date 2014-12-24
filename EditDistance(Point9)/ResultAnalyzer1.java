import java.io.*;
import java.util.*;

public class ResultAnalyzer1 {
	public static void main(String[] args) throws IOException {
		//Folder in which the predicted files are kept
		String folder1 = args[0];
		
		//Folder in which the aligned files are kept
		String folder2 = args[1];

		//Output file saved in same folder with name 'output.txt'
		PrintWriter writer = new PrintWriter(folder1+"/"+"output.txt");
		
		File dir = new File(folder1);
		
		File[] predictedFiles = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".predictedfasta");
			}
		});

		BufferedReader br = null;
		StringTokenizer st = null;
		long sumED = 0L;
		long sumSqED = 0L;
		int fileCount = predictedFiles.length;
		for (int i = 0; i < fileCount; ++i) {
			File predictedFile = predictedFiles[i];
			String fileName = predictedFile.getName();
			br = new BufferedReader(new FileReader(predictedFile));
    			String predictedFasta = br.readLine();
			br.close();
			
			String fileNameWoExt = fileName.substring(0,fileName.lastIndexOf("."));
			br = new BufferedReader(new FileReader(folder2+"/"+fileNameWoExt+".alignedfasta"));
                        int tokenCount = 0;
                        String alignedFasta = null;
                        String temp = null;
                        while((temp = br.readLine()) != null) {
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
		
		
		
