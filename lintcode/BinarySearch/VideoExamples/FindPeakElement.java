/**
 * Refer to
 * http://www.lintcode.com/en/problem/find-peak-element/
 * There is an integer array which has the following features:
    The numbers in adjacent positions are different.
    A[0] < A[1] && A[A.length - 2] > A[A.length - 1].
 * We define a position P is a peek if:
 *  A[P] > A[P-1] && A[P] > A[P+1]
 * Find a peak element in this array. Return the index of the peak.
 * Notice
 * The array may contains multiple peeks, find any of them.
*/
