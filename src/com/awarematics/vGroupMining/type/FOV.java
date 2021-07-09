package com.awarematics.vGroupMining.type;

public class FOV implements Comparable<FOV> {

	public int videoID;
	public double latitude;
	public double longitude;
	public MPoint point;
	public long timestamp;
	public double veiwDist;
	public double direction;
	public int timesID;
	public double hAngle;
	public MPolygon polygon;
	public int cellID;
	public Integer sort;

	public FOV() {
		super();
	}

	/**
	 * @param videoID
	 * @param latitude
	 * @param longitude
	 * @param piont
	 * @param timestamp
	 * @param veiwDist
	 * @param direction
	 * @param timesID
	 * @param hAngle
	 * @param polygon
	 * @param cellID
	 */
	public FOV(int videoID, double latitude, double longitude, MPoint point, long timestamp, double veiwDist,
			double direction, int timesID, double hAngle, MPolygon polygon, int cellID) {
		super();
		this.videoID = videoID;
		this.latitude = latitude;
		this.longitude = longitude;
		this.point = point;
		this.timestamp = timestamp;
		this.veiwDist = veiwDist;
		this.direction = direction;
		this.timesID = timesID;
		this.hAngle = hAngle;
		this.polygon = polygon;
		this.cellID = cellID;
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

	public MPoint getPoint() {
		return point;
	}

	public void setPoint(MPoint point) {
		this.point = point;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public double getVeiwDist() {
		return veiwDist;
	}

	public void setVeiwDist(double veiwDist) {
		this.veiwDist = veiwDist;
	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}

	public int getTimesID() {
		return timesID;
	}

	public void setTimesID(int timesID) {
		this.timesID = timesID;
	}

	public double gethAngle() {
		return hAngle;
	}

	public void sethAngle(double hAngle) {
		this.hAngle = hAngle;
	}

	public MPolygon getPolygon() {
		return polygon;
	}

	public void setPolygon(MPolygon polygon) {
		this.polygon = polygon;
	}

	public int getCellID() {
		return cellID;
	}

	public void setCellID(int cellID) {
		this.cellID = cellID;
	}

	@Override
	public int compareTo(FOV f) {
		if (this.getVideoID() > f.getVideoID()) {
			return 1;
		} else if (this.getVideoID() < f.getVideoID()) {
			return -1;
		} else {
			return 0;
		}
	}
}
