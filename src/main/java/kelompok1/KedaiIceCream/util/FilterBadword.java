package kelompok1.KedaiIceCream.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class FilterBadword {
    private static final Set<String> badWords = new HashSet<>();

    static {
        try {
            loadBadWordsFromFile("./badwords.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadBadWordsFromFile(String filename) throws IOException {
        InputStream inputStream = FilterBadword.class.getResourceAsStream(filename);
        if (inputStream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                badWords.add(line.trim().toLowerCase());
            }
            reader.close();
        } else {
            throw new IOException("Bad words file not found: " + filename);
        }
    }

    public static boolean isBadWord(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        String[] words = input.toLowerCase().split("\\W+");
        for (String word : words) {
            if (badWords.contains(word)) {
                return true;
            }
        }

        return false;
    }
}