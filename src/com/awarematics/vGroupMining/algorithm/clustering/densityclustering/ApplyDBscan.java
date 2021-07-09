package com.awarematics.vGroupMining.algorithm.clustering.densityclustering;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.collections4.CollectionUtils;

import com.awarematics.vGroupMining.algorithm.clustering.idensityClustering.BoundaryFoVBelong;
import com.awarematics.vGroupMining.algorithm.clustering.idensityClustering.IDensityClustering;
import com.awarematics.vGroupMining.transform.GPSHelper;
import com.awarematics.vGroupMining.type.FOV;

public class ApplyDBscan extends BoundaryFoVBelong implements IDensityClustering {
	DBscanProperty dc = new DBscanProperty();
	public ArrayList<FOV> visit = new ArrayList<FOV>();

	public ArrayList<ArrayList<ArrayList<FOV>>> applyDB(int minPts, double eps, double areaInter,
			ArrayList<ArrayList<FOV>> snapArr) throws IOException {

		ArrayList<ArrayList<ArrayList<FOV>>> clustersSnap = new ArrayList<ArrayList<ArrayList<FOV>>>();
		for (ArrayList<FOV> fovArray : snapArr) {
			ArrayList<ArrayList<FOV>> clusters = null;
			clusters = apply(fovArray, minPts, eps, areaInter);
			clustersSnap.add(clusters);
		}
		return clustersSnap;
	}

	@Override
	public ArrayList<ArrayList<FOV>> apply(ArrayList<FOV> fovArray, int minPts, double eps, double areaInter) {
		IntersectArea interarea = new IntersectArea();
		ArrayList<FOV> noiList = null;
		ArrayList<ArrayList<FOV>> resultList =new ArrayList<ArrayList<FOV>>();
		visit.clear();
		noiList = new ArrayList<FOV>();

		for (int i = 0; i < fovArray.size(); i++) {
			FOV f = fovArray.get(i);
			if (!dc.isVisited(f)) {
				visit = dc.visited(f);
				// find candidates by distance
				ArrayList<FOV> neighbours = dc.getNeighbours(f, eps, fovArray);
				if (neighbours.size() >= minPts) {
					int index = 0;
					while (neighbours.size() > index) {
						FOV n = neighbours.get(index);
						if (!dc.isVisited(n)) {
							ArrayList<FOV> neighbours2 = dc.getNeighbours(n, eps, fovArray);
							if (neighbours2.size() >= minPts) {
								visit = dc.visited(f);
								neighbours = dc.merge(neighbours, neighbours2);
							}
						}
						index++;
					}
					if (neighbours.size() >= minPts) {
						ArrayList<FOV> cluster_temp = interarea.judgeInterArea(f, neighbours, areaInter,fovArray);
						ArrayList<FOV> cluster = new ArrayList<FOV>();
						if (cluster_temp.size() >= minPts) {
							for (FOV fc : cluster_temp) {
								double dist = GPSHelper.getDistance(f, fc);
								if (dist <= eps) {
									cluster.add(fc);
								}
							}
							if (cluster.size() >= minPts) {
								resultList.add(cluster);
							}
						} else {
							if (!noiList.contains(f)) {
								noiList = dc.noise(f);
							}
						}
					}
				} else {
					for (FOV n : neighbours) {
						if (!noiList.contains(n)) {
							noiList = dc.noise(n);
						}
					}
				}
			}
			/*
			 * Get noiseList
			 */
			for (ArrayList<FOV> temp : resultList) {
				for (FOV rf : temp) {
					if (noiList.contains(rf)) {
						noiList.remove(rf);
					}
				}
			}
		}
		resultList= filteringDuplicate(resultList);
//		resultList= boundaryFoVBelong(resultList,minPts);
		return resultList;
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
