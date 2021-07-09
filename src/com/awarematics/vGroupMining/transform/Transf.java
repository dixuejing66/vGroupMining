package com.awarematics.vGroupMining.transform;

import java.text.DecimalFormat;
import java.util.Random;

public class Transf {

	public static double pi = 3.1415926535897932384626;
	public static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
	public static double a = 6378245.0;
	public static double ee = 0.00669342162296594323;

	public static double transformLat(double x, double y) {
		double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
		return ret;
	}

	public static double transformLon(double x, double y) {
		double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
		return ret;
	}

	public static double[] GaussToBL(double X, double Y) {
		double[] output = new double[2];
		double longitude1, latitude1, longitude0, X0, Y0, xval, yval;

		double e1, e2, f, a, ee, NN, T, C, M, D, R, u, fai, iPI;
		iPI = 3.1415926535898 / 180.0;
		a = 6378137.0;
		f = 1 / 298.257222101;
		longitude0 = 114.0;
		longitude0 = longitude0 * iPI;

		X0 = 500000L;
		Y0 = 0;
		xval = X - X0;
		yval = Y - Y0;
		e2 = 2 * f - f * f;
		e1 = (1.0 - Math.sqrt(1 - e2)) / (1.0 + Math.sqrt(1 - e2));
		ee = e2 / (1 - e2);
		M = yval;
		u = M / (a * (1 - e2 / 4 - 3 * e2 * e2 / 64 - 5 * e2 * e2 * e2 / 256));
		fai = u + (3 * e1 / 2 - 27 * e1 * e1 * e1 / 32) * Math.sin(2 * u)
				+ (21 * e1 * e1 / 16 - 55 * e1 * e1 * e1 * e1 / 32) * Math.sin(4 * u)
				+ (151 * e1 * e1 * e1 / 96) * Math.sin(6 * u) + (1097 * e1 * e1 * e1 * e1 / 512) * Math.sin(8 * u);
		C = ee * Math.cos(fai) * Math.cos(fai);
		T = Math.tan(fai) * Math.tan(fai);
		NN = a / Math.sqrt(1.0 - e2 * Math.sin(fai) * Math.sin(fai));
		R = a * (1 - e2) / Math.sqrt((1 - e2 * Math.sin(fai) * Math.sin(fai)) * (1 - e2 * Math.sin(fai) * Math.sin(fai))
				* (1 - e2 * Math.sin(fai) * Math.sin(fai)));
		D = xval / NN;

		longitude1 = longitude0 + (D - (1 + 2 * T + C) * D * D * D / 6
				+ (5 - 2 * C + 28 * T - 3 * C * C + 8 * ee + 24 * T * T) * D * D * D * D * D / 120) / Math.cos(fai);
		latitude1 = fai
				- (NN * Math.tan(fai) / R) * (D * D / 2 - (5 + 3 * T + 10 * C - 4 * C * C - 9 * ee) * D * D * D * D / 24
						+ (61 + 90 * T + 298 * C + 45 * T * T - 256 * ee - 3 * C * C) * D * D * D * D * D * D / 720);

		output[0] = longitude1 / iPI;
		output[1] = latitude1 / iPI;
		return output;
	}

	public static void main(String[] args) {
		double[] output = new double[2];
		double x = 34873;
		double y = -17618;
		output = GaussToBL(x, y);
		System.out.println(output[0] + " " + output[1]);
	}

	public static String generateDire() {
		DecimalFormat dcmFmt = new DecimalFormat("0.00");
		Random rand = new Random();
		double dirTemp = rand.nextDouble() * 360;
		String direction = dcmFmt.format(dirTemp);
		return direction;
	}

}
