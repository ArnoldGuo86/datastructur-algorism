package com.mguo.routeProblems.trains;

import java.util.LinkedList;
import java.util.List;

public class Trace {
	List<TraceItem> path;

	Float distance = 0f;

	public Trace() {
		path = new LinkedList<>();
	}

	public List<TraceItem> getPath() {
		return path;
	}

	public void setPath(List<TraceItem> path) {
		this.path = path;
	}

	public Float getDistance() {
		return distance;
	}

	public void setDistance(Float distance) {
		this.distance = distance;
	}

	public void addTracItem(TraceItem item) {
		path.add(item);
		distance += item.getDistance();
	}

	public void addTracItemAtFirst(TraceItem item) {
		path.add(0, item);
		distance += item.getDistance();
	}

	public void removeItem(TraceItem item) {
		path.remove(item);
		distance -= item.getDistance();
	}

	public void removeItem(int index) {
		TraceItem removeItem = path.remove(index);
		distance -= removeItem.getDistance();
	}

	@Override
	public String toString() {
		return "Trace [path=" + path + ", distance=" + distance + "]";
	}

}
