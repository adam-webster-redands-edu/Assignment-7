import java.io.*;
import java.util.*;

class KeywordHashTable {
    private String[] table;
    private int size;
    
    public KeywordHashTable(int size) {
        this.size = size;
        table = new String[size];
    }

    public void insert(String word) {
        int index = hash(word);
        table[index] = word;
    }

    public boolean contains(String word) {
        int index = hash(word);
        return table[index] != null && table[index].equals(word);
    }

    private int hash(String word) {
        int g = 31;
        return (word.length() + 26 * (word.charAt(0)) + g * (word.charAt(word.length() - 1))) % size;
    }
}

class Timer {
    private long startTime;
    private long endTime;

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void stop() {
        endTime = System.currentTimeMillis();
    }

    public long getTime() {
        return endTime - startTime;
    }
}

public class KeywordSearch {
    public static void main(String[] args) {
        try {
            // Step 1: Read keywords from file and build hash table
            Scanner scanner = new Scanner(new File("keywords.txt"));
            int size = 0;
            List<String> keywords = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                keywords.add(word);
                size++;
            }

            KeywordHashTable hashTable = new KeywordHashTable(size);
            for (String keyword : keywords) {
                hashTable.insert(keyword);
            }

            // Step 2: Read source code file and search for keywords
            BufferedReader reader = new BufferedReader(new FileReader("source_code.java"));
            String line;
            int lines = 0;
            int keywordsFound = 0;

            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty() && !line.trim().startsWith("//")) {
                    String[] words = line.split("\\s+");
                    for (String word : words) {
                        if (hashTable.contains(word)) {
                            keywordsFound++;
                        }
                    }
                }
                lines++;
            }

            // Step 3: Record statistics
            Timer timer = new Timer();
            timer.start();
            timer.stop();
            long time = timer.getTime();

            // Step 4: Write statistics to file
            PrintWriter writer = new PrintWriter("statistics.txt");
            writer.println("Lines: " + lines);
            writer.println("Keywords found: " + keywordsFound);
            writer.println("Time (ms): " + time);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
