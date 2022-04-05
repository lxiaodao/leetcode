package twoarray.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

public class Solution {
	
	final int INF = 2_000_000_000;
    int[] ns;
    int n;
  
    ArrayList<TreeSet<Integer>> ls=new ArrayList<TreeSet<Integer>>();
    int ans;
    //深度优先，穷举n个（n为所有数字的一半）数字形成两个数组的情况，记录两个数组的差值
    //从小到大排序后
    //对左边的数字进行遍历分组，穷举所有可能出现的分组情况
    //ldfs减法，相当于减去另一个组的一个数字；加法递归，就是求同一个分组里面的数字
    private void ldfs(int idx, int sum, int cnt) {
        if (idx >= n) {
            ls.get(cnt).add(sum);
            return;
        }
        ldfs(idx + 1, sum - ns[idx], cnt);
        ldfs(idx + 1, sum + ns[idx], cnt + 1);
    }

    private void rdfs(int idx, int sum, int cnt) {
        if (idx >= 2 * n) {
            Integer x1 = ls.get(n - cnt).floor(-sum);
            Integer x2 = ls.get(n - cnt).ceiling(-sum);
            if (x1 != null) {
                ans = Math.min(ans, Math.abs(sum + x1));
            }
            if (x2 != null) {
                ans = Math.min(ans, Math.abs(sum + x2));
            }
            return;
        }
        rdfs(idx + 1, sum - ns[idx], cnt);
        rdfs(idx + 1, sum + ns[idx], cnt + 1);
    }

    public int minimumDifference(int[] _ns) {
        ns = _ns;
        n = ns.length / 2;
        ans = INF;
        ls = new ArrayList<>();
        for (int i = 0; i < n + 1; i++) {
            ls.add(new TreeSet<>());
        }
        Arrays.sort(ns); // 加速TreeSet内部排序
       
        System.out.println(Arrays.toString(ns));
        ldfs(0, 0, 0);
        rdfs(n, 0, 0);
        return ans;
    }



	public static void main(String[] args) {
		//int[] nums = {3,9,7,3};
		int[] nums=  {2,-1,0,4,-2,-9};
		System.out.println(new Solution().minimumDifference(nums));

	}

}
