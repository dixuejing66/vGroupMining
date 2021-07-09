package com.awarematics.vGroupMining.algorithm.clustering.gridclustering;

import java.util.ArrayList;
import java.util.Collections;

import com.awarematics.vGroupMining.transform.GPSHelper;
import com.awarematics.vGroupMining.type.FOV;
import com.awarematics.vGroupMining.type.MPoint;

public class PositionClustering {

	public static ArrayList<Integer> visited = new ArrayList<Integer>();


	public static ArrayList<ArrayList<Integer>> positionClu(int M, ArrayList<ArrayList<Integer>> gridIndex1, ArrayList<FOV> fovArr,
			int[][] gridCell, double eps) {

		ArrayList<ArrayList<Integer>> positionClusters = new ArrayList<ArrayList<Integer>>();
		for (ArrayList<Integer> coreCell : gridIndex1) {

			ArrayList<Integer> neiCells = new ArrayList<Integer>();
			ArrayList<Integer> neighbors = new ArrayList<Integer>();
			ArrayList<Integer> candidates = new ArrayList<Integer>();
			if (coreCell.size() >= M) {

				int cID = gridIndex1.indexOf(coreCell); // cID is cell's id of the core cell
				neiCells = PositionClustering.NeiCells(cID, gridCell); // neiCells is the neighboring cells with core cell
				neighbors = PositionClustering.neighbors(gridIndex1, neiCells); // neighbors is the entries in the neighboring cells

				// traverse the position in coreCell
				for (Integer pid : coreCell) {
					MPoint p_c = new MPoint();
					visited.add(pid);
					p_c.setX(fovArr.get(coreCell.get(pid)).getLongitude());
					p_c.setY(fovArr.get(pid).getLatitude());

					// traverse the position in
					for (int i = 0; i < neighbors.size(); i++) {
						MPoint p_n = new MPoint();
						p_n.setX(fovArr.get(neighbors.get(i)).getLongitude());
						p_n.setY(fovArr.get(neighbors.get(i)).getLatitude());

						// compute distance between the p_c and p_n
						double dist = GPSHelper.getDistancePtoP(p_c.getX(), p_c.getY(), p_n.getX(), p_n.getY());
						if (dist <= eps) {
							candidates.add(neighbors.get(i));
						}
					}
					if(candidates.size()>=M) {
						positionClusters.add(candidates);
					}
				}
			}
			
		}
		return positionClusters;
	}

	// obtain the neighboring cells
	public static ArrayList<Integer> NeiCells(int cID, int[][] gridCell) {
		ArrayList<Integer> neiCells = new ArrayList<Integer>();

		int temp1 = cID - 9;
		int temp2 = temp1 + gridCell[0].length;
		int temp3 = temp2 + gridCell[0].length;
		for (int i = 0; i <= 4; i++) {
			neiCells.add(temp1 + i);
			neiCells.add(temp2 + i);
			neiCells.add(temp3 + i);
		}
		int temp4 = cID - 15;
		int temp5 = cID + 13;
		for (int i = 0; i < 3; i++) {
			neiCells.add(temp4 + i);
			neiCells.add(temp5 + i);
		}
		Collections.sort(neiCells);
		return neiCells;
	}

	// obtain the neighbors in neighboring cells
	public static ArrayList<Integer> neighbors(ArrayList<ArrayList<Integer>> gridIndex1, ArrayList<Integer> neiCells) {

		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		for (Integer neiCid : neiCells) { // get the neighboring cell's id
			neighbors.addAll(gridIndex1.get(neiCid));
		}
		return neighbors;
	}

}
