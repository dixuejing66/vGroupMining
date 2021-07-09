package com.awarematics.vGroupMining.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.awarematics.vGroupMining.algorithm.clustering.gridclustering.GridProperty;
import com.awarematics.vGroupMining.type.FOV;
import com.awarematics.vGroupMining.type.MPolygon;

public class ReadSnap implements IDataRead {
	static GridProperty grid = new GridProperty();

	public ArrayList<ArrayList<FOV>> readSnapFile(String filePath) throws IOException {

		ArrayList<FOV> fovArr = new ArrayList<FOV>();
		ArrayList<ArrayList<FOV>> snapArray = new ArrayList<ArrayList<FOV>>();
		ArrayList<File> fileList = new ArrayList<File>();
		File file = new File(filePath);
		File[] files = file.listFiles();
		if (files == null) {
			return null;
		}

		for (File f : files) {
			if (f.isFile() && f.getName().endsWith(".csv")) {
				fileList.add(f);
			} else if (f.isDirectory()) {
				readSnapFile(f.getAbsolutePath());
			}
		}

		for (int i = 0; i < fileList.size(); i++) {
			BufferedReader reader = new BufferedReader(new FileReader(fileList.get(i)));
			reader.readLine();
			reader.readLine();
			String line;
			while ((line = reader.readLine()) != null) {
				Random r = new Random();

				FOV fov = new FOV();
				String tempTimes = line.split(",")[0];
				int timesId = Integer.valueOf(tempTimes);
				fov.setTimesID(timesId);

				String tempId = line.split(",")[1];
				int id = Integer.valueOf(tempId);
				fov.setVideoID(id);

				String tempLat = line.split(",")[2];
				double lat = Double.valueOf(tempLat.trim());
				if (!isNumeric(tempLat)) {
					double d1 = r.nextDouble() * 90;
					lat = d1;
				} else {
					if ((Double.isInfinite(lat)) || (Double.isNaN(lat) || (lat < 0) || lat > 90)) {

						double d1 = r.nextDouble() * 90;
						lat = d1;
					}
				}
				fov.setLatitude(lat);

				String tempLong = line.split(",")[3];
				double lon = Double.parseDouble(tempLong);
				if (!isNumeric(tempLong)) {
					double d2 = r.nextDouble() * 80;
					lon = d2;
				} else {
					if ((Double.isInfinite(lon)) || (Double.isNaN(lon) || (lon < -180) || (lon > 180))) {
						double d2 = r.nextDouble() * 80;
						lon = d2;
					}
					fov.setLongitude(lon);
				}

				String tempAngle = line.split(",")[4];
				double hAngle = Double.valueOf(tempAngle);
				fov.sethAngle(hAngle);

				String tempDist = line.split(",")[5];
				double dist = Double.valueOf(tempDist) * 100;
				fov.setVeiwDist(dist);

				String tempDir = line.split(",")[6];
				double dir = Double.valueOf(tempDir.trim());
				if (!isNumeric(tempDir)) {
					double d1 = r.nextDouble() * 360;
					dir = d1;
				} else {
					if (dir < 0) {
						double d3 = r.nextDouble() * 360;
						dir = d3;
					}
				}
				fov.setDirection(dir);

				String tempTime = line.split(",")[7];
				long time = Long.parseLong(tempTime.trim());
				fov.setTimestamp(time);

				MPolygon mp = new MPolygon().computeVertex_latLong(fov);
				fov.setPolygon(mp);

				fovArr.add(fov);
			}

			snapArray.add(fovArr);
			fovArr = new ArrayList<FOV>();
			if (i == (fileList.size() - 1)) {
				reader.close();
			}
		}

		return snapArray;
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

}
