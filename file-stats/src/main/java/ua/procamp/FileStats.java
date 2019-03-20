package ua.procamp;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * {@link FileStats} provides an API that allow to get character statistic based on text file. All whitespace characters
 * are ignored.
 */
public class FileStats {
    private Map<Character, Long> charMap;

    /**
     * Creates a new immutable {@link FileStats} objects using data from text file received as a parameter.
     *
     * @param fileName input text file name
     * @return new FileStats object created from text file
     */
    public static FileStats from(String fileName) {
        return new FileStats(fileName);
    }

    private FileStats(String fileName) {
        initCharMap(fileName);
    }

    private void initCharMap(String fileName) {
        Path path = filePath(fileName);
        try {
            Stream<String> lines = Files.lines(path);
            charMap = lines
                    .flatMapToInt(String::chars)
                    .filter(ch -> ch != ' ')
                    .mapToObj(ch -> (char) ch)
                    .collect(groupingBy(x -> x, counting()));
        } catch (IOException e) {
            throw new FileStatsException("Exception occurred during reading");
        }
    }

    private Path filePath(String fileName) {
        Objects.requireNonNull(fileName);
        URL resource = this.getClass().getClassLoader().getResource(fileName);
        if (resource == null) {
            String message = String.format("File %s doesn't exist", fileName);
            throw new FileStatsException(message);
        }

        try {
            return Paths.get(resource.toURI());
        } catch (URISyntaxException e) {
            throw new FileStatsException("Can't resolve passed fileName parameter", e);
        }
    }

    /**
     * Returns a number of occurrences of the particular character.
     *
     * @param character a specific character
     * @return a number that shows how many times this character appeared in a text file
     */
    public int getCharCount(char character) {
        return charMap.get(character).intValue();
    }

    /**
     * Returns a character that appeared most often in the text.
     *
     * @return the most frequently appeared character
     */
    public char getMostPopularCharacter() {
        return charMap.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElseThrow();
    }

    /**
     * Returns {@code true} if this character has appeared in the text, and {@code false} otherwise
     *
     * @param character a specific character to check
     * @return {@code true} if this character has appeared in the text, and {@code false} otherwise
     */
    public boolean containsCharacter(char character) {
        return charMap.containsKey(character);
    }
}
