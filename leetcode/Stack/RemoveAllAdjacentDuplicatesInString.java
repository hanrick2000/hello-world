/**
 Refer to
 https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string/
 Given a string S of lowercase letters, a duplicate removal consists of choosing 
 two adjacent and equal letters, and removing them.

We repeatedly make duplicate removals on S until we no longer can.

Return the final string after all such duplicate removals have been made.  
It is guaranteed the answer is unique.

Example 1:
Input: "abbaca"
Output: "ca"
Explanation: 
For example, in "abbaca" we could remove "bb" since the letters are adjacent and equal, 
and this is the only possible move.  The result of this move is that the string is "aaca", 
of which only "aa" is possible, so the final string is "ca".

Note:
1 <= S.length <= 20000
S consists only of English lowercase letters.
*/
// Solution 1: Stack
class Solution {
    public String removeDuplicates(String S) {
        Stack<Character> stack = new Stack<Character>();
        stack.push(S.charAt(0));
        for(int i = 1; i < S.length(); i++) {
            char c = S.charAt(i);
            if(!stack.isEmpty() && stack.peek() == c) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }
        String result = "";
        while(!stack.isEmpty()) {
            result = stack.pop() + result;
        }
        return result;
    }
}

// Solutoin 2: Two points
// Refer to
// https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string/discuss/294964/Java-three-easy-iterative-codes-w-brief-explanation-and-analysis.
class Solution {
    public String removeDuplicates(String S) {
        int j = -1; // use j to filter original string
        char[] chars = S.toCharArray();
        for(int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if(j < 0 || chars[j] != c) {
                j++; // move forward one step similar as push one more char on stack
                chars[j] = c; // record non-duplicate character on position j
            } else {
                j--; // move back one step similar as pop out current char on stack
            }
        }
        return String.valueOf(chars, 0, j + 1);
    }
}
