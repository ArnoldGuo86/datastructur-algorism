package com.mguo.sorts.compareSort.selectSort;

import com.mguo.sorts.SwapUtil;

public class SimpleSelectSort {

	/**
	 * simple select sort
	 * 
	 * unstable
	 * 
	 * O(n2)
	 * 
	 * @param arr
	 */
	public static void simpleSelectSort(int[] arr) {
		if (arr == null) {
			throw new IllegalArgumentException();
		}

		if (arr.length > 2) {
			for (int i = 0; i < arr.length - 1; i++) {
				int minIndex = i;

				for (int j = i + 1; j < arr.length; j++) {
					if (arr[j] < arr[minIndex]) {
						minIndex = j;
					}
				}

				if (minIndex != i) {
					SwapUtil.intArraySwap(arr, i, minIndex);
				}
			}
		}
	}
}
