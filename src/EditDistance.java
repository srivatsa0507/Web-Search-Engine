
public class EditDistance {
	public static int editDistance(String firstString, String secondString) {
        int length1 = firstString.length();
        int length2 = secondString.length();

        int[][] array = new int[length1 + 1][length2 + 1];

        for (int i = 0; i <= length1; i++) {
            array[i][0] = i;
        }

        for (int j = 0; j <= length2; j++) {
            array[0][j] = j;
        }

        // Checking last char while iterating
        for (int i = 0; i < length1; i++) {
            char c1 = firstString.charAt(i);
            for (int j = 0; j < length2; j++) {
                char c2 = secondString.charAt(j);

                if (c1 == c2) {
                    array[i + 1][j + 1] = array[i][j];
                } else {
                    int replace = array[i][j] + 1;
                    int insert = array[i][j + 1] + 1;
                    int delete = array[i + 1][j] + 1;

                    int minimum = replace > insert ? insert : replace;
                    minimum = delete > minimum ? minimum : delete;
                    array[i + 1][j + 1] = minimum;
                }
            }
        }
        return array[length1][length2];
    }
}
