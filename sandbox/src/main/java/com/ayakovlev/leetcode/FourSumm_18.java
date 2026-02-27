package com.ayakovlev.leetcode;

import java.util.*;

/**
 * 18. 4Sum
 * Solved
 * Medium
 * Topics
 * premium lock icon
 * Companies
 * Given an array nums of n integers, return an array of all the unique quadruplets [nums[a], nums[b], nums[c], nums[d]] such that:
 *
 * 0 <= a, b, c, d < n
 * a, b, c, and d are distinct.
 * nums[a] + nums[b] + nums[c] + nums[d] == target
 * You may return the answer in any order.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,0,-1,0,-2,2], target = 0
 * Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
 * Example 2:
 *
 * Input: nums = [2,2,2,2,2], target = 8
 * Output: [[2,2,2,2]]
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 200
 * -109 <= nums[i] <= 109
 * -109 <= target <= 109
 */
public class FourSumm_18 {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        if(nums == null || nums.length < 4){
            return new ArrayList<>(list);
        }
        long longTarget = (long)target;
        Arrays.sort(nums);
        int n = nums.length;
        for(int i = 0; i < n - 3; i++){
            // Делаем проверки, что при сдвиге i элемент массива изменится:
            if(i > 0 && nums[i] == nums[i-1]){
                continue; // skip duplicate i
            }
            // Early pruning: minimal possible sum too big?
            long minSumI = (long)nums[i] + nums[i+1] + nums[i+2] + nums[i+2];
            if(minSumI > longTarget) break;
            // Early pruning: maximal possible sum to small?
            long maxSumI = (long)nums[i] + nums[n-1] + nums[n-2] + nums[n-2];
            if(maxSumI < longTarget) continue;

            for(int j = i+1; j < n - 2; j++){
                // Делаем проверки, что при сдвиге j елемент массива изменится:
                if(j > i + 1 && nums[j] == nums[j-1]) {
                    continue; // skip duplicate j
                }
                long minSumJ = (long)nums[i] + nums[j] + nums[j+1] + nums[j+2];
                if(minSumJ > longTarget)break;
                long maxSumJ = (long)nums[i] + nums[j] + nums[n-2] +nums[n-1];
                if(maxSumJ < longTarget)continue;

                int left = j+1;
                int right = n-1;
                while(left < right){
                    long sum = (long)nums[i] + nums[j] + nums[left] + nums[right];
                    if(sum == longTarget ){
                        list.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        // skip duplicates on left
                        int leftVal = nums[left];
                        while(left < right && nums[left] == leftVal){
                            left++;
                        }
                        // skip duplicates on right
                        int rightVal = nums[right];
                        while(left < right && nums[right] == rightVal){
                            right--;
                        }
                    }else if(sum < longTarget){
                        left++;
                    }else{
                        right--;
                    }
                }
            }
        }
        return list;
    }

    public <T extends Number> void process(List<T> list, T value) {
        // Здесь T - параметр метода и можно работать с T:
        list.add(value);
        for(T t : list){
            System.out.println(t);
        }
    }
}
