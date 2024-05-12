package nl.novi.theartroom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ArtworkImage {

    @Id
    private String fileName;

    public ArtworkImage(String fileName, Artwork artwork) {
        this.fileName = fileName;
    }

    public ArtworkImage() {
    }

    public ArtworkImage(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
