package com.awarematics.vGroupMining.type;



public class Snapshot {

	private int videoID;
	private double latitude;
	private double longitude;
	public MPoint[] coordinate;
	public MTime[] timeStamp;
	public MDirection[] direction;
	public MDistance[] distance;
	public MAngle[] hAngle;

	/**
	 * 
	 */
	public Snapshot() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param videoID
	 * @param latitude
	 * @param longitude
	 * @param coordinate
	 * @param timeStamp
	 * @param direction
	 * @param distance
	 * @param hAngle
	 */
	public Snapshot(int videoID, double latitude, double longitude, MPoint[] coordinate, MTime[] timeStamp,
			MDirection[] direction, MDistance[] distance, MAngle[] hAngle) {
		super();
		this.videoID = videoID;
		this.latitude = latitude;
		this.longitude = longitude;
		this.coordinate = coordinate;
		this.timeStamp = timeStamp;
		this.direction = direction;
		this.distance = distance;
		this.hAngle = hAngle;
	}

	public int getVideoID() {
		return videoID;
	}

	public void setVideoID(int videoID) {
		this.videoID = videoID;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public MPoint[] getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(MPoint[] coordinate) {
		this.coordinate = coordinate;
	}

	public MTime[] getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(MTime[] timeStamp) {
		this.timeStamp = timeStamp;
	}

	public MDirection[] getDirection() {
		return direction;
	}

	public void setDirection(MDirection[] direction) {
		this.direction = direction;
	}

	public MDistance[] getDistance() {
		return distance;
	}

	public void setDistance(MDistance[] distance) {
		this.distance = distance;
	}

	public MAngle[] gethAngle() {
		return hAngle;
	}

	public void sethAngle(MAngle[] hAngle) {
		this.hAngle = hAngle;
	}

	public int coordinateLength() {
		return coordinate.length;
	}

	public int timeStampLength() {
		return timeStamp.length;
	}

	public int directionLength() {
		return direction.length;
	}

	public String toRow() {
		return String.format("%s,%s,%s,%s,%s", this.videoID, this.latitude, this.longitude, this.direction,
				this.timeStamp);
	}
}
