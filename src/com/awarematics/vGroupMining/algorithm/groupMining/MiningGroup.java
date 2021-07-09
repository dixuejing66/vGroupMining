package com.awarematics.vGroupMining.algorithm.groupMining;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MiningGroup {
	public static GroupProperty p = new GroupProperty();

	public static ArrayList<GroupProperty> findCandidates(ArrayList<ArrayList<Cluster>> cluSnapshots, int M) {

		GroupProperty p = new GroupProperty();
		ArrayList<GroupProperty> firCandi = new ArrayList<GroupProperty>();
		ArrayList<GroupProperty> candidates = new ArrayList<GroupProperty>();
		// Traverse each snapshot
		for (int i = 0; i < cluSnapshots.size(); i++) {
			p = new GroupProperty();
			// Traverse clusters in one snapshot i
			ArrayList<Cluster> clusters1 = cluSnapshots.get(i);
			for (int m = 0; m < clusters1.size(); m++) {
				p = new GroupProperty();
				Cluster c1 = clusters1.get(m);
				List<Integer> c1IdArr = Arrays.stream(c1.getVideoIDArr()).boxed().collect(Collectors.toList());
				c1IdArr.remove(c1IdArr.size() - 1);
				ArrayList<Integer> commonEle = new ArrayList<Integer>();
				ArrayList<Integer> commonSnapNum = new ArrayList<Integer>();

				for (int j = i + 1; j < cluSnapshots.size(); j++) {
					// Traverse clusters in one snapshot i+1
					ArrayList<Cluster> clusters2 = cluSnapshots.get(j);
					for (int n = 0; n < clusters2.size(); n++) {
						Cluster c2 = clusters2.get(n);
						List<Integer> c2IdArr = Arrays.stream(c2.getVideoIDArr()).boxed().collect(Collectors.toList());
						c2IdArr.remove(c2IdArr.size() - 1);

						// Find common elements
						ArrayList<Integer> tempEle = new ArrayList<Integer>();
						tempEle = (ArrayList<Integer>) receiveCollectionList(c1IdArr, c2IdArr);
						if ((tempEle.size() > M) && (tempEle != null)) {

							commonEle.addAll(tempEle);
							commonSnapNum.add(c1.getsnapID());
							commonSnapNum.add(c2.getsnapID());

							// jiang yige de jieguo fangru patterns
							ArrayList<Integer> resultEle = (ArrayList<Integer>) commonEle.stream().distinct()
									.collect(Collectors.toList());
							ArrayList<Integer> resultNum = (ArrayList<Integer>) commonSnapNum.stream().distinct()
									.collect(Collectors.toList());
							p.setVideoID(resultEle);
							p.setSnapshotNum(resultNum);
							firCandi.add(p);
							p = new GroupProperty();
							commonEle = new ArrayList<Integer>();
							commonSnapNum = new ArrayList<Integer>();
						}

					}
				}

			}
		}

		candidates = MiningGroup.combine(firCandi, M);
		for (GroupProperty pCan : candidates) {
			Collections.sort(pCan.getSnapshotNum());
		}
		return candidates;
	}

	// Filtering the same elements and snapshotNum
	public static List<Integer> receiveCollectionList(List<Integer> firstArrayList, List<Integer> secondArrayList) {
		List<Integer> resultList = new ArrayList<Integer>();
		LinkedList<Integer> result = new LinkedList<Integer>(firstArrayList);
		HashSet<Integer> othHash = new HashSet<Integer>(secondArrayList);
		Iterator<Integer> iter = result.iterator();
		while (iter.hasNext()) {
			if (!othHash.contains(iter.next())) {
				iter.remove();
			}
		}
		resultList = new ArrayList<Integer>(result);
		return resultList;
	}

	public static ArrayList<GroupProperty> combine(ArrayList<GroupProperty> firCandi, int M) {

		ArrayList<GroupProperty> candidates = new ArrayList<GroupProperty>();
		for (int i = 0, len = firCandi.size(); i < len; i++) {
			ArrayList<Integer> firIdArr = new ArrayList<Integer>();
			firIdArr = firCandi.get(i).getVideoID();

			if (candidates.isEmpty()) {
				ArrayList<Integer> combineSnapNum = new ArrayList<Integer>();
				ArrayList<Integer> combineEle = new ArrayList<Integer>();

				combineSnapNum.addAll(firCandi.get(i).getSnapshotNum());

				for (int j = i + 1, len2 = firCandi.size(); j < len2; j++) {
					ArrayList<Integer> secIdArr = new ArrayList<Integer>();
					secIdArr = firCandi.get(j).getVideoID();

					ArrayList<Integer> tempEle = new ArrayList<Integer>();
					tempEle = (ArrayList<Integer>) receiveCollectionList(firIdArr, secIdArr);
					if (tempEle.size() > M) {
						combineSnapNum.addAll(firCandi.get(j).getSnapshotNum());
						combineEle.addAll(tempEle);
					}
				}
				ArrayList<Integer> resultNum = (ArrayList<Integer>) combineSnapNum.stream().distinct()
						.collect(Collectors.toList());
				ArrayList<Integer> resultEle = (ArrayList<Integer>) combineEle.stream().distinct()
						.collect(Collectors.toList());
				GroupProperty p = new GroupProperty();
				p.setVideoID(resultEle);
				p.setSnapshotNum(resultNum);
				candidates.add(p);
			} else {

				if (!judgeContain(candidates, firIdArr)) {
					ArrayList<Integer> combineSnapNum = new ArrayList<Integer>();
					ArrayList<Integer> combineEle = new ArrayList<Integer>();
					combineEle.addAll(firIdArr);
					combineSnapNum.addAll(firCandi.get(i).getSnapshotNum());

					for (int j = i + 1, len2 = firCandi.size(); j < len2; j++) {
						ArrayList<Integer> secIdArr = new ArrayList<Integer>();
						secIdArr = firCandi.get(j).getVideoID();
						ArrayList<Integer> tempEle = new ArrayList<Integer>();
						tempEle = (ArrayList<Integer>) receiveCollectionList(firIdArr, secIdArr);
						if (tempEle.size() > M) {
							combineSnapNum.addAll(firCandi.get(j).getSnapshotNum());
							combineEle.addAll(tempEle);
						}
					}
					ArrayList<Integer> resultNum = (ArrayList<Integer>) combineSnapNum.stream().distinct()
							.collect(Collectors.toList());
					ArrayList<Integer> resultEle = (ArrayList<Integer>) combineEle.stream().distinct()
							.collect(Collectors.toList());
					GroupProperty p = new GroupProperty();
					p.setVideoID(resultEle);
					p.setSnapshotNum(resultNum);
					candidates.add(p);
				}
			}
		}
		return candidates;
	}

	public static boolean judgeContain(ArrayList<GroupProperty> candidates, ArrayList<Integer> firIdArr) {
		for (GroupProperty p : candidates) {
			if (p.getVideoID().containsAll(firIdArr) && firIdArr.containsAll(p.getVideoID())) {
				return true;
			}
		}
		return false;
	}
}
