package com.awarematics.vGroupMining.transform;

import java.util.ArrayList;
import java.util.Collections;
import com.awarematics.vGroupMining.type.FOV;

public class GetMapboundary {

	public static double[] getboundary(ArrayList<FOV> snap) {

		double[] boundary = new double[4];
		double[] latitude = new double[2];
		double[] longitude = new double[2];
		latitude = GetMapboundary.get_X(snap);
		longitude = GetMapboundary.get_Y(snap);
		boundary[0] = latitude[0];
		boundary[1] = latitude[1];
		boundary[2] = longitude[0];
		boundary[3] = longitude[1];

		return boundary;
	}

	public static double[] get_X(ArrayList<FOV> snap) {

		double[] latitude = new double[2];
		ArrayList<Double> xArr = new ArrayList<Double>();
		for (int i = 0; i < snap.size(); i++) {
			xArr.add(snap.get(i).getLatitude());
		}
		latitude[0] = Collections.max(xArr);
		latitude[1] = Collections.min(xArr);
		return latitude;
	}

	public static double[] get_Y(ArrayList<FOV> snap) {

		double[] longitude = new double[2];
		ArrayList<Double> xArr = new ArrayList<Double>();
		for (int i = 0; i < snap.size(); i++) {
			xArr.add(snap.get(i).getLongitude());
		}
		longitude[0] = Collections.max(xArr);
		longitude[1] = Collections.min(xArr);
		return longitude;
	}

}
