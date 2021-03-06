/**
 * Given two arrays, write a function to compute their intersection.
 * Example:
 * Given nums1 = [1, 2, 2, 1], nums2 = [2, 2], return [2, 2].
 * Note:
 * Each element in the result should appear as many times as it shows in both arrays.
 * The result can be in any order.
 * Follow up:
 * What if the given array is already sorted? How would you optimize your algorithm?
 * What if nums1's size is small compared to nums2's size? Which algorithm is better?
 * What if elements of nums2 are stored on disk, and the memory is limited such that 
 * you cannot load all elements into the memory at once?
 *
 * For questions:
 * Rfer to 
 * http://buttercola.blogspot.com/2016/06/leetcode-intersection-of-two-arrays-ii.html
 *
 * (1) What if the given array is already sorted? How would you optimize your algorithm?
 * Solution 2, i.e., sorting,  would be better since it does not need extra memory. 
 * 
 * (2) What if nums1's size is small compared to nums2's size? Which algorithm is better?
 * In Solution 2, Sort the two arrays and iterate over to find out the intersections. So the overall time complexity is bounded 
 * by O(n logn), where n is the length of the longer array. The main body of the loop is bounded by O(m + n). 
 * If two arrays are sorted, we could use binary search, i.e., for each element in the shorter array, search in the longer one. 
 * So the overall time complexity is O(nlogm), where n is the length of the shorter array, and m is the length of the longer array. 
 * Note that this is better than Solution 2 since the time complexity is O(n + m) in the worst case. 
 * 
 * (3) What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements 
 * into the memory at once?
 * If the two arrays have relatively the same length, we can use "External Sort" to sort out the two arrays in the disk. 
 * Then load chunks of each array into the memory and compare, by using the solution 2.
 * If one array is too short while the other is long, in this case, if memory is limited and nums2 is stored in disk, 
 * partition it and send portions of nums2 piece by piece. keep a pointer for nums1 indicating the current position, 
 * and it should be working fine.
 * Another method is, store the larger array into disk, and for each element in the shorter array, use "Exponential Search" 
 * and search in the longer array. 
 * 
 * Another explain
 * https://segmentfault.com/a/1190000005720072
 * If only nums2 cannot fit in memory, put all elements of nums1 into a HashMap, read chunks of array that fit into the memory, 
 * and record the intersections.
 * If both nums1 and nums2 are so huge that neither fit into the memory, sort them individually (External Sort), then read 
 * (let's say) 2G of each into memory and then using the 2 pointer technique(solution 2), then read 2G more from the array 
 * that has been exhausted. Repeat this until no more data to read from disk.
 *
 * Note: Both metioned about "External Sort"
 * Refer to
 * http://www.csbio.unc.edu/mcmillan/Media/Comp521F10Lecture17.pdf
 * http://pages.cs.wisc.edu/~jignesh/cs564/notes/lec07-sorting.pdf
 * https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Sort_Search_DataStructure/ExternalMergeSort.java
*/


// Solution 1: Use HashMap as a filter, build the HashMap first depends on nums1,
// then use this map to measure nums2, find the intersection between two array.
// 这道题是之前那道Intersection of Two Arrays的拓展，不同之处在于这道题允许我们返回重复的数字，而且是尽可能多的返回，
// 之前那道题是说有重复的数字只返回一个就行。那么这道题我们用哈希表来建立nums1中字符和其出现个数之间的映射, 
// 然后遍历nums2数组，如果当前字符在哈希表中的个数大于0，则将此字符加入结果res中，然后哈希表的对应值自减1
// Refer to 
// http://www.jiuzhang.com/solutions/intersection-of-two-arrays-ii/
// https://aaronice.gitbooks.io/lintcode/content/array/intersection_of_two_arrays_ii.html
public class Solution {
    public int[] intersect(int[] nums1, int[] nums2) {
        // Construct the HashMap based on nums1
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i = 0; i < nums1.length; i++) {
            if(!map.containsKey(nums1[i])) {
                map.put(nums1[i], 1);
            } else {
                map.put(nums1[i], map.get(nums1[i]) + 1);
            }
        }
        
        // Measure nums2 with HashMap and find the intersection part
        List<Integer> tmp = new ArrayList<Integer>();
        for(int j = 0; j < nums2.length; j++) {
            if(map.containsKey(nums2[j]) && map.get(nums2[j]) > 0) {
                tmp.add(nums2[j]);
                map.put(nums2[j], map.get(nums2[j]) - 1);
            }
        }
        
        // Print out the result
        int[] result = new int[tmp.size()];
        int k = 0;
        for(Integer i : tmp) {
            result[k++] = i;
        }
        
        return result;
    }
}

// Solution 2: First sort and then use two pointers
// Refer to
// http://www.cnblogs.com/grandyang/p/5533305.html
// 先给两个数组排序，然后用两个指针分别指向两个数组的起始位置，如果两个指针指的数字相等，
// 则存入结果中，两个指针均自增1，如果第一个指针指的数字大，则第二个指针自增1，反之亦然
public class Solution {
    public int[] intersect(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        List<Integer> tmp = new ArrayList<Integer>();
        
        int i = 0;
        int j = 0;
        while(i < nums1.length && j < nums2.length) {
            int x = nums1[i];
            int y = nums2[j];
            
            if(x == y) {
                tmp.add(y);
                i++;
                j++;
            } else if(x > y) {
                j++;
            } else {
                i++;
            }
        }
        
        int k = 0;
        int[] result = new int[tmp.size()];
        for(Integer m : tmp) {
            result[k++] = m;
        }
        
        return result;
    }
}


