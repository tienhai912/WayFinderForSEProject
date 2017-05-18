package info;

public class JoinDistance {
	public Join join;
	public int distance = 1, route = 0;

	public JoinDistance(Join join, int distance) {
		this.join = join;
		this.distance = distance;
	}

	public JoinDistance(Join join, int distance, int route) {
		this(join, distance);
		this.route = route;

	}
}
