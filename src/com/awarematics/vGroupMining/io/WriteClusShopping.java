package com.awarematics.vGroupMining.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.awarematics.vGroupMining.type.FOV;

public class WriteClusShopping implements IDataWrite {
	public void clusterToCsvFile(ArrayList<ArrayList<ArrayList<FOV>>> snaplist, String desPath, double areaInter, int M,
			double cellWidth, String name) throws IOException {

		for (int i = 0, len = snaplist.size(); i < len; i++) {

			String fileName = name + (i + 1) + ".csv";
			File writePath = new File(desPath + File.separator + fileName);
			writePath.createNewFile();
			BufferedWriter br = new BufferedWriter(new FileWriter(writePath));
//			String head = "GridQueryResultShopping (instant=1000ms) areaInter=" + areaInter + " M=" + M + " cellWidth="
//					+ cellWidth;
			String head = "GridQueryResultBDD (instant=1000ms) areaInter=" + areaInter + " M=" + M + " cellWidth="
					+ cellWidth;
			br.write(head);
			br.newLine();

			ArrayList<ArrayList<FOV>> snap = snaplist.get(i);
//			System.out.println("snapshot : " + (i + 1));

			String snapnum = "Snapshot" + (i + 1);
			br.write(snapnum);
			br.newLine();

//			String[] headArr = { "ClusterNum", "VideoID", "latitude", "longitude", "hAngle", "distance", "direction",
//					"timestamp", "" };//for BDD data
			String[] headArr = { "ClusterNum", "VideoID", "Position_X", "Position_Y", "hAngle", "distance", "direction",
					"timestamp", "polygon", "" };
			br.write(String.join(",", headArr));
			br.flush();

			br.newLine();

			for (int n = 0, lenSnap = snap.size(); n < lenSnap; n++) {
				ArrayList<FOV> cluster = snap.get(n);
				for (int m = 0, lenCluster = cluster.size(); m < lenCluster; m++) {
					br.write((n + 1) + "," + String.valueOf(cluster.get(m).getVideoID()) + ","
							+ String.valueOf(cluster.get(m).getLatitude() * 1000) + ","
							+ String.valueOf(cluster.get(m).getLongitude() * 1000) + ","
							+ String.valueOf(cluster.get(m).gethAngle()) + ","
							+ String.valueOf(cluster.get(m).getVeiwDist()) + ","
							+ String.valueOf(cluster.get(m).getDirection()) + ","
							+ Long.toString(cluster.get(m).getTimestamp()) + ","
							+ String.valueOf(cluster.get(m).getPolygon().getP1().getX() * 1000 + " "
									+ cluster.get(m).getPolygon().getP1().getY() * 1000)
							+ " "
							+ String.valueOf(cluster.get(m).getPolygon().getP2().getX() * 1000 + " "
									+ cluster.get(m).getPolygon().getP2().getY() * 1000)
							+ " "
							+ String.valueOf(cluster.get(m).getPolygon().getP3().getX() * 1000 + " "
									+ cluster.get(m).getPolygon().getP3().getY() * 1000)
							+ " "
							+ String.valueOf(cluster.get(m).getPolygon().getP4().getX() * 1000 + " "
									+ cluster.get(m).getPolygon().getP4().getY() * 1000)
							+ " "
							+ String.valueOf(cluster.get(m).getPolygon().getD().getX() * 1000 + " "
									+ cluster.get(m).getPolygon().getD().getY() * 1000)
							+ " " + String.valueOf(cluster.get(m).getPolygon().getLocation().getX() * 1000 + " "
									+ cluster.get(m).getPolygon().getLocation().getY() * 1000)
							+ ",");
					br.newLine();
					br.flush();
				}
			}
			br.close();
		}

	}

}
