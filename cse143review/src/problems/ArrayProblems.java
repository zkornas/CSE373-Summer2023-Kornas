package problems;

/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - Do not add any additional imports
 * - Do not create new `int[]` objects for `toString` or `rotateRight`
 */
public class ArrayProblems {

    /**
     * Returns a `String` representation of the input array.
     * Always starts with '[' and ends with ']'; elements are separated by ',' and a space.
     */
    public static String toString(int[] array) {
        String arrayString = "[";
        if (array.length == 0) {
            return "[]";
        }
        for (int i = 0; i <= array.length-1; i++) {
            arrayString = arrayString + array[i];
            if (i == array.length - 1) {
                arrayString = arrayString +"]";
            } else {
                arrayString = arrayString + ", ";
            }
        }
        return arrayString;
    }

    /**
     * Returns a new array containing the input array's elements in reversed order.
     * Does not modify the input array.
     */
    public static int[] reverse(int[] array) {
        int[] newArray = new int[array.length];
        int count = 0;
        for (int i = array.length - 1; i >= 0; i--) {
            newArray[count] = array[i];
            count++;
        }
        return newArray;
    }

    /**
     * Rotates the values in the array to the right.
     */
    public static void rotateRight(int[] array) {
        if (array.length > 1) {
            int lastPlace = array[array.length - 1];
            for (int i = array.length - 1; i >= 0; i--) {
                if (i == 0) {
                    array[i] = lastPlace;
                } else {
                    array[i] = array[i - 1];
                }
            }
        }
    }
}
