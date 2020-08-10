package com.mguo.sorts.compareSort.insertSort;

public class SimpleInsertSort {

	/**
	 * simple insert sort
	 * 
	 * stable
	 * 
	 * O(n2)
	 * 
	 */
	public static void simpleInsertSort(int[] arr) {

		if (arr == null) {
			throw new IllegalArgumentException();
		}
		int length = arr.length;
		if (length > 2) {

			for (int i = 1; i < length; i++) {
				int temp = arr[i];
				for (int j = i; j >= 0; j--) {
					if (j > 0 && arr[j - 1] > temp) {
						arr[j] = arr[j - 1];
					} else {
						arr[j] = temp;
						break;
					}
				}
			}
		}

	}
}
