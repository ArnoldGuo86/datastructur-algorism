package com.mguo.sorts.nonCompareSort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RadixSort {

	public static void main(String[] args) {

		int arr[] = { 3, 9, 5, -3214, -3233, -4444, -3333, 7, 11, -32, -45, 22, 99, 54, 6, 4, 8, 15, 34, 44, 55, 200,
				300, 210, 222, 321, 0, -200, -555, -101, -99 };
		radixSort(arr);

		for (int i : arr) {
			System.out.println(i);
		}

	}

	/**
	 * radix sort
	 * 
	 * stable
	 * 
	 * O(d(n+r))
	 * 
	 * @param arr
	 */
	public static void radixSort(int[] arr) {
		int max = findoutMaximumAbs(arr);

		int length = getHighOrdrDigitLength(max);

		// the bucket contains 20 positions include positive numbers and negative
		// numbers
		List<List<Integer>> bucket = new ArrayList<List<Integer>>();

		int[] temp = new int[arr.length];
		System.arraycopy(arr, 0, temp, 0, arr.length);

		for (int i = 1; i <= length; i++) {
			// clear bucket and initialize before sort base on different radix: 1, 10, 100,
			// 1000, 10000... note that integer max value is 2147483647 which means it
			// supports up to 10 digital
			init(bucket);
			int radix = getRadix(i);

			for (int index = 0; index < temp.length; index++) {
				int numb = temp[index];

				int mod = (numb / radix) % 10;

				if (numb >= 0) {
					// positive number should be store from 10 to 19 position based on mod result
					mod += 10;
				} else {
					// note negative numbers mod result should be store in reverse order
					mod += 9;
				}

				List<Integer> list = bucket.get(mod);
				list.add(numb);
			}

			int tempIndex = 0;
			for (int k = 0; k < bucket.size(); k++) {
				List<Integer> list = bucket.get(k);
				if (list.size() > 0) {
					for (Iterator<Integer> it = list.iterator(); it.hasNext();) {
						temp[tempIndex++] = it.next();
					}
				}
			}
		}

		System.arraycopy(temp, 0, arr, 0, arr.length);
	}

	// use list instead of array[][] to simplify structure operation
	private static void init(List<List<Integer>> list) {
		list.clear();

		for (int i = 0; i < 20; i++) {
			list.add(new ArrayList<Integer>());
		}
	}

	private static int findoutMaximumAbs(int[] arr) {
		int max = Math.abs(arr[0]);

		for (int i = 1; i < arr.length; i++) {
			max = Math.max(max, Math.abs((arr[i])));
		}

		return max;
	}

	/**
	 * @param number
	 * @return length of digital
	 */
	private static int getHighOrdrDigitLength(int number) {
		int radix = 10;
		int length = 1;

		number /= radix;
		while (number != 0) {
			length++;
			number /= radix;
		}
		return length;
	}

	private static int getRadix(int i) {
		return (int) Math.pow(10, i - 1);
	}
}
