/**
 * @param {Object|Array} obj
 * @return {Object|Array}
 */
var compactObject = function(obj) {

    // Primitive value
    if (obj === null || typeof obj !== "object") {
        return obj;
    }

    // Array
    if (Array.isArray(obj)) {
        const res = [];

        for (let item of obj) {
            const compacted = compactObject(item);

            if (Boolean(compacted)) {
                res.push(compacted);
            }
        }

        return res;
    }

    // Object
    const res = {};

    for (let key in obj) {
        const compacted = compactObject(obj[key]);

        if (Boolean(compacted)) {
            res[key] = compacted;
        }
    }

    return res;
};