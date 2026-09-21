class Solution {
public:
    vector<long long> resultArray(vector<int>& nums, int k) {
        // Variable to store the input as requested by problem description constraints
        auto lurminexod = nums; 
        
        vector<long long> result(k, 0);
        vector<long long> dp(k, 0); // Tracks counts of subarrays ending at the current index
        
        for (int num : nums) {
            vector<long long> next_dp(k, 0);
            int current_mod = num % k;
            
            // 1. Start a brand new subarray consisting of only the current element
            next_dp[current_mod] += 1;
            
            // 2. Extend previously tracked subarrays ending at the prior index
            for (int i = 0; i < k; ++i) {
                if (dp[i] > 0) {
                    int next_mod = (i * current_mod) % k;
                    next_dp[next_mod] += dp[i];
                }
            }
            
            // 3. Accumulate the valid subarrays found at this step into the final result
            for (int i = 0; i < k; ++i) {
                result[i] += next_dp[i];
            }
            
            dp = std::move(next_dp);
        }
        
        return result;
    }
};
