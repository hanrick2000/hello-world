/**
 * Find the sum of all left leaves in a given binary tree.
 * Example:

    3
   / \
  9  20
    /  \
   15   7

 * There are two left leaves in the binary tree, with values 9 and 15 respectively. Return 24.
*/
//Wrong way
public class Solution {
	public TreeNode root;
	
	private static class TreeNode {
		private String val;
		private TreeNode left, right;
		public TreeNode(String x) {
			this.val = x;
		}
	}
	
    public static int sum = 0;
    public static int sumOfLeftLeaves(TreeNode root) {
        if(root == null) {
            return 0;
        }
        
        sumOfLeftLeavesRec(root, false, sum);
        return sum;
    }
    
    public static void sumOfLeftLeavesRec(TreeNode x, boolean isLeft, int sum) {
        if(x == null) {
            return;
        }
        
        if(x.left == null && x.right == null && isLeft) {
            sum += Integer.valueOf(x.val);
        }
        
        sumOfLeftLeavesRec(x.left, true, sum);
	// As debug model check, if just use static memeber variable sum could not
	// keep the value when return from deepest recursion, e.g when return from
	// node 8, the sum should be 8 and pass into new recursion on node 6(which
	// return from recursion of node 8), but real situation is sum will change
	// back to 0.
        sumOfLeftLeavesRec(x.right, false, sum);
    }
	
	public static void main(String[] args) {
	/*
	 * The tree used for test
	 *	       1
	 *		 /   \
	 *		2     3
	 *	   / \   /
	 *	  6   5 9
	 *	 /
	 *	8
	*/
	Solution s = new Solution();
	s.root = new TreeNode("1");
	s.root.left = new TreeNode("2");
	s.root.right = new TreeNode("3");
	s.root.left.left = new TreeNode("6");
	s.root.left.right = new TreeNode("5");
	s.root.left.left.left = new TreeNode("8");
	s.root.right.left = new TreeNode("9");
	
	int result = sumOfLeftLeaves(s.root);
	System.out.println(result);
}

	
// Two right ways
// http://stackoverflow.com/questions/40499420/why-static-member-variable-not-work-for-retain-value-in-recursive-method
// Solution 1: Use class member variable "sum" and side effect to record result, as sum is primitive type, should NOT
// pass into recursive method.
// Refer to http://stackoverflow.com/a/10265620/6706875
public class Solution {
    public TreeNode root;
	
    private static class TreeNode {
       private String val;
       private TreeNode left, right;
       public TreeNode(String x) {
	  this.val = x;
       }
    }
	
    public static int sum = 0;
    public static int sumOfLeftLeaves(TreeNode root) {
        if(root == null) {
            return 0;
        }
        
        sumOfLeftLeavesRec(root, false);
        return sum;
    }

    public static void sumOfLeftLeavesRec(TreeNode x, boolean isLeft) {
        if(x == null) {
            return;
        }
        
        if(x.left == null && x.right == null && isLeft) {
        	sum += Integer.valueOf(x.val);
        }
        
        sumOfLeftLeavesRec(x.left, true);
        sumOfLeftLeavesRec(x.right, false);
    }
    
    public static void main(String[] args) {
	/*
	 * The tree used for test
	 *	       1
	 *		 /   \
	 *		2     3
	 *	   / \   /
	 *	  6   5 9
	 *	 /
	 *	8
	*/
	Solution s = new Solution();
	s.root = new TreeNode("1");
	s.root.left = new TreeNode("2");
	s.root.right = new TreeNode("3");
	s.root.left.left = new TreeNode("6");
	s.root.left.right = new TreeNode("5");
	s.root.left.left.left = new TreeNode("8");
	s.root.right.left = new TreeNode("9");
	
	int result = sumOfLeftLeaves(s.root);
	System.out.println(result); 
    }
}

// Solution 2: Create a new class Accumulator and pass an instance of this type as parameter into recursive method
// to record.
// Refer to http://www.geeksforgeeks.org/find-sum-left-leaves-given-binary-tree/
// and http://stackoverflow.com/a/10265620/6706875
public class Solution {
    public TreeNode root;
	
    private static class TreeNode {
       private String val;
       private TreeNode left, right;
       public TreeNode(String x) {
	  this.val = x;
       }
    }

    private static class Accumulator {
       int sum = 0;
    }
    
    public static int sumOfLeftLeaves(TreeNode root) {
        if(root == null) {
            return 0;
        }
        
	Accumulator accumulator = new Accumulator();
	    
        sumOfLeftLeavesRec(root, false, accumulator);
        return sum;
    }

    public static void sumOfLeftLeavesRec(TreeNode x, boolean isLeft, Accumulator accumulator) {
        if(x == null) {
            return;
        }
        
        if(x.left == null && x.right == null && isLeft) {
        	accumulator.sum += Integer.valueOf(x.val);
        }
        
        sumOfLeftLeavesRec(x.left, true, accumulator);
        sumOfLeftLeavesRec(x.right, false, accumulator);
    }
    
    public static void main(String[] args) {
	/*
	 * The tree used for test
	 *	       1
	 *		 /   \
	 *		2     3
	 *	   / \   /
	 *	  6   5 9
	 *	 /
	 *	8
	*/
	Solution s = new Solution();
	s.root = new TreeNode("1");
	s.root.left = new TreeNode("2");
	s.root.right = new TreeNode("3");
	s.root.left.left = new TreeNode("6");
	s.root.left.right = new TreeNode("5");
	s.root.left.left.left = new TreeNode("8");
	s.root.right.left = new TreeNode("9");
	
	int result = sumOfLeftLeaves(s.root);
	System.out.println(result); 
    }
}

	
// Solution 3: The best way which match the recursive design rule
// Rule refer to http://web.mit.edu/6.005/www/fa15/classes/10-recursion/
// Implement refer to https://my.oschina.net/styshoo/blog/757251
// In Solution 1: (1)The static member variable is not a good idea (2)In an ideal recursive implementation, all variables are final, 
// all data is immutable, and the recursive methods are all pure functions in the sense that they do not mutate anything. 
// The behavior of a method can be understood simply as a relationship between its parameters and its return value, with no side 
// effects on any other part of the program. (3)Design including two major part, one is base case, another is recursive step
public class Solution {
    public TreeNode root;

    private static class TreeNode {
       private String val;
       private TreeNode left, right;
       public TreeNode(String x) {
	  this.val = x;
       }
     }
    
    public static int sumOfLeftLeaves(TreeNode root) {
        if(root == null) {
            return 0;
        }

        return sumOfLeftLeavesRec(root, false);
    }
    
    public static int sumOfLeftLeavesRec(TreeNode x, boolean isLeft) {
        if(x == null) {
            return 0;
        }
	    
        int sum = 0;
        // Base Case
        if(x.left == null && x.right == null && isLeft) {
        	sum += Integer.valueOf(x.val);
        } else {
            // Recursive Steps
            sum += sumOfLeftLeavesRec(x.left, true);
            sum += sumOfLeftLeavesRec(x.right, false);
        }
        
        return sum;
    }
    
    public static void main(String[] args) {
	/*
	 * The tree used for test
	 *	       1
	 *		 /   \
	 *		2     3
	 *	   / \   /
	 *	  6   5 9
	 *	 /
	 *	8
	*/
	Solution s = new Solution();
	s.root = new TreeNode("1");
	s.root.left = new TreeNode("2");
	s.root.right = new TreeNode("3");
	s.root.left.left = new TreeNode("6");
	s.root.left.right = new TreeNode("5");
	s.root.left.left.left = new TreeNode("8");
	s.root.right.left = new TreeNode("9");
	
	int result = sumOfLeftLeaves(s.root);
	System.out.println(result);
    }
}

	
	
