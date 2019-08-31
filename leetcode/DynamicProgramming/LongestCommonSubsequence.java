/**
 Refer to
 https://leetcode.com/problems/longest-common-subsequence/
 Given two strings text1 and text2, return the length of their longest common subsequence.

A subsequence of a string is a new string generated from the original string with some characters
(can be none) deleted without changing the relative order of the remaining characters. (eg, "ace" 
is a subsequence of "abcde" while "aec" is not). A common subsequence of two strings is a 
subsequence that is common to both strings.

If there is no common subsequence, return 0.

Example 1:
Input: text1 = "abcde", text2 = "ace" 
Output: 3  
Explanation: The longest common subsequence is "ace" and its length is 3.

Example 2:
Input: text1 = "abc", text2 = "abc"
Output: 3
Explanation: The longest common subsequence is "abc" and its length is 3.

Example 3:
Input: text1 = "abc", text2 = "def"
Output: 0
Explanation: There is no such common subsequence, so the result is 0.

Constraints:
1 <= text1.length <= 1000
1 <= text2.length <= 1000
The input strings consist of lowercase English characters only.
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/longest-common-subsequence/discuss/348884/C%2B%2B-with-picture-O(nm)
// https://leetcode.com/problems/longest-common-subsequence/discuss/349346/LC1143-Classic-DP-Longest-Common-Subsequence-With-Follow-up-Problems
// https://leetcode.com/problems/longest-common-subsequence/discuss/351689/Java-Two-DP-codes-of-O(mn)-and-O(min(m-n))-spaces-w-picture-and-analysis
/**
Other DP+String combiantion problems (non premium) with similar pattern or involving LCS as intermediate step -
72. edit distance - https://leetcode.com/problems/edit-distance/
10. regular expression matching - https://leetcode.com/problems/regular-expression-matching/
44. wildcard matching - https://leetcode.com/problems/wildcard-matching/
1092. shortest common supersequence (solution involves a LCS step) - https://leetcode.com/problems/shortest-common-supersequence1062. Longest Repeating Substring
516. Longest Palindromic Subsequence 
*/
class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        int len1 = text1.length();
        int len2 = text2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];
        for(int i = 1; i <= len1; i++) {
            for(int j = 1; j <= len2; j++) {
                if(text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[len1][len2];
    }
}
