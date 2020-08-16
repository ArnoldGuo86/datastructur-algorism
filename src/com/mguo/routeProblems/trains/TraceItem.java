package com.mguo.routeProblems.trains;

public class TraceItem {
	private String startingPoint;

	private String destination;

	private Float distance;

	public String getStartingPoint() {
		return startingPoint;
	}

	public TraceItem(String startingPoint, String destination, Float distance) {
		super();
		this.startingPoint = startingPoint;
		this.destination = destination;
		this.distance = distance;
	}

	public void setStartingPoint(String startingPoint) {
		this.startingPoint = startingPoint;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Float getDistance() {
		return distance;
	}

	public void setDistance(Float distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return startingPoint + " -->" + destination + ": " + distance;
	}
}
