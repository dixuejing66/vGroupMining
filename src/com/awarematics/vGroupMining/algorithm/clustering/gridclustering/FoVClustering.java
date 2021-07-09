package com.awarematics.vGroupMining.algorithm.clustering.gridclustering;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;
import com.awarematics.vGroupMining.type.FOV;

public class FoVClustering {

public static  ArrayList<ArrayList<FOV>> resultList = new ArrayList<ArrayList<FOV>>();
	


	public static ArrayList<ArrayList<FOV>> fovClu(int M, ArrayList<ArrayList<Integer>> gridIndex2,
			ArrayList<ArrayList<Integer>> pQuery, ArrayList<FOV> fovArray, int[][] gridCell, double cellWidth, double eps,
			double[] mapBoundary, double areaInter) {

		IntersectArea ia = new IntersectArea();
		resultList = new ArrayList<ArrayList<FOV>>();
		FOV f = new FOV();

		ArrayList<Integer> cluster_id = new ArrayList<Integer>();
		// obtain corresponding cells from f in N to gridIndex2

		for (ArrayList<Integer> candidate : pQuery) {
			ArrayList<Integer> corres_fid = new ArrayList<Integer>();
			corres_fid = FoVClustering.corresponding(candidate, gridIndex2, M);

			LinkedList<Integer> corres_fArr = new LinkedList<Integer>();
			for (Integer fid : corres_fid) {
				corres_fArr.add(fid);
			}
		
			cluster_id = ia.judgeInterArea(f, areaInter, corres_fArr, fovArray);
			ArrayList<FOV> cluster = new ArrayList<FOV>();
			for(int i=0;i<cluster_id.size();i++) {
				FOV fov = fovArray.get(cluster_id.get(i)-1);
				cluster.add(fov);
			}
			
			resultList.add(cluster);
		}

		return resultList;
	}

	/*
	 * Obtain the grid corresponding to f in N in gridIndex2 and filtering
	 */
	public static ArrayList<Integer> corresponding(ArrayList<Integer> candidate, ArrayList<ArrayList<Integer>> gridIndex2,
			int M) {
		ArrayList<Integer> corresCells_f = new ArrayList<Integer>();
		for (Integer fovid : candidate) {
			for (ArrayList<Integer> cell : gridIndex2) {
				if ((cell.isEmpty()) && (cell != null) && (cell.size() >= M)) {
					if (cell.contains(fovid)) {
						corresCells_f.addAll(cell);

					}
				}
			}
		}
		ArrayList<Integer> corres_fid = (ArrayList<Integer>) corresCells_f.stream().distinct()
				.collect(Collectors.toList());
		return corres_fid;
	}


	public LinkedList<Integer> idToFov(LinkedList<Integer> fovInRangeID2, ArrayList<FOV> fovArr) {
		LinkedList<Integer> candiOneClu = new LinkedList<Integer>();
		Iterator<Integer> it = fovInRangeID2.iterator();
		while (it.hasNext()) {
			Integer fovID = it.next();
//			int tep = fovID - 1;
//			FOV f = fovArr.get(tep);
			candiOneClu.add(fovID);

		}
		return candiOneClu;
	}
}
