/**
 * Given an array and a value, remove all instances of that value in place and return the new length.
 * Do not allocate extra space for another array, you must do this in place with constant memory.
 * The order of elements can be changed. It doesn't matter what you leave beyond the new length.
 * Example:
 * Given input array nums = [3,2,2,3], val = 3
 * Your function should return length = 2, with the first two elements of nums being 2.
 * Show Hint 
 * Hint:
 * 1. Try two pointers.
 * 2. Did you use the property of "the order of elements can be changed"?
 * 3. What happens when the elements to remove are rare?
*/
public class Solution {
    public int removeElement(int[] nums, int val) {
        int length = nums.length;
        // Slow pointer i to record remain array items
        int i = 0;
        // Fast pointer j to swap origianl array items
        int j = 0;
        while(j < length) {
            if(nums[j] == val) {
                // If match given val in array, increase fast pointer j,
                // and skip nums[j] with continue
                j++;
                continue;
            } else {
                // If not match, replace nums[i] with nums[j] first,
                // then increase both slow and fast pointers.
                nums[i] = nums[j];
                i++;
                j++;
            }
        }
        // Should not return nums.length, based on "It doesn't matter what you leave beyond the new length.",
        // just return value of i which present remain array length.
        return i;
    }
}
