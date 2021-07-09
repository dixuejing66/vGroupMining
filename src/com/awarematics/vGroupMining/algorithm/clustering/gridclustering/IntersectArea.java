package com.awarematics.vGroupMining.algorithm.clustering.gridclustering;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import org.locationtech.jts.geom.Geometry;
import com.awarematics.vGroupMining.transform.GPSHelper;
import com.awarematics.vGroupMining.type.FOV;
import com.awarematics.vGroupMining.type.MPolygon;

public class IntersectArea {
	/*
	 * judgeInterArea naive method
	 */
	GPSHelper gh = new GPSHelper();

	public ArrayList<Integer> judgeInterArea(FOV fov, double areaInter, LinkedList<Integer> tempRange,
			ArrayList<FOV> fovArray) {
		ArrayList<Integer> result = new ArrayList<Integer>();

		MPolygon mPolygon = new MPolygon();
		Geometry g1 = mPolygon.creatPolygon(fov);
		IntersectArea inter = new IntersectArea();
		for (int j = 0; j < tempRange.size(); j++) {
			Integer f2ID = tempRange.get(j);
			FOV f2 = fovArray.get(f2ID - 1);
			Geometry g2 = mPolygon.creatPolygon(f2);
			try {
				if (g1.intersects(g2) || inter.polygonContainsPolygon(g1) || inter.polygonContainsPolygon(g2)) {

					// calculate intersection Area
					Geometry intersect = g1.intersection(g2);
					Geometry union = g1.union(g2);
					// Compute the percentage of the area of the two FOVS that intersect the area
					double caw = intersect.getArea() / union.getArea();
					if (caw < areaInter) {
						Object index_f2 = f2.getVideoID();
						tempRange.remove(index_f2);
						j--;
					}
				}

			} catch (Exception e) {
					e.printStackTrace();
			}
		}
		Iterator<Integer> it = tempRange.iterator();
		while (it.hasNext()) {
			Integer id = it.next();
			result.add(id);
		}
		return result;
	}

	public boolean isContainSelf(Geometry geometry) {
		return geometry.contains(geometry);
	}

	public boolean polygonContainsPolygon(Geometry geometry) throws Exception {
		IntersectArea inter = new IntersectArea();
		boolean result = inter.isContainSelf(geometry);
		return result;
	}
}