class Solution {
    public int missingInteger(int[] nums) {
        int sum = nums[0];

        // Find sum of longest sequential prefix
        int i = 1;
        while (i < nums.length && nums[i] == nums[i - 1] + 1) {
            sum += nums[i];
            i++;
        }

        // Find the smallest missing integer >= sum
        int x = sum;

        while (contains(nums, x)) {
            x++;
        }

        return x;
    }

    private boolean contains(int[] nums, int x) {
        for (int num : nums) {
            if (num == x) {
                return true;
            }
        }
        return false;
    }
}

// CPP Code 
class Solution {
public:
    int missingInteger(vector<int>& nums) {
        int prefix_sum = nums[0];
        
        for (size_t i = 1; i < nums.size(); ++i) {
            if (nums[i] != nums[i - 1] + 1) {
                break;
            }
            prefix_sum += nums[i];
        }
        
        unordered_set<int> presence_map(nums.begin(), nums.end());
        
        while (presence_map.count(prefix_sum)) {
            prefix_sum++;
        }
        
        return prefix_sum;
    }
};
