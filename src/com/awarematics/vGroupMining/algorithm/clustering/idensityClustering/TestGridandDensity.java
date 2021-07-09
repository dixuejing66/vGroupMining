package com.awarematics.vGroupMining.algorithm.clustering.idensityClustering;

import java.io.IOException;
import java.util.ArrayList;

import com.awarematics.vGroupMining.algorithm.clustering.gridclustering.RunTimeCluster;
import com.awarematics.vGroupMining.io.ReadSnap;
import com.awarematics.vGroupMining.type.FOV;

public class TestGridandDensity {
	public static void main(String[] args) throws IOException {

		int minPts = 2;

		double eps = 5000; // because cellwidth 100 times
		double areaInter = 0.5;
		// 1 meter = 0.000009 degree
		String snapPath = "snapshotsPath";
		String desPath = "ClustersResultPath";
		String nameGrid = "GridClusteringResult_";
		String nameDensy = "DensityClusteringResult_";
		ArrayList<ArrayList<FOV>> snaplist = new ArrayList<ArrayList<FOV>>();
		ReadSnap rb = new ReadSnap();
		snaplist = rb.readSnapFile(snapPath);
		RunTimeCluster rtc = new RunTimeCluster();

		long timeDB = rtc.runTimeDB(snaplist, desPath, minPts, eps, areaInter, nameDensy);
		long timeGrid = rtc.runTimeGrid(snaplist, desPath, minPts, eps, areaInter, nameGrid);

		System.out.println("run timeDB : " + timeDB);
		System.out.println("run timeGrid : " + timeGrid);
	}
}
