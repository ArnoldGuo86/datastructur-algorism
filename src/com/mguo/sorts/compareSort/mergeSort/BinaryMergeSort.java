package com.mguo.sorts.compareSort.mergeSort;

public class BinaryMergeSort {
	/**
	 * merge sort
	 * 
	 * stable
	 * 
	 * O(nlog2n)
	 * 
	 * @param arr
	 */
	public static void binaryMergeSort(int[] arr) {
		divide(arr, 0, arr.length - 1);
	}

	private static void divide(int[] arr, int low, int high) {
		if (low >= high) {
			return;
		}
		// divide part of arr from low to high into two arrays logically
		// left part is arr[low..mid] and right part is arr[mid +1, high]
		int mid = (low + high) >>> 1;
		divide(arr, 0, mid);
		divide(arr, mid + 1, high);

		merge(arr, low, mid, high);

	}

	private static void merge(int[] arr, int low, int mid, int high) {

		// left array index pointer
		int lIndex = low;

		// right array index pointer
		int rIndex = mid + 1;

		// use extra space to contain sorted lements
		int[] temp = new int[high - low + 1];
		int tempIndex = 0;

		// merge
		while (lIndex <= mid && rIndex <= high) {
			if (arr[lIndex] <= arr[rIndex]) {
				temp[tempIndex++] = arr[lIndex++];
			} else {
				temp[tempIndex++] = arr[rIndex++];
			}

		}

		// when left array has elements not merged, copy them to the end of temp array
		while (lIndex <= mid) {
			temp[tempIndex++] = arr[lIndex++];
		}

		// when right array has elements not merged, copy them to the end of temp array
		while (rIndex <= high) {
			temp[tempIndex++] = arr[rIndex++];
		}
		System.arraycopy(temp, 0, arr, low, (high - low + 1));
	}
}
