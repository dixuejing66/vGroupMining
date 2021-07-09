package com.awarematics.vGroupMining.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.awarematics.vGroupMining.transform.Transf;
import com.awarematics.vGroupMining.type.FOV;
import com.awarematics.vGroupMining.type.MPoint;

public class ReadShoppingSrcfile {

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

		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new FileReader(fileList.get(0)));
		String line;

		while ((line = reader.readLine()) != null) {

			FOV fov = new FOV();

			String tempTime = line.split(",")[0];
			double temptime = Math.round(Double.valueOf(tempTime)) * 1000;
			long time = Double.valueOf(temptime).longValue();

			String tempId = line.split(",")[1];
			int id = Integer.valueOf(tempId);

			if (!snapArray.isEmpty()) {

				for (int i = 0; i < snapArray.size(); i++) {
					int fovArrNum = 0;

					if (i == snapArray.size() - 1 && snapArray.get(i).get(0).getTimestamp() != time) {
						fovArr = new ArrayList<FOV>();
						fov = new FOV();
						fov.setTimestamp(time);
						fov.setVideoID(id);

						double x = Double.valueOf(line.split(",")[2]);
						double y = Double.valueOf(line.split(",")[3]);
						double z = Double.valueOf(line.split(",")[4]);

						MPoint p = new MPoint();
						p.setX(x);
						p.setY(y);
						p.setZ(z);

						fov.setPoint(p);

						double hAngle = 120;
						fov.sethAngle(hAngle);

						double dist = 50;
						fov.setVeiwDist(dist);

						String tempDir = Transf.generateDire();
						double dir = Double.valueOf(tempDir);
						fov.setDirection(dir);

						fovArr.add(fov);
						snapArray.add(fovArr);
						break;
					}

					if (snapArray.get(i).get(fovArrNum).getTimestamp() == time) {

						fov.setTimestamp(time);

						fov.setVideoID(id);

						double x = Double.valueOf(line.split(",")[2]);
						double y = Double.valueOf(line.split(",")[3]);
						double z = Double.valueOf(line.split(",")[4]);

						MPoint p = new MPoint();
						p.setX(x);
						p.setY(y);
						p.setZ(z);

						fov.setPoint(p);

						double hAngle = 120;
						fov.sethAngle(hAngle);

						double dist = 50;
						fov.setVeiwDist(dist);

						String tempDir = Transf.generateDire();
						double dir = Double.valueOf(tempDir);
						fov.setDirection(dir);

						fovArr.add(fov);

						break;
					} else {
						continue;
					}
				}
			}
			else {

				fov.setTimestamp(time);
				fov.setVideoID(id);

				double x = Double.valueOf(line.split(",")[2]);
				double y = Double.valueOf(line.split(",")[3]);
				double z = Double.valueOf(line.split(",")[4]);

				MPoint p = new MPoint();
				p.setX(x);
				p.setY(y);
				p.setZ(z);

				fov.setPoint(p);

				double hAngle = 120;
				fov.sethAngle(hAngle);

				double dist = 50;
				fov.setVeiwDist(dist);

				String tempDir = Transf.generateDire();
				double dir = Double.valueOf(tempDir);
				fov.setDirection(dir);

				fovArr.add(fov);
				snapArray.add(fovArr);
			}

		}
		return snapArray;
	}

}
