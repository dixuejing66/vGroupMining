package com.awarematics.vGroupMining.type;

import java.awt.Polygon;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;



public class MPolygon extends Polygon {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2809244512950313251L;

	MPoint location;
	MPoint p1;
	MPoint p2;
	MPoint p3;
	MPoint p4;
	MPoint D;

	/**
	 * 
	 */
	public MPolygon() {
		super();

	}

	/**
	 * @param location
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 * @param d
	 */
	public MPolygon(MPoint location, MPoint p1, MPoint p2, MPoint p3, MPoint p4, MPoint d) {
		super();
		this.location = location;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.p4 = p4;
		D = d;
	}

	public MPoint getLocation() {
		return location;
	}

	public void setLocation(MPoint location) {
		this.location = location;
	}

	public MPoint getP1() {
		return p1;
	}

	public void setP1(MPoint p1) {
		this.p1 = p1;
	}

	public MPoint getP2() {
		return p2;
	}

	public void setP2(MPoint p2) {
		this.p2 = p2;
	}

	public MPoint getP3() {
		return p3;
	}

	public void setP3(MPoint p3) {
		this.p3 = p3;
	}

	public MPoint getP4() {
		return p4;
	}

	public void setP4(MPoint p4) {
		this.p4 = p4;
	}

	public MPoint getD() {
		return D;
	}

	public void setD(MPoint d) {
		D = d;
	}

	public static double toRadian(double angle) {
		return (Math.PI / 180) * angle;
	}

	// compute the vertex for fov (for location[lng,lat])
	public MPolygon computeVertex_latLong(FOV f) {

		MPolygon mpolygon = new MPolygon();

		double radian1 = MPolygon.toRadian(450 - (f.getDirection() - f.gethAngle() / 2));
		double radian2 = MPolygon.toRadian(450 - (f.getDirection() - f.gethAngle() / 4));
		double radian3 = MPolygon.toRadian(450 - (f.getDirection() + f.gethAngle() / 4));
		double radian4 = MPolygon.toRadian(450 - (f.getDirection() + f.gethAngle() / 2));
		double radianD = MPolygon.toRadian(450 - f.getDirection());
		MPoint p1 = new MPoint();

		p1.x = f.getLatitude() + Math.cos(radian1) * (f.getVeiwDist() * 0.00001);
		p1.y = f.getLongitude() + Math.sin(radian1) * (f.getVeiwDist() * 0.00001);
		MPoint p2 = new MPoint();
		p2.x = f.getLatitude() + Math.cos(radian2) * (f.getVeiwDist() * 0.00001);
		p2.y = f.getLongitude() + Math.sin(radian2) * (f.getVeiwDist() * 0.00001);
		MPoint p3 = new MPoint();
		p3.x = f.getLatitude() + Math.cos(radian3) * (f.getVeiwDist() * 0.00001);
		p3.y = f.getLongitude() + Math.sin(radian3) * (f.getVeiwDist() * 0.00001);
		MPoint p4 = new MPoint();
		p4.x = f.getLatitude() + Math.cos(radian4) * (f.getVeiwDist() * 0.00001);
		p4.y = f.getLongitude() + Math.sin(radian4) * (f.getVeiwDist() * 0.00001);
		MPoint D = new MPoint();
		D.x = f.getLatitude() + Math.cos(radianD) * (f.getVeiwDist() * 0.00001);
		D.y = f.getLongitude() + Math.sin(radianD) * (f.getVeiwDist() * 0.00001);
		MPoint location = new MPoint();
		location.x = f.latitude;
		location.y = f.longitude;
		mpolygon.setP1(p1);
		mpolygon.setP2(p2);
		mpolygon.setP3(p2);
		mpolygon.setP4(p4);
		mpolygon.setD(D);
		mpolygon.setLocation(location);

		return mpolygon;

	}

	public double formatP(double x) {
		String tempx1 = String.format("%.3f", x);
		double value = Double.valueOf(tempx1);
		return value;
	}

	// compute the vertex for fov(for location[x,y])
	public MPolygon computeVertex_xyz(FOV f) {

		MPolygon mpolygon = new MPolygon();

		double radian1 = MPolygon.toRadian(450 - (f.getDirection() - f.gethAngle() / 2));
		double radian2 = MPolygon.toRadian(450 - (f.getDirection() - f.gethAngle() / 4));
		double radian3 = MPolygon.toRadian(450 - (f.getDirection() + f.gethAngle() / 4));
		double radian4 = MPolygon.toRadian(450 - (f.getDirection() + f.gethAngle() / 2));
		double radianD = MPolygon.toRadian(450 - f.getDirection());

		MPoint p1 = new MPoint();
		p1.x = f.getLatitude() + Math.cos(f.getVeiwDist() * (radian1 * Math.PI / 180));
		p1.y = f.getLongitude() + Math.sin(f.getVeiwDist() * (radian1 * Math.PI / 180));

		MPoint p2 = new MPoint();
		p2.x = f.getLatitude() + Math.cos(f.getVeiwDist() * (radian2 * Math.PI / 180));
		p2.y = f.getLongitude() + Math.sin(f.getVeiwDist() * (radian2 * Math.PI / 180));

		MPoint p3 = new MPoint();
		p3.x = f.getLatitude() + Math.cos(f.getVeiwDist() * (radian3 * Math.PI / 180));
		p3.y = f.getLongitude() + Math.sin(f.getVeiwDist() * (radian3 * Math.PI / 180));

		MPoint p4 = new MPoint();
		p4.x = f.getLatitude() + Math.cos(f.getVeiwDist() * (radian4 * Math.PI / 180));
		p4.y = f.getLongitude() + Math.sin(f.getVeiwDist() * (radian4 * Math.PI / 180));

		MPoint D = new MPoint();
		D.x = f.getLatitude() + Math.cos(f.getVeiwDist() * (radianD * Math.PI / 180));
		D.y = f.getLongitude() + Math.sin(f.getVeiwDist() * (radianD * Math.PI / 180));

		MPoint location = new MPoint();
		location.x = f.getLatitude();
		location.y = f.getLongitude();
		mpolygon.setP1(p1);
		mpolygon.setP2(p2);
		mpolygon.setP3(p2);
		mpolygon.setP4(p4);
		mpolygon.setD(D);
		mpolygon.setLocation(location);

		return mpolygon;

	}

	public Geometry creatPolygon(FOV f) {
		// create a geometry by specifying the coordinates directly
		Coordinate[] coordinates = new Coordinate[] {
				new Coordinate(f.getPolygon().getLocation().getX(), f.getPolygon().getLocation().getY()),
				new Coordinate(f.getPolygon().getP1().getX(), f.getPolygon().getP1().getY()),
				new Coordinate(f.getPolygon().getP2().getX(), f.getPolygon().getP2().getY()),
				new Coordinate(f.getPolygon().getP3().getX(), f.getPolygon().getP3().getY()),
				new Coordinate(f.getPolygon().getP4().getX(), f.getPolygon().getP4().getY()),
				new Coordinate(f.getPolygon().getLocation().getX(), f.getPolygon().getLocation().getY()) };
		// use the default factory, which gives full double-precision
		Geometry g1 = new GeometryFactory().createPolygon(coordinates);
		return g1;
	}

}
