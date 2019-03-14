package ua.procamp;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * {@link FileReaders} privides an API that allow to read whole file into a {@link String} by file name.
 */
public class FileReaders {


    /**
     * Returns a {@link String} that contains whole text from the file specified by name.
     *
     * @param fileName a name of a text file
     * @return string that holds whole file content
     */
    public static String readWholeFile(String fileName) {
        URL url = FileReaders.class.getClassLoader().getResource(fileName);
        List<String> allLines = null;
        try {
            allLines = Files.readAllLines(getPath(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.join("\n", allLines);
    }

    private static Path getPath(URL url) {
        try {
            Path path = Paths.get(url.toURI());
            return path;
        } catch (URISyntaxException e) {
            throw new IllegalStateException();
        }
    }
}
