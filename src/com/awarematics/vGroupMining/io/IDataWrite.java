package com.awarematics.vGroupMining.io;

import java.io.IOException;
import java.util.ArrayList;
import com.awarematics.vGroupMining.type.FOV;

public interface IDataWrite {
	public void clusterToCsvFile(ArrayList<ArrayList<ArrayList<FOV>>> snaplist, 
			String desPath, 
			double areaInter, 
			int M,
			double cellWidth, 
			String name) throws IOException;
}
