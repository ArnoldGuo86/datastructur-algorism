package com.mguo.sorts.nonCompareSort;

public class CountSort {

	public static void main(String[] args) {
		int[] arr = { -3, -2, -4, -5, -2, -1, 0, -2, 0, 1, 2, 5, 3, 2, 5, 4, 0, 2, -1, -2, -1 };

		countSort(arr);

		for (int i : arr) {
			System.out.println(i);
		}
	}

	/**
	 * count sort
	 * 
	 * stable
	 * 
	 * O(n+k) n: array lngth k: max value in array
	 * 
	 * count sort many numbers in an array but in a narrow number range
	 * 
	 * it need extra n + k
	 * 
	 * @param arr
	 */
	public static void countSort(int[] arr) {
		ArrayProperty arrayProperty = getArrayProperty(arr);

		int[] counterArr = new int[arrayProperty.maximumValue - arrayProperty.minimumValue + 1];

		for (int i = 0; i < arr.length; i++) {
			int number = arr[i];
			counterArr[number - arrayProperty.minimumValue]++;
		}

		int[] temp = new int[arr.length];
		int tempIndex = 0;
		for (int index = 0; index < counterArr.length; index++) {
			int num = counterArr[index];
			if (num > 0) {
				for (int j = 0; j < num; j++) {
					temp[tempIndex++] = index + arrayProperty.minimumValue;
				}
			}
		}

		System.arraycopy(temp, 0, arr, 0, arr.length);
	}

	private static ArrayProperty getArrayProperty(int[] arr) {
		ArrayProperty p = new ArrayProperty();
		p.arrayLength = arr.length;

		int max = arr[0];
		int min = arr[0];

		for (int i = 1; i < arr.length; i++) {
			max = Math.max(max, arr[i]);
			min = Math.min(min, arr[i]);

			if (arr[i] < 0) {
				p.negativeNum++;
			}
		}

		p.maximumValue = max;
		p.minimumValue = min;
		// we suppose 0 is positive number
		p.positiveNum = arr.length - p.negativeNum;

		return p;
	}

	private static class ArrayProperty {
		private int arrayLength;

		private int positiveNum;

		private int negativeNum;

		private int maximumValue;

		private int minimumValue;
	}
}
