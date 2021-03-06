/**
 * Refer to
 * https://segmentfault.com/a/1190000003874375
 * Given an array of numbers, verify whether it is the correct preorder traversal sequence of 
   a binary search tree.
   You may assume each number in the sequence is unique.
   Follow up: Could you do it using only constant space complexity?
 *
 * Solution
 * https://www.youtube.com/watch?v=_k944o5yilQ
 * https://segmentfault.com/a/1190000003874375
   二叉搜索树先序遍历序列的特点是降序的部分一定是向左走的，一旦开始升序说明开始向右走了，则上一个降序的
   点则限定了后面的数的最小值。如果继续降序，说明又向左走了，这样等到下次向右走得时候也要再次更新最小值。

       10
      /  \
     5    12
    / \
   2   6
   
   如这个例子，我们在10的位置是没有最小值限定的，然后降序走到5，依然没有最小值，降序走到2，依然没有，然后
   开始升序了，遇到6，这时候之后的数字一定大于2，同时也大于5，所以最小值更新为之前遍历过的，且比当前数稍
   微小一点的那个数(e.g 5)。这里我们可以用一个栈来暂存之前的路径，所以升序时就是将栈中元素不断pop出来直到栈顶
   大于当前数，而最小值就是最后一个pop出来的数，最后再把该数push进去。对于降序的时候，直接向里面push就行了。
   这样，序列无效的条件就是违反了这个最小值的限定
*/

// For this problem just remember is fine, no need too much understanding
public class VerifyPreorderSequenceInBinarySearchTree {
    private class TreeNode {
		TreeNode left;
		TreeNode right;
		int val;
		public TreeNode(int x) {
			this.val = x;
		}
	}
	
	// Create preorder traverse for given tree
	public List<Integer> preorderTraverse(TreeNode root) {
		List<Integer> result = new ArrayList<Integer>();
		helper(result, root);
		return result;
	}
	
	private void helper(List<Integer> result, TreeNode node) {
	    if(node == null) {
	    	return;
	    }
	    result.add(node.val);
	    helper(result, node.left);
	    helper(result, node.right);
	}
	
	// preorder = 10, 5, 2, 6, 12
        public boolean verifyPreorder(int[] preorder) {
	    Stack<Integer> stk = new Stack<Integer>();
	    // 初始化最小值为最小整数
	    int min = Integer.MIN_VALUE;
	    for(int num : preorder){
                // 违反最小值限定则是无效的
		if(num < min) return false;
		// 将路径中所有小于当前的数pop出来并更新最小值
		while(!stk.isEmpty() && num > stk.peek()){
	            min = stk.pop();
		}
		// 将当前值push进去
		stk.push(num);
            }
	    return true;
	}
	
	public static void main(String[] args) {
	    VerifyPreorderSequenceInBinarySearchTree v = new VerifyPreorderSequenceInBinarySearchTree();
	    /**
		10
	       /  \
	      5    12
	     / \
	    2   6
		 */
		TreeNode ten = v.new TreeNode(10);
		TreeNode five = v.new TreeNode(5);
		TreeNode twelve = v.new TreeNode(12);
		TreeNode two = v.new TreeNode(2);
		TreeNode six = v.new TreeNode(6);
		ten.left = five;
		ten.right = twelve;
		five.left = two;
		five.right = six;
		
		// Create preorder traverse for given tree as [10, 5, 2, 6, 12] 
		// and suppose result will be true
		List<Integer> list = v.preorderTraverse(ten);
		int[] preorder = new int[list.size()];
		for(int i = 0; i < list.size(); i++) {
			preorder[i] = list.get(i);
			System.out.print(preorder[i] + " ");
		}
		boolean result = v.verifyPreorder(preorder);
		System.out.println(result);
	}
}
