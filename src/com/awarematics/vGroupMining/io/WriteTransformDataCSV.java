package com.awarematics.vGroupMining.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.awarematics.vGroupMining.type.FOV;

public class WriteTransformDataCSV {

	public void writeFile(ArrayList<ArrayList<FOV>> snapArray, String destFilePath) throws IOException {

		String destFileName = "destFileName";

		File writePath = new File(destFilePath + File.separator + destFileName);
		writePath.createNewFile();
		BufferedWriter br = new BufferedWriter(new FileWriter(writePath));
		String head = "file head";
		br.write(head);
		br.newLine();
		String[] headArr = { "VideoID", "Timestamp", "Position_X", "Position_Y", "Position_Z", "hAngle", "direction",
				"distance", "" };
		br.write(String.join(",", headArr));
		br.newLine();

		for (int i = 0; i < snapArray.size(); i++) {
			for (int j = 0; j < snapArray.get(i).size(); j++) {

				br.write(String.valueOf(j + 1) + ","
//						String.valueOf(snapArray.get(i).get(j).getVideoID()) + ","
						+ String.valueOf(snapArray.get(i).get(j).getTimestamp() + ",")
						+ String.valueOf(snapArray.get(i).get(j).getPoint().getX() + ",")
						+ String.valueOf(snapArray.get(i).get(j).getPoint().getY() + ",")
						+ String.valueOf(snapArray.get(i).get(j).getPoint().getZ() + ",")
						+ String.valueOf(snapArray.get(i).get(j).gethAngle() + ",")
						+ String.valueOf(snapArray.get(i).get(j).getDirection() + ",")
						+ String.valueOf(snapArray.get(i).get(j).getVeiwDist()) + ",");
//				+ String.valueOf(snapArray.get(i).get(j).getPolygon().getD().getX()+" "+String.valueOf(snapArray.get(i).get(j).getPolygon().getD().getY())+" " 
//						+ String.valueOf(snapArray.get(i).get(j).getPolygon().getP1().getX()+" "+String.valueOf(snapArray.get(i).get(j).getPolygon().getP1().getY())+" "
//						+ String.valueOf(snapArray.get(i).get(j).getPolygon().getP2().getX()+" "+String.valueOf(snapArray.get(i).get(j).getPolygon().getP2().getY())+" "
//						+ String.valueOf(snapArray.get(i).get(j).getPolygon().getP3().getX()+" "+String.valueOf(snapArray.get(i).get(j).getPolygon().getP3().getY())+" "
//						+ String.valueOf(snapArray.get(i).get(j).getPolygon().getP4().getX()+" "+String.valueOf(snapArray.get(i).get(j).getPolygon().getP1().getY()))))))
				br.newLine();
				br.flush();
			}

		}
		br.close();
	}
}
