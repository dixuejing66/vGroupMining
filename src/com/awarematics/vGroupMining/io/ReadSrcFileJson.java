package com.awarematics.vGroupMining.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import com.awarematics.vGroupMining.type.MAngle;
import com.awarematics.vGroupMining.type.MDirection;
import com.awarematics.vGroupMining.type.MDistance;
import com.awarematics.vGroupMining.type.MPoint;
import com.awarematics.vGroupMining.type.MSpeed;
import com.awarematics.vGroupMining.type.MTime;
import com.awarematics.vGroupMining.type.MVideo;

public class ReadSrcFileJson {
	public ArrayList<MVideo> readJsonFile(String fileDir) throws IOException {
		ArrayList<MVideo> gvArray = new ArrayList<MVideo>();
		ArrayList<File> fileList = new ArrayList<File>();
		File file = new File(fileDir);
		File[] files = file.listFiles();
		if (files == null) {
			return null;
		}

		for (File f : files) {
			if (f.isFile() && f.getName().endsWith(".json")) {
				fileList.add(f);
			} else if (f.isDirectory()) {
				readJsonFile(f.getAbsolutePath());
			}
		}

		/*
		 * read "locations" data
		 */
		for (int i = 0; i < fileList.size(); i++) {
			String content = FileUtils.readFileToString(fileList.get(i), "UTF-8");
			JSONObject jsonObject = new JSONObject(content);
			JSONArray getJsonArray = jsonObject.getJSONArray("locations");
			int num = getJsonArray.length();

			double[] latitude = new double[num];
			double[] longitude = new double[num];
			MPoint[] coordinateArray = new MPoint[num];
			long[] time = new long[num];
			MTime[] timeArray = new MTime[num];
			double[] speed = new double[num];
			MSpeed[] speedArray = new MSpeed[num];
			double[] direction = new double[num];
			MDirection[] directionArray = new MDirection[num];
			MDistance[] distArray = new MDistance[num];
			MAngle[] hAngle = new MAngle[num];

			for (int x = 0; x < num; x++) {
				distArray[x] = new MDistance();
				distArray[x].distance = 50;
				hAngle[x] = new MAngle();
				hAngle[x].hAngle = 120;
			}

			for (int j = 0; j < num; j++) {
				String[] location = getJsonArray.get(j).toString().split(":");
				String location_x = location[1].split(",")[0];
				String location_y = location[6].split(",")[0].replace("}", "");
				String location_time = location[5].split(",")[0];
				String location_speed = location[4].split(",")[0];
				String location_direction = location[2].split(",")[0];

				latitude[j] = Double.valueOf(location_x);
				longitude[j] = Double.valueOf(location_y);
				coordinateArray[j] = new MPoint();
				coordinateArray[j].x = latitude[j];
				coordinateArray[j].y = longitude[j];

				time[j] = Long.valueOf(location_time);
				timeArray[j] = new MTime();
				timeArray[j].time = time[j];

				speed[j] = Double.valueOf(location_speed);
				speedArray[j] = new MSpeed();
				speedArray[j].speed = speed[j];

				direction[j] = Double.valueOf(location_direction);
				directionArray[j] = new MDirection();
				directionArray[j].direction = direction[j];

			}
			MVideo gv = new MVideo();
			gv.setGvTime(timeArray);
			gv.setGvPoints(coordinateArray);
			gv.setGvSpeed(speedArray);
			gv.setGvDirection(directionArray);
			gv.setGvdist(distArray);
			gv.sethAngle(hAngle);
			gvArray.add(gv);
		}
		return gvArray;
	}
}
