package com.mguo.routeProblems.trains;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DirectedRouteMap {

	private static final String ERROR_ROUTE = "NO SUCH ROUTE";

	private Map<String, Route> map;

	private Map<String, DestinationRoute> destinationMap;

	private Map<String, Map<String, Float>> disMap;

	private Map<String, Map<String, String>> pre;

	private Set<String> points;

	// cache dijkstra rsult
	private Map<String, Map<String, Trace>> shortestCache;

	// private
	public DirectedRouteMap() {
		map = new HashMap<String, Route>();
		destinationMap = new HashMap<String, DestinationRoute>();
		disMap = new HashMap<String, Map<String, Float>>();
		pre = new HashMap<String, Map<String, String>>();
		points = new HashSet<String>();

		shortestCache = new HashMap<String, Map<String, Trace>>();

	}

	public void addRoute(String startingPoint, String destination, Float distance) {
		if (startingPoint == null || startingPoint.trim().length() == 0) {
			throw new IllegalArgumentException("StartingPoinst can't be empty");
		}

		if (destination == null || destination.trim().length() == 0) {
			throw new IllegalArgumentException("Destination can't be empty");
		}

		if (distance.floatValue() <= 0) {
			throw new IllegalArgumentException("Distance must be positive");
		}

		startingPoint = startingPoint.trim();
		destination = destination.trim();

		if (!map.containsKey(startingPoint)) {
			map.put(startingPoint, new Route(startingPoint));
		}

		Route route = map.get(startingPoint);
		route.addRoute(destination, distance);

		if (!disMap.containsKey(startingPoint)) {
			disMap.put(startingPoint, new HashMap<String, Float>());
		}

		Map<String, Float> destinationDisMap = disMap.get(startingPoint);
		destinationDisMap.put(destination, distance);

		if (!destinationMap.containsKey(destination)) {
			destinationMap.put(destination, new DestinationRoute(destination));
		}
		DestinationRoute destinationRoute = destinationMap.get(destination);

		destinationRoute.addRoute(startingPoint, distance);

		if (!pre.containsKey(startingPoint)) {
			HashMap<String, String> m = new HashMap<>();
			pre.put(startingPoint, new HashMap<>());
		}
		pre.get(startingPoint).put(destination, startingPoint);

		points.add(startingPoint);
		points.add(destination);
	}

	Float getNeighbourDistance(String startingPoint, String destination) {
		if (startingPoint == null || startingPoint.trim().length() == 0) {
			throw new IllegalArgumentException("StartingPoinst can't be empty");
		}

		if (destination == null || destination.trim().length() == 0) {
			throw new IllegalArgumentException("Destination can't be empty");
		}

		startingPoint = startingPoint.trim();
		destination = destination.trim();

		Float distance = null;

		if (startingPoint.equals(destination)) {
			distance = 0f;
		} else {

			Route route = map.get(startingPoint);
			if (route != null) {
				distance = route.getDistance(destination);
			}
		}
		return distance;
	}

	public Set<String> canStartPoints() {
		return map.keySet();

	}

	public Set<String> canTerminatePoints() {
		return destinationMap.keySet();
	}

	public Set<String> neighboursFrom(String startingPosition) {
		if (startingPosition == null || startingPosition.trim().length() == 0) {
			throw new IllegalArgumentException("StartingPoinst can't be empty");
		}

		startingPosition = startingPosition.trim();

		return map.get(startingPosition).neighbors();
	}

	public Set<String> neighboursTo(String destination) {
		if (destination == null || destination.trim().length() == 0) {
			throw new IllegalArgumentException("StartingPoinst can't be empty");
		}

		destination = destination.trim();

		return destinationMap.get(destination).neighbors();
	}

	public Set<String> getAllPoints() {
		return points;
	}

	public String getDistanceByTrace(String... trace) {
		List<String> list = Arrays.asList(trace);
		return getDistanceByTrace(list);
	}

	public String getDistanceByTrace(List<String> trace) {
		if (trace == null || trace.size() < 2) {
			throw new IllegalArgumentException("Trace cannot be empty and should have at least 2 points!!");
		}

		String startingPoint = trace.get(0);
		String result = null;

		Float distance = new Float(0);

		for (int i = 1; i < trace.size(); i++) {
			String dest = trace.get(i);

			Float neighbourDistance = getNeighbourDistance(startingPoint, dest);
			if (neighbourDistance == null) {
				result = ERROR_ROUTE;
				break;
			} else {
				distance += neighbourDistance;
				// set up new starting position
				startingPoint = dest;
			}
		}

		if (result == null) {
			result = String.valueOf(distance);
		}

		return result;
	}

	private Trace buildTraces(List<String> list) {

		Trace trace = new Trace();
		String starting = list.get(0);

		for (int i = 1; i < list.size(); i++) {

			String next = list.get(i);

			Float neighbourDistance = getNeighbourDistance(starting, next);

			trace.addTracItem(new TraceItem(starting, next, neighbourDistance));

			starting = next;
		}

		return trace;
	}

	public List<Trace> getTraces(String startingPosition, String destination) {
		return getTraces(startingPosition, destination, -1, false);
	}

	public List<Trace> getTraces(String startingPosition, String destination, int maxSteps) {
		return getTraces(startingPosition, destination, maxSteps, false);
	}

	public List<Trace> getTraces(String startingPosition, String destination, int maxSteps, boolean isExact) {

		if (maxSteps <= 0) {
			maxSteps = Integer.MAX_VALUE;
		}
		Set<String> visited = new HashSet<String>();
		List<String> tracer = new LinkedList<String>();

		List<Trace> results = new ArrayList<Trace>();

		dfs(startingPosition, destination, maxSteps, isExact, tracer, visited, results);

		return results;
	}

	/**
	 * this function can get path without circle from the route map
	 * 
	 * 
	 * @param startingPosition from this position to search
	 * @param destination      the target
	 * @param steps            the max number of stops can include in results
	 * @param isExact          if true, the results include steps + 1 stops(include
	 *                         staring point, if isExact is true, steps must be
	 *                         positive)
	 * @param pathTracer       help keep the visit trace
	 * @param visited          mark whether a point is visited, if a point exists in
	 *                         the set, means it is visited. Just use a set instead
	 *                         of typical visited array to make it flexible and more
	 *                         object oriented.
	 * @param results          a list to collect results
	 */
	private void dfs(String startingPosition, String destination, int steps, boolean isExact, List<String> pathTracer,
			Set<String> visited, List<Trace> results) {

		if (startingPosition.equals(destination) && pathTracer.size() > 1) {

			// finish the recursion if the new started position is the destination
			boolean isValidResult = true;
			if (isExact) {
				isValidResult &= pathTracer.size() == steps;
			}
			// found, push the destination into stack to simplify trace build, remove it
			// after trace built
			if (isValidResult) {

				pathTracer.add(destination);
				results.add(buildTraces(pathTracer));
				pathTracer.remove(pathTracer.size() - 1);
			}
			return;
		}

		// step 1: start from startingPosition, mark it is visited, and record it in
		// tracer
		pathTracer.add(startingPosition);
		visited.add(startingPosition);

		// if still not found and current steps in path tracer is great than maxSteps,
		// finish processing
		// for instance we need find A->C less than 3 tops(include 3)
		// A->B->D->C is valid. if in tracer current elements are:
		// A->E->B->D, stop searching, due to the results we can find next step will
		// have more than 3 stops
		if (pathTracer.size() - 1 >= steps) {
			pathTracer.remove(pathTracer.size() - 1);
			visited.remove(startingPosition);
			return;
		}

		// step 2: find all reachable points from startingPosition
		for (Iterator<String> it = neighboursFrom(startingPosition).iterator(); it.hasNext();) {
			String next = it.next();

			// if the point is not visited, use this point as new starting point and find
			// the target destination by recursive call
			if (!visited.contains(next)) {
				dfs(next, destination, steps, isExact, pathTracer, visited, results);

			}
		}

		// step 3: if all reachable points are processed, flash back
		// Note: it's important to remove it's visit status.
		// for instance: we have two ways from A to C:
		// A->B->C
		// and
		// A->D->B->C
		// after processing A->B if B visit status is still visited, when processing
		// A->D->B, B is in visited status, then recursive invocation will skip
		// processing B
		// and cannot get A->D->B->C
		visited.remove(startingPosition);
		pathTracer.remove(pathTracer.size() - 1);
	}

	private void dfsWithCircle(String startingPosition, String destination, int steps, boolean isExact,
			List<String> pathTracer, List<Trace> results) {
		boolean isValidResult = pathTracer.size() > 1;
		if (isExact) {
			isValidResult &= pathTracer.size() == steps;
		}
		if (startingPosition.equals(destination) && isValidResult) {

			// found, push the destination into stack to simplify trace build, remove it
			// after trace built

			pathTracer.add(destination);
			results.add(buildTraces(pathTracer));
			pathTracer.remove(pathTracer.size() - 1);
		}

		pathTracer.add(startingPosition);

		if (pathTracer.size() - 1 >= steps) {
			pathTracer.remove(pathTracer.size() - 1);
			return;
		}

		for (Iterator<String> it = neighboursFrom(startingPosition).iterator(); it.hasNext();) {
			String next = it.next();
			dfsWithCircle(next, destination, steps, isExact, pathTracer, results);

		}

		pathTracer.remove(pathTracer.size() - 1);
	}

	public List<Trace> getTracesWithCircle(String startingPosition, String destination, int maxSteps) {
		return getTracesWithCircle(startingPosition, destination, maxSteps, false);
	}

	public List<Trace> getTracesWithCircle(String startingPosition, String destination, int maxSteps, boolean isExact) {
		List<String> tracer = new LinkedList<String>();

		List<Trace> results = new ArrayList<Trace>();

		dfsWithCircle(startingPosition, destination, maxSteps, isExact, tracer, results);

		return results;
	}

	public Float getDistance(List<TraceItem> pathTrace) {
		Float distance = 0f;
		for (TraceItem trace : pathTrace) {
			distance += trace.getDistance();
		}

		return distance;
	}

	public Trace getShortestTrace(String startingPoint, String destination) {
		Map<String, Trace> shortestTraces = getShortestTracesFrom(startingPoint);
		Trace trace = shortestTraces.get(destination);
		return trace;
	}

	public Map<String, Trace> getShortestTracesFrom(String startingPoint) {
		Map<String, Trace> result = shortestCache.get(startingPoint);
		if (result != null) {
			return result;
		}

		result = new HashMap<>();
		// previous point
		Map<String, String> pre = new HashMap<String, String>();

		// store distance to each point from starting point, key is destination, if
		// cannot find the destination in key set, means that point is not reachable
		// from starting point
		Map<String, Float> distanceMap = new HashMap<String, Float>();

		dijkstra(startingPoint, distanceMap, pre);

		for (Iterator<String> it = distanceMap.keySet().iterator(); it.hasNext();) {
			String destination = it.next();
			Trace trace = buildTraceFrom(distanceMap, pre, startingPoint, destination);

			result.put(destination, trace);
		}

		return result;
	}

	private void dijkstra(String startingPoint, Map<String, Float> distanceMap, Map<String, String> pre) {
		// map instance can be consider as the matrix in dijkstra

		// visited set, if exists in the set, means it is visited
		Set<String> visit = new HashSet<String>();

		// init
		distanceMap.put(startingPoint, getNeighbourDistance(startingPoint, startingPoint));

		while (startingPoint != null) {
			visit.add(startingPoint);
			update(startingPoint, distanceMap, pre, visit);
			startingPoint = getNextNearestUnvistedPointFromDistMap(distanceMap, visit);
		}

//		System.out.println(distanceMap);
	}

	private String getNextNearestUnvistedPointFromDistMap(Map<String, Float> distanceMap, Set<String> visit) {
		Float min = Float.MAX_VALUE;
		String nextPoint = null;

		for (Iterator<String> it = distanceMap.keySet().iterator(); it.hasNext();) {
			String next = it.next();
			Float currntDistance = distanceMap.get(next);
			if (!visit.contains(next) && currntDistance < min) {
				min = currntDistance;
				nextPoint = next;
			}
		}

		return nextPoint;
	}

	private void update(String startingPoint, Map<String, Float> distanceMap, Map<String, String> pre,
			Set<String> visit) {

		Set<String> neighbours = neighboursFrom(startingPoint);
		for (Iterator<String> it = neighbours.iterator(); it.hasNext();) {
			String next = it.next();

			// update the distance from starting position to neighbours in distance map
			Float existDis = distanceMap.get(next);
			if (existDis != null) {
			}

			existDis = existDis == null ? Float.MAX_VALUE : existDis;

			Float newDis = distanceMap.get(startingPoint) + getNeighbourDistance(startingPoint, next);
			if (!visit.contains(next) && newDis < existDis) {

				// update distance map
				distanceMap.put(next, newDis);

				//
				// System.out.println("Before update:");
				// System.out.println(buildTraceFrom(distanceMap, pre, startingPoint, next));

				// update previous points

				pre.put(next, startingPoint);

				// System.out.println("After update:");
				// System.out.println(buildTraceFrom(distanceMap, pre, startingPoint, next));
				//
				// System.out.println("======================================");
			}
		}
	}

	private Trace buildTraceFrom(Map<String, Float> distanceMap, Map<String, String> pre, String startingPoint,
			String destination) {
		Trace trace = new Trace();
		String prePoint = pre.get(destination);
		while (prePoint != null) {
			trace.addTracItemAtFirst(new TraceItem(prePoint, destination, getNeighbourDistance(prePoint, destination)));
			destination = prePoint;
			prePoint = pre.get(destination);
		}

		return trace;
	}

	public Trace getMinimumCircle(String point) {
		this.floyd();

		Map<String, Float> toMap = disMap.get(point);

		Set<String> canTrans = new HashSet<String>();

		for (String trans : toMap.keySet()) {
			if (disMap.get(trans).containsKey(point)) {
				canTrans.add(trans);
			}
		}

		if (canTrans.isEmpty()) {
			return null;
		}

		Trace minTrace = null;

		for (String trans : canTrans) {
			Trace trace1 = buildCircle(point, trans);
			Trace trace2 = buildCircle(trans, point);

			Trace fullTrace = new Trace();
			for (TraceItem item : trace1.getPath()) {
				fullTrace.addTracItem(item);
			}

			for (TraceItem item : trace2.getPath()) {
				fullTrace.addTracItem(item);
			}

			if (minTrace == null) {
				minTrace = fullTrace;
			} else {
				if (fullTrace.getDistance() < minTrace.getDistance()) {
					minTrace = fullTrace;
				}
			}
		}

		return minTrace;
	}

	private Trace buildCircle(String point, String transPoint) {
		Trace trace = new Trace();
		String prePoint = pre.get(point).get(transPoint);

		while (prePoint != null) {
			trace.addTracItemAtFirst(new TraceItem(prePoint, transPoint, getNeighbourDistance(prePoint, transPoint)));
			if (prePoint.equals(point)) {
				prePoint = null;
			}
			transPoint = prePoint;
			prePoint = pre.get(point).get(prePoint);

		}

		return trace;
	}

	private void floyd() {

		Set<String> canStartPoints = canStartPoints();
		Set<String> canTerminatePoints = canTerminatePoints();
		// a point has in edge and out edge can be a transfer point

		HashSet<String> canTransferPoints = new HashSet<String>();
		canTransferPoints.addAll(canStartPoints);
		canTransferPoints.retainAll(canTerminatePoints);

		// typical floyd, triple loops
		for (Iterator<String> it = canTransferPoints.iterator(); it.hasNext();) {
			String transferPoint = it.next();

			// loop the previous points
			Set<String> preNeighbours = neighboursTo(transferPoint);
			for (Iterator<String> itPre = preNeighbours.iterator(); itPre.hasNext();) {
				String prePoint = itPre.next();

				// loop the next points
				Set<String> nextNeighbours = neighboursFrom(transferPoint);
				for (Iterator<String> itNext = nextNeighbours.iterator(); itNext.hasNext();) {
					String nextPoint = itNext.next();

					Float distancWithTransfer = disMap.get(prePoint).get(transferPoint)
							+ disMap.get(transferPoint).get(nextPoint);

					Float distanceWithoutTransfer = disMap.get(prePoint).get(nextPoint);
					if (distanceWithoutTransfer == null) {
						distanceWithoutTransfer = Float.MAX_VALUE;
					}

					if (distancWithTransfer < distanceWithoutTransfer) {
						disMap.get(prePoint).put(nextPoint, distancWithTransfer);
						if (!pre.containsKey(prePoint)) {
							HashMap<String, String> m = new HashMap<>();
							m.put(nextPoint, prePoint);
							pre.put(prePoint, m);
						}
						pre.get(prePoint).put(nextPoint, transferPoint);
					}
				}
			}
		}

		System.out.println(disMap);
		System.out.println(pre);
	}
}
