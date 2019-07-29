/**
 Refer to
 https://leetcode.com/problems/combination-sum-iv/
 Given an integer array with all positive numbers and no duplicates, find the number of possible 
 combinations that add up to a positive integer target.

Example:

nums = [1, 2, 3]
target = 4

The possible combination ways are:
(1, 1, 1, 1)
(1, 1, 2)
(1, 2, 1)
(1, 3)
(2, 1, 1)
(2, 2)
(3, 1)

Note that different sequences are counted as different combinations.

Therefore the output is 7.

Follow up:
What if negative numbers are allowed in the given array?
How does it change the problem?
What limitation we need to add to the question to allow negative numbers?
*/

// Native DFS
class Solution {
    public int combinationSum4(int[] nums, int target) {
        if(nums == null || nums.length == 0 || target <= 0) {
            return 0;
        }
        return helper(nums, target);
    }
    
    private int helper(int[] nums, int sum) {
        if(sum == 0) {
            return 1;
        }
        int result = 0;
        for(int i = 0; i < nums.length; i++) {
            if(sum >= nums[i]) {
                result += helper(nums, sum - nums[i]);
            }
        }
        return result;
    }
}

// DFS + Memoization
// Runtime: 3 ms, faster than 16.74% of Java online submissions for Combination Sum IV.
// Memory Usage: 36.2 MB, less than 13.80% of Java online submissions for Combination Sum IV
class Solution {
    public int combinationSum4(int[] nums, int target) {
        if(nums == null || nums.length == 0 || target <= 0) {
            return 0;
        }
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        return helper(nums, target, map);
    }
    
    private int helper(int[] nums, int sum, Map<Integer, Integer> map) {
        if(sum == 0) {
            return 1;
        }
        if(map.containsKey(sum)) {
            return map.get(sum);
        }
        int result = 0;
        for(int i = 0; i < nums.length; i++) {
            if(sum >= nums[i]) {
                result += helper(nums, sum - nums[i], map);
            }
        }
        map.put(sum, result);
        return result;
    }
}

// Follow up
// What if negative numbers are allowed in the given array?
// Refer to
// https://leetcode.com/problems/combination-sum-iv/discuss/85038/JAVA%3A-follow-up-using-recursion-and-memorization.
// In order to allow negative integers, the length of the combination sum needs to be restricted, 
// or the search will not stop. This is a modification from my previous solution, which also use 
// memory to avoid repeated calculations.
class Solution {
    public int combinationSum4(int[] nums, int target, int maxLen) {
        if(nums == null || nums.length == 0 || target <= 0 || maxLen <= 0) {
            return 0;
        }
        // key = current target, value = <key = current length, value = combinations mapping to current length and current target>
        Map<Integer, Map<Integer, Integer>> map = new HashMap<Integer, Map<Integer, Integer>>();
        return helper(nums, target, map, 0, maxLen);
    }
    
    private int helper(int[] nums, int sum, Map<Integer, Map<Integer, Integer>> map, int len, int maxLen) {
        if(len > maxLen) {
            return 0;
        }
        if(sum == 0) {
            return 1;
        }
        if(map.containsKey(sum) && map.get(sum).containsKey(len)) {
            return map.get(sum).get(len);
        }
        int result = 0;
        for(int i = 0; i < nums.length; i++) {
            if(sum >= nums[i]) {
                result += helper(nums, sum - nums[i], map, len + 1, maxLen);
            }
        }
        if(!map.containsKey(sum)) {
            map.put(sum, new HashMap<Integer, Integer>());
        }
        Map<Integer, Integer> memo = map.get(sum);
        memo.put(len, result);
        return result;
    }
}