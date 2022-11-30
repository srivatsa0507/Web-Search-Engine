
import java.util.Scanner;
import java.util.stream.Collectors;



import java.util.ArrayList;
import java.util.Hashtable;
import java.io.File;


public class Application {

	/*
	 * Using Hash Table to maintain the occurrences of the word in the url
	 */
	private static Hashtable<String, Integer> frequencyListOfWord = new Hashtable<String, Integer>();

	public static void main(String[] args) {

		resetFolderDetail();
		System.out.println("#################\nSearch Engine v" + Settings.TAG.toString() + "\n#################");

		try (Scanner sc = new Scanner(System.in);) {

			while (true) {
				System.out.print("Please enter a url : ");
				String url = sc.next();
				if (!url.contains("http"))
					url = "https://" + url;
				System.out.println(
						"===============================\nStarting crawler....\n===============================");
				boolean isCrawledDone = WebCrawler.startCrawler(url, 0);

				if (isCrawledDone)
					break;

				System.err.println("#################\nEntered URL is Invalid. Please Try Again!\n#################");
			}

			while (true) {
				System.out.println("##################################");
				System.out.println("Enter \"Quit\" to exit or Enter a word to search : ");
				/*
				 * Taking input from user to enter a word
				 */
				String word = sc.next().toLowerCase();
				if (word.equalsIgnoreCase("quit")) {
					System.out.println("SUCCESSFULLY TERMINATED");
					break;
				}

				/*
				 * Removing all instances of the word data" from the previous search
				 */
				frequencyListOfWord.clear();
				int totalNumberOfFiles = 0;
				for (File file : new File(Settings.TEXT_PATH).listFiles()) {
					if (isWordFound(file, word))
						totalNumberOfFiles++;
				}
				System.out.println(" Word: \"" + word + "\" found in " + totalNumberOfFiles + " files\n");
				if (totalNumberOfFiles == 0) {
					if (SearchWord.suggestAltWord(word))
						System.out.println("Entered word can't be resolved....");
				} else {
					SearchWord.rankFiles(frequencyListOfWord);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
    
    public static void resetFolderDetail() {
        File file = new File(Settings.HTML_PATH.toString());
        System.out.println("HTML PATH : " + file.getAbsolutePath());
        file.mkdirs();
        for (File fil: file.listFiles())
            fil.delete();
        
        file = new File(Settings.TEXT_PATH.toString());
        file.mkdirs();
        for (File fil: file.listFiles())
            fil.delete();
    }
    
    public static boolean isWordFound(File file, String word) {
        try (Scanner scanner = new Scanner(file, Settings.CHARSET_NAME);) {
            scanner.useDelimiter("\\Z");
            if (scanner.hasNext()) {
	            String[] array = scanner.next().toLowerCase().split(Settings.DELIMITER);
	            if (array.length > 1) {
	            	String url = array[0];
	                int wordFrequency = BoyerMoore.searchCount(array[1], word);
	                if (wordFrequency > 0) {
	                    System.out.printf("[%d] url: %s count:%d\n", frequencyListOfWord.size(), url, wordFrequency);
	                    frequencyListOfWord.put(url, wordFrequency);
	                    return true;
	                }
	            }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

  }
