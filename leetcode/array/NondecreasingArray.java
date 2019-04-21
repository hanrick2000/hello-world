/**
 Refer to
 https://leetcode.com/problems/non-decreasing-array/
 Given an array with n integers, your task is to check if it could become 
 non-decreasing by modifying at most 1 element.

We define an array is non-decreasing if array[i] <= array[i + 1] holds for every i (1 <= i < n).

Example 1:
Input: [4,2,3]
Output: True
Explanation: You could modify the first 4 to 1 to get a non-decreasing array.

Example 2:
Input: [4,2,1]
Output: False
Explanation: You can't get a non-decreasing array by modify at most one element.

Note: The n belongs to [1, 10,000].
*/
// Wrong solution, still wrong with [2,3,3,2,4]
class Solution {
    public boolean checkPossibility(int[] nums) {
        int count = 0;
        for(int i = 0; i < nums.length - 1; i++) {
            if(nums[i] > nums[i + 1]) {
                count++;
                int temp = nums[i + 1];
                nums[i] = nums[i + 1];
                nums[i + 1] = temp;
                // [3,4,2,3]
                if(i >= 1 && nums[i - 1] > temp) {
                    return false;
                }
            }
            if(count == 2) {
                return false;
            }
        }
        return true;
    }
}

