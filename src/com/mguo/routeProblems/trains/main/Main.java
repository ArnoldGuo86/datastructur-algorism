package com.mguo.routeProblems.trains.main;

import java.util.List;

import com.mguo.routeProblems.trains.DirectedRouteMap;
import com.mguo.routeProblems.trains.Trace;
import com.mguo.routeProblems.trains.util.Parser;

public class Main {
	public static void main(String[] args) {
		DirectedRouteMap map = Parser.init();

	}

	private static void execute(DirectedRouteMap map) {

		// 1
		// The distance of the route A-B-C.
		String abcDistance = map.getDistanceByTrace("A", "B", "C");
		System.out.println("A->B->C: " + abcDistance);

		// 2
		// he distance of the route A-D
		String adDistance = map.getDistanceByTrace("A", "D");
		System.out.println("A->D: " + adDistance);

		// 3
		// The distance of the route A-D-C
		String adcDistance = map.getDistanceByTrace("A", "D", "C");
		System.out.println("A->D->C: " + adcDistance);

		// 4
		// The distance of the route A-E-B-C-D
		String aebcdDistance = map.getDistanceByTrace("A", "E", "B", "C", "D");
		System.out.println("A->E->B->C->D: " + aebcdDistance);

		// 5
		// The distance of the route A-E-D.
		String aedDistance = map.getDistanceByTrace("A", "E", "D");
		System.out.println("A->E->D: " + aedDistance);

		// 6
		// The number of trips starting at C and ending at C with a maximum of 3 stops.
		// In the sample data below, there are two such trips: C-D-C (2 stops). and
		// C-E-B-C (3 stops).
		List<Trace> ccTrace = map.getTracesWithCircle("C", "C", 3);
		System.out.println(ccTrace);

		// 7
		// The number of trips starting at A and ending at C with exactly 4 stops. In
		// the sample data below, there are three such trips: A to C (via B,C,D); A to C
		// (via D,C,D); and A to C (via D,E,B).
		List<Trace> acTraces = map.getTracesWithCircle("A", "C", 4, true);
		System.out.println(acTraces);

		// 8
		// The length of the shortest route (in terms of distance to travel) from A to C
		// DFS
		List<Trace> traces = map.getTraces("A", "C");
		Trace shortestTrace = null;

		for (Trace trace : traces) {
			if (shortestTrace == null || trace.getDistance() < shortestTrace.getDistance()) {
				shortestTrace = trace;
			}
		}
		System.out.println("shortest trac: A->C by dfs: " + shortestTrace);

		// dijkstra
		Trace shortestTrace2 = map.getShortestTrace("A", "C");
		System.out.println("shortest trac: A->C by dijkstra: " + shortestTrace2);

		// 9
		// The length of the shortest route (in terms of distance to travel) from B
		// to B
		Trace minimumCircle = map.getMinimumCircle("B");
		System.out.println(minimumCircle);
	}
}
