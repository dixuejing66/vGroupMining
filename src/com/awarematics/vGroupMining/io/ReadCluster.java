package com.awarematics.vGroupMining.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.awarematics.vGroupMining.algorithm.groupMining.Cluster;

public class ReadCluster {

	public static void main(String[] args) {

		ReadCluster rc = new ReadCluster();
		ArrayList<ArrayList<Cluster>> cluSnapshots = new ArrayList<ArrayList<Cluster>>();

		String filesPath = "E:\\javaSourceFolder\\findingInterestedGroups-master\\experimentalData\\clustersFiles\\a";
		try {
			cluSnapshots = rc.readCluster(filesPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (ArrayList<Cluster> temp : cluSnapshots) {
			System.out.println("snapshot: ");
			for (Cluster c : temp) {
				System.out.println(c.getClusterID() + "\t\n");
				for (int i = 0; i < c.getVideoIDArr().length; i++) {
					System.out.print(c.getVideoIDArr()[i] + "\t");
				}
				System.out.println();
			}
		}

	}

	public ArrayList<ArrayList<Cluster>> readCluster(String filePath) throws IOException {

		ArrayList<Cluster> clusterV = new ArrayList<Cluster>();
		ArrayList<ArrayList<Cluster>> clusters = new ArrayList<ArrayList<Cluster>>();
		int[] videoID = new int[1];
		ArrayList<File> fileList = new ArrayList<File>();
		File file = new File(filePath);
		File[] files = file.listFiles();
		if (files == null) {
			return null;
		}

		for (File f : files) {
			if (f.isFile() && f.getName().endsWith(".csv")) {
				fileList.add(f);
			} else if (f.isDirectory()) {
				readCluster(f.getAbsolutePath());
			}
		}

		for (int i = 0; i < fileList.size(); i++) {
			BufferedReader reader = new BufferedReader(new FileReader(fileList.get(i)));
			reader.readLine();
			reader.readLine();
			reader.readLine();
			String line1;
//			String line2;
			Cluster cluster = new Cluster();

			while ((line1 = reader.readLine()) != null) {

				String temCluID = line1.split(",")[0];
				int clusterID = Integer.valueOf(temCluID);

//				String temCluSize = line1.split(",")[1];
//				int clusterSize = Integer.valueOf(temCluSize);

				String temVid = line1.split(",")[1];
				int vid = Integer.valueOf(temVid);

				if (videoID.length != 1) {
					if (cluster.getClusterID() == clusterID) {
						int a = videoID.length;
						videoID[a - 1] = vid;
						videoID = Arrays.copyOf(videoID, videoID.length + 1);
					} else {
						cluster.setVideoIDArr(videoID);
						clusterV.add(cluster);
						cluster = new Cluster();
						videoID = new int[1];
						videoID[0] = vid;
						cluster.setClusterID(clusterID);
						videoID = Arrays.copyOf(videoID, videoID.length + 1);
					}
				} else {
					videoID[0] = vid;
					cluster.setClusterID(clusterID);
					videoID = Arrays.copyOf(videoID, videoID.length + 1);
				}

//				line2 = reader.readLine();
//				int[] videoID = new int[clusterSize];
//
//				for (int n = 0; n < clusterSize; n++) {
//					String temViID = line2.split(",")[1];
//					videoID[n] = Integer.valueOf(temViID);
//				}

				cluster.setsnapID(i + 1);

			}

			if (line1 == null) {
				cluster.setVideoIDArr(videoID);
				clusterV.add(cluster);
			}
			clusters.add(clusterV);
			clusterV = new ArrayList<Cluster>();
			videoID = new int[1];
			if (i == (fileList.size() - 1)) {
				reader.close();
			}
		}
		return clusters;
	}
}
