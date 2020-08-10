package com.mguo.sorts.compareSort.exchange;

import com.mguo.sorts.SwapUtil;

public class PopSort {
	/**
	 * pop sort
	 * 
	 * stable
	 * 
	 * O(n2)
	 * 
	 * @param arr
	 */
	public static void popSort(int[] arr) {
		if (arr == null || arr.length == 0) {
			throw new IllegalArgumentException();
		}

		int length = arr.length;
		if (arr.length > 2) {

			for (int i = 0; i < length - 1; i++) {
				for (int j = 0; j < length - i - 1; j++) {
					if (arr[j] > arr[j + 1]) {
						SwapUtil.intArraySwap(arr, j, j + 1);
					}
				}
			}

		}
	}
}
