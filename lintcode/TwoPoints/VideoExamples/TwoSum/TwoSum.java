/**
 * Refer to
 *
 * 
 * Solution
 * 
 * 
*/
public class Solution {
    /*
     * @param numbers: An array of Integer
     * @param target: target = numbers[index1] + numbers[index2]
     * @return: [index1 + 1, index2 + 1] (index1 < index2)
     */
    public int[] twoSum(int[] numbers, int target) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int[] result = new int[2];
        for(int i = 0; i < numbers.length; i++) {
            if(map.containsKey(target - numbers[i])) {
                // Important: As description as "where index1 must be less than index2"
                // we must set result[0] and result[1] as below, cannot reverse
                // E.g numbers = {2,7,11,15}, target = 9,
                // you will put 2 onto map first as {2 = 0}, then continue next loop,
                // check (9 - 7 = 2) if exist or not, actually 7 not yet put on map,
                // but already find its counterpart 2 on the map at index 0,
                // and as requirement result[0] must < result[1]
                // so return not zero based result[0] = 1, result[1] = 2
                result[0] = map.get(target - numbers[i]) + 1;
                result[1] = i + 1;
            }
            map.put(numbers[i], i);
        }
        return result;
    }
}
