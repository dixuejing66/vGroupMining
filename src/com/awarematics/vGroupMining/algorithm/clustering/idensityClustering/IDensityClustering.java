package com.awarematics.vGroupMining.algorithm.clustering.idensityClustering;

import java.util.ArrayList;

import com.awarematics.vGroupMining.type.FOV;

public interface IDensityClustering {

	public ArrayList<ArrayList<FOV>> apply(ArrayList<FOV> fovArray, int minPts, double eps, double areaInter);


}
