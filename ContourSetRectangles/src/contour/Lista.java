package contour;

import java.util.Iterator;
import java.util.LinkedList;

import javafx.util.Pair;
import model.Edge;

public class Lista extends LinkedList<Edge>{

	@Override
	public boolean add(Edge e) {
		int index = 0;
		if(this.isEmpty()) {
			super.add(e);
			return true;
		}
		if(e.compareTo(this.getFirst()) < 0) {
			super.addFirst(e);
			return true;
		}
		for (Iterator iterator = this.iterator(); iterator.hasNext();) {
			index++;
			Edge edge = (Edge) iterator.next();
			if(e.compareTo(edge) == 0) return false;
			if(e.compareTo(edge) < 0) {
				super.add(index - 1, e);
				return true;
			}
		}
		super.add(e);
		return true;
	}

	
}