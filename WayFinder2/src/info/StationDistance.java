package info;

public class StationDistance {
	public Station station;
	public int distance = 1;

	public StationDistance(Station station) {
		this.station = station;
	}

	public StationDistance(Station station, int distance) {
		this(station);
		this.distance = distance;

	}
}
