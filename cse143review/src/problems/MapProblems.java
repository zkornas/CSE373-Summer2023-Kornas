package problems;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * See the spec on the website for example behavior.
 */
public class MapProblems {

    /**
     * Returns true if any string appears at least 3 times in the given list; false otherwise.
     */
    public static boolean contains3(List<String> list) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String i: list) {
            if (!map.containsKey(i)) {
                map.put(i, 1);
            } else {
                map.put(i, map.get(i) + 1);
            }
        }
        for (Map.Entry<String, Integer> element : map.entrySet()) {
            if (element.getValue() >= 3) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a map containing the intersection of the two input maps.
     * A key-value pair exists in the output iff the same key-value pair exists in both input maps.
     */
    public static Map<String, Integer> intersect(Map<String, Integer> m1, Map<String, Integer> m2) {
        Map<String, Integer> intersectMap = new HashMap<String, Integer>();
        for (Map.Entry<String, Integer> i : m1.entrySet()) {
            for (Map.Entry<String, Integer> j : m2.entrySet()) {
                if (i.equals(j)) {
                    intersectMap.put(i.getKey(), i.getValue());
                }
            }
        }
        return intersectMap;
    }
}
