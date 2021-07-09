package com.awarematics.vGroupMining.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.awarematics.vGroupMining.algorithm.groupMining.GroupProperty;


public class WriteGroupstoFile {
	public static void clusterToCsvFile(ArrayList<GroupProperty> patterns, String desPath, int M, int L, int G,int K, int S)
			throws IOException {

		String fileName = "Patterns of Geo-tagged videos" + ".csv";
		File writePath = new File(desPath + File.separator + fileName);
		writePath.createNewFile();
		BufferedWriter br = new BufferedWriter(new FileWriter(writePath));

		String head = "GridQueryResultBDD (instant=1000ms) M=" + M + " L=" + L + " G=" + G + "K="+K+ "S=" + S;
		br.write(head);
		br.newLine();
		

		for (int i = 0, len = patterns.size(); i < len; i++) {

			GroupProperty p = patterns.get(i);
			String pattern = "Patterns ID " +(i+1);
			br.write(pattern);
			br.newLine();
			
			
			String item1 = "Video ID";
			br.write(item1);
			
			
			for (int m = 0; m < p.getVideoID().size(); m++) {
				Integer id = p.getVideoID().get(m);
				br.write("," + String.valueOf(id) );
				br.flush();
			}
			br.newLine();
			
			String item2 ="Snapnum";
			br.write(item2);
			for (int n = 0; n < p.getSnapshotNum().size(); n++) {
				Integer num = p.getSnapshotNum().get(n);
				br.write("," + String.valueOf(num) + " ");
				br.flush();
			}
			br.newLine();
			br.flush();
			
		}
		br.close();
	}

}
