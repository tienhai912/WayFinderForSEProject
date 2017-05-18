package info;

import java.util.ArrayList;

public class Station {
	public int ID;
	public String name = "No Name";
	public ArrayList<Join> joins = new ArrayList<Join>();

	public Station(int ID) {
		this.ID = ID;
	}

	public Station(int ID, String name) {
		this(ID);
		this.name = name;
	}
}
