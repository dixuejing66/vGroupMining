package com.awarematics.vGroupMining.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.awarematics.vGroupMining.type.Snapshot;

public class WriteSnapToCsvOneFile {
	public void snapToCsvFile(ArrayList<Snapshot> snapArr, String destFilePath, String destFileName)
			throws IOException {
		Snapshot snap = new Snapshot();

		Iterator<Snapshot> itsnap = snapArr.iterator();
		while (itsnap.hasNext()) {
			Snapshot tempSnap = itsnap.next();
			snap.setCoordinate(tempSnap.coordinate);
			snap.setTimeStamp(tempSnap.timeStamp);
			snap.setDirection(tempSnap.direction);
			snap.setDistance(tempSnap.distance);
		}
		File writePath = new File(destFilePath + File.separator + destFileName);
		writePath.createNewFile();
		BufferedWriter br = new BufferedWriter(new FileWriter(writePath));
		String head = "VideoSnapshot (instant=7ms)";
		br.write(head);
		br.newLine();
		for (int i = 0; i < snap.getTimeStamp().length; i++) {

			String[] headArr = { "times", "VideoID", "latitude", "longitude", "hAngle", "distance", "direction",
					"timestamp", "" };

			br.write(String.join(",", headArr));
			br.flush();
		}
		br.newLine();
		for (int j = 0; j < snapArr.size(); j++) {
			for (int i = 0; i < snap.getTimeStamp().length; i++) {
				Snapshot s = snapArr.get(j);
				if (s.getTimeStamp()[i] == null) {
					continue;
				}
				if ((s.getTimeStamp().length > i)) {
					br.write(((i + 1) + "," + (j + 1) + "," + String.valueOf(s.getCoordinate()[i].getX()) + ","
							+ String.valueOf(s.getCoordinate()[i].getY()) + ","
							+ String.valueOf(s.gethAngle()[i].gethAngle()) + ","
							+ String.valueOf(s.getDistance()[i].getDistance()) + ","
							+ String.valueOf(s.getDirection()[i].getDirection()) + ","
							+ Long.toString(s.getTimeStamp()[i].getTime())) + ",");
				} else {
					break;
				}
			}
			br.newLine();
			br.flush();
		}

		br.close();
	}
}
