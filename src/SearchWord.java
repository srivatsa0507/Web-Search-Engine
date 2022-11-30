

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchWord {
	private static HashMap<String, Integer> numbers = new HashMap<String, Integer>(); // Alternative words and their distance
    private static String regex = "[a-z0-9]+";
    private static Pattern pattern = Pattern.compile(regex);
    private static Matcher matcher = pattern.matcher(" ");

    // To rank the pages using Merge-sort
    public static void rankFiles(Hashtable<String, Integer> files) {

        // Passing list and sorting it
        ArrayList<Map.Entry<String, Integer>> listInFile = new ArrayList<>(files.entrySet());

        Collections.sort(listInFile, new Comparator<Map.Entry<String, Integer>>() {

            public int compare(Map.Entry<String, Integer> object1, Map.Entry<String, Integer> object2) {
                return object1.getValue().compareTo(object2.getValue());
            }
        });

        Collections.reverse(listInFile);

        System.out.println("-------------------------");
        System.out.println("Top 5 search results");
        System.out.println("-------------------------");
        for ( int j = 0; listInFile.size() > j &&  j < Settings.NumberOfSearch;  j++) {
            if(listInFile.get(j).getKey()!=null) 
                System.out.printf("[%d] %s\n", j, listInFile.get(j).getKey());
        } 
    }

    // Suggesting the similar words to the user
    public static boolean suggestAltWord(String wordToSearch) {


        for (File f : new File(Settings.TEXT_PATH).listFiles()) 
                findWord(f, wordToSearch);

        int i = 0;
        for (Map.Entry entry : numbers.entrySet()) {
            if (Settings.AltWordDistance > (Integer)entry.getValue()) {
                i++;
                if (i==1)
                	 System.out.println("Did you mean? ");         	
                else if (i >= Settings.NumberOfSearch)
                	break;
                System.out.printf("[%d] %s\n", i,  entry.getKey());
            }
        }
        return i != 0;

    }

    // Finding strings with similar pattern and calling edit distance() functions for those strings
    public static void findWord(File sourceFile, String str) {
        try(
    		FileReader f = new FileReader(sourceFile);
    		BufferedReader readerObject = new BufferedReader(f);
		) {
         
            for (String line = readerObject.readLine(); line != null; line = readerObject.readLine()) {
                matcher.reset(line.toLowerCase());
                while (matcher.find()) {
                	String word = matcher.group().toLowerCase();
                    numbers.put(word, EditDistance.editDistance(str, word));
                }
            }

   
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }

    }
    
}
