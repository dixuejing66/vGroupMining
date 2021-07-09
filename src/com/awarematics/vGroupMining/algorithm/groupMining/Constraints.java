package com.awarematics.vGroupMining.algorithm.groupMining;

import java.util.ArrayList;

public class Constraints {

	public static GroupProperty p = new GroupProperty();

	public static ArrayList<GroupProperty> judgG(ArrayList<GroupProperty> candidates, int G) {
		int time1 = 0;
		int time2 = 0;

		for (int i = 0; i < candidates.size(); i++) {

			GroupProperty p = candidates.get(i);
			for (int m = 0; m < p.getSnapshotNum().size(); m++) {

				time1 = p.getSnapshotNum().get(m);
				if (m != p.getSnapshotNum().size() - 1) {
					time2 = p.getSnapshotNum().get(m + 1);
					if ((time2 - time1) > G) {
						candidates.remove(p);
						i = i - 1;
						if (i == -1) {
							i = 0;
						}
					}
				}
			}
		}
		return candidates;
	}

	public static ArrayList<GroupProperty> judgL(ArrayList<GroupProperty> candidates, int L) {

		for (int i = 0; i < candidates.size(); i++) {

			GroupProperty p = candidates.get(i);
			int segment = 0;
			int time1 = 0;
			int time2 = 0;

			for (int m = 0, len1 = p.getSnapshotNum().size(); m < len1; m++) {
				time1 = p.getSnapshotNum().get(m);
				if (m != p.getSnapshotNum().size() - 1) {
					time2 = p.getSnapshotNum().get(m + 1);
					if ((time2 - time1) > 1) {
						segment++;
					}
				}
			}
			if (segment < L) {
				candidates.remove(p);
				i = i - 1;
				if (i == -1) {
					i = 0;
				}
			}
		}
		return candidates;
	}

	public static ArrayList<GroupProperty> judgK(ArrayList<GroupProperty> candidatesG, int K) {
		for (int i = 0; i < candidatesG.size(); i++) {
			GroupProperty p = candidatesG.get(i);
			int numSnap = p.getSnapshotNum().size();
			if (numSnap < K) {
				candidatesG.remove(p);
				i = i - 1;
				if (i == -1) {
					i = 0;
				}
			}
		}
		return candidatesG;
	}

	public static ArrayList<GroupProperty> filterPattern(ArrayList<GroupProperty> candidatesK, int N) {
		for (int i = 0; i < candidatesK.size(); i++) {
			int singleNum = 0;
			GroupProperty p = candidatesK.get(i);
			for (int m = 0; m < p.getSnapshotNum().size(); m++) {
				int time = p.getSnapshotNum().get(m);
				if (m != p.getSnapshotNum().size() - 1) {
					int time2 = p.getSnapshotNum().get(m + 1);
					if (m != 0) {
						int time1 = p.getSnapshotNum().get(m - 1);
						if ((time2 - time > 1) && (time - time1 > 1)) {
							singleNum++;
						}
					} else if (m == 0) {
						if (time2 - time > 1) {
							singleNum++;
						}
					}
				} else {
					int time1 = p.getSnapshotNum().get(m - 1);
					if (time - time1 > 1) {
						singleNum++;
					}
				}

			}
			if (singleNum < N) {
				candidatesK.remove(p);
				i = i - 1;
				if (i == -1) {
					i = 0;
				}
			}
		}
		return candidatesK;
	}
}
