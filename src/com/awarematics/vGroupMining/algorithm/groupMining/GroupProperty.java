package com.awarematics.vGroupMining.algorithm.groupMining;

import java.util.ArrayList;

public class GroupProperty {

	public ArrayList<Integer> snapshotNum = new ArrayList<Integer>();
	public ArrayList<Integer> videoID = new ArrayList<Integer>();

	public GroupProperty() {
		super();

	}

	public GroupProperty(ArrayList<Integer> snapshotNum, ArrayList<Integer> videoID) {
		super();
		this.snapshotNum = snapshotNum;
		this.videoID = videoID;
	}

	public ArrayList<Integer> getSnapshotNum() {
		return snapshotNum;
	}

	public void setSnapshotNum(ArrayList<Integer> snapshotNum) {
		this.snapshotNum = snapshotNum;
	}

	public ArrayList<Integer> getVideoID() {
		return videoID;
	}

	public void setVideoID(ArrayList<Integer> videoID) {
		this.videoID = videoID;
	}

}
