package com.awarematics.vGroupMining.type;


public class MVideo {

	public MPoint[] gvPoints;
	public MTime[] gvTime;
	public MSpeed[] gvSpeed;
	public MDirection[] gvDirection;
	public MDistance[] gvdist;
	public MAngle[] hAngle;
	public MVideo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param gvPoints
	 * @param gvTime
	 * @param gvSpeed
	 * @param gvDirection
	 * @param gvdist
	 * @param hAngle
	 */
	public MVideo(MPoint[] gvPoints, MTime[] gvTime, MSpeed[] gvSpeed, MDirection[] gvDirection, MDistance[] gvdist,
			MAngle[] hAngle) {
		super();
		this.gvPoints = gvPoints;
		this.gvTime = gvTime;
		this.gvSpeed = gvSpeed;
		this.gvDirection = gvDirection;
		this.gvdist = gvdist;
		this.hAngle = hAngle;
	}

	public MPoint[] getGvPoints() {
		return gvPoints;
	}

	public void setGvPoints(MPoint[] gvPoints) {
		this.gvPoints = gvPoints;
	}

	public MTime[] getGvTime() {
		return gvTime;
	}

	public void setGvTime(MTime[] gvTime) {
		this.gvTime = gvTime;
	}

	public MSpeed[] getGvSpeed() {
		return gvSpeed;
	}

	public void setGvSpeed(MSpeed[] gvSpeed) {
		this.gvSpeed = gvSpeed;
	}

	public MDirection[] getGvDirection() {
		return gvDirection;
	}

	public void setGvDirection(MDirection[] gvDirection) {
		this.gvDirection = gvDirection;
	}

	public MDistance[] getGvdist() {
		return gvdist;
	}

	public void setGvdist(MDistance[] gvdist) {
		this.gvdist = gvdist;
	}

	public MAngle[] gethAngle() {
		return hAngle;
	}

	public void sethAngle(MAngle[] hAngle) {
		this.hAngle = hAngle;
	}

	public int gvPointsLength() {
		return gvPoints.length;
	}

	public int gvTimeLength() {
		return gvTime.length;
	}

}
