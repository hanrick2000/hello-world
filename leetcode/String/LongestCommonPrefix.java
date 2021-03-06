/**
 * Write a function to find the longest common prefix string amongst an array of strings.
*/
// Solution 1: Binary Search
// Refer to
// http://www.geeksforgeeks.org/longest-common-prefix-set-4-binary-search/
/**
 * In this article, an approach using Binary Search is discussed.
 * Steps:
 * (1) Find the string having the minimum length. Let this length be L.
 * (2) Perform a binary search on any one string (from the input array of strings). 
 *     Let us take the first string and do a binary search on the characters from 
 *     the index – 0 to L-1.
 * (3) Initially, take low = 0 and high = L-1 and divide the string into two 
 *     halves – left (low to mid) and right (mid+1 to high).Check whether all the 
 *     characters in the left half is present at the corresponding indices (low to 
 *     mid) of all the strings or not. If it is present then we append this half to 
 *     our prefix string and we look in the right half in a hope to find a longer 
 *     prefix.(It is guaranteed that a common prefix string is there.)
 * (4) Otherwise, if all the characters in the left half is not present at the 
 *     corresponding indices (low to mid) in all the strings, then we need not look 
 *     at the right half as there is some character(s) in the left half itself which 
 *     is not a part of the longest prefix string. So we indeed look at the left half 
 *     in a hope to find a common prefix string. (It may be possible that we don’t 
 *     find any common prefix string)
 *     
 * E.g Strings: geeksforgeeks   geeks   geek   geezer
 *     Length:       13           5       4      6
 *     The string with minimum length is "geek" (length = 4)
 *     So, we will do a binary search on any of the strings with the low as 0 and high
 *     as 3 (4 -1)
 *     For convenience we take the first string of the above array - "geeksforgeeks"
 *     In the string "geeksforgeeks" we do a binary search on its substring from index
 *     0 to index 3, i.e - "geek"
 *     
 *     We will do a binary search later
 *                                  
 *                                  geek
 *                       -----------    -----------            
 *                      /                          \
 *                     ge                           ek
 *     since "ge" is present                  ------   ------          
 *     in all the strings, so                /               \
 *     append this to our                   e                 k    
 *     longest common prefix           since "e" is        "k" is not present in 
 *     and go to the right side        present in all       all strings at its
 *                                     the strings at       correct index(it is not)
 *                                     its correct index,   present in "geezer" as
 *                                     so append it to      at the place of "k", "z"
 *                                     our longest common   is there, so we don't
 *                                     substring and go     append "k" to our longest
 *                                     to the right         common prefix
 *    
 *    Hence our longest common prefix is "gee"     
 */           
public class LongestCommonPrefixBinarySearch {
	public String longestCommonPrefix(String[] strs) {
		String result = "";
	    String strForBS = findShortestString(strs);
	    int lo = 0;
	    int hi = strForBS.length() - 1;
	    while(lo <= hi) {
		    int mid = (lo + hi) / 2;
		    // To get left part of string for binary search, index 
		    // range end at (mid + 1), as java substring method
		    // will naturally exclusive last position, e.g if
		    // strForBS is "geek", lo = 0, hi = 3, mid = 1, if not
		    // using (mid + 1), leftSubstring is "g", not what we
		    // expect as "ge"
		    String leftSubstring = strForBS.substring(lo, mid + 1);
	    	if(!existInAllStrs(strs, leftSubstring, lo, mid + 1)) {
	    		// If not exist in all strings, keep search in 
	    		// current left part of string for binary search
	    		hi = mid - 1;
	    	} else {
	    		// If exist, add current left part to result
	    		// and move on to search right part of string
	    		// for binary search
	    		lo = mid + 1;
	    		result += leftSubstring;
	    	}
	    }
	    return result;
    }
	
	// Find shorest string in all strings in given array
	public String findShortestString(String[] strs) {
		int minimum = Integer.MAX_VALUE;
		String result = "";
		for(int i = 0; i < strs.length; i++) {
			if(strs[i].length() <= minimum) {
				result = strs[i];
				minimum = strs[i].length();
			}
		}
		return result;
	}
	
	// Identify whether current left part of string for binary search exist or not
	// in all strings in given array
	public boolean existInAllStrs(String[] strs, String leftSubstring, int lo, int mid) {
		for(int i = 0; i < strs.length; i++) {
			String s = strs[i].substring(lo, mid);
			if(!s.equals(leftSubstring)) {
				return false;
			} 
		}
		return true;
	}
	
	public static void main(String[] args) {
		String[] strings = {"geek", "geezer", "geeksforgeeks", "geeks"};
		LongestCommonPrefixBinarySearch lcp = new LongestCommonPrefixBinarySearch();
		String result = lcp.longestCommonPrefix(strings);
		System.out.println(result);
	}
}


// Solution 2: Trie
// Refer to
// http://www.geeksforgeeks.org/longest-common-prefix-set-5-using-trie/
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Sort_Search_DataStructure/ImplementTrie.java
import java.util.ArrayList;
import java.util.List;

/**
 * Refer to
 * http://www.geeksforgeeks.org/longest-common-prefix-set-5-using-trie/
 * 
 * (1)Insert all the words one by one in the trie. After inserting we perform a walk on the trie.
 * (2)In this walk, go deeper until we find a node having more than 1 children(branching occurs) or 
 *    0 children (one of the string gets exhausted).This is because the characters (nodes in trie) 
 *    which are present in the longest common prefix must be the single child of its parent, 
 *    i.e- there should not be a branching in any of these nodes
 *  
 *                   root                   A trie for the words:
 *                     |                    "geek", "geezer", "geeksforgeeks", "geeks"
 *                     g
 *                     |
 *                     e
 *                     |
 *                     e   --> First node where branching occurs, hence all the characters
 *                    / \      above this node is in our longest prefix string, as "gee"   
 *                   k   z  
 *                  /     \
 */
public class LongestCommonPrefixTrie {
	static final int ascii = 256;
	public String longestCommonPrefix(String[] strs) {
		Trie trie = new LongestCommonPrefixTrie().new Trie();
		for(int i = 0; i < strs.length; i++) {
			trie.insert(strs[i]);
		}
		return walkTrie(trie.root);
    }
	
	public String walkTrie(TrieNode node) {
		String result = "";
		while(!node.isLeaf) {
			// Exactly "== 1" (not <= 1) has two meanings:
			// 1. If current node has next[] array only contain 1 NOT null item, 
			//    which means only one child, this is necessary for common 
			//    string, if over 1 item in next[] array equals branch happen
			// 2. If current node has next[] array all are null item, which means
			//    current node is already leaf, combine with while loop condition
			//    it will throw java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
			//    when given input Strings as empty array [], because list.get(0)
			//    cannot run with this empty list, so not "<= 1", exactly "== 1"
			if(countChildren(node).size() == 1) {
				int index = countChildren(node).get(0);
				String c = indexToString(index);
				result += c;
				// Recursive find child node
				node = node.next[index];
			} else {
				// If not match condition, break out, e.g has 2 children
				break;
			}
		}
		return result;
	}
	
	// Convert index back to next[index] ascii character
	public String indexToString(int index) {
		return Character.toString((char)index);
	}
	
	// Find how many items in current node next[] array not NULL
	// and record their indexes into list
	public List<Integer> countChildren(TrieNode node) {
		List<Integer> result = new ArrayList<Integer>();
		for(int i = 0; i < ascii; i++) {
			if(node.next[i] != null) {
				result.add(i);
			}
		}
		return result;
	}
	
	private class Trie {
		private TrieNode root;
		
		public Trie() {
			this.root = new TrieNode();
		}
		
		// In lcp(longest common prefix) only need insert method
		public void insert(String word) {
			put(root, word, 0);
		} 
		
		public TrieNode put(TrieNode x, String word, int d) {
			if(x == null) {
				x = new TrieNode();
			}
			if(d == word.length()) {
				x.isLeaf = true;
				return x;
			}
			char c = word.charAt(d);
			x.next[c] = put(x.next[c], word, d + 1);
			return x;
		}
	}
	
	private class TrieNode {
		boolean isLeaf;
		public TrieNode[] next;
		
		public TrieNode() {
			this.isLeaf = false;
			this.next = new TrieNode[ascii];
		}
	}
	
	public static void main(String[] args) {
		String[] strings = {"geek", "geezer", "geeksforgeeks", "geeks"};
		LongestCommonPrefixTrie lcp = new LongestCommonPrefixTrie();
		String result = lcp.longestCommonPrefix(strings);
		System.out.println(result);
	}
}

 
// Solution 3: Word by Word Matching
// Refer to
// http://www.geeksforgeeks.org/longest-common-prefix-set-1-word-by-word-matching/
/**
 * We start with an example. Suppose there are two strings- “geeksforgeeks” and “geeks”. 
 * What is the longest common prefix in both of them? It is “geeks”.
 * Now let us introduce another word “geek”. So now what is the longest common prefix in 
 * these three words ? It is “geek”
 * We can see that the longest common prefix holds the associative property, i.e-
 *    LCP(string1, string2, string3) 
         = LCP (LCP (string1, string2), string3)
      
      Like here
      LCP (“geeksforgeeks”, “geeks”, “geek”) 
         =  LCP (LCP (“geeksforgeeks”, “geeks”), “geek”)
         =  LCP (“geeks”, “geek”) = “geek”
 * So we can make use of the above associative property to find the LCP of the given strings. 
 * We one by one calculate the LCP of each of the given string with the LCP so far. The final 
 * result will be our longest common prefix of all the strings.
 * Note that it is possible that the given strings have no common prefix. This happens when 
 * the first character of all the strings are not same.
 * We show the algorithm with the input strings- “geeksforgeeks”, “geeks”, “geek”, “geezer” 
 * by the below figure.
 * 
 *            geek   geezer  geeksforgeeks  geeks
 *                \    /      /             /
 *                  gee      /             /
 *                    \     /             /   
 *                      gee              /
 *                        \             /
 *                         ---- gee ----
 * 
 */
public class LongestCommonPrefixWordToWord {
	public String longestCommonPrefix(String[] strs) {
        int length = strs.length;
        String result = "";
        if(length == 1) {
            result = strs[0];
        } else if(length > 1) {
        	// This process is elegant, the two points:
        	// (1) The base case is individually store strs[0]
        	//     as initial status
        	// (2) When we finish a common prefix retrieve
        	//     between two strings, need to use its result
        	//     into next loop, like iterative update temporary
        	//     string. Also, in each loop need to update
        	//     comparing object as next string in string array
        	//     by increase index as 1
            String temp = strs[0];
            for(int i = 1; i < length; i++) {
                temp = commonPrefixBetweenTwoStrings(temp, strs[i]);
            }
            result = temp;
        }
        return result;
    }
    
    public String commonPrefixBetweenTwoStrings(String str1, String str2) {
		String result = "";
		int str1Len = str1.length();
		int str2Len = str2.length();
		int minLen = str1Len <= str2Len ? str1Len : str2Len;
		int i;
		for(i = 0; i < minLen; i++) {
			if(str1.charAt(i) != str2.charAt(i)) {
				break;
			}
		}
		result = str1.substring(0, i);
		return result;
	}
    
    public static void main(String[] args) {
    	String[] strings = {"geek", "geezer", "geeksforgeeks", "geeks"};
    	LongestCommonPrefixCharToChar lcp = new LongestCommonPrefixCharToChar();
		String result = lcp.longestCommonPrefix(strings);
		System.out.println(result);
    }
}



// Solution 4: Character by Character Matching
// Refer to
// http://www.geeksforgeeks.org/longest-common-prefix-set-2-character-by-character-matching/
public class LongestCommonPrefixCharToChar {
	public String longestCommonPrefix(String[] strs) {
	    String result = "";
	    if(strs.length == 0) {
	        return result;
	    }
        int minLen = minLen(strs);
        for(int i = 0; i < minLen; i++) {
            boolean same = true;
            char c = strs[0].charAt(i);
            for(int j = 1; j < strs.length; j++) {
                if(strs[j].charAt(i) != c) {
                    same = false;
                    break;
                }
            }
            if(same) {
               result += c; 
            } else {
               // Must also break in outside for loop,
               // otherwise result may continue add same
               // characters after current different
               // character (e.g aca, cba if not break
               // here after first character not match,
               // it will continue add match character
               // as 'a' on position 2)
               break;
            }
        }
        return result;
    }
    
    public int minLen(String[] strs) {
        int minLen = Integer.MAX_VALUE;
        for(int i = 0; i < strs.length; i++) {
            if(strs[i].length() <= minLen) {
                minLen = strs[i].length();
            }
        }
        return minLen;
    }
    
    public static void main(String[] args) {
    	String[] strings = {"aca", "cba"};
    	LongestCommonPrefixCharToChar lcp = new LongestCommonPrefixCharToChar();
		String result = lcp.longestCommonPrefix(strings);
		System.out.println(result);
    }
}



// Solution 5: Divide and Conquer
// Refer to
// http://www.geeksforgeeks.org/longest-common-prefix-set-3-divide-and-conquer/
/**
 * What is the difference between recursion and divide and conquer?
 * Refer to
 * https://www.quora.com/What-is-the-difference-between-recursion-and-divide-and-conquer
 * Recursion is a programming method where you define a function in terms of itself. 
 * The function generally calls itself with slightly modified parameters (in order to converge).
 * Divide and conquer is when you split a problem into non-overlapping sub-problems. 
 * For example, in merge sort, you split the array into two halves and sort them and then merge 
 * them back. You divided the problem into two sub-problems, solved them and got a solution to 
 * the original problem. Note that we use recursion to solve the sub-problems.
 * 
 * Recursion is a "Programming Paradigm" which is generally used to implement 
 * the "Algorithmic Paradigm" Divide and Conquer.
 * 
 * Divide and conquer algorithms
 * Refer to
 * https://www.khanacademy.org/computing/computer-science/algorithms/merge-sort/a/divide-and-conquer-algorithms
 * 
 * Merge Sort
 * Refer to
 * https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Sort_Search_DataStructure/MergeSort.java
 * 
 * How to print log with indent ?
 * Refer to
 * https://github.com/lampardchelsea/hello-world/blob/master/leetcode/BackTracking/WordPatternII.java
 * e.g "geek", "geezer", "geeksforgeeks", "geeks"
 * Enter divideAndConquer() method
	|  Enter divideAndConquer() method
	|  |  Enter divideAndConquer() method
	|  |  Into branch --> index lo == hi(0,0) return string on current index position = 'geek'
	|  |  Enter divideAndConquer() method
	|  |  Into branch --> index lo == hi(1,1) return string on current index position = 'geezer'
	|  Enter commonPrefixBetweenTwoStrings() method
	|  Into branch --> index lo < hi(0,1) return common prefix between two strings = 'gee'
	|  Enter divideAndConquer() method
	|  |  Enter divideAndConquer() method
	|  |  Into branch --> index lo == hi(2,2) return string on current index position = 'geeksforgeeks'
	|  |  Enter divideAndConquer() method
	|  |  Into branch --> index lo == hi(3,3) return string on current index position = 'geeks'
	|  Enter commonPrefixBetweenTwoStrings() method
	|  Into branch --> index lo < hi(2,3) return common prefix between two strings = 'geeks'
	Enter commonPrefixBetweenTwoStrings() method
	Into branch --> index lo < hi(0,3) return common prefix between two strings = 'gee'
	gee
 * 
 * 
 * Refer to
 * http://www.geeksforgeeks.org/longest-common-prefix-set-3-divide-and-conquer/
 * In this algorithm, a divide and conquer approach is discussed. We first divide the arrays of string into two parts. 
 * Then we do the same for left part and after that for the right part. We will do it until and unless all the strings 
 * becomes of length 1. Now after that we will start conquering by returning the common prefix of the left and the 
 * right strings.
 * The algorithm will be clear using the below illustration. We consider our strings as 
 * – "geek", "geezer", "geeksforgeeks", "geeks"
 * 
 *                                 geek   geezer  geeksforgeeks  geeks
 *                                /                                   \
 *                          geek    geezer              geeksforgeeks    geeks
 *                            |       |                       |            |
 *                          geek    geezer              geeksforgeeks    geeks         
 *                             \     /                            \      /
 *       Longest Common Prefix = gee        Longest Common Prefix = geeks
 *                                 \                                 /
 *                                  ---------------    --------------
 *                          Longest Common Prefix = gee                          
 */
public class LongestCommonPrefixDivideAndConquer {
	public String indent = "";
	public boolean debug = true;
	
	public String longestCommonPrefix(String[] strs) {
	    int lo = 0;
	    int hi = strs.length - 1;
	    return divideAndConquer(strs, lo, hi);
    }
	
	public String commonPrefixBetweenTwoStrings(String str1, String str2) {
		// For debug
		enterCommonPrefixBetweenTwoStrings();
		String result = "";
		int str1Len = str1.length();
		int str2Len = str2.length();
		int minLen = str1Len <= str2Len ? str1Len : str2Len;
		int i;
		for(i = 0; i < minLen; i++) {
			if(str1.charAt(i) != str2.charAt(i)) {
				break;
			}
		}
		result = str1.substring(0, i);
		return result;
	}
	
	public String divideAndConquer(String[] strs, int lo, int hi) {
		// For debug
		enterDivideAndConquer();
		String result = "";
		// Important: base case --> index (lo == hi) will return
		// the string on current index position in string array
		if(lo == hi) {
		   result = strs[lo];
		   // For debug
		   enter1(result, lo, hi);
		}
		// We first divide the arrays of string into two parts. 
		// Then we do the same for left part and after that for the 
		// right part. We will do it until and unless all the strings 
		// becomes of length 1. Now after that we will start conquering 
		// by returning the common prefix of the left and the right strings.
		if(lo < hi) {
			int mid = (lo + hi) / 2;
			String str1 = divideAndConquer(strs, lo, mid);
			String str2 = divideAndConquer(strs, mid + 1, hi);
			result = commonPrefixBetweenTwoStrings(str1, str2);
			// For debug
			enter2(result, lo, hi);
		}		
		return result;
	}	
	
	public void enterDivideAndConquer() {
		if(debug) {
			System.out.println(indent + "Enter divideAndConquer() method");
			indent += "|  ";
		}
	}
	
	public void enterCommonPrefixBetweenTwoStrings() {
		if(debug) {
			indent = indent.substring(3);
			System.out.println(indent + "Enter commonPrefixBetweenTwoStrings() method");
			indent += "|  ";
		}
	}
	
	public void enter1(String result, int lo, int hi) {
		if(debug) {
			indent = indent.substring(3);
			System.out.println(indent + "Into branch --> index lo == hi(" + lo + "," + hi + ") return string on current index position = '" + result + "'");			
		}
	}
	
	public void enter2(String result, int lo, int hi) {
		if(debug) {
			indent = indent.substring(3);
			System.out.println(indent + "Into branch --> index lo < hi(" + lo + "," + hi + ") return common prefix between two strings = '" + result + "'");			
		}
	}
	
	public static void main(String[] args) {
		String[] strings = {"geek", "geezer", "geeksforgeeks", "geeks"};
		LongestCommonPrefixDivideAndConquer lcp = new LongestCommonPrefixDivideAndConquer();
		String result = lcp.longestCommonPrefix(strings);
		System.out.println(result);
	}
}



// Solution 6: Simple Solution
// Refer to
// https://discuss.leetcode.com/topic/6987/java-code-with-13-lines/6
