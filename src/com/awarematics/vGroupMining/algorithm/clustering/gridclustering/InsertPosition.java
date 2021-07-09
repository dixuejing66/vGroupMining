package com.awarematics.vGroupMining.algorithm.clustering.gridclustering;

import java.util.ArrayList;
import java.util.Collections;
import com.awarematics.vGroupMining.type.FOV;

public class InsertPosition {

	static GridProperty grid = new GridProperty();
	public static ArrayList<Integer> fovIDArr;

	public static ArrayList<ArrayList<Integer>> insert(int[][] gridCell, ArrayList<FOV> fovArray,
			double cellWidth, double[] mapBoundary) {

		grid.setMinLongitude(mapBoundary[3]);
		grid.setMinLatitude(mapBoundary[1]);
		grid.setMaxLongitude(mapBoundary[2]);
		grid.setMaxLatitude(mapBoundary[0]);
		grid.setCellWidth(cellWidth);
		ArrayList<ArrayList<Integer>> gridIndex_Position =new ArrayList<ArrayList<Integer>>();
		int cellNum = gridCell.length * gridCell[0].length;
		if (gridIndex_Position.isEmpty()) {
			gridIndex_Position = new ArrayList<ArrayList<Integer>>(Collections.nCopies(cellNum - 1, null));
		}

		for(FOV fov : fovArray) {
			double location_x = fov.getPolygon().getLocation().getY();
			double location_y = fov.getPolygon().getLocation().getX();
			double miniLong = mapBoundary[3];
			double miniLat = mapBoundary[1];
			int index_locationX = (int) Math.ceil((location_x - miniLong) / cellWidth);
			int index_locationY = (int) Math.ceil((location_y - miniLat) / cellWidth);
			int locCell = calCellID(index_locationX, index_locationY, gridCell[0].length);
			correspondingIndex(locCell, fov.getVideoID(), gridIndex_Position);
		}
		
		return gridIndex_Position;
	}

	public static int calCellID(int index_X, int index_Y, int grid_column) {
		int cellID = 0;
		if (index_X != 0 && index_Y != 0) {
			cellID = (index_Y - 1) * grid_column + (index_X - 1);
		} else if (index_X == 0) {
			cellID = (index_Y - 1) * grid_column + (index_X);
		} else if (index_Y == 0) {
			cellID = (index_Y) * grid_column + (index_X - 1);
		}
		return cellID;
	}

	public static ArrayList<ArrayList<Integer>> correspondingIndex(int cellID, int fovID,
			ArrayList<ArrayList<Integer>> level1) {

//		if(level1.size()!=0) {
//			level1.get(cellID).add(fovID);
//		}else {
//			fovIDArr = new ArrayList<Integer>();
//			fovIDArr.add(fovID);
//			level1.set(cellID, fovIDArr);
//		}
		if (level1.get(cellID) != null) {
			level1.get(cellID).add(fovID);
		} else {
			fovIDArr = new ArrayList<Integer>();
			fovIDArr.add(fovID);
			level1.set(cellID, fovIDArr);
		}
		return level1;
	}
}
