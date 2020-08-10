package com.mguo.tournament;

public class Tournament {

	// best way to find the max value besides/except max value
	// O(nlog2n)
	public static int[] tournament(int[] arr) {
		if (arr == null || arr.length < 2) {
			throw new IllegalArgumentException();
		}
		int length = arr.length;

		if (length == 2) {
			return new int[] { Math.max(arr[0], arr[1]), Math.min(arr[0], arr[1]) };
		}

		int[] tempArr = new int[length * 2];

		System.arraycopy(arr, 0, tempArr, length, length);

		int index = 2 * length - 1;

		while (index > 1) {

			tempArr[index / 2] = Math.max(tempArr[index], tempArr[index - 1]);

			index -= 2;
		}

		int maxValue = tempArr[1];

		// make index point to the max value
		index = 1;

		Integer secondValue = null;

		while (2 * index + 1 < tempArr.length) {

			if (tempArr[2 * index] == maxValue) {
				secondValue = secondValue == null ? tempArr[2 * index + 1]
						: Math.max(secondValue, tempArr[2 * index + 1]);

				index = 2 * index;
			} else {
				// this means the max value is tempArr[2 * index + 1]

				secondValue = secondValue == null ? tempArr[2 * index] : Math.max(secondValue, tempArr[2 * index]);

				index = 2 * index + 1;
			}

		}

		return new int[] { maxValue, secondValue };
	}
}
