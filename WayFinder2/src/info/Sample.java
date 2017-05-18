package info;

import java.util.ArrayList;

public class Sample {
	public static final int DEFAULT_MIN_DISTANCE = 1000;
	public ArrayList<Station> stations = new ArrayList<Station>();
	public ArrayList<Join> joins = new ArrayList<Join>();
	public ArrayList<Join> remainJoins = new ArrayList<Join>();
	public ArrayList<CheckJoin> completedJoins = new ArrayList<CheckJoin>();
	public CheckJoin lastCheckJoin;
	public Station s406, s438, s701, s702;
	public ArrayList<Integer> path, shortestPath;
	public int minDistance, distance;

	public Join j305710, j311811, j312432, j322423, j324821, j401706, j404609, j433810, j440708, j627723, j711805,
			j308616807;

	public Sample() {
		initJoin();
		initStation();

	}

	public void initJoin() {
		j305710 = createJoin(305710);
		j311811 = createJoin(311811);
		j312432 = createJoin(312432);
		j322423 = createJoin(322423);
		j324821 = createJoin(324821);
		j401706 = createJoin(401706);
		j404609 = createJoin(404609);
		j433810 = createJoin(433810);
		j440708 = createJoin(440708);
		j627723 = createJoin(627723);
		j711805 = createJoin(711805);
		j308616807 = createJoin(308616807);
		///

		j305710.addJoin(new JoinDistance(j308616807, 3, 3));
		j305710.addJoin(new JoinDistance(j440708, 2, 7));
		j305710.addJoin(new JoinDistance(j711805, 1, 7));

		j308616807.addJoin(new JoinDistance(j311811, 3, 3));
		j308616807.addJoin(new JoinDistance(j404609, 7, 6));
		j308616807.addJoin(new JoinDistance(j627723, 11, 6));
		j308616807.addJoin(new JoinDistance(j711805, 2, 8));
		j308616807.addJoin(new JoinDistance(j433810, 3, 8));

		j311811.addJoin(new JoinDistance(j312432, 1, 3));
		j311811.addJoin(new JoinDistance(j433810, 1, 8));
		j311811.addJoin(new JoinDistance(j324821, 13, 8));

		j312432.addJoin(new JoinDistance(j322423, 10, 3));
		j312432.addJoin(new JoinDistance(j322423, 9, 4));
		j312432.addJoin(new JoinDistance(j440708, 8, 4));

		j322423.addJoin(new JoinDistance(j324821, 2, 3));
		j322423.addJoin(new JoinDistance(j404609, 19, 4));

		j401706.addJoin(new JoinDistance(j404609, 3, 4));
		j401706.addJoin(new JoinDistance(j440708, 2, 7));

		j433810.addJoin(new JoinDistance(j440708, 7, 4));

		j627723.addJoin(new JoinDistance(j711805, 12, 7));
	}

	public void initStation() {
		s701 = createStation(701);
		s406 = createStation(406);
		j401706.addStation(new StationDistance(s701, 5));
		j404609.addStation(new StationDistance(s406, 2));
		j322423.addStation(new StationDistance(s406, 17));

		//
		s702 = createStation(702);
		j401706.addStation(new StationDistance(s702, 4));

		//
		s438 = createStation(438);
		j440708.addStation(new StationDistance(s438, 2));
		j433810.addStation(new StationDistance(s438, 7));
	}

	public Join createJoin(int ID) {
		Join newJoin = new Join(ID);
		joins.add(newJoin);
		return newJoin;
	}

	public Station createStation(int ID) {
		Station newStation = new Station(ID);
		stations.add(newStation);
		return newStation;
	}

	// finding methods

	public void testFind() {
		findShortestRoad(s701, s406);
	}

	public void testFind2() {
		findShortestRoad(s701, s702);
	}

	public void testFind3() {
		findShortestRoad(s406, s438);
	}

	public void initFind() {
		remainJoins = new ArrayList<Join>();
		completedJoins = new ArrayList<CheckJoin>();
		for (Join j : joins) {
			remainJoins.add(j);
			completedJoins.add(new CheckJoin(j, DEFAULT_MIN_DISTANCE));
		}
	}

	public void checkInitFind() {
		System.out.println(remainJoins.size() + " " + completedJoins.size());
		for (Join j : remainJoins) {
			System.out.println(j.ID);
		}
		for (CheckJoin cj : completedJoins) {
			if (cj.lastJoin != null) {
				System.out.println(cj.join.ID + " - " + cj.lastJoin.ID + ": " + cj.distance);

			} else {
				System.out.println(cj.join.ID + " - null: " + cj.distance);
			}
		}
	}

	public void findShortestRoad(Station s1, Station s2) {
		if (s1.joins.size() == 0 || s2.joins.size() == 0) {
			System.out.println("Input problem");
			return;
		}

		initFind();

		minDistance = DEFAULT_MIN_DISTANCE;

		// same road or not ?
		inSameRoad(s1, s2);

		// find path
		dijkstra(s1);
		// checkInitFind();
		lastCheckJoin = findMinDistanceFromJoin(s2);
		// not found
		if (minDistance == DEFAULT_MIN_DISTANCE) {
			System.out.println("No way found between " + s1.ID + " and " + s2.ID);
			return;
		}

		// found
		System.out.println("minDistance: " + minDistance);
		System.out.print("Road: ");
		System.out.print(s1.ID);
		CheckJoin temp = lastCheckJoin;
		while (temp != null) {
			System.out.print(" " + temp.join.ID);
			temp = findCheckJoin(temp.lastJoin);
		}

		System.out.print(" " + s2.ID);
		System.out.println();
		System.out.println();
	}

	public CheckJoin findMinDistanceFromJoin(Station s2) {
		CheckJoin cj = null, temp;
		for (Join j : s2.joins) {
			for (StationDistance sd : j.sd) {
				if (sd.station == s2) {
					temp = findCheckJoin(j);
					if (sd.distance + findCheckJoin(j).distance < minDistance) {
						minDistance = sd.distance + temp.distance;
						cj = temp;
					}
				}
				break;
			}
		}
		return cj;
	}

	public CheckJoin findMinCheckJoin() {
		int min = DEFAULT_MIN_DISTANCE + 1;
		CheckJoin temp = null;
		for (CheckJoin cj : completedJoins) {
			if (cj.distance < min && remainJoins.contains(cj.join)) {
				min = cj.distance;
				temp = cj;
			}
		}
		return temp;
	}

	public CheckJoin findCheckJoin(Join join) {
		for (CheckJoin cj : completedJoins) {
			if (cj.join == join)
				return cj;
		}
		return null;
	}

	public void dijkstra(Station s1) {
		// update for join connect to station s1
		for (Join j1 : s1.joins) {

			CheckJoin cj = findCheckJoin(j1);
			for (StationDistance sd1 : j1.sd) {
				if (sd1.station == s1) {
					cj.distance = sd1.distance;

				}
				break;
			}

		}
		// Dijkstra for Join

		dijkstraJoin();

	}

	public void dijkstraJoin() {
		CheckJoin cj;
		while (remainJoins.size() > 0) {
			cj = findMinCheckJoin();

			remainJoins.remove(cj.join);
			for (Join j : remainJoins) {
				for (JoinDistance jd : j.jd) {
					if (jd.join == cj.join) {
						CheckJoin temp = findCheckJoin(j);
						if (cj.distance + jd.distance < temp.distance) {
							temp.lastJoin = cj.join;
							temp.distance = cj.distance + jd.distance;
						}

						break;
					}

				}
			}
		}
	}

	public void inSameRoad(Station s1, Station s2) {
		if (s1.joins.size() == s2.joins.size()) {
			int x = 0;
			for (Join j1 : s1.joins) {
				if (s2.joins.contains(j1)) {
					x++;
				}
			}
			if (x == s1.joins.size()) {
				Join jx = s1.joins.get(0);
				int s1d = 0, s2d = 0;
				for (StationDistance sd1 : jx.sd) {
					if (sd1.station == s1) {
						s1d = sd1.distance;
					}
					if (sd1.station == s2) {
						s2d = sd1.distance;
					}
				}
				if (s1d > s2d) {
					minDistance = s1d - s2d;
				} else {
					minDistance = s2d - s1d;
				}
			}
		}
	}

	// end of finding methods

	public void out() {
		System.out.println("Station info: ");
		for (Station s : stations) {
			System.out.print(s.ID);
			for (Join j : s.joins) {
				System.out.print(" " + j.ID);

			}
			System.out.println();
			System.out.println();
		}
		System.out.println("//////////////////////////////////////");
		System.out.println("Join info: ");
		for (Join j : joins) {
			System.out.println(j.ID + ": ");
			System.out.println("Station:");
			for (StationDistance sd1 : j.sd) {
				System.out.println(sd1.station.ID + " " + sd1.distance);
			}
			System.out.println();
			System.out.println("Join: ");
			for (JoinDistance jd1 : j.jd) {
				System.out.println(jd1.join.ID + " " + jd1.distance + " " + jd1.route);
			}
			System.out.println();
		}
	}
}
