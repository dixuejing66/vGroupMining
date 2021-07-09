package com.awarematics.vGroupMining.algorithm.clustering.densityclustering;

import java.util.ArrayList;
import java.util.Iterator;
import com.awarematics.vGroupMining.transform.GPSHelper;
import com.awarematics.vGroupMining.type.FOV;

public class DBscanProperty {

	public ArrayList<FOV> vistList = new ArrayList<FOV>();
	public ArrayList<FOV> noiList = new ArrayList<FOV>();
	/*
	 * visited judgment
	 */
	public ArrayList<FOV> visited(FOV f) {
		vistList.add(f);
		return vistList;
	}

	public boolean isVisited(FOV f) {

		if (vistList.contains(f)) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * calculate distance between two fovs
	 */
	public static double getDistance(FOV f1, FOV f2) {
		
		double dist = GPSHelper.getDistance(f1, f2);// be used for BDD data
//		double dist = h.getDistXY(f1,f2);//be used for Shopping data

		return dist;
	}

	public ArrayList<FOV> getNeighbours(FOV f, double eps, ArrayList<FOV> fovArr) {

		ArrayList<FOV> neigh = new ArrayList<FOV>();
		for (FOV fov : fovArr) {
			if (getDistance(f, fov) <= eps) {
				neigh.add(fov);
			}
		}
		return neigh;
	}

	public ArrayList<FOV> merge(ArrayList<FOV> neighbours, ArrayList<FOV> neighbours2) {
		Iterator<FOV> itb = neighbours2.iterator();
//		DBscanProperty.p2pDistance(neighbours);
		while (itb.hasNext()) {
			FOV f = itb.next();
			if (!neighbours.contains(f)) {
				neighbours.add(f);
			}
		}
		return neighbours;
	}

	public ArrayList<FOV> noise(FOV f) {
		noiList.add(f);
		return noiList;
	}

	public static void p2pDistance(ArrayList<FOV> fovList) {

		ArrayList<Double> distance = new ArrayList<Double>();
		double temp = 0;
		float sum = 0;
		for (int i = 0; i < fovList.size(); i++) {
			for (int j = i + 1; j < fovList.size(); j++) {
				temp = getDistance(fovList.get(i), fovList.get(j));
				distance.add(temp);
				sum += temp;
			}
		}
		Iterator<Double> itdist = distance.iterator();
		while (itdist.hasNext()) {
			Double temp2 = itdist.next();
		}

		double Average = sum / distance.size();
	}
}
