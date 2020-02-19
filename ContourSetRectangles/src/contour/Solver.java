package contour;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javafx.util.Pair;
import model.Direction;
import model.Edge;
import model.Rectangle;

public class Solver {

	public static List<Edge> slabs = new LinkedList<Edge>();
	public static List<Edge> hEdges = new Lista();

	public static List<Edge> relCurr = new Lista();
	public static List<Edge> relPrev = new Lista();
	
	public static List<Line> cont = new LinkedList<Line>();
	public static List<Pair<Integer, Edge>> merged = new ListaP();
	
	public static void solve() {
		sort();
		int index = 0;
		for (Iterator iterator = slabs.iterator(); iterator.hasNext();) {
			Edge e = (Edge) iterator.next();
			relPrev.clear();
			relPrev.addAll(relCurr);
			relCurr.clear();
			merged.clear();
			activeEdges(index);
			relEdges(index);
			contour(index);
			index++;
		}
		Line last = get(slabs.get(index - 1));
		cont.add(last);
	}
	
	private static void sort() {
		slabs.sort(null);
		//hEdges.sort(null);
	}
	
	private static void activeEdges(int index) {
		Edge s = slabs.get(index);
		if(s.getSide() == Direction.UP) {
			hEdges.add(new Edge(s.getId(), Direction.LEFT));
			hEdges.add(new Edge(s.getId(), Direction.RIGHT));
		}else {
			hEdges.remove(new Edge(s.getId(), Direction.LEFT));
			hEdges.remove(new Edge(s.getId(), Direction.RIGHT));
		}
	}
	
	private static void relEdges(int index) {
		int layer = 0, prev = 0;
		if(hEdges.isEmpty()) return;
		Edge edge = hEdges.get(0);
		for (Iterator iterator = hEdges.iterator(); iterator.hasNext();) {
			edge = (Edge) iterator.next();
			
			if(edge.getSide() == Direction.LEFT)
				layer++;
			else layer--;
			
			if((layer == 0 && prev == 1) || (layer == 1 && prev == 0))
				relCurr.add(edge);
			
			prev = layer;
		}
		if (layer == 0 && prev == 1) {
			relCurr.add(edge);
		}
	}
	
	private static void contour(int index) {
		merge();
		Iterator i1 = merged.iterator();
		Line e1 = null;
		Pair<Integer, Edge> one = null;
		Line e2 = null;
		Pair<Integer, Edge> two = null;
		Line v = get(slabs.get(index));
		
		while(i1.hasNext()) {
			one = (Pair<Integer, Edge>) i1.next();
			e1 = get(one.getValue());
			if(i1.hasNext()) {
				two = (Pair<Integer, Edge>) i1.next();
				e2 = get(two.getValue());
				int xN, xP;
				if(index < slabs.size() - 1) xN = get(slabs.get(index + 1)).getX2();
				else xN = e1.getX2();
				if(index > 0) xP = get(slabs.get(index - 1)).getX1();
				else xP = e2.getX1();
				
				if(e1.getY1() == e2.getY1()) {
					cont.add(new Line(v.getX1(), xN, e2.getY1(), e1.getY2()));
				}else {
					cont.add(new Line(v.getX1(), v.getX2(), e2.getY1(), e1.getY2()));
					if(two.getKey() == 1)
						cont.add(new Line(v.getX1(), xN, e2.getY1(), e2.getY2()));
					if(one.getKey() == 1)
						cont.add(new Line(v.getX1(), xN, e1.getY1(), e1.getY2()));
						
				}
			}
		}
	}
	
	private static void merge() {
		Iterator i1 = relCurr.iterator();
		Iterator i2 = relPrev.iterator();
		Edge e1 = null;
		Edge e2 = null;
		int i = 0, j = 0;
		
		while(i1.hasNext() && i2.hasNext()) {
			if(e1 == null)
				e1 = (Edge) i1.next();
			if(e2 == null)
				e2 = (Edge) i2.next();
			
			if(e1.compareTo(e2) < 0) {
				merged.add(new Pair<Integer, Edge>(1, e1));
				e1 = (Edge) i1.next();
				i = 1;
			}else {
				merged.add(new Pair<Integer, Edge>(0, e2));
				e2 = (Edge) i2.next();
				j = 1;
			}
		}
		while(i1.hasNext()) {
			i = 1;
			if(e1 == null)
				e1 = (Edge) i1.next();
			merged.add(new Pair<Integer, Edge>(1, e1));
			e1 = (Edge) i1.next();
		}
		if(i > 0) merged.add(new Pair<Integer, Edge>(1, e1));
		while(i2.hasNext()) {
			j = 1;
			if(e2 == null)
				e2 = (Edge) i2.next();
			merged.add(new Pair<Integer, Edge>(0, e2));
			e2 = (Edge) i2.next();
		}
		if(j > 0) merged.add(new Pair<Integer, Edge>(0, e2));
	}
	
	private static Line get(Edge e) {
		Rectangle r = Rectangle.map.get(e.getId());
		if(e.getSide() == Direction.UP) {
			return new Line(r.getLeftX(), r.getLeftX(), r.getLeftY(), r.getRightY());
		}else if(e.getSide() == Direction.DOWN) {
			return new Line(r.getRightX(), r.getRightX(), r.getLeftY(), r.getRightY());
		}else if(e.getSide() == Direction.LEFT) {
			return new Line(r.getLeftX(), r.getRightX(), r.getLeftY(), r.getLeftY());
		}
		return new Line(r.getLeftX(), r.getRightX(), r.getRightY(), r.getRightY());
	}
}
