package info;

public class CheckJoin {
	public Join join, lastJoin;
	public int distance = Sample.DEFAULT_MIN_DISTANCE;

	public CheckJoin(Join join){
		this.join = join;
		lastJoin = null;
	}

	public CheckJoin(Join join, int distance){
		this(join);
		this.distance = distance;
	}

}
