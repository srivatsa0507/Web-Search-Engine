

import java.io.File;

public class Settings {

    public static final String HTML_PATH = "Files"+ File.separator+ "htmlFiles"; // Path for HTML file
    public static final String TEXT_PATH = "Files"+ File.separator+ "TextFiles"; // Path for TXT file
    public static final String  TAG = "1.0.0"; // Tag for search engine
    public static final Integer MAX_DEPTH = 2; // Maximum depth till depth is allowed
    public static final String CHARSET_NAME = "UTF-8"; // Vital for BoyerMoore operation else the search will not work  
    public static final String DELIMITER = "::"; // Delimiter to save document in the TXT file
    public static final Integer RadixSize = 255; // ASCII format is used in BoyerMoore search
    public static final Integer NumberOfSearch = 5; // Top 5 search results
    public static final Integer AltWordDistance = 3;  // For edit distance algorithm
}