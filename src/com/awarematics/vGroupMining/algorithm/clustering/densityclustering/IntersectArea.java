package com.awarematics.vGroupMining.algorithm.clustering.densityclustering;

import java.util.ArrayList;

import org.locationtech.jts.geom.Geometry;

import com.awarematics.vGroupMining.type.FOV;
import com.awarematics.vGroupMining.type.MPolygon;

public class IntersectArea {
	/*
	 * judgeInterArea naive method
	 */
	public ArrayList<FOV> judgeInterArea(FOV f, ArrayList<FOV> cluster, double areaInter,ArrayList<FOV> fovArray) {

		MPolygon mPolygon = new MPolygon();
		IntersectArea inter = new IntersectArea();
		Geometry g1 = mPolygon.creatPolygon(f);

		for (int j = 0; j < cluster.size(); j++) {
			FOV f2 = cluster.get(j);
			Geometry g2 = mPolygon.creatPolygon(f2);
			try {
				if (g1.intersects(g2) || inter.polygonContainsPolygon(g1) || inter.polygonContainsPolygon(g2)) {
					// calculate intersection Area
					Geometry intersect = g1.intersection(g2);
					Geometry union = g1.union(g2);
					// Compute the percentage of the area of the two FOVS that intersect the area
					double caw = intersect.getArea() / union.getArea();
					if (caw < areaInter) {
						cluster.remove(f2);
						j--;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cluster;
	}

	public boolean isContainSelf(Geometry geometry) {
		return geometry.contains(geometry);
	}

	public boolean polygonContainsPolygon(Geometry geometry) throws Exception {
		IntersectArea inter = new IntersectArea();

		boolean result = inter.isContainSelf(geometry);
		return result;
	}

//	/*
//	 * judgeInterArea optimization method
//	 */
//	public ArrayList<FOV> judgeInterAreaOp(ArrayList<FOV> cluster, double areaInter) {
//		/*
//		 * first case
//		 */
//		DensityCluster dc = new DensityCluster();
//		GPSHelper gps = new GPSHelper();
//		MPolygon mPolygon = new MPolygon();
//		for (int i = 0; i < cluster.size(); i++) {
//			FOV f1 = cluster.get(i);
//			Geometry g1 = mPolygon.creatPolygon(f1);
//			for (int j = i + 1; j < cluster.size(); j++) {
//				FOV f2 = cluster.get(j);
//				Geometry g2 = mPolygon.creatPolygon(f2);
//
//				double caseDir1 = Math.abs(f1.getDirection() + f2.getDirection()) - 360;
//				double caseDir2 = Math.abs(f1.getDirection() - f2.getDirection());
//				double caseDir3 = Math.abs(f1.getDirection() - f2.getDirection());
//				if (caseDir1 > 90 && caseDir1 < 270) {
//					if (f1.getDirection() < f2.getDirection()) {
//
//						// judge the position between two fovs
//						if ((f1.getLatitude() < f2.getLatitude()) || (f1.getLongitude() > f2.getLongitude())) {
//							// view directions be intersected
//							if (dc.getDistance(f1, f2) < (f1.getVeiwDist() + f2.getVeiwDist())) {
//								// calculate intersection Area
//								Geometry intersect = g1.intersection(g2);
//								Geometry union = g1.union(g2);
//
//								// Compute the percentage of the area of the two FOVS that intersect the area
//								double caw = intersect.getArea() / union.getArea();
//								if (caw < areaInter) {
//									cluster.remove(f2);
//								}
//							}
//						}
//					}
//				}
//				/*
//				 * Second Case
//				 */
//				// same view direction
//				else if (caseDir2 == 0) {
//					if ((dc.getDistance(f1, f2) < f1.getVeiwDist()) || (dc.getDistance(f1, f2) < f2.getVeiwDist())) {
//						// calculate intersection Area
//						Geometry intersect = g1.intersection(g2);
//						Geometry union = g1.union(g2);
//
//						// Compute the percentage of the area of the two FOVS that intersect the area
//						double caw = intersect.getArea() / union.getArea();
//						if (caw < areaInter) {
//							cluster.remove(f2);
//						}
//					}
//				} // view in the opposite direction
//				else if (caseDir3 == 180) {
//					double dist = gps.getDistancePtoP(f1.getPolygon().getD().getY(), f1.getPolygon().getD().getX(),
//							f2.getLongitude(), f2.getLongitude());
//					if ((dist > 0 && dist < f2.getVeiwDist()) || (dist > 0 && dist == f2.getVeiwDist())) {
//						boolean isInside = f2.getPolygon().contains(f1.getPolygon().getD().getX(),
//								f1.getPolygon().getD().getY());
//						if (isInside) {
//							return cluster;
//						} else {
//							cluster.remove(f2);
//						}
//
//					} else {
//						cluster.remove(f2);
//					}
//				} else {
//					cluster.remove(f2);
//				}
//			}
//		}
//		return cluster;
//	}

}