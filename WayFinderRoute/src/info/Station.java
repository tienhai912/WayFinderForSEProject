package info;

import java.util.ArrayList;

public class Station {
	public int ID, route; // route = 0 when the station is a join
	public String name = "No Name";
	public ArrayList<Join> joins = new ArrayList<Join>();

	public Station(int ID) {
		this.ID = ID;
	}

	public Station(int ID, int route) {
		this(ID);
		this.route = route;
	}

	public Station(int ID, int route, String name) {
		this(ID,route);
		this.name = name;
	}

}
