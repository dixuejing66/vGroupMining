package com.awarematics.vGroupMining.algorithm.clustering.idensityClustering;

import java.io.IOException;
import java.util.ArrayList;
import com.awarematics.vGroupMining.io.WriteClus;
import com.awarematics.vGroupMining.type.FOV;

public interface runTime {

	public long runTimeDB(ArrayList<ArrayList<FOV>> snapArry, String desPath, int minPts, double eps, double areaInter,
			String filename)throws IOException;

	public long runTimeGrid(ArrayList<ArrayList<FOV>> snapArry, String desPath, int minPts, double eps,
			double areaInter,  String filename)throws IOException;

}
