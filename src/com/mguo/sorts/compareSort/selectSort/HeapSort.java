package com.mguo.sorts.compareSort.selectSort;

import com.mguo.sorts.SwapUtil;

public class HeapSort {

	public static void main(String[] args) {
		int arr[] = { 3, 9, 5, 7, 11, 22, 99, 54, 6, 4, 8, 15, 34, 44, 55, 200, 300, 210, 222, 321 };
		heapSort(arr);

		for (int i : arr) {
			System.out.println(i);
		}
	}

	/**
	 * 
	 * @param arr
	 */
	public static void heapSort(int[] arr) {
		// first step is to set up a heap
		buildHeap(arr);

		for (int i = arr.length - 1; i >= 0; i--) {
			// after heapify each time, the max value must be at heap top, which means it's
			// index is 0, exchange it with the last node, and when execute heapify again,
			// the length should minus 1 each time which means the biggest one is already at
			// last and we don't need sort it again. Therefore length is i.
			SwapUtil.intArraySwap(arr, i, 0);

			// adjust heap structure on remain nodes
			heapify(arr, 0, i);
		}
	}

	private static void buildHeap(int[] arr) {
		int lastNodeIndex = arr.length - 1;

		int parent = (lastNodeIndex - 1) / 2;

		// from last non-leaf node, adjust parent and children index to build up a heap
		// move to previous non-leaf node one-by-one, and make each non-leaf node
		// satisfy heap rule
		for (int i = parent; i >= 0; i--) {
			heapify(arr, i, arr.length);
		}

	}

	/**
	 * 
	 * @param arr    the array to sort
	 * 
	 * @param parent the index of non-leaf node to adjust in heap
	 * 
	 * @param length the number of elements need adjust which decrease continuously
	 */
	private static void heapify(int[] arr, int parent, int length) {
		if (parent >= length) {
			return;
		}
		// entire binary character
		int leftChildIndex = parent * 2 + 1;
		int rightChildIndex = parent * 2 + 2;

		int max = parent;

		// pick up the max value at index(parent, left child, right child)
		if (leftChildIndex < length && arr[leftChildIndex] > arr[max]) {
			max = leftChildIndex;
		}

		if (rightChildIndex < length && arr[rightChildIndex] > arr[max]) {
			max = rightChildIndex;
		}

		// if max value is parent's child, swap them
		if (max != parent) {
			SwapUtil.intArraySwap(arr, max, parent);

			// max point to original parent's child node
			// make it as new parent and adjust the subtree
			heapify(arr, max, length);
		}

	}
}
