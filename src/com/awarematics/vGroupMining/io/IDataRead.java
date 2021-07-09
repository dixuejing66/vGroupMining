package com.awarematics.vGroupMining.io;

import java.io.IOException;
import java.util.ArrayList;

import com.awarematics.vGroupMining.type.FOV;

public interface IDataRead {
	public ArrayList<ArrayList<FOV>> readSnapFile(String filePath) throws IOException;
}
