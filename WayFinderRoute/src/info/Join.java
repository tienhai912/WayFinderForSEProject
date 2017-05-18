package info;

import java.util.ArrayList;

public class Join {
	public int ID;
	public ArrayList<StationDistance> sd = new ArrayList<StationDistance>();
	public ArrayList<JoinDistance> jd = new ArrayList<JoinDistance>();

	public Join(int ID) {
		this.ID = ID;
	}

	public void addStation(StationDistance sd1) {
		for (StationDistance sdd : sd) {
			if (sdd.station == sd1.station) {
				System.out.println("You have put this station in: " + sd1.station.ID);
				return;
			}
		}
		sd.add(sd1);
		sd1.station.joins.add(this);
	}

	public void addJoin(JoinDistance jd1) {
		for (JoinDistance jdd : jd) {
			if (jdd.join == jd1.join && jdd.route == jd1.route) {
				System.out
						.println(ID + ": You have put this join: " + jd1.join.ID + " in route: " + jd1.route);
				return;
			}
		}
		jd.add(jd1);
		jd1.join.jd.add(new JoinDistance(this, jd1.distance, jd1.route));
	}

}
