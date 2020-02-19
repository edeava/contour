package model;

public class Edge implements Comparable<Edge>{

	private int id;
	private Direction side;
	
	public Edge(int id, Direction side) {
		super();
		this.id = id;
		this.side = side;
	}

	public int getId() {
		return id;
	}

	public Direction getSide() {
		return side;
	}

	@Override
	public int compareTo(Edge o) {
		if(this.side == Direction.UP || this.side == Direction.DOWN) {
			if(fi(this.id, this.side) < fi(o.id, o.side))
					return -1;
			if(this.side == Direction.UP && o.side == Direction.DOWN && fi(this.id, this.side) == fi(o.id, o.side))
				return -1;
			if(this.side == o.side && fi(this.id, this.side) == fi(o.id, o.side) && this.id < o.id)
				return -1;
			return 1;
		}else {
			if(fi(this.id, this.side) < fi(o.id, o.side))
				return 1;
			if(this.side == Direction.LEFT && o.side == Direction.RIGHT && fi(this.id, this.side) == fi(o.id, o.side))
				return 1;
			if(this.side == o.side && fi(this.id, this.side) == fi(o.id, o.side) && this.id < o.id)
				return 1;
			return -1;
		}
	}
	
	private int fi(int k, Direction d) {
		if(d == Direction.UP)
			return Rectangle.map.get(k).getLeftX();
		if(d == Direction.DOWN)
			return Rectangle.map.get(k).getRightX();
		if(d == Direction.LEFT)
			return Rectangle.map.get(k).getLeftY();
		return Rectangle.map.get(k).getRightY();
	}
	
	@Override
	public String toString() {
		return id + " " + side;
	}
	
	@Override
	public boolean equals(Object obj) {
		Edge e = (Edge) obj;
		return this.id == e.id && this.side == e.side;
	}
}
