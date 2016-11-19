/**
 * Given two strings s and t which consist of only lowercase letters.
 * String t is generated by random shuffling string s and then add one more letter at a random position.
 * Find the letter that was added in t.
 * Example:
 * Input:
 * s = "abcd"
 * t = "abcde"
 * Output:
 * e
 * Explanation:
 * 'e' is the letter that was added.
*/

// Wrong Solution
// If input as s = "a", t = "aa", expected output is 'a', but this method will return null
// the tricky part is condition to judge when same char but different digits.
public class Solution {
    public char findTheDifference(String s, String t) {
        char[] a = s.toCharArray();
        char[] b = t.toCharArray();

        char result = '\0';
        
        for(int i = 0; i < b.length; i++) {
            char x = b[i];
            boolean notExist = true;
            int j;
            for(j = 0; j < a.length; j++) {
                char y = a[j];
                // This condition cause the error, only consideration on x and y are different
                // chars, but even if x, y are same char, because of different digits issue,
                // the additional char will also happen.
                if(x == y) {
                    notExist = false;
                    break;
                }
            }
            
            if(notExist) {
                result = b[i];
            } 
        }
        
        return result;
    }
}

// Solution 1: 这种解法比较直接，建立以个map对应字符串s，key为字符，value就是该字符出现的次数。首先遍历字符串s，
// 来建立这个map，然后再遍历字符串t。对t中出现的每个字符，都从map中减一，当最后剩下一个value值为1时，则说明该
// 字符就是多出的字符
// Refer to 
// https://my.oschina.net/styshoo/blog/752872
public class Solution {
    public char findTheDifference(String s, String t) {
        // To initial a char is different than normal primary type
        // Refer to 
        // http://stackoverflow.com/questions/5859934/char-initial-value-in-java
        char result = '\0';
        
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        char[] a = s.toCharArray();
        char[] b = t.toCharArray();
        
        for(int i = 0; i < b.length; i++) {
            if(!map.containsKey(b[i])) {
                map.put(b[i], 1);
            } else {
                map.put(b[i], map.get(b[i]) + 1);
            }
        }
        
        for(int j = 0; j < a.length; j++) {
            if(map.containsKey(a[j]) && map.get(a[j]) > 0) {
                map.put(a[j], map.get(a[j]) - 1);
            }
        }
        
        for(Character c : map.keySet()) {
            if(map.get(c) == 1) {
                result = c;
            }
        } 
        
        return result;
    }
}

// Solution 2: 第一种解法的map有点太重了，而且涉及到Character和char，int和Integer之间的转换, 
// 所以，我就用了另一种很类似的方法——int数组来代替map。因为字符串只有小写字母，也就是只有26中可能，
// 那么建立一个int[26]的数组即可，索引就是字符char-'a'，而数组值就是字符出现在字符串s中的次数。
// 说白了，和解法二思路一致。
// Refer to 
// https://my.oschina.net/styshoo/blog/752872
public class Solution {
    public char findTheDifference(String s, String t) {
        char result = '\0';
        int[] frequence = new int[26];
        
        char[] a = s.toCharArray();
        char[] b = t.toCharArray();
        
        for(int i = 0; i < b.length; i++) {
            int index = b[i] - 'a';
            frequence[index]++;
        }
        
        for(int j = 0; j < a.length; j++) {
            int index = a[j] - 'a';
            frequence[index]--;
        }
        
        for(int k = 0; k < frequence.length; k++) {
            if(frequence[k] == 1) {
                // The convert from int to char refer to
                // http://stackoverflow.com/questions/17984975/convert-int-to-char-in-java
                result = (char)('a' + k);
            }
        }
        
        return result;
    }
}

// Solution 3: 由于字符串t只比字符串s多了一个字符，那么直接用t中所有字符值的和减去字符串s中字符值的和即可。
// Refer to
// https://my.oschina.net/styshoo/blog/752872
public class Solution {
    public char findTheDifference(String s, String t) {
        char[] a = s.toCharArray();
        char[] b = t.toCharArray();
        
        int sum1 = 0;
        int sum2 = 0;
        
        for(int i = 0; i < a.length; i++) {
            sum1 += a[i]; 
        }
        
        for(int j = 0; j < b.length; j++) {
            sum2 += b[j];
        }
        
        return (char)(sum2 - sum1);
    }
}


// Solution 4: 另外一种思路，既然字符串t只比字符串s多了一个字符，也就是说大部分字符都是相同的。
// 那么，我们可以使用异或的方式，来找出这个不同的值。
// Refer to
// https://my.oschina.net/styshoo/blog/752872
public static char findTheDifference(String s, String t) {
    int ret = 0;
    // E.g 'a' = 97 = 0110 0001 'b' = 98 = 0110 0010
    // 'a' ^ 'b' = 0000 0011 = 3
    for(int i = 0; i < s.length(); i++) {
        ret ^= s.charAt(i);
    }
    for(int j = 0; j < t.length(); j++) {
        ret ^= t.charAt(j);
    }

    return (char)ret;
}
