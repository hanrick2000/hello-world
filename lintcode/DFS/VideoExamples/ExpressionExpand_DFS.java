/**
 * Refer to
 * http://www.lintcode.com/en/problem/expression-expand/
 * https://leetcode.com/problems/decode-string/description/
 *
 * Solution
 * https://github.com/lampardchelsea/hello-world/tree/master/lintcode/BinaryTree__DivideAndConquer/VideoExamples
 * Style 1
 * http://www.cnblogs.com/Dylan-Java-NYC/p/6751689.html
 * Style 2
 * http://www.cnblogs.com/lishiblog/p/5874147.html
*/
// Style 1:
// Refer to
// http://www.cnblogs.com/Dylan-Java-NYC/p/6751689.html
// Base on definition here we implement Traversal + Divide and Conquer way together on this problem as
// we use native method (Divide and Conquer) and return void (Traversal), also set i as global variable
// to control the loop condition (Traversal), they are both DFS recursive way
public class Solution {
    /*
     * @param s: an expression includes numbers, letters and brackets
     * @return: a string
     */
    int i = 0;
    public String expressionExpand(String s) {
        if(s == null || s.length() == 0) {
            return "";
        }
        // Initial global StringBuilder to record each recursion temparay
        // result as DFS Traversal way
        StringBuilder sb = new StringBuilder();
        while(i < s.length()) {
            if(Character.isDigit(s.charAt(i))) {
                int start = i;
                // Loop until character not numeric number, based
                // on problem description this character is '[',
                // need to skip
                while(Character.isDigit(s.charAt(i))) {
                    i++;
                }
                int val = Integer.parseInt(s.substring(start, i));
                // Skip '[' just after numeric value
                i++;
                // DFS to get nested substring in current '[]'
                String nested = expressionExpand(s);
                while(val-- > 0) {
                    sb.append(nested);
                } 
            } else if(Character.isLetter(s.charAt(i))) {
                sb.append(s.charAt(i));
                i++;
            } else if(s.charAt(i) == ']') {
                // Skip ']'
                i++;
                return sb.toString();
            }
        }
        return sb.toString();
    }
}




// Style 2: 
// Refer to
// http://www.cnblogs.com/lishiblog/p/5874147.html
// Base on definition here we implement Traversal + Divide and Conquer way together on this problem as
// we create new helper method (Traversal) and return value not void (Divide and Conquer), also set
// StringBuilder builder as global variable to store each recursion temporary result (Traversal), 
// they are both DFS recursive way
