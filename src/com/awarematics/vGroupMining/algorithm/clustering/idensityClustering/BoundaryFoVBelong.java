package com.awarematics.vGroupMining.algorithm.clustering.idensityClustering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.locationtech.jts.geom.Geometry;

import static java.util.stream.Collectors.toList;
import com.awarematics.vGroupMining.type.FOV;
import com.awarematics.vGroupMining.type.MPolygon;

public class BoundaryFoVBelong {
	public ArrayList<FOV> visit = null;

	public ArrayList<ArrayList<FOV>> boundaryFoVBelong(ArrayList<ArrayList<FOV>> clusters, double minPts) {
		MPolygon mPolygon = new MPolygon();
		visit = new ArrayList<FOV>();
		ArrayList<ArrayList<FOV>> clustersResult = clusters;
		for (int i = 0; i < clustersResult.size(); i++) {
			ArrayList<FOV> c_1 = clustersResult.get(i);
			for (int j = i+1; j < clustersResult.size(); j++) {
				ArrayList<FOV> c_2 = clustersResult.get(j);
				ArrayList<FOV> commonFoV = (ArrayList<FOV>) receiveCollectionList(c_1, c_2);
				boolean flag=false;
				if (commonFoV.size() > 0) {
					double[] sumArea_c1 = computOverlap(commonFoV, c_1, mPolygon);
					double[] sumArea_c2 = computOverlap(commonFoV, c_2, mPolygon);
					Set<Map<String, FOV>> mapset = compareArea(sumArea_c1, sumArea_c2, c_1, c_2, commonFoV);
					Iterator<Map<String, FOV>> it = mapset.iterator();
					while (it.hasNext()) {
						Map<String, FOV> map = it.next();
						Iterator<Entry<String, FOV>> entries = map.entrySet().iterator();
						Entry<String, FOV> entry = entries.next();
//    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
						if (entry.getKey() == "c_1") {
							ArrayList<FOV> clus_2 = clusters.get(j);
							FOV f = entry.getValue();
							clus_2.remove(f);
							if (clus_2.size() < minPts) {
								clusters.remove(j);
								if (j != 0) {
									j--;
								} else {
									j = 0;
								}

							}
						} else {
							ArrayList<FOV> clus_1 = clusters.get(i);
							FOV f = entry.getValue();
							clus_1.remove(f);
							if (clus_1.size() < minPts) {
								clusters.remove(i);
								if (i != 0) {
									i--;
									flag = true;      
							        break ;//跳出整个循环
								} else {
									i = 0;
									flag = true;      
							        break ;//跳出整个循环
								}
								
							}
						}
					}
					if(flag) {
						i--;
						break;
					}
				}
			}

		}
		return clusters;
	}

	// compute overlap squared between the common FoV with clusters
	public double[] computOverlap(ArrayList<FOV> commonFoV, ArrayList<FOV> c, MPolygon mPolygon) {
		double[] sumArea_c = new double[commonFoV.size()];
		for (int i = 0; i < commonFoV.size(); i++) {
			double sumArea = 0;
			FOV f_com = commonFoV.get(i);
			System.out.println(f_com.getVideoID());
			Geometry g1 = mPolygon.creatPolygon(f_com);
			for (FOV f : c) {
				Geometry g2 = mPolygon.creatPolygon(f);
				Geometry intersect = g1.intersection(g2);
				sumArea += intersect.getArea();
			}
			sumArea_c[i] = sumArea;
		}
		return sumArea_c;
	}

	// compare the overlap squared between the common FoV with clusters
	public Set<Map<String, FOV>> compareArea(double[] sumArea_c1, double[] sumArea_c2, ArrayList<FOV> c_1,
			ArrayList<FOV> c_2, ArrayList<FOV> commonFoV) {
		Set<Map<String, FOV>> mapSet = new HashSet<Map<String, FOV>>();
		Map<String, FOV> map = new HashMap<String, FOV>();
		for (int i = 0; i < sumArea_c1.length; i++) {
			FOV f_com = commonFoV.get(i);
			double area_c1 = sumArea_c1[i];
			for (int j = i; j < sumArea_c2.length;) {
				double area_c2 = sumArea_c2[j];
				if (area_c1 < area_c2) {
//					int fid = f_com.getVideoID();
					map.put("c_2", f_com);
					mapSet.add(map);
					map = new HashMap<String, FOV>();
				} else {
//					int fid = f_com.getVideoID();
					map.put("c_1", f_com);
					mapSet.add(map);
					map = new HashMap<String, FOV>();
				}
				break;
			}
		}
		return mapSet;
	}

	// find the common elements
	public static List<FOV> receiveCollectionList(ArrayList<FOV> firstArrayList, ArrayList<FOV> secondArrayList) {
		List<FOV> resultList = new ArrayList<FOV>();
		LinkedList<FOV> result = new LinkedList<FOV>(firstArrayList);
		HashSet<FOV> othHash = new HashSet<FOV>(secondArrayList);
		for (FOV f : othHash) {
			if (result.contains(f)) {
				resultList.add(f);
			}
		}
		return resultList;
	}
}
