package com.awarematics.vGroupMining.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import java.util.Random;

import com.awarematics.vGroupMining.type.Snapshot;

public class WriteSnapToCsvFiles {
	public void snapToCsvFile(ArrayList<Snapshot> snapArr) throws IOException {

		WriteSnapToCsvFiles wsc = new WriteSnapToCsvFiles();
		Snapshot snap = new Snapshot();

		Iterator<Snapshot> itsnap = snapArr.iterator();
		while (itsnap.hasNext()) {
			Snapshot tempSnap = itsnap.next();
			snap.setCoordinate(tempSnap.coordinate);
			snap.setTimeStamp(tempSnap.timeStamp);
			snap.setDirection(tempSnap.direction);
			snap.setDistance(tempSnap.distance);
			snap.sethAngle(tempSnap.hAngle);
		}

		for (int i = 0; i < snap.getTimeStamp().length; i++) {
//			if (snap.timeStamp[i] == null) {
//				//System.out.println("There is no data in the"+ (j+1) +"track of the"+ (i+1)+" track");
//				continue;
//			} else {
			String filePath = "D:\\PaperExperimentalData\\Test\\TEST";
			String fileName = "VideosSnapshot_" + (i + 1) + ".csv";
			File writePath = new File(filePath + File.separator + fileName);
			writePath.createNewFile();
			BufferedWriter br = new BufferedWriter(new FileWriter(writePath));
			String head = "VideoSnapshot (instant=4000ms)";
			String[] headArr = { "times", "VideoID", "latitude", "longitude", "hAngle", "distance", "direction",
					"timestamp" };
			br.write(head);
			br.newLine();
			br.write(String.join(",", headArr));
			br.newLine();

			for (int j = 0; j < snapArr.size(); j++) {
				Snapshot s = snapArr.get(j);
				if (s.getTimeStamp()[i] == null) {
					continue;
				}
				if ((s.getTimeStamp().length > i)) {

					double x = s.getCoordinate()[i].getX();
					double y = s.getCoordinate()[i].getY();
					double ang = s.gethAngle()[i].gethAngle();
					double dist = s.getDistance()[i].getDistance();
					double dir = s.getDirection()[i].getDirection();
					long time = s.getTimeStamp()[i].getTime();
					String lat = wsc.judgeLat(x, 50);
					String lon = wsc.judgeLon(y, 80);
					String direction = wsc.judgeDir(dir, 360);
					br.write(((i + 1) + "," + (j + 1) + "," + lat + "," + lon + "," + String.valueOf(ang) + ","
							+ String.valueOf(dist) + "," + direction + "," + String.valueOf(time)) + ",");
					br.newLine();
					br.flush();
				} else {
					break;
				}
			}
			br.close();
		}
	}

	public String judgeLat(double lat, int para) {
		Random r = new Random();
		if ((!Double.isFinite(lat)) || (Double.isInfinite(lat)) || (Double.isNaN(lat))) {
			double temp = r.nextDouble() * para;
			return String.valueOf(temp);
		} else if ((lat > 90) || (lat < 0)) {
			double temp = r.nextDouble() * para;
			return String.valueOf(temp);
		} else {
			String.valueOf(lat);
			return String.valueOf(lat);
		}
	}

	public String judgeLon(double lon, int para) {
		Random r = new Random();
		if ((!Double.isFinite(lon)) || (Double.isInfinite(lon)) || (Double.isNaN(lon))) {
			double temp = r.nextDouble() * para;
			return String.valueOf(temp);
		} else if ((lon > 180) || (lon < (-180))) {
			double temp = r.nextDouble() * para;
			return String.valueOf(temp);
		} else {
			String.valueOf(lon);
			return String.valueOf(lon);
		}
	}

	public String judgeDir(double dir, int para) {
		Random r = new Random();
		if ((!Double.isFinite(dir)) || (Double.isInfinite(dir)) || (Double.isNaN(dir))) {
			double temp = r.nextDouble() * para;
			return String.valueOf(temp);
		} else if (dir < 0) {
			double temp = r.nextDouble() * para;
			return String.valueOf(temp);
		} else {
			String.valueOf(dir);
			return String.valueOf(dir);
		}

	}
}
