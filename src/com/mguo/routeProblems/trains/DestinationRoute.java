package com.mguo.routeProblems.trains;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DestinationRoute {
	private String destination;

	private Map<String, Float> routes;

	public DestinationRoute(String destination) {
		this.destination = destination;
		routes = new HashMap<String, Float>();
	}

	public void addRoute(String startingPoint, Float distance) {

		if (routes.containsKey(startingPoint)) {
			throw new IllegalArgumentException("Route already exists");
		}

		routes.put(startingPoint, distance);
	}

	public void updateRoute(String startingPoint, Float distance) {

		if (distance.floatValue() <= 0) {
			throw new IllegalArgumentException("Distance must be positive");
		}

		if (!routes.containsKey(startingPoint)) {
			throw new IllegalArgumentException("Route not exists");
		}

		routes.put(startingPoint, distance);
	}

	public Float getDistance(String startingPoint) {
		if (startingPoint == null || startingPoint.trim().length() == 0) {
			throw new IllegalArgumentException("Destination can't be empty");
		}

		return routes.get(startingPoint.trim());
	}

	public String getDestination() {
		return destination;
	}

	public Set<String> neighbors() {
		return routes.keySet();
	}

	public boolean hasNeighbour(String startingPoint) {
		if (startingPoint == null || startingPoint.trim().length() == 0) {
			throw new IllegalArgumentException("Destination can't be empty");
		}

		return routes.containsKey(startingPoint.trim());

	}
}
