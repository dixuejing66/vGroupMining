package com.awarematics.vGroupMining.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.awarematics.vGroupMining.type.FOV;
import com.awarematics.vGroupMining.type.MPolygon;

public class ReadShoppingsnap implements IDataRead {

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

				FOV fov = new FOV();
				String tempvID = line.split(",")[0];
				int videoID = Integer.valueOf(tempvID);
				fov.setVideoID(videoID);

				String tempTimes = line.split(",")[1];
				long time = Long.parseLong(tempTimes.trim());
				fov.setTimestamp(time);

				String tempLat = line.split(",")[3];
				double lat = Double.valueOf(tempLat) * 0.001;
				fov.setLatitude(lat);

				String tempLong = line.split(",")[2];
				double lon = Double.valueOf(tempLong) * 0.001;
				fov.setLongitude(lon);

				String tempAngle = line.split(",")[5];
				double hAngle = Double.valueOf(tempAngle);
				fov.sethAngle(hAngle);

				String tempDist = line.split(",")[7];
				double dist = Double.valueOf(tempDist);
				fov.setVeiwDist(dist);

				String tempDir = line.split(",")[6];
				double dir = Double.valueOf(tempDir);
				fov.setDirection(dir);

				MPolygon mp = new MPolygon().computeVertex_xyz(fov);
				fov.setPolygon(mp);

				if (!fovArr.isEmpty()) {
					if (fovArr.get(0).getTimestamp() == fov.getTimestamp()) {
						fovArr.add(fov);
					} else {
						snapArray.add(fovArr);
						fovArr = new ArrayList<FOV>();
						fovArr.add(fov);
					}
				} else {
					fovArr.add(fov);
				}

			}
			if (line == null) {
				snapArray.add(fovArr);
			}
			if (i == (fileList.size() - 1)) {
				reader.close();
			}
		}

		return snapArray;
	}
}
