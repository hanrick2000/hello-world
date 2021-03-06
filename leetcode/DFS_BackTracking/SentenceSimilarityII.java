/**
 * Refer to
 * http://zxi.mytechroad.com/blog/hashtable/leetcode-737-sentence-similarity-ii/
 * Given two sentences words1, words2 (each represented as an array of strings), and a list of 
   similar word pairs pairs, determine if two sentences are similar.

    For example, words1 = ["great", "acting", "skills"] and words2 = ["fine", "drama", "talent"] 
    are similar, if the similar word pairs are pairs = [["great", "good"], ["fine", "good"], 
    ["acting","drama"], ["skills","talent"]].

    Note that the similarity relation is transitive. For example, if “great” and “good” are 
    similar, and “fine” and “good” are similar, then “great” and “fine” are similar.

    Similarity is also symmetric. For example, “great” and “fine” being similar is the same as 
    “fine” and “great” being similar.

    Also, a word is always similar with itself. For example, the sentences words1 = ["great"], 
    words2 = ["great"], pairs = [] are similar, even though there are no specified similar word pairs.

    Finally, sentences can only be similar if they have the same number of words. So a sentence 
    like words1 = ["great"] can never be similar to words2 = ["doubleplus","good"].

    Note:
    The length of words1 and words2 will not exceed 1000.
    The length of pairs will not exceed 2000.
    The length of each pairs[i] will be 2.
    The length of each words[i] and pairs[i][j] will be in the range [1, 20].
 *
 * Solution
 * http://zxi.mytechroad.com/blog/hashtable/leetcode-737-sentence-similarity-ii/
 * 题目大意：
    给你两个句子（由单词数组表示）和一些近义词对，问你这两个句子是否相似，即每组相对应的单词都要相似。
    注意相似性可以传递，比如只给你”great”和”fine”相似、”fine”和”good”相似，能推断”great”和”good”也相似。
    Idea:
    DFS / Union Find
* https://medium.com/@rebeccahezhang/leetcode-737-sentence-similarity-ii-2ca213f10115
  This is more complicated than Sentence Similarity I. To check if two words are similar given the transitivity 
  (for example If “A” = “B”, “B” = “C”, then “A” = “C”), we can use a graph to help us connect all similar words 
  together. Then for each word pairs, we start from the source word, using DFS to find the destination word. 
  In case of we do DFS to the same node twice, we can create a set to keep a record of visited nodes.

   Let’s walk through a simple example:

   words1 = [“A”, “B”, “C”], words2 = [“D”, “E”, “F”]

   pairs[][] = [“A”, “G”],[“D”, “G”],[“B”, “E”],[“C”, “F”]

   We construct the graph using a map to represent it. 
   String |set<String>
   A | [G]
   G | [A, D] 
   D | [G]
   B | [E]
   E | [B]
   C | [F]
   F | [C]

   Now we begin to check A and D. A is the source, D is the target.

   We go to the entry where the key is A, and check if this set contains D. It doesn’t, but contains G! It’s 
   possible G has a set contains D, so we change our source from A to G and keep finding out. We get the set [A, D], 
   and we need to check each word here. The first one is A again, oh we just checked this! We don’t want to go to 
   an endless loop. So we need to skip this, and we see D. It’s equal to the target! We are done. Well, on the 
   opposite, if we are not this lucky, we need to keep finding. After we go through the entire map we still can’t 
   find the target, we failed.
*/
class Solution {   
    public boolean areSentencesSimilarTwo(String[] words1, String[] words2, String[][] pairs) {
        if(words1.length != words2.length) {
            return false;   
        }
        Map<String, Set<String>> similar_words = new HashMap<>();
        for(String[] pair : pairs) {
            if(!similar_words.containsKey(pair[0])) {
                similar_words.put(pair[0], new HashSet<>());            
            }
            if(!similar_words.containsKey(pair[1])) {
                similar_words.put(pair[1], new HashSet<>());
            }
            similar_words.get(pair[0]).add(pair[1]);
            similar_words.get(pair[1]).add(pair[0]);
        }
        for(int i = 0; i < words1.length; i++) {
            if(words1[i].equals(words2[i])) {
                continue;
            }
            if(!simiar_words.containsKey(words1[i]) || !simiar_words.containsKey(words2[i])) {
                return false;
            }
            if(dfs(words1[i], words2[i], similar_words, new HashSet<String>())) {
                return false;
            }
        }
        return true;
    }
   
    private boolean dfs(String source, String target, Map<String, Set<String>> similar_words, Set<String> visited) {
        if(similar_words.get(source).contains(target)) {
            return true;
        }
        // Mark as visited
        visited.add(source);
        for(String next : similar_words.get(source)) {
            // DFS other connected node, except the mirrowed string
            if(!visited.contains(next) && hext.equals(target) || !visited.contains(next) && dfs(next, target, similar_words, visited)) {
                return true;
            }
        }
        // We've done dfs still can't find the target
        return false;
    }
}
