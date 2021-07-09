package com.awarematics.vGroupMining.algorithm.groupMining;

import java.io.IOException;
import java.util.ArrayList;

import com.awarematics.vGroupMining.io.ReadCluster;
import com.awarematics.vGroupMining.io.WriteGroupstoFile;

public class Main {
	public static void main(String[] args) throws IOException {

		ReadCluster rc = new ReadCluster();
		ArrayList<ArrayList<Cluster>> cluSnapshots = new ArrayList<ArrayList<Cluster>>();
		ArrayList<GroupProperty> candidates = new ArrayList<GroupProperty>();
		ArrayList<GroupProperty> candidatesL = new ArrayList<GroupProperty>();
		ArrayList<GroupProperty> candidatesG = new ArrayList<GroupProperty>();
		ArrayList<GroupProperty> candidatesK = new ArrayList<GroupProperty>();
		ArrayList<GroupProperty> patterns = new ArrayList<GroupProperty>();

//		String filesPath = "ClusterFilePath";
		String filesPath = "D:\\PaperExperimentalData\\Cluser\\Cluster(ShoppingNum=4500)\\DensityCluster(duration=1000ms)\\(1000ms)";
		String desPath = "D:\\PaperExperimentalData\\Groups mining\\Shopping(1000ms)\\Density\\Min_o\\4,3,4,4,7()";
		long time3 = System.currentTimeMillis();
		cluSnapshots = rc.readCluster(filesPath);
		long time1 = System.currentTimeMillis();
		System.out.println(time1 - time3);
		candidates = MiningGroup.findCandidates(cluSnapshots, 4);
		candidatesL = Constraints.judgL(candidates, 3);
		candidatesG = Constraints.judgG(candidatesL, 4);
		candidatesK = Constraints.judgK(candidatesG, 4);
		patterns = Constraints.filterPattern(candidatesK, 7);
		long time2 = System.currentTimeMillis();
		System.out.println("MainGrid: " + (time2 - time1));
		WriteGroupstoFile.clusterToCsvFile(patterns, desPath, 4, 3, 4, 4, 7);
	}
}
