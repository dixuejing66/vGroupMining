package com.awarematics.vGroupMining.type;

public class MPoint implements Comparable<MPoint> {
	public double x;
	public double y;
	public double z;
	public MPoint() {

	}

	public MPoint(double x, double y,double z) {
		this.x = x;
		this.y = y;
		this.z =z;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public String toText() {
		return x + "\t" + y;
	}

	public double calculateDist(double x1, double y1, double x2, double y2) {

		double dist = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));

		return dist;
	}

	public MPoint getCoords() {
		double x = this.getX();
		double y = this.getY();

		// TODO Auto-generated method stub
		return new MPoint(x, y,z);
	}

	@Override
	public int compareTo(MPoint p) {

		return this.x > p.x ? -1 : (this.x == p.x ? 0 : 1);
	}

}
