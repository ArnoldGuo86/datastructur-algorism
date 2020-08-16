package com.mguo.routeProblems.trains.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import com.mguo.routeProblems.trains.DirectedRouteMap;

public class Parser {

	public static DirectedRouteMap init() {
		String initFilePath = "com/mguo/routeProblems/trains/input";

		URL resource = Parser.class.getClassLoader().getResource(initFilePath);

		File file = new File(resource.getFile());
		BufferedReader reader = null;

		FileReader in = null;
		DirectedRouteMap map = new DirectedRouteMap();
		try {
			in = new FileReader(file);
			reader = new BufferedReader(in);
			String content = reader.readLine();

			String[] split = content.split(",");

			for (String str : split) {
				str = str.trim();

				String startingPoinst = str.substring(0, 1);
				String destination = str.substring(1, 2);

				float distance = Float.parseFloat(str.substring(2));

				map.addRoute(startingPoinst, destination, distance);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();

				}

				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return map;
	}
}
