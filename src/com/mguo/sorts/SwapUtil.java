package com.mguo.sorts;

public class SwapUtil {

	/**
	 * swap value at index0 and index1 in arr
	 * 
	 * @param arr
	 * @param index0
	 * @param index1
	 */
	public static void arrSwap(Object[] arr, int index0, int index1) {
		if (arr == null || index0 >= arr.length || index1 >= arr.length) {
			throw new IllegalArgumentException();
		}

		if (index0 != index1) {
			Object temp = arr[index0];
			arr[index0] = arr[index1];
			arr[index1] = temp;
		}
	}

	/**
	 * swap int value at index0 and index1 in arr
	 * 
	 * @param arr
	 * @param index0
	 * @param index1
	 */
	public static void intArraySwap(int[] arr, int index0, int index1) {
		if (arr == null || index0 >= arr.length || index1 >= arr.length) {
			throw new IllegalArgumentException();
		}
		if (index0 != index1) {
			arr[index0] = arr[index0] ^ arr[index1];
			arr[index1] = arr[index0] ^ arr[index1];
			arr[index0] = arr[index0] ^ arr[index1];
		}
	}

}
