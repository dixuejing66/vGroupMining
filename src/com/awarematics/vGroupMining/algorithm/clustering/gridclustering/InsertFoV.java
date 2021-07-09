package com.awarematics.vGroupMining.algorithm.clustering.gridclustering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.awarematics.vGroupMining.type.FOV;
import com.awarematics.vGroupMining.type.MPoint;

public class InsertFoV {
	public ArrayList<Integer> fovIDArr;
	static GridProperty grid = new GridProperty();

	public static ArrayList<ArrayList<Integer>> insert(int[][] gridCell, ArrayList<FOV> fovArray, double cellWidth,
			double[] mapBoundary) {

		InsertFoV insertF = new InsertFoV();
		ArrayList<MPoint> intersecList = new ArrayList<MPoint>();
		LinkedList<Integer> edgeGridPass_tem = new LinkedList<Integer>();
		LinkedList<Integer> edgeGridPass = new LinkedList<Integer>();

		grid.setMinLongitude(mapBoundary[3]);
		grid.setMinLatitude(mapBoundary[1]);
		grid.setMaxLongitude(mapBoundary[2]);
		grid.setMaxLatitude(mapBoundary[0]);
		grid.setCellWidth(cellWidth);
		ArrayList<ArrayList<Integer>> FOVsIndex = new ArrayList<ArrayList<Integer>>();
		int cellNum = gridCell.length * gridCell[0].length;
		if (FOVsIndex.isEmpty()) {
			FOVsIndex = new ArrayList<ArrayList<Integer>>(Collections.nCopies(cellNum - 1, null));
		}

		for (FOV fov : fovArray) {
			for (int i = 0; i < 3; i++) {
				int[] intersecNum = new int[2];
				switch (i) {
				case 0:
					intersecNum = insertF.numCellCover(fov.getPolygon().getLocation(), fov.getPolygon().getP1(),
							cellWidth);
					if ((intersecNum[0] == 0) && (intersecNum[1] == 0)) {
						int cellInde_x = (int) Math
								.ceil((fov.getPolygon().getLocation().getY() - mapBoundary[3]) / cellWidth);
						int cellInde_y = (int) Math
								.ceil((fov.getPolygon().getLocation().getX() - mapBoundary[1]) / cellWidth);
						int cellID = calCellID(cellInde_x, cellInde_y, gridCell[0].length);
						insertF.correspondingIndex(cellID, fov.getVideoID(), FOVsIndex);
					} else {
						ArrayList<MPoint> tempList = insertF.intersectionCal(fov.getPolygon().getLocation(),
								fov.getPolygon().getP1(), cellWidth, mapBoundary);
						intersecList.addAll(tempList);
					}

					continue;
				case 1:
					intersecNum = insertF.numCellCover(fov.getPolygon().getP1(), fov.getPolygon().getP4(), cellWidth);
					if ((intersecNum[0] == 0) && (intersecNum[1] == 0)) {
						int cellInde_x = (int) Math
								.ceil((fov.getPolygon().getLocation().getY() - mapBoundary[3]) / cellWidth);
						int cellInde_y = (int) Math
								.ceil((fov.getPolygon().getLocation().getX() - mapBoundary[1]) / cellWidth);
						int cellID = calCellID(cellInde_x, cellInde_y, gridCell[0].length);
						insertF.correspondingIndex(cellID, fov.getVideoID(), FOVsIndex);
					} else {
						ArrayList<MPoint> tempList = insertF.intersectionCal(fov.getPolygon().getP1(),
								fov.getPolygon().getP4(), cellWidth, mapBoundary);
						intersecList.addAll(tempList);
					}
					continue;
				case 2:
					intersecNum = insertF.numCellCover(fov.getPolygon().getP4(), fov.getPolygon().getLocation(),
							cellWidth);
					if ((intersecNum[0] == 0) && (intersecNum[1] == 0)) {
						int cellInde_x = (int) Math
								.ceil((fov.getPolygon().getLocation().getY() - mapBoundary[3]) / cellWidth);
						int cellInde_y = (int) Math
								.ceil((fov.getPolygon().getLocation().getX() - mapBoundary[1]) / cellWidth);
						int cellID = calCellID(cellInde_x, cellInde_y, gridCell[0].length);
						insertF.correspondingIndex(cellID, fov.getVideoID(), FOVsIndex);
					} else {
						ArrayList<MPoint> tempList = insertF.intersectionCal(fov.getPolygon().getP4(),
								fov.getPolygon().getLocation(), cellWidth, mapBoundary);
						intersecList.addAll(tempList);
					}
					continue;
				default:
					break;
				}

			}
			edgeGridPass_tem = insertF.edgeGridPass(intersecList, mapBoundary, cellWidth, gridCell, fov);
			edgeGridPass.addAll(edgeGridPass_tem);
			// Remove duplicate cell id
			List<Integer> edgesGridPass1 = edgeGridPass.stream().distinct().collect(Collectors.toList());
			LinkedList<Integer> edgesGridPass2 = new LinkedList<>(edgesGridPass1);
			for (Integer cell_id : edgesGridPass2) {
				insertF.correspondingIndex(cell_id, fov.getVideoID(), FOVsIndex);
			}
		}

		// insert edges of FoV

		return FOVsIndex;
	}

	/*
	 * Calculate the number of cells covered by fov
	 */
	public int[] numCellCover(MPoint p1, MPoint p2, double width) {
		MPoint startP = new MPoint();
		MPoint endP = new MPoint();
		int[] intersecNum = new int[2];
		if (p1.getY() <= p2.getY()) {
			startP = p1;
			endP = p2;
		} else {
			startP = p2;
			endP = p1;
		}
		double dx = endP.getY() - startP.getY();
		double dy = endP.getX() - startP.getX();

		int intersecNum_x = (int) Math.floor(Math.abs(dx) / width);
		int intersecNum_y = (int) Math.floor(Math.abs(dy) / width);
		intersecNum[0] = intersecNum_x;
		intersecNum[1] = intersecNum_y;
		return intersecNum;
	}

	public ArrayList<MPoint> intersectionCal(MPoint p1, MPoint p2, double width, double[] mapBoundary) {

		ArrayList<MPoint> intersecList = new ArrayList<MPoint>(); // List of intersections on x-axis and y-axis
		MPoint startP = new MPoint();
		MPoint endP = new MPoint();
		/*
		 * MAP FOR BDD100K
		 */
		// boundary[0] = latitude[0]; // MaxLatitude
		// boundary[1] = latitude[1]; // MinLatitude
		// boundary[2] = longitude[0]; // MaxLongitude
		// boundary[3] = longitude[1]; // MinLongitude
		if (p1.getY() <= p2.getY()) {
			startP = p1;
			endP = p2;
		} else {
			startP = p2;
			endP = p1;
		}
		double dx = endP.getY() - startP.getY();
		double dy = endP.getX() - startP.getX();

		// Linear equation : y=kx+b
		double k = dy / dx;
		double b = endP.getX() - k * endP.getY();

		int intersecNum_x = (int) Math.floor(Math.abs(dx) / width); // intersect with x-axis
		int intersecNum_y = (int) Math.floor(Math.abs(dy) / width); // intersect with y-axis

		// One fov covers multiple cells

		// compute the intersection with cells
		for (int i = 0; i < intersecNum_x; i++) {

			// traverse along the x-axis
			MPoint tempP1 = new MPoint();
			tempP1.x = Math.ceil((startP.getY() - mapBoundary[3]) / width) * width + mapBoundary[3] + width * i;
			tempP1.y = k * tempP1.getX() + b;
			intersecList.add(tempP1);

		}
		for (int i = 0; i < intersecNum_y; i++) {

			// traverse along the y-axis
			MPoint tempP2 = new MPoint();
			if (k >= 0) {
				tempP2.y = Math.ceil((startP.getX() - mapBoundary[1]) / width) * width + mapBoundary[1] + width * i;
			} else {
				tempP2.y = Math.floor((startP.getX() - mapBoundary[1]) / width) * width + mapBoundary[1] + width * i;
			}
			tempP2.x = (tempP2.getY() - b) / k;
			intersecList.add(tempP2);
		}
//		}else {                //One fov only covers one cell
//			intersecList.add(startP);
//		}

		return intersecList;
	}

	/*
	 * compute the cell id that edge of fov passed
	 */
	public LinkedList<Integer> edgeGridPass(ArrayList<MPoint> intersecList, double[] mapBoundary, double width,
			int[][] gridCell, FOV fov) {
		/*
		 * MAP FOR BDD100K
		 */
		// boundary[0] = latitude[0]; // MaxLatitude
		// boundary[1] = latitude[1]; // MinLatitude
		// boundary[2] = longitude[0]; // MaxLongitude
		// boundary[3] = longitude[1]; // MinLongitude

		LinkedList<Integer> edgeGridPass_tem = new LinkedList<Integer>();

		for (int i = 0; i < intersecList.size(); i++) {
			// intersection on the y-axis

			double x = intersecList.get(i).getX();
			double y = intersecList.get(i).getY();
			double m = (y - mapBoundary[1]) / width;
			double n = (x - mapBoundary[3]) / width;
//			System.out.println(m);
//			System.out.println(n);
			if ((m == Math.ceil(m)) && (n != Math.floor(n))) {
				int cellUpIndex_x = (int) Math.ceil((x - mapBoundary[3]) / width);
				int cellUpIndex_y = (int) Math.ceil((y - mapBoundary[1]) / width) + 1;
				int cellUp = calCellID(cellUpIndex_x, cellUpIndex_y, gridCell[0].length);
				int cellDownInde_x = (int) Math.ceil((x - mapBoundary[3]) / width);
				int cellDownInde_y = (int) Math.ceil((y - mapBoundary[1]) / width);
				int cellDown = calCellID(cellDownInde_x, cellDownInde_y, gridCell[0].length);

				edgeGridPass_tem.add(cellUp);
				edgeGridPass_tem.add(cellDown);
			}
			// intersection on the x-axis
			else if ((m != Math.floor(m)) && (n == Math.floor(n))) {
				int cellLeftInde_x = (int) Math.ceil((x - mapBoundary[3]) / width);
				int cellLeftInde_y = (int) Math.ceil((y - mapBoundary[1]) / width);
				int cellLeft = calCellID(cellLeftInde_x, cellLeftInde_y, gridCell[0].length);

				int cellRightInde_x = (int) Math.ceil((x - mapBoundary[3]) / width) + 1;
				int cellRightInde_y = (int) Math.ceil((y - mapBoundary[1]) / width);
				int cellRight = calCellID(cellRightInde_x, cellRightInde_y, gridCell[0].length);
				edgeGridPass_tem.add(cellLeft);
				edgeGridPass_tem.add(cellRight);
			}
		}
		List<Integer> edgeGridPass1 = edgeGridPass_tem.stream().distinct().collect(Collectors.toList());
		LinkedList<Integer> edgeGridPass2 = new LinkedList<>(edgeGridPass1);
		return edgeGridPass2;
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

	public ArrayList<ArrayList<Integer>> correspondingIndex(int cellID, int fovID,
			ArrayList<ArrayList<Integer>> level2) {

		if (level2.size() != 0 && level2.get(cellID) != null) {
			level2.get(cellID).add(fovID);
//			 System.out.println(level2.get(cellID));
		} else {
			fovIDArr = new ArrayList<Integer>();
			fovIDArr.add(fovID);
			level2.set(cellID, fovIDArr);
		}

		return level2;
	}
}
