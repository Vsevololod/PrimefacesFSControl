package today.vse;


import java.io.Serializable;
import java.nio.file.Path;

public class Document implements Serializable, Comparable<Document> {

    private Path path;

    private String name;

    Document(Path path) {
        this.path = path;
        this.name = path.getFileName().toString();
    }

    public String getName() {
        return name;
    }

    Path getPath() {
        return path;
    }

    @Override
    public String toString() {
        return name;
    }

    public int compareTo(Document document) {
        return this.getPath().compareTo(document.getPath());
    }
}
