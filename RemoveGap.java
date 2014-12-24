import java.io.*;
import java.util.*;

public class RemoveGap{
	public static void main(String[] args) throws IOException {
		String folder = args[0];
		File dir = new File(folder);

                File[] outFiles = dir.listFiles(new FilenameFilter() {
                        public boolean accept(File dir, String name) {
                                return name.toLowerCase().endsWith(".out");
                        }
                });

		int outFileCount = outFiles.length;
		BufferedReader br = null;
		PrintWriter out = null;
		int start = -1, end = -1;
		String query = null;
		String subject = null;
		StringTokenizer st = null;	
	
		for (int c = 0; c < outFileCount; ++c) { 
			try 
			{
				File outFile = outFiles[c];
				String fileName = outFile.getName();
				//System.out.println(fileName);
				String fileNameWoExt = fileName.substring(0,fileName.lastIndexOf("."));
				br = new BufferedReader(new FileReader(outFile));
				st = new StringTokenizer(br.readLine());
				
				if (st.hasMoreTokens()) 
					start = Integer.parseInt(st.nextToken());
				if (st.hasMoreTokens())
					end = Integer.parseInt(st.nextToken());
				if (st.hasMoreTokens())
					query = st.nextToken();
				if (st.hasMoreTokens())
					subject = st.nextToken();

				out = new PrintWriter(folder+"/"+fileNameWoExt+".alignedfasta");
				out.println(start);
				out.println(end);

				int length1 = query.length();
				int length2 = subject.length();

				if (length1 == length2) {
					StringBuffer temp1 = new StringBuffer();
					StringBuffer temp2 = new StringBuffer();
					for (int i = 0; i < length1; ++i) {
						char ch1 = query.charAt(i);
						char ch2 = subject.charAt(i);	
						if (ch1 != '-') {
							temp1.append(ch1);
							temp2.append(ch2);
						}
					}

					if ((end-start+1) != temp1.length())
						System.out.println("Query string doesn't agree with start and end, start =  " + start + ", end = " + end + ", length = " + temp1.length());
					out.println(temp1.toString());
					out.println(temp2.toString()); 
				}

				out.flush();
				out.close();
			}
			catch (Exception e)
			{
				//e.printStackTrace();
			}
		}

		System.exit(0);
	}
}
			
					
