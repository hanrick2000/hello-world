/**
 * Refer to
 * http://www.cnblogs.com/grandyang/p/5209621.html
 * Implement an iterator to flatten a 2d vector.

    For example,
    Given 2d vector =

    [
      [1,2],
      [3],
      [4,5,6]
    ]


    By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,2,3,4,5,6].

    Hint:

    How many variables do you need to keep track?
    Two variables is all you need. Try with x and y.
    Beware of empty rows. It could be the first few rows.
    To write correct code, think about the invariant to maintain. What is it?
    The invariant is x and y must always point to a valid point in the 2d vector. Should you maintain your invariant ahead of 
    time or right when you need it?
    Not sure? Think about how you would implement hasNext(). Which is more complex?
    Common logic in two different places should be refactored into a common method.
    Follow up:
    As an added challenge, try to code it using only iterators in C++ or iterators in Java.
    
 *
 * Solution
 * https://discuss.leetcode.com/topic/30277/java-solution-beats-60-10
 * https://www.youtube.com/watch?v=Hwk1CD86YmA
 * http://www.cnblogs.com/grandyang/p/5209621.html
*/
import java.util.List;

public class Flatten2DVector {
    private int listIndex;
	private int elementIndex;
	// For later use of given 'vec2d', need copy
	// to a new global reference
	private List<List<Integer>> list;
	public Flatten2DVector(List<List<Integer>> vec2d) {
		this.listIndex = 0;
		this.elementIndex = 0;
		this.list = vec2d;
	}
	
	
	public Integer next() {
		return list.get(listIndex).get(elementIndex++);
	}
	
	public boolean hasNext() {
		while(listIndex < list.size()) {
			if(elementIndex < list.get(listIndex).size()) {
				return true;
			} else {
				listIndex++;
				elementIndex = 0;
			}
		}
		return false;
	}
}
