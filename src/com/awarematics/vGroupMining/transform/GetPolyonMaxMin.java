/**
 * 
 */
package com.awarematics.vGroupMining.transform;

import java.util.ArrayList;
import java.util.Collections;
import com.awarematics.vGroupMining.algorithm.clustering.gridclustering.GridProperty;
import com.awarematics.vGroupMining.type.FOV;
import com.awarematics.vGroupMining.type.MPolygon;

/**
 * @author DI
 *
 */

public class GetPolyonMaxMin {
	GridProperty grid = new GridProperty();

	public double[] getMapBoundary(ArrayList<ArrayList<FOV>> snaparr) {

		double[] mapBoundary = new double[4];

		for (ArrayList<FOV> snap : snaparr) {
			for (FOV f : snap) {
				GetPolyonMaxMin.getPolyonBound(f.getPolygon(), grid);
			}
		}
		mapBoundary[0] = grid.getMaxLatitude();
		mapBoundary[1] = grid.getMinLatitude();
		mapBoundary[2] = grid.getMaxLongitude();
		mapBoundary[3] = grid.getMinLongitude();
		return mapBoundary;
	}

	public static double[] getPolyonBound(MPolygon polygon, GridProperty grid) {
		double[] box = new double[4];
		ArrayList<Double> latArr = new ArrayList<Double>();
		ArrayList<Double> logArr = new ArrayList<Double>();
		double location_X = Double.valueOf(polygon.getLocation().getX()),
				location_Y = Double.valueOf(polygon.getLocation().getY());
		double p1_X = Double.valueOf(polygon.getP1().getX()), p1_Y = Double.valueOf(polygon.getP1().getY());
		double p2_X = Double.valueOf(polygon.getP2().getX()), p2_Y = Double.valueOf(polygon.getP2().getY());
		double D_X = Double.valueOf(polygon.getD().getX()), D_Y = Double.valueOf(polygon.getD().getY());
		double p3_X = Double.valueOf(polygon.getP3().getX()), p3_Y = Double.valueOf(polygon.getP3().getY());
		double p4_X = Double.valueOf(polygon.getP4().getX()), p4_Y = Double.valueOf(polygon.getP4().getY());
		latArr.add(location_X);
		latArr.add(p1_X);
		latArr.add(p2_X);
		latArr.add(D_X);
		latArr.add(p3_X);
		latArr.add(p4_X);

		logArr.add(location_Y);
		logArr.add(p1_Y);
		logArr.add(p2_Y);
		logArr.add(D_Y);
		logArr.add(p3_Y);
		logArr.add(p4_Y);

		box[0] = Collections.max(latArr);
		box[1] = Collections.min(latArr);
		box[2] = Collections.max(logArr);
		box[3] = Collections.min(logArr);

		if (Collections.max(latArr) >= grid.getMaxLatitude()) {
			grid.MaxLatitude = Collections.max(latArr);
		} else if (Collections.min(latArr) < grid.getMinLatitude()) {
			grid.MinLatitude = Collections.min(latArr);
		}
		if (Collections.max(logArr) >= grid.getMaxLongitude()) {
			grid.MaxLongitude = Collections.max(logArr);
		} else if (Collections.min(logArr) < grid.getMinLongitude()) {
			grid.MinLongitude = Collections.min(logArr);
		}
		return box;
	}

}
