package com.ayakovlev.leetcode;

import java.util.Arrays;

public class ThreeSumClosest_16 {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int sum = nums[0] + nums[1] + nums[2];
        int s;
        int left;
        int right;
        for(int i = 0; i < nums.length-2; i++){
            left = i + 1;
            right = nums.length - 1;
            while(left < right){
                s = nums[i] + nums[left] + nums[right];
                if(sum == target){
                    return sum;
                }else if(Math.abs(s-target) < Math.abs(sum-target)){
                    sum = s;
                }
                if(s < target){
                    left++;
                }else{
                    right--;
                }
            }
        }
        return sum;
    }
    public int threeSumClosest_complete_enumeration(int[] nums, int target) {
        Integer sum = null;
        for(int i = 0; i < nums.length; i++){
            for(int j = 0; j < i; j++){
                for(int k = 0; k < j; k++){
                    int s = nums[i] + nums[j] + nums[k];
                    if(sum == null || Math.abs(s-target) < Math.abs(sum-target)){
                        sum = s;
                    }
                }
            }
        }
        return sum;
    }
}
