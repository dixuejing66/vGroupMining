package com.awarematics.vGroupMining.algorithm.clustering.gridclustering;

import java.util.ArrayList;

import com.awarematics.vGroupMining.type.FOV;

public interface Grid {

	public int[][] createGrid(
			double cellWidth,  
			ArrayList<FOV> fovArr);
	
	public ArrayList<ArrayList<Integer>> insertFoVs(
			int[][] gridCell, 
			ArrayList<FOV> fovArr, 
			double cellWidth, 
			double[] mapBoundary);
	
	public ArrayList<ArrayList<Integer>> insertPosition(
			int[][] gridCell,
			ArrayList<FOV> fovArr,
			double cellWidth,
			double[] mapBoundary
			);
}
