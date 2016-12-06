/**
 * You are given a map in form of a two-dimensional integer grid where 1 represents land and 0 represents water. 
 * Grid cells are connected horizontally/vertically (not diagonally). The grid is completely surrounded by water, 
 * and there is exactly one island (i.e., one or more connected land cells). The island doesn't have "lakes" 
 * (water inside that isn't connected to the water around the island). One cell is a square with side length 1. 
 * The grid is rectangular, width and height don't exceed 100. Determine the perimeter of the island.
 * 
 * [[0,1,0,0],
    [1,1,1,0],
    [0,1,0,0],
    [1,1,0,0]]
 * Answer: 16
 * Explanation: The perimeter is the 16 yellow stripes in the image below:
 * 
 * Refer to
 * http://www.cnblogs.com/grandyang/p/6096138.html
 * 这道题给了我们一个格子图，若干连在一起的格子形成了一个小岛，规定了图中只有一个相连的岛，且岛中没有湖，
 * 让我们求岛的周长。我们知道一个格子有四条边，但是当两个格子相邻，周围为6，若某个格子四周都有格子，那么
 * 这个格子一条边都不算在周长里。那么我们怎么统计出岛的周长呢？第一种方法，我们对于每个格子的四条边分别来处理，
 * 首先看左边的边，只有当左边的边处于第一个位置或者当前格子的左面没有岛格子的时候，左边的边计入周长。其他三
 * 条边的分析情况都跟左边的边相似
*/
