package com.awarematics.vGroupMining.transform;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

import com.awarematics.vGroupMining.type.FOV;

public class GPSHelper {

	public final double PI = 3.14159265358979324;

	private final static double EARTH_RADIUS = 6378137;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	public static double getDistancePtoP(double lon1, double lat1, double lon2, double lat2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lon1) - rad(lon2);
		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;

		return s;
	}

	public double getDistPtoP(double x1, double y1, double x2, double y2) {

		double dist = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
		return dist;

	}

	public double getDistXY(FOV f1, FOV f2) {
		double dist = Math.sqrt(Math.pow((f1.getLatitude() - f2.getLatitude()), 2)
				+ Math.pow((f1.getLongitude() - f2.getLongitude()), 2));
		return dist;
	}

	public static String getDistance2(FOV f1, FOV f2) {
		GlobalCoordinates source = new GlobalCoordinates(f1.getLatitude(), f1.getLongitude());
		GlobalCoordinates target = new GlobalCoordinates(f2.getLatitude(), f2.getLongitude());
		GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.Sphere, source, target);
		double distance = geoCurve.getEllipsoidalDistance();
		BigDecimal distanceBig = new BigDecimal(distance).setScale(2, RoundingMode.UP);
		distanceBig = distanceBig.multiply(new BigDecimal("0.001")).setScale(2, RoundingMode.UP);
		return distanceBig.toString().concat("km");
	}

	public static double getDistance(FOV f1, FOV f2) {
		double radLat1 = rad(f1.getLatitude());
		double radLat2 = rad(f2.getLatitude());
		double a = radLat1 - radLat2;
		double b = rad(f1.getLongitude()) - rad(f2.getLongitude());
		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		return s;
	}

}
