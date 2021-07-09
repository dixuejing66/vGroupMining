package com.awarematics.vGroupMining.algorithm.snapshotGenerate;

import java.util.ArrayList;
import java.util.Iterator;

import com.awarematics.vGroupMining.transform.GPSHelper;
import com.awarematics.vGroupMining.type.MAngle;
import com.awarematics.vGroupMining.type.MDirection;
import com.awarematics.vGroupMining.type.MDistance;
import com.awarematics.vGroupMining.type.MPoint;
import com.awarematics.vGroupMining.type.MSpeed;
import com.awarematics.vGroupMining.type.MTime;
import com.awarematics.vGroupMining.type.MVideo;
import com.awarematics.vGroupMining.type.Snapshot;

public class SnapshotGenerate {

	public ArrayList<Snapshot> snapshot(ArrayList<MVideo> gvArray, long instant) {
		GPSHelper gps = new GPSHelper();
		ArrayList<Snapshot> snArray = new ArrayList<Snapshot>();

		Iterator<MVideo> it = gvArray.iterator();
		while (it.hasNext()) {
			MVideo gv = new MVideo();
			Snapshot sn = new Snapshot();
			MVideo tempObj = it.next();
			gv.setGvPoints(tempObj.gvPoints);
			gv.setGvTime(tempObj.gvTime);
			gv.setGvSpeed(tempObj.gvSpeed);
			gv.setGvDirection(tempObj.gvDirection);
			gv.setGvdist(tempObj.gvdist);
			gv.sethAngle(tempObj.hAngle);

			MTime[] creationTime = gv.getGvTime();
			MPoint[] creationPoint = gv.getGvPoints();
			MDirection[] creationDirection = gv.getGvDirection();
			MSpeed[] creationSpeed = gv.getGvSpeed();

			if (creationTime == null) {
				continue;
			} else if ((creationTime.length > 0) && ((instant < 0)
					|| (instant >= (creationTime[creationTime.length - 1].getTime() - creationTime[0].getTime())))) {
				continue;
			} else if (creationTime.length == 0 || creationTime.length < (instant / 1000)) {
				continue;
			} else {

				double startPosition_x = 0;
				double startPosition_y = 0;
				double startCourse = 0;
				double startSpeed = 0;
				double endPosition_x = 0;
				double endPosition_y = 0;
				double endCourse = 0;
				double endSpeed = 0;

				long startTime = creationTime[0].getTime();

				long endTime = creationTime[creationTime.length - 1].getTime();
				long duration = endTime - startTime;
				int times = (int) (duration / instant);

				long[] tsArray = new long[times];
				double[] scArray = new double[times];
				MPoint[] result = new MPoint[times];
				MTime[] timeStampArray = new MTime[times];
				MDirection[] stampDirection = new MDirection[times];
				MDistance[] distArray = new MDistance[times];
				MAngle[] hAngle = new MAngle[times];
				
				long stamp = 0;

				if (0 < instant && instant <= duration) {

					for (int j = 0; j < times; j++) {

						for (int i = 0; i < creationTime.length; i++) {
							stamp = (instant * (j + 1) + creationTime[0].getTime());
							if (stamp == creationTime[i].getTime()) {
								result[j] = new MPoint();
								result[j].x = creationPoint[i].getX();
								result[j].y = creationPoint[i].getY();

								tsArray[j] = creationTime[i].getTime();
								timeStampArray[j] = new MTime();
								timeStampArray[j].time = tsArray[j];

								scArray[j] = creationDirection[i].getDirection();
								stampDirection[j] = new MDirection();
								stampDirection[j].direction = creationDirection[i].getDirection();

								distArray[j] = new MDistance();
								distArray[j].distance = 50;

								hAngle[j] = new MAngle();
								hAngle[j].hAngle = 120;

								break;
							}
						}
						if (result[j] != null) {
							sn.setCoordinate(result);
							sn.setTimeStamp(timeStampArray);
							sn.setDirection(stampDirection);
							sn.setDistance(distArray);
							sn.sethAngle(hAngle);
							continue;
						} else {
							int a = (int) ((stamp - creationTime[0].getTime()) / 1000);
							int b = (int) (((stamp - creationTime[0].getTime()) / 1000) + 1);

							if (b < creationTime.length) {
								long startTimeSeg = creationTime[a].getTime();
								long endTimeSeg = creationTime[b].getTime();

								if (startTimeSeg == creationTime[a].getTime()) {
									startPosition_x = creationPoint[a].getX();
									startPosition_y = creationPoint[a].getY();
									startCourse = creationDirection[a].getDirection();
									startSpeed = creationSpeed[a].getSpeed();
								}
								if (endTimeSeg == creationTime[b].getTime()) {
									endPosition_x = creationPoint[b].getX();
									endPosition_y = creationPoint[b].getY();
									endCourse = creationDirection[b].getDirection();
									endSpeed = creationSpeed[b].getSpeed();
								}

								double accelerate = 0;
								accelerate = (endSpeed - startSpeed);
								double distseg = startSpeed * ((stamp - startTimeSeg) / 1000) + (accelerate
										* ((stamp - startTimeSeg) / 1000) * ((stamp - startTimeSeg) / 1000)) / 2;
								double dist = gps.getDistancePtoP(startPosition_y, startPosition_x, endPosition_y,
										endPosition_x);
								result[j] = new MPoint();
								result[j].x = distseg / dist * endPosition_x + startPosition_x;
								result[j].y = distseg / dist * endPosition_y + startPosition_y;

								tsArray[j] = stamp;
								timeStampArray[j] = new MTime();
								timeStampArray[j].time = tsArray[j];

//								result[j] = new MPoint();
//								result[j].x = ((endPosition_x - startPosition_x) / (endTimeSeg - startTimeSeg)
//										* (stamp - startTimeSeg)) + startPosition_x;
//								result[j].y = ((endPosition_y - startPosition_y) / (endTimeSeg - startTimeSeg)
//										* (stamp - startTimeSeg)) + startPosition_y;
//
//								tsArray[j] = stamp;
//								timeStampArray[j] = new MTime();
//								timeStampArray[j].time = tsArray[j];

								double courseDiff = endCourse - startCourse;
								if (courseDiff > 30) {
									scArray[j] = courseDiff * ((stamp - startTimeSeg) / 1000);
									stampDirection[j] = new MDirection();
									stampDirection[j].direction = scArray[j];
								} else {

									stampDirection[j] = new MDirection();
									stampDirection[j].direction = startCourse;
								}

								distArray[j] = new MDistance();
								distArray[j].distance = 50;

								hAngle[j] = new MAngle();
								hAngle[j].hAngle = 120;

							} else {
								break;
							}
						}
						sn.setCoordinate(result);
						sn.setTimeStamp(timeStampArray);
						sn.setDirection(stampDirection);
						sn.setDistance(distArray);
						sn.sethAngle(hAngle);
					}
				}
				snArray.add(sn);
				Iterator<Snapshot> itsn = snArray.iterator();
				while (itsn.hasNext()) {
					Snapshot s = itsn.next();
					if (s.timeStamp == null) {
						itsn.remove();
					}
				}

			}
			/*
			 * filer the snapshot
			 */
			Iterator<Snapshot> itsn = snArray.iterator();
			while (itsn.hasNext()) {
				Snapshot s = itsn.next();
//				if(s.coordinate.length!=41) {//1000ms
				if (s.coordinate.length != 10) { // 4000ms
//				if(s.coordinate.length!=8) {	//4500ms
//					if(s.coordinate.length!=26) { //1500ms
//				if(s.coordinate.length!=120) { //333ms
//				if (s.coordinate.length != 5714) { // 7ms
				}
			}
		}
		return snArray;
	}
}
