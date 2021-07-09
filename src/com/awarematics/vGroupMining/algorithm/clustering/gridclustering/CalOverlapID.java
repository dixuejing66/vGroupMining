package com.awarematics.vGroupMining.algorithm.clustering.gridclustering;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CalOverlapID {

	/*
	 * This function for calculating the cells' ID of FoVs that overlap with core
	 * FoV
	 */

	public static ArrayList<Integer> calculateCellID(int[] cellID_vertex, int[][] gridCell) {

		ArrayList<Integer> overID_temp = new ArrayList<Integer>(); // overID is cellID of all fovs that overlap with
																	// core fov

		for (int x = 0; x < cellID_vertex.length - 1; x++) {
			for (int y = x + 1; y < cellID_vertex.length; y++) {
				if (cellID_vertex[x] > cellID_vertex[y]) {
					int temp = cellID_vertex[x];
					cellID_vertex[x] = cellID_vertex[y];
					cellID_vertex[y] = temp;
				}
			}
		}

		int coverRow = Math.abs((cellID_vertex[2] - cellID_vertex[0])) / gridCell[0].length+1;
		for (int i = 0; i <= coverRow; i++) {
			for (int j = cellID_vertex[0]; j <= cellID_vertex[1]; j++) {
				int cellNum = gridCell.length * gridCell[0].length;
				int cellID = j + gridCell[0].length * i;
				if(cellID<=cellNum) {
				overID_temp.add(cellID);
				}
			}
		}

		ArrayList<Integer> overID = (ArrayList<Integer>) overID_temp.stream().distinct().collect(Collectors.toList());
//		
//		for (int i = 0; i < cellID_vertex.length; i++) {
//			int cellID = cellID_vertex[i];
//			if (!overID.contains(cellID)) {
//				overID.add(cellID_vertex[i]);
//			}
//		}
//
//		int colDiff = cellID_vertex[2] - cellID_vertex[1] + 1;
//		int rowDiff = (cellID_vertex[0] - cellID_vertex[1]) / gridCell[0].length - 1;
//
//		// Top layer
//		for (int i = cellID_vertex[0]; i < colDiff; i++) {
//			overID.add(i);
//		}
//
//		// Bottom layer
//		for (int i = cellID_vertex[1]; i < colDiff; i++) {
//			overID.add(i);
//		}
//
//		// middle layer
//		for (int i = 1; i <= rowDiff; i++) {
//			int firNum = cellID_vertex[1] + gridCell[0].length * i;
//			for (int j = firNum; j < colDiff; j++) {
//				overID.add(j);
//			}
//		}
		return overID;
	}
}
