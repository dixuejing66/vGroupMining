package com.awarematics.vGroupMining.algorithm.clustering.gridclustering;

import java.io.IOException;
import java.util.ArrayList;

import com.awarematics.vGroupMining.algorithm.clustering.densityclustering.ApplyDBscan;
import com.awarematics.vGroupMining.algorithm.clustering.idensityClustering.runTime;
import com.awarematics.vGroupMining.io.WriteClus;
import com.awarematics.vGroupMining.type.FOV;

public class RunTimeCluster implements runTime {

	@Override
	public long runTimeDB(ArrayList<ArrayList<FOV>> snapArry, String desPath, int minPts, double eps, double areaInter,
			String filename) throws IOException {

		long runTime = 0;
		long startTime = System.currentTimeMillis();
		ApplyDBscan app = new ApplyDBscan();
		ArrayList<ArrayList<ArrayList<FOV>>> clustersSnap = app.applyDB(minPts, eps, areaInter, snapArry);
		WriteClus wc = new WriteClus();
		wc.clusterToCsvFile(clustersSnap, desPath, areaInter, minPts, eps, filename);
		long endTime = System.currentTimeMillis();
		runTime = endTime - startTime;
		return runTime;
	}

	@Override
	public long runTimeGrid(ArrayList<ArrayList<FOV>> snapArry, String desPath, int minPts, double eps,
			double areaInter, String filename) throws IOException {
		long runTime = 0;
		long startTime = System.currentTimeMillis();
		ApplyGrid appG = new ApplyGrid();
		WriteClus wc = new WriteClus();
		ArrayList<ArrayList<ArrayList<FOV>>> snapsClus = new ArrayList<ArrayList<ArrayList<FOV>>>();
		snapsClus = appG.applyGrid(snapArry, minPts, eps, areaInter);
		wc.clusterToCsvFile(snapsClus, desPath, areaInter, minPts, eps, filename);
		long endTime = System.currentTimeMillis();
		runTime = endTime - startTime;
		return runTime;
	}

}
