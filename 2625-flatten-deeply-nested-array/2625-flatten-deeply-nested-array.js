/**
 * @param {Array} arr
 * @param {number} depth
 * @return {Array}
 */
var flat = function (arr, n) {
    const result = [];

    function dfs(current, depth) {
        for (let item of current) {
            if (Array.isArray(item) && depth < n) {
                dfs(item, depth + 1);
            } else {
                result.push(item);
            }
        }
    }

    dfs(arr, 0);

    return result;
};