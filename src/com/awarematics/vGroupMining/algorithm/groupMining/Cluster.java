package com.awarematics.vGroupMining.algorithm.groupMining;

public class Cluster {
	public int clusterID;
	public int snapID;
	public int[] videoIDArr;

	public Cluster() {
		super();
	}

	public Cluster(int clusterID, int videoID, int[] videoIDArr, int snapID) {
		super();
		this.clusterID = clusterID;
		this.snapID = snapID;
		this.videoIDArr = videoIDArr;
	}

	public int getClusterID() {
		return clusterID;
	}

	public void setClusterID(int clusterID) {
		this.clusterID = clusterID;
	}

	public int getsnapID() {
		return snapID;
	}

	public void setsnapID(int snapID) {
		this.snapID = snapID;
	}

	public int[] getVideoIDArr() {
		return videoIDArr;
	}

	public void setVideoIDArr(int[] videoIDArr) {
		this.videoIDArr = videoIDArr;
	}

}
