package contour;

import java.util.Iterator;
import java.util.LinkedList;

import javafx.util.Pair;
import model.Edge;

public class ListaP extends LinkedList<Pair<Integer, Edge>>{

	@Override
	public boolean add(Pair<Integer, Edge> e) {
		int index = 0;
		if(this.isEmpty()) {
			super.add(e);
			return true;
		}
		if(e.getValue().compareTo(this.getFirst().getValue()) < 0) {
			super.addFirst(e);
			return true;
		}
		for (Iterator iterator = this.iterator(); iterator.hasNext();) {
			index++;
			Pair<Integer, Edge> edge = (Pair<Integer, Edge>) iterator.next();
			if(e.getValue().compareTo(edge.getValue()) == 0) return false;
			if(e.getValue().compareTo(edge.getValue()) < 0) {
				super.add(index - 1, e);
				return true;
			}
		}
		super.add(e);
		return true;
	}

	
}
