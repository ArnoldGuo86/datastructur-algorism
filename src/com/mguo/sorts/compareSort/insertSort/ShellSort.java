package com.mguo.sorts.compareSort.insertSort;

public class ShellSort {

	/**
	 * shell sort
	 * 
	 * unstable
	 * 
	 * O(nlog2n)
	 * 
	 * @param arr
	 */
	public static void shellSort(int[] arr) {
		if (arr == null) {
			throw new IllegalArgumentException();
		}

		if (arr.length > 2) {

			// increment
			for (int gap = arr.length / 2; gap > 0; gap /= 2) {

				// simple insert sort for each group
				for (int i = gap; i < arr.length; i += gap) {
					int j = i;
					int temp = arr[j];

					while (j >= gap && temp < arr[j - gap]) {
						arr[j] = arr[j - gap];
						j -= gap;
					}

					arr[j] = temp;
				}
			}
		}
	}
}
