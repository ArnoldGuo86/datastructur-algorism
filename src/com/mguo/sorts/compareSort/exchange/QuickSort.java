package com.mguo.sorts.compareSort.exchange;

import com.mguo.sorts.SwapUtil;

public class QuickSort {

	/**
	 * quick sort
	 * 
	 * O(nlog2n)
	 * 
	 * unstable
	 * 
	 * @param arr
	 */
	public static void quickSort(int[] arr) {
		sort(arr, 0, arr.length - 1);
	}

	private static int partition(int[] arr, int low, int high) {

		int pivot = arr[low];

		while (low < high) {
			while (pivot < arr[high] && low < high) {
				high--;
			}
			SwapUtil.intArraySwap(arr, high, low);

			while (pivot > arr[low] && low < high) {
				low++;
			}

			SwapUtil.intArraySwap(arr, high, low);
		}

		return low;
	}

	private static void sort(int[] arr, int low, int high) {

		if (low < high) {
			int index = partition(arr, low, high);

			sort(arr, low, index - 1);

			sort(arr, index + 1, high);
		}
	}
}
