package com.mguo.routeProblems.trains;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class Route {
	private String startingPoint;

	private Map<String, Float> routes;

	public Route(String startingPoint) {
		this.startingPoint = startingPoint;
		routes = new HashMap<String, Float>();
	}

	public void addRoute(String startingPoint, Float distance) {

		if (routes.containsKey(startingPoint)) {
			throw new IllegalArgumentException("Route already exists");
		}

		routes.put(startingPoint, distance);
	}

	public void updateRoute(String destination, Float distance) {

		if (distance.floatValue() <= 0) {
			throw new IllegalArgumentException("Distance must be positive");
		}

		if (!routes.containsKey(destination)) {
			throw new IllegalArgumentException("Route not exists");
		}

		routes.put(destination, distance);
	}

	public Float getDistance(String destination) {
		if (destination == null || destination.trim().length() == 0) {
			throw new IllegalArgumentException("Destination can't be empty");
		}

		return routes.get(destination.trim());
	}

	public String getStartingPoint() {
		return startingPoint;
	}

	public Set<String> neighbors() {
		return routes.keySet();
	}

	public boolean hasNeighbour(String destination) {
		if (destination == null || destination.trim().length() == 0) {
			throw new IllegalArgumentException("Destination can't be empty");
		}

		return routes.containsKey(destination.trim());

	}
}
