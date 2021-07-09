/**
 * 
 */
package com.awarematics.vGroupMining.algorithm.clustering.gridclustering;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.commons.collections4.CollectionUtils;

import com.awarematics.vGroupMining.algorithm.clustering.idensityClustering.BoundaryFoVBelong;
import com.awarematics.vGroupMining.algorithm.clustering.idensityClustering.IDensityClustering;
import com.awarematics.vGroupMining.transform.GetMapboundary;
import com.awarematics.vGroupMining.type.FOV;

/**
 * @author DI
 *
 */
public class ApplyGrid extends BoundaryFoVBelong implements IDensityClustering {
	public LinkedList<Integer> visitList = null;
	public ArrayList<Integer> fovInRangeID = null;
	public ArrayList<FOV> noiList = new ArrayList<FOV>();

	public ArrayList<ArrayList<ArrayList<FOV>>> applyGrid( ArrayList<ArrayList<FOV>> snapArr, int minPts,
			double eps, double areaInter) {

		ArrayList<ArrayList<ArrayList<FOV>>> clustersSnap = new ArrayList<ArrayList<ArrayList<FOV>>>();
		for (ArrayList<FOV> fovArray : snapArr) {
			ArrayList<ArrayList<FOV>> clusters = apply(fovArray, minPts, eps, areaInter);

			if (!clusters.isEmpty()) {
				clustersSnap.add(clusters);
			}
		}
		return clustersSnap;
	}

	@Override
	public ArrayList<ArrayList<FOV>> apply(ArrayList<FOV> fovArray, int minPts, double eps, double areaInter) {

		GridIndex gi = new GridIndex();
		double[] mapBoundary = GetMapboundary.getboundary(fovArray);
		double cellWidth = eps * 0.000009;
		int[][] gridCell = gi.createGrid(cellWidth,fovArray);
		
		//Insert position to first index
		ArrayList<ArrayList<Integer>> positionIndex = gi.insertPosition(gridCell, fovArray, cellWidth, mapBoundary);
		//Insert FoVs to second index
		ArrayList<ArrayList<Integer>> FOVsIndex = gi.insertFoVs(gridCell, fovArray, cellWidth, mapBoundary);
		//position points clustering based on density
		ArrayList<ArrayList<Integer>> positionClusters = PositionClustering.positionClu(minPts, positionIndex, fovArray,
				gridCell, eps);
		//FOV clustering based on the position clustering
		ArrayList<ArrayList<FOV>> resultList = FoVClustering.fovClu(minPts, FOVsIndex, positionClusters, fovArray,
				gridCell, cellWidth, eps, mapBoundary, areaInter);

		return resultList;
	}

	public ArrayList<FOV> noise(ArrayList<Integer> fovInRangeID, ArrayList<FOV> fovArr) {

		for (Integer fovID : fovInRangeID) {
			if (!noiList.contains(fovArr.get(fovID - 1))) {
				noiList.add(fovArr.get(fovID - 1));
			}
		}
		return noiList;
	}

	public ArrayList<Integer> getFovID(ArrayList<Integer> fovIDArr, ArrayList<FOV> fovArr) {
		if (fovIDArr != null) {

			Iterator<Integer> it = fovIDArr.iterator();
			while (it.hasNext()) {
				Integer fovID = it.next();
				if (!fovInRangeID.contains(fovID)) {
					fovInRangeID.add(fovID);
				}
			}
			return fovInRangeID;
		} else {
			return null;
		}
	}



	public LinkedList<Integer> idToFov(LinkedList<Integer> fovInRangeID2, ArrayList<FOV> fovArr) {
		LinkedList<Integer> candiOneClu = new LinkedList<Integer>();
		Iterator<Integer> it = fovInRangeID2.iterator();
		while (it.hasNext()) {
			Integer fovID = it.next();
			candiOneClu.add(fovID);
		}
		return candiOneClu;
	}

	public ArrayList<ArrayList<FOV>> filteringDuplicate(ArrayList<ArrayList<FOV>> resultList) {
		ArrayList<ArrayList<FOV>> cluSnapshots = new ArrayList<ArrayList<FOV>>();
		for (ArrayList<FOV> cluSnapshot : resultList) {
			cluSnapshots.add(cluSnapshot);
		}
		for (int i = 0; i < cluSnapshots.size(); i++) {
			ArrayList<FOV> cluSnapshots_i = cluSnapshots.get(i);
			for (int j = i + 1; j < cluSnapshots.size(); j++) {
				ArrayList<FOV> cluSnapshots_j = cluSnapshots.get(j);
				if (CollectionUtils.isEqualCollection(cluSnapshots_i, cluSnapshots_j)) {
					resultList.remove(cluSnapshots_j);
					j--;
				}
			}
		}
		return resultList;
	}

}
