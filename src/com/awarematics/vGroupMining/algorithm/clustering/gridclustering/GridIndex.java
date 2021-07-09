/**
 * 
 */
package com.awarematics.vGroupMining.algorithm.clustering.gridclustering;

import java.io.Serializable;
import java.util.ArrayList;
import org.locationtech.jts.geom.Envelope;
import com.awarematics.vGroupMining.transform.GetMapboundary;
import com.awarematics.vGroupMining.transform.GetPolyonMaxMin;
import com.awarematics.vGroupMining.type.FOV;
import com.awarematics.vGroupMining.type.MPolygon;

/**
 * @author DI
 *
 */
public class GridIndex implements Serializable, Grid {

	private static final long serialVersionUID = 4648235715171600864L;
	Envelope env = new Envelope();
	static GridProperty grid = new GridProperty();

	@Override
	public int[][] createGrid(double cellWidth, ArrayList<FOV> fovArr) {

		double[] mapBoundary = GetMapboundary.getboundary(fovArr);
		grid.setMaxLatitude(mapBoundary[0]);
		grid.setMinLatitude(mapBoundary[1]);
		grid.setMaxLongitude(mapBoundary[2]);
		grid.setMinLongitude(mapBoundary[3]);
		grid.setCellWidth(cellWidth);
		
		if (((grid.getMaxLatitude() - grid.getMinLatitude()) % cellWidth) != 0) {
			int temp_row_Num = (int) Math.ceil((grid.getMaxLatitude() - grid.getMinLatitude()) / grid.getCellWidth());
			grid.row_Num = temp_row_Num;
		} else {
			int temp_row_Num = (int) ((grid.getMaxLatitude() - grid.getMinLatitude()) / grid.getCellWidth());
			grid.row_Num = temp_row_Num;
		}
		if (((grid.getMaxLongitude() - grid.getMinLongitude()) % cellWidth) != 0) {
			int temp_coulmn_Num = (int) Math
					.ceil((grid.getMaxLongitude() - grid.getMinLongitude()) / grid.getCellWidth());
			grid.coulmn_Num = temp_coulmn_Num;
		} else {
			int temp_coulmn_Num = (int) ((grid.getMaxLongitude() - grid.getMinLongitude()) / grid.getCellWidth());
			grid.coulmn_Num = temp_coulmn_Num;
		}
		int[][] gridCell = new int[grid.getRow_Num()][grid.getCoulmn_Num()];
		return gridCell;
	}

	@Override
	public ArrayList<ArrayList<Integer>> insertPosition(int[][] gridCell, ArrayList<FOV> fovArr, double cellWidth,
			double[] mapBoundary) {
		ArrayList<ArrayList<Integer>> gridIndex_Position = InsertPosition.insert(gridCell, fovArr, cellWidth,
				mapBoundary);
		return gridIndex_Position;
	}

	@Override
	public ArrayList<ArrayList<Integer>> insertFoVs(int[][] gridCell, ArrayList<FOV> fovArr, double cellWidth,
			double[] mapBoundary) {
		ArrayList<ArrayList<Integer>> gridIndex_FoV = InsertFoV.insert(gridCell, fovArr, cellWidth, mapBoundary);

		return gridIndex_FoV;
	}

	public static Envelope getBox(MPolygon polygon) {
		double[] box = GetPolyonMaxMin.getPolyonBound(polygon, grid);
		double maxLat_box = box[0];
		double minLat_box = box[1];
		double maxLog_box = box[2];
		double minLog_box = box[3];
		Envelope mbr = new Envelope(minLog_box, maxLog_box, minLat_box, maxLat_box);

		return mbr;
	}


}
