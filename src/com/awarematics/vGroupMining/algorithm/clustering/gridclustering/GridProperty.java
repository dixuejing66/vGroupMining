package com.awarematics.vGroupMining.algorithm.clustering.gridclustering;

/**
 * @author DI
 *
 */
public class GridProperty {

	public double MaxLatitude;
	public double MinLatitude;
	public double MaxLongitude;
	public double MinLongitude;
	public double cellWidth;// width of each cell
//	public double StartLatitude = 0;//
//	public double StartLongitude = 0;//

	public int row_Num;
	public int coulmn_Num;

	public GridProperty() {

	}

	public double getMaxLatitude() {
		return MaxLatitude;
	}

	public void setMaxLatitude(double maxLatitude) {
		MaxLatitude = maxLatitude;
	}

	public double getMinLatitude() {
		return MinLatitude;
	}

	public void setMinLatitude(double minLatitude) {
		MinLatitude = minLatitude;
	}

	public double getMaxLongitude() {
		return MaxLongitude;
	}

	public void setMaxLongitude(double maxLongitude) {
		MaxLongitude = maxLongitude;
	}

	public double getMinLongitude() {
		return MinLongitude;
	}

	public void setMinLongitude(double minLongitude) {
		MinLongitude = minLongitude;
	}

	public double getCellWidth() {
		return cellWidth;
	}

	public void setCellWidth(double cellWidth) {
		this.cellWidth = cellWidth;
	}

//	public double getStartLatitude() {
//		return StartLatitude;
//	}
//
//	public void setStartLatitude(double startLatitude) {
//		StartLatitude = startLatitude;
//	}
//
//	public double getStartLongitude() {
//		return StartLongitude;
//	}
//
//	public void setStartLongitude(double startLongitude) {
//		StartLongitude = startLongitude;
//	}

	public int getRow_Num() {
		return row_Num;
	}

	public void setRow_Num(int row_Num) {
		this.row_Num = row_Num;
	}

	public int getCoulmn_Num() {
		return coulmn_Num;
	}

	public void setCoulmn_Num(int coulmn_Num) {
		this.coulmn_Num = coulmn_Num;
	}

}
